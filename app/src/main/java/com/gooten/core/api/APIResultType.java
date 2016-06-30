package com.gooten.core.api;

/**
 * Enumeration of possible API outcomes.
 */
public enum APIResultType {

    /**  API request has processed successfully. */
    SUCCESS,

    /**  Failed to process API request. */
    FAILURE,

    /**  Failed stablishing connection with server. */
    NETWORK_FAILURE;
}
