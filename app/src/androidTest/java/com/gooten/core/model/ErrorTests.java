package com.gooten.core.model;

import android.app.Application;
import android.os.Parcelable;

/**
 * Tests for {@link Error} class.
 */
public class ErrorTests extends BaseModelTests<Error> {

    public ErrorTests() {
        super(Application.class, Error.class);
    }

    @Override
    protected Error createValidObject() {
        return new Error("Invalid value", "Property", "INVALID");
    }

    @Override
    protected String getValidJSON() {
        return "{\"ErrorMessage\":\"Invalid value\",\"PropertyName\":\"Property\",\"AttemptedValue\":\"INVALID\"}";
    }

    @Override
    protected Parcelable.Creator<Error> getCreator() {
        return Error.CREATOR;
    }

}
