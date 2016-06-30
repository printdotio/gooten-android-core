package com.gooten.core.api;

import android.os.Bundle;

import com.gooten.core.api.cache.APICache;
import com.gooten.core.api.cache.APICacheEntryHolder;
import com.gooten.core.utils.HTTP;
import com.gooten.core.utils.HTTP.HTTPResult;
import com.gooten.core.utils.Utils;

/**
 * Encapsulates mechanism for issuing API requests.
 *
 * @param <R> Type of the API call response
 * @author Vlado
 */
public class APIRequest<R> {

    private static final APICache cache = new APICache();

    public static void clearCache() {
        cache.invalidate();
    }

    public static <T> APIRequest<T> createGET(long cacheValidity, String urlString, ResponseFactory<T> factory, APIRequestCompleteListener<T> onComplete) {
        return new APIRequest<T>(cacheValidity, true, urlString, null, null, null, factory, onComplete);
    }

    public static <T> APIRequest<T> createPOST(int cacheValidity, String urlString, byte[] data, Bundle headerProperties, ResponseFactory<T> factory, APIRequestCompleteListener<T> onComplete) {
        return new APIRequest<T>(cacheValidity, false, urlString, null, data, headerProperties, factory, onComplete);
    }

    private long cacheValidity;
    private boolean isGet;
    private String urlString;
    private Bundle params;
    private byte[] postData;
    private Bundle headerProperties;
    private Runnable requestTask;
    private ResponseFactory<R> factory;
    private Thread task;
    private APIRequestCompleteListener<R> completeListener;

    public APIRequest(long _cacheValidity, boolean _isGet, String _urlString, Bundle _params, byte[] _postData, Bundle _headerProperties, ResponseFactory<R> _factory, APIRequestCompleteListener<R> _completeListener) {
        this.cacheValidity = _cacheValidity;
        this.isGet = _isGet;
        this.urlString = _urlString;
        this.params = _params;
        this.completeListener = _completeListener;
        this.factory = _factory;
        this.headerProperties = _headerProperties;
        this.postData = _postData;

        requestTask = new Runnable() {

            @Override
            public void run() {
                String requestUrl = null;
                try {
                    requestUrl = HTTP.createGetUrl(urlString, params);
                } catch (Exception e) {
                    onRequestComplete(false, true, null);
                    return;
                }

                APICacheEntryHolder cacheHolder = null;
                if (cacheValidity > 0) {
                    cacheHolder = cache.getCache(cacheValidity, requestUrl);
                    if (cacheHolder.isHit()) {
                        endWithResponse(null, cacheHolder.get());
                        return;
                    }
                }

                HTTPResult result = null;
                if (isGet) {
                    result = HTTP.GET(requestUrl);
                } else {
                    if (postData == null) {
                        result = HTTP.POST(urlString, params, headerProperties);
                    } else {
                        result = HTTP.POST(urlString, postData, headerProperties);
                    }
                }

                if (factory != null) {
                    if (result.networkSuccess) {
                        endWithResponse(cacheHolder, result.responseString);
                    } else {
                        onRequestComplete(false, false, null);
                    }
                } else {
                    onRequestComplete(result.success, result.networkSuccess, null);
                }
            }
        };

    }

    private void endWithResponse(APICacheEntryHolder cacheHolder, String responseString) {
        R response = null;
        boolean success = true;
        try {
            response = factory.deserializeResponse(responseString);
            if (cacheHolder != null && cacheValidity > 0) {
                cacheHolder.put(responseString);
            }
        } catch (Exception e) {
            success = false;
            e.printStackTrace();
        }
        onRequestComplete(success, true, response);
    }

    private synchronized void onRequestComplete(boolean success, boolean networkSuccess, R response) {
        if (completeListener != null) {
            APIResultType resultType = success ? APIResultType.SUCCESS : APIResultType.FAILURE;
            if (!networkSuccess) {
                resultType = APIResultType.NETWORK_FAILURE;
            }
            completeListener.onRequestComplete(resultType, response);
        }
        notifyAll();
    }

    /**
     * Executes request asynchronously.
     */
    public synchronized void execute() {
        if (task != null) {
            task.interrupt();
            task = null;
        }
        task = Utils.execute(requestTask);
    }

    /**
     * Executes request synchronously. Method blocks until request has been processed by server.
     *
     * @return true if waiting for the request has been interrupted
     */
    public synchronized boolean executeAndWait() {
        execute();
        try {
            wait();
        } catch (InterruptedException e) {
            return true;
        }
        return false;
    }

    /**
     * Executes request synchronously. Method blocks until request has been processed by server
     * or until waiting has timed out.
     *
     * @param millis Time im milliseconds to wait for response
     * @return true if waiting for the request has been interrupted
     */
    public synchronized boolean executeAndWait(long millis) {
        execute();
        try {
            wait(millis);
        } catch (InterruptedException e) {
            return true;
        }
        return false;
    }

    /**
     * Aborts current request.
     */
    public synchronized void abort() {
        if (task != null) {
            task.interrupt();
            task = null;
        }
    }

}
