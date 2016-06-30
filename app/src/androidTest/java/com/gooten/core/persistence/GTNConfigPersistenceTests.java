package com.gooten.core.persistence;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.gooten.core.GTNConfig;
import com.gooten.core.TestUtils;

/**
 * Tests for {@link GTNConfigPersistence} class.
 *
 * @author Vlado
 */
public class GTNConfigPersistenceTests extends ApplicationTestCase<Application> {

    public GTNConfigPersistenceTests() {
        super(Application.class);
    }

    @SmallTest
    public void testStore() {
        { // Store-Load not-null config
            GTNConfig cfg = TestUtils.createConfig();
            GTNConfigPersistence.store(getContext(), cfg);
            assertEquals(GTNConfigPersistence.load(getContext()), cfg);
        }

        { // Store-Load not-null config (default values)
            GTNConfig cfg = new GTNConfig();
            GTNConfigPersistence.store(getContext(), cfg);
            assertEquals(GTNConfigPersistence.load(getContext()), cfg);
        }

        { // Store-Load null config
            GTNConfigPersistence.store(getContext(), null);
            assertNull(GTNConfigPersistence.load(getContext()));
        }
    }

}