package com.gooten.core.model;

import android.app.Application;
import android.os.Parcelable;

/**
 * Tests for {@link Country} class.
 */
public class CountryTests extends BaseModelTests<Country> {

    public CountryTests() {
        super(Application.class, Country.class);
    }

    @Override
    protected Country createValidObject() {
        return new Country("United States", "US", true, "INCH", "http://app-imgs.print.io/app-imgs/US.png", new Currency("United States dollar", "USD", "${1}"));
    }

    @Override
    protected String getValidJSON() {
        return "{\"Name\":\"United States\",\"Code\":\"US\",\"IsSupported\":true,\"MeasurementCode\":\"INCH\",\"FlagUrl\":\"http://app-imgs.print.io/app-imgs/US.png\", \"DefaultCurrency\":{\"Name\":\"United States dollar\",\"Code\":\"USD\",\"Format\":\"${1}\"}}";
    }

    @Override
    protected Parcelable.Creator<Country> getCreator() {
        return Country.CREATOR;
    }

}
