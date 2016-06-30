package com.gooten.core.api;

/**
 * Interface for API request callbacks.
 *
 * @param <R> Type of the API call response
 * @author Vlado
 */
public interface APIRequestCompleteListener<R> {

    void onRequestComplete(APIResultType resultType, R response);

}
