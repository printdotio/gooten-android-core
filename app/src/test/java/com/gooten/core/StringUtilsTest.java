package com.gooten.core;

import com.gooten.core.utils.StringUtils;

import junit.framework.Assert;

import org.junit.Test;

public class StringUtilsTest {

    private static final String STRING = "some non-empty string";
    private static final String STRING_WHITESPACED = "  some non-empty string ";
    private static final String BLANK_STRING = "   ";

    @Test
    public void testIsEmpty() {
        Assert.assertTrue(StringUtils.isEmpty(StringUtils.EMPTY));
        Assert.assertTrue(StringUtils.isEmpty(null));
        Assert.assertFalse(StringUtils.isEmpty(STRING));
        Assert.assertFalse(StringUtils.isEmpty(BLANK_STRING));
    }

    @Test
    public void testIsNotEmpty() {
        Assert.assertTrue(StringUtils.isNotEmpty(STRING));
        Assert.assertTrue(StringUtils.isNotEmpty(BLANK_STRING));
        Assert.assertFalse(StringUtils.isNotEmpty(null));
        Assert.assertFalse(StringUtils.isNotEmpty(StringUtils.EMPTY));
    }

    @Test
    public void testIsBlank() {
        Assert.assertTrue(StringUtils.isBlank(StringUtils.EMPTY));
        Assert.assertTrue(StringUtils.isBlank(null));
        Assert.assertTrue(StringUtils.isBlank(BLANK_STRING));
        Assert.assertFalse(StringUtils.isBlank(STRING));
    }

    @Test
    public void testIsNotBlank() {
        Assert.assertTrue(StringUtils.isNotBlank(STRING));
        Assert.assertFalse(StringUtils.isNotBlank(StringUtils.EMPTY));
        Assert.assertFalse(StringUtils.isNotBlank(null));
        Assert.assertFalse(StringUtils.isNotBlank("   "));
    }

    @Test
    public void testToUpperCase() {
        Assert.assertEquals("TEST", StringUtils.toUpperCase("test"));
        Assert.assertFalse("test".equals(StringUtils.toUpperCase("test")));
        Assert.assertEquals(null, StringUtils.toUpperCase(null));
        Assert.assertFalse(STRING.equals(StringUtils.toUpperCase(null)));
    }

    @Test
    public void testToLowerCase() {
        Assert.assertEquals("test", StringUtils.toLowerCase("TEST"));
        Assert.assertFalse("TEST".equals(StringUtils.toLowerCase("TEST")));
        Assert.assertEquals(null, StringUtils.toLowerCase(null));
        Assert.assertFalse(STRING.equals(StringUtils.toLowerCase(null)));
    }

    @Test
    public void testTrim() {
        Assert.assertEquals(STRING, StringUtils.trim(STRING_WHITESPACED));
        Assert.assertEquals(StringUtils.EMPTY, StringUtils.trim(BLANK_STRING));
        Assert.assertEquals(null, StringUtils.trim(null));
        Assert.assertFalse(STRING_WHITESPACED.equals(StringUtils.trim(STRING_WHITESPACED)));
    }

}
