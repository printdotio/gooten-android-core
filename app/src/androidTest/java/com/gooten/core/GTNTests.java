package com.gooten.core;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

/**
 * Tests for {@link GTN} class.
 *
 * @author Vlado
 */
public class GTNTests extends ApplicationTestCase<Application> {

    public GTNTests() {
        super(Application.class);
    }

    @SmallTest
    public void testSetConfig() {
        GTN.setConfigNoValidation(getContext(), null);
        assertNull(GTN.getConfig());
        assertNull(GTN.getRestoredConfig(getContext()));

        { // Valid config must be set
            try {
                GTN.setConfig(getContext(), TestUtils.createConfig());
            } catch (GTNException e) {
            }
            assertEquals(TestUtils.createConfig(), GTN.getConfig());
        }

        { // Config should not be set when context is null
            try {
                GTN.setConfigNoValidation(getContext(), null);
                GTN.setConfig(null, TestUtils.createConfig());
            } catch (GTNException e) {
            }
            assertNull(GTN.getConfig());
        }

        { // Config should not be set when config is null
            try {
                GTN.setConfig(getContext(), TestUtils.createConfig());
                GTN.setConfig(getContext(), null);
            } catch (GTNException e) {
            }
            assertNotNull(GTN.getConfig());
        }
    }

}
