package com.gooten.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for {@link Version} class.
 *
 * @author Vlado
 */
public class VersionTests {

    @Test
    public void testVersions() {
        assertNotNull(Version.BUILD_VERSION);
        String[] version = Version.BUILD_VERSION.split("\\.");
        assertNotNull(version);
        assertEquals(3, version.length);
        assertNotNull(version[0]);
        assertNotNull(version[1]);
        assertNotNull(version[2]);
    }
}
