package com.gooten.core.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;

public class UserInfo implements Parcelable, JSONSerializable {

    private String currencyCode;
    private String countryCode;

    public UserInfo() {
        // NOP
    }

    public UserInfo(String currencyCode, String countryCode) {
        this.currencyCode = currencyCode;
        this.countryCode = countryCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInfo userInfo = (UserInfo) o;

        if (currencyCode != null ? !currencyCode.equals(userInfo.currencyCode) : userInfo.currencyCode != null)
            return false;
        return countryCode != null ? countryCode.equals(userInfo.countryCode) : userInfo.countryCode == null;

    }

    @Override
    public int hashCode() {
        int result = currencyCode != null ? currencyCode.hashCode() : 0;
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        return result;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("CountryCode", countryCode);
            json.put("CurrencyCode", currencyCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public void fromJSON(JSONObject json) {
        countryCode = json.optString("CountryCode");
        currencyCode = json.optString("CurrencyCode");
    }

    // ==================================================================================
    // Methods to make class Parcelable
    // ==================================================================================

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(currencyCode);
        dest.writeString(countryCode);
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {

        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }

    };

    private UserInfo(Parcel in) {
        currencyCode = in.readString();
        countryCode = in.readString();
    }
}
