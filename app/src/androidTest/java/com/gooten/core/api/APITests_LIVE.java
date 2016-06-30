package com.gooten.core.api;

import com.gooten.core.types.Environment;

/**
 * Executes {@code APITests} for LIVE environment.
 *
 * @author Vlado
 */
public class APITests_LIVE extends APITests {

    public APITests_LIVE() {
        super(Environment.LIVE);
    }

}
