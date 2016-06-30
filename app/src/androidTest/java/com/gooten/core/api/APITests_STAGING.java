package com.gooten.core.api;

import com.gooten.core.types.Environment;

/**
 * Executes {@code APITests} for STAGING environment.
 *
 * @author Vlado
 */
public class APITests_STAGING extends APITests {

    public APITests_STAGING() {
        super(Environment.STAGING);
    }

}
