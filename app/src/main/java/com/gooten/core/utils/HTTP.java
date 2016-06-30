package com.gooten.core.utils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;

import android.os.Bundle;

/**
 * Encapsulates logic for issuing HTTP requests.
 * 
 * @author Vlado
 */
public class HTTP {

   public static final String UTF_8 = "UTF-8";
   private static final int DEFAULT_HTTP_REQUEST_TIMEOUT = 15000;

   public static String createGetUrl(String url, Bundle params) throws UnsupportedEncodingException {
      if (params == null || params.isEmpty()) {
         return url;
      }
      StringBuilder sb = new StringBuilder();
      boolean isFirstParam = true;
      int paramStart = url.indexOf("?");
      if (paramStart == -1) {
         sb.append("?");
      } else {
         isFirstParam = (url.length() - paramStart - 1) == 0;
      }
      for (String paramName : params.keySet()) {
         if (isFirstParam) {
            isFirstParam = false;
         } else {
            sb.append("&");
         }
         sb.append(URLEncoder.encode(paramName, UTF_8));
         sb.append("=");
         sb.append(URLEncoder.encode(params.getString(paramName), UTF_8));
      }
      return url + sb.toString();
   }

   public static HTTPResult GET(String urlString) {
      return GET_inner(urlString, null);
   }

   public static HTTPResult GET(String urlString, Bundle headerProperties) {
      return GET_inner(urlString, headerProperties);
   }

   public static HTTPResult GET(String urlString, Bundle params, Bundle headerProperties) {
      try {
         urlString = createGetUrl(urlString, params);
      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
         return HTTPResult.newUnsuccessful();
      }
      return GET_inner(urlString, headerProperties);
   }

   private static HTTPResult GET_inner(String urlString, Bundle headerProperties) {
      boolean serverNotResponding = false;
      boolean networkSuccess = false;
      boolean success = false;
      int statusCode = 0;
      String responseString = null;

      HttpURLConnection httpConnection = null;
      try {
         URL url = new URL(urlString);
         httpConnection = (HttpURLConnection) url.openConnection();
         httpConnection.setRequestMethod("GET");
         if (headerProperties != null) {
            for (String paramName : headerProperties.keySet()) {
               httpConnection.setRequestProperty(paramName, headerProperties.getString(paramName));
            }
         }
         httpConnection.setConnectTimeout(DEFAULT_HTTP_REQUEST_TIMEOUT);
         httpConnection.setReadTimeout(DEFAULT_HTTP_REQUEST_TIMEOUT);
         httpConnection.setDoInput(true);

         statusCode = httpConnection.getResponseCode();
         success = (statusCode / 100 == 2);
         if (success) {
            InputStream inputStream = httpConnection.getInputStream();
            responseString = readStream(inputStream, UTF_8);
            if (inputStream != null) {
               inputStream.close();
            }
         }
         networkSuccess = true;
      } catch (MalformedURLException e) {
         success = false;
      } catch (SocketTimeoutException e) {
         success = false;
         networkSuccess = true;
         serverNotResponding = true;
      } catch (IOException e) {
         success = false;
      } finally {
         if (httpConnection != null) {
            httpConnection.disconnect();
         }
      }

      return new HTTPResult(networkSuccess, serverNotResponding, success, statusCode, responseString);
   }

   public static HTTPResult POST(String urlString) {
      return POST_inner(urlString, null, null);
   }

   public static HTTPResult POST(String urlString, Bundle params) {
      return POST(urlString, params, null);
   }

   public static HTTPResult POST(String urlString, Bundle params, Bundle headerProperties) {
      byte[] data = null;
      if (params != null) {
         try {
            data = createPostData(params).getBytes(UTF_8);
         } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return HTTPResult.newUnsuccessful();
         }
      }
      return POST_inner(urlString, data, headerProperties);
   }

   public static HTTPResult POST(String urlString, byte[] data, Bundle headerProperties) {
      return POST_inner(urlString, data, headerProperties);
   }

   private static HTTPResult POST_inner(String urlString, byte[] data, Bundle headerProperties) {
      boolean networkSuccess = false;
      boolean serverNotResponding = false;
      boolean success = false;
      int statusCode = 0;
      String responseString = null;

      HttpURLConnection httpConnection = null;
      try {
         URL url = new URL(urlString);
         httpConnection = (HttpURLConnection) url.openConnection();
         httpConnection.setRequestMethod("POST");
         if (headerProperties != null) {
            for (String paramName : headerProperties.keySet()) {
               httpConnection.setRequestProperty(paramName, headerProperties.getString(paramName));
            }
         }
         httpConnection.setConnectTimeout(DEFAULT_HTTP_REQUEST_TIMEOUT);
         httpConnection.setReadTimeout(DEFAULT_HTTP_REQUEST_TIMEOUT);
         httpConnection.setDoInput(true);
         httpConnection.setDoOutput(true);
         httpConnection.connect();

         if (data != null) {
            BufferedOutputStream outputStream = new BufferedOutputStream(httpConnection.getOutputStream());
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
         }

         statusCode = httpConnection.getResponseCode();
         success = (statusCode / 100 == 2);
         if (success) {
            InputStream inputStream = httpConnection.getInputStream();
            responseString = readStream(inputStream, UTF_8);
            if (inputStream != null) {
               inputStream.close();
            }
         }
         networkSuccess = true;
      } catch (MalformedURLException e) {
         success = false;
      } catch (SocketTimeoutException e) {
         success = false;
         networkSuccess = true;
         serverNotResponding = true;
      } catch (IOException e) {
         success = false;
      } finally {
         if (httpConnection != null) {
            httpConnection.disconnect();
         }
      }

      return new HTTPResult(networkSuccess, serverNotResponding, success, statusCode, responseString);
   }

   private static String createPostData(Bundle params) throws UnsupportedEncodingException {
      StringBuilder result = new StringBuilder();
      boolean isFirstParam = true;
      for (String paramName : params.keySet()) {
         if (isFirstParam) {
            isFirstParam = false;
         } else {
            result.append("&");
         }
         result.append(URLEncoder.encode(paramName, UTF_8));
         result.append("=");
         result.append(URLEncoder.encode(params.getString(paramName), UTF_8));
      }
      return result.toString();
   }

   private static String readStream(InputStream is, String charsetName) throws IOException {
      if (is == null)
         return null;
      StringBuilder result = new StringBuilder(1024);
      InputStreamReader in = new InputStreamReader(is, charsetName);
      int n = 0;
      char[] buffer = new char[1024 * 4];
      while (-1 != (n = in.read(buffer))) {
         result.append(buffer, 0, n);
      }
      return result.toString();
   }

   /**
    * Result of HTTP GET/POST request calls.
    * 
    * @author Vlado
    */
   public static class HTTPResult {

      public static HTTPResult newUnsuccessful() {
         return new HTTPResult(true, false, false, 0, null);
      }

      public final boolean networkSuccess;
      public final boolean serverNotResponding;
      public final boolean success;
      public final int statusCode;
      public final String responseString;

      public HTTPResult(boolean networkSuccess, boolean serverNotResponding, boolean success, int statusCode, String responseString) {
         this.networkSuccess = networkSuccess;
         this.serverNotResponding = serverNotResponding;
         this.success = success;
         this.statusCode = statusCode;
         this.responseString = responseString;
      }

      @Override
      public String toString() {
         return statusCode + " (" + (success ? "success" : "unsuccess") + ")" + "\n" + responseString;
      }

   }

}
