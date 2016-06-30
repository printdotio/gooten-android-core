package com.gooten.core;

import org.json.JSONObject;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.gooten.core.types.Environment;
import com.gooten.core.utils.JsonUtils;

/**
 * Tests for {@link GTNConfig} class.
 *
 * @author Vlado
 */
public class GTNConfigTests extends ApplicationTestCase<Application> {

    public GTNConfigTests() {
        super(Application.class);
    }

    @SmallTest
    public void testGsonDefaultValues() {
        // Non existing values from JSON object must default to same values as in new GTNConfig obj
        assertEquals(new GTNConfig(), JsonUtils.fromJson(GTNConfig.class, new JSONObject()));
    }

    @SmallTest
    public void testJsonSerializationValid() {
        String json = "{\"countryCode\":\"US\",\"languageCode\":\"en\",\"currencyCode\":\"USD\",\"environment\":\"LIVE\",\"recipeId\":\"1AB4E1F8-DBCB-4D6C-829F-EE0B2A60C0B3\",\"allProductsAndVariants\":false}";
        GTNConfig validObject = TestUtils.createConfig(Environment.LIVE);
        GTNConfig validObjectFromJson = JsonUtils.fromJsonString(GTNConfig.class, json);
        assertEquals(validObject.toJSON().toString(), validObjectFromJson.toJSON().toString());
    }

    @SmallTest
    public void testJsonDeserializationInvalid() {
        assertNull(JsonUtils.fromJsonString(GTNConfig.class, "Invalid JSON"));
        assertNull(JsonUtils.fromJsonString(GTNConfig.class, null));
        assertNull(JsonUtils.fromJson(GTNConfig.class, null));
    }

}
