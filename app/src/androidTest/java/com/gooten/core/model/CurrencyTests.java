package com.gooten.core.model;

import android.app.Application;
import android.os.Parcelable;

/**
 * Tests for {@link Currency} class.
 */
public class CurrencyTests extends BaseModelTests<Currency> {

    public static final String JSON_CURRENCY_USD_VALID = "{\"Name\":\"United States dollar\",\"Code\":\"USD\",\"Format\":\"${1}\"}";

    public CurrencyTests() {
        super(Application.class, Currency.class);
    }

    @Override
    protected Currency createValidObject() {
        return new Currency("United States dollar", "USD", "${1}");
    }

    @Override
    protected String getValidJSON() {
        return JSON_CURRENCY_USD_VALID;
    }

    @Override
    protected Parcelable.Creator<Currency> getCreator() {
        return Currency.CREATOR;
    }

}
