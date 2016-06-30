package com.gooten.core.model;

import android.app.Application;
import android.os.Parcelable;

/**
 * Tests for {@link UserInfo} class.
 */
public class UserInfoTests extends BaseModelTests<UserInfo> {

    public UserInfoTests() {
        super(Application.class, UserInfo.class);
    }

    @Override
    protected UserInfo createValidObject() {
        return new UserInfo("USD", "US");
    }

    @Override
    protected String getValidJSON() {
        return "{\"CountryCode\":\"US\", \"CurrencyCode\":\"USD\"}";
    }

    @Override
    protected Parcelable.Creator<UserInfo> getCreator() {
        return UserInfo.CREATOR;
    }

}
