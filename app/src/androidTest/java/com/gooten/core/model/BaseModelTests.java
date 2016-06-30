package com.gooten.core.model;

import org.json.JSONObject;

import android.app.Application;
import android.os.Parcel;
import android.os.Parcelable;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.gooten.core.types.JSONSerializable;
import com.gooten.core.utils.JsonUtils;

/**
 * Base class for all model test classes. This class encapsulates common tests for all model classes.
 *
 * @author Vlado
 */
public abstract class BaseModelTests<T extends JSONSerializable & Parcelable> extends ApplicationTestCase<Application> {

    private Class<T> modelClass;

    public BaseModelTests(Class<Application> applicationClass, Class<T> modelClass) {
        super(applicationClass);
        this.modelClass = modelClass;
    }

    protected abstract T createValidObject();

    protected abstract String getValidJSON();

    protected abstract Parcelable.Creator<T> getCreator();

    @SmallTest
    public void testJsonSerializationValid() {
        T validObject = createValidObject();
        T validObjectFromJson = JsonUtils.fromJsonString(modelClass, getValidJSON());
        assertEquals(validObject.toJSON().toString(), validObjectFromJson.toJSON().toString());
    }

    @SmallTest
    public void testJsonDeserializationEmpty() {
        assertNotNull(JsonUtils.fromJson(modelClass, new JSONObject()));
    }

    @SmallTest
    public void testJsonDeserializationInvalid() {
        assertNull(JsonUtils.fromJsonString(modelClass, "Invalid JSON"));
        assertNull(JsonUtils.fromJsonString(modelClass, ""));
        assertNull(JsonUtils.fromJsonString(modelClass, null));
        assertNull(JsonUtils.fromJson(modelClass, null));
    }

    @SmallTest
    public void testParcelableValid() {
        T validObject = createValidObject();

        Parcel parcel = Parcel.obtain();
        validObject.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        T createdFromParcel = getCreator().createFromParcel(parcel);

        assertEquals(validObject, createdFromParcel);
    }

}
