package com.gooten.core.model;

import android.app.Application;
import android.os.Parcelable;

/**
 * Tests for {@link PriceInfo} class.
 */
public class PriceInfoTests extends BaseModelTests<PriceInfo> {

    public PriceInfoTests() {
        super(Application.class, PriceInfo.class);
    }

    @Override
    protected PriceInfo createValidObject() {
        return new PriceInfo(5, "USD", "$5", "${1}", 2);
    }

    @Override
    protected String getValidJSON() {
        return "{\"Price\":\"5\",\"CurrencyCode\":\"USD\",\"FormattedPrice\":\"$5\",\"CurrencyFormat\":\"${1}\",\"CurrencyDigits\":\"2\"}";
    }

    @Override
    protected Parcelable.Creator<PriceInfo> getCreator() {
        return PriceInfo.CREATOR;
    }

}
