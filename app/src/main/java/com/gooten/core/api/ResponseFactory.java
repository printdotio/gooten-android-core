package com.gooten.core.api;

/**
 * Factory for deserializing API responses.
 *
 * @param <R> Type of the API call response
 * @author Vlado
 */
public interface ResponseFactory<R> {

    public R deserializeResponse(String response) throws Exception;

}
