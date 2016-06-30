package com.gooten.core.model;

import android.app.Application;
import android.os.Parcelable;

public class AddressTests extends BaseModelTests<Address> {

    private final String JSON_ADDRESS_US_VALID = "{\"FirstName\":\"Bojan\",\"LastName\":\"Radivojevic\",\"Line1\":\"416 PARK AVE S\",\"Line2\":\"\",\"City\":\"NEW YORK\",\"State\":\"NY\",\"CountryCode\":\"US\",\"PostalCode\":\"10016-8403\",\"IsBusinessAddress\":false,\"Phone\":\"381630000000\",\"Email\":\"bojan@gooten.com\"}";

    public AddressTests() {
        super(Application.class, Address.class);
    }

    @Override
    protected Address createValidObject() {
        return new Address("Bojan", "Radivojevic", "416 PARK AVE S", "", "NEW YORK", "NY", "US", "10016-8403", false, "381630000000", "bojan@gooten.com");
    }

    @Override
    protected String getValidJSON() {
        return JSON_ADDRESS_US_VALID;
    }

    @Override
    protected Parcelable.Creator<Address> getCreator() {
        return Address.CREATOR;
    }

}
