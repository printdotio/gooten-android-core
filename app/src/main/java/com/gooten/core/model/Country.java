package com.gooten.core.model;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;
import com.gooten.core.utils.JsonUtils;

public class Country implements Parcelable, JSONSerializable {

    public static Country findByCode(List<Country> countries, String code) {
        if (countries != null && code != null) {
            for (Country c : countries) {
                if (c != null && code.equalsIgnoreCase(c.code)) {
                    return c;
                }
            }
        }
        return null;
    }

    private String name;
    private String code;
    private boolean isSupported;
    private String measurementCode;
    private String flagUrl;
    private Currency defaultCurrency;

    public Country() {
        // NOP
    }

    public Country(String name, String code, boolean isSupported, String measurementCode, String flagUrl, Currency defaultCurrency) {
        this.name = name;
        this.code = code;
        this.isSupported = isSupported;
        this.measurementCode = measurementCode;
        this.flagUrl = flagUrl;
        this.defaultCurrency = defaultCurrency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSupported() {
        return isSupported;
    }

    public void setSupported(boolean supported) {
        isSupported = supported;
    }

    public String getMeasurementCode() {
        return measurementCode;
    }

    public void setMeasurementCode(String measurementCode) {
        this.measurementCode = measurementCode;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        if (isSupported != country.isSupported) return false;
        if (name != null ? !name.equals(country.name) : country.name != null) return false;
        if (code != null ? !code.equals(country.code) : country.code != null) return false;
        if (measurementCode != null ? !measurementCode.equals(country.measurementCode) : country.measurementCode != null)
            return false;
        if (flagUrl != null ? !flagUrl.equals(country.flagUrl) : country.flagUrl != null)
            return false;
        return defaultCurrency != null ? defaultCurrency.equals(country.defaultCurrency) : country.defaultCurrency == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (isSupported ? 1 : 0);
        result = 31 * result + (measurementCode != null ? measurementCode.hashCode() : 0);
        result = 31 * result + (flagUrl != null ? flagUrl.hashCode() : 0);
        result = 31 * result + (defaultCurrency != null ? defaultCurrency.hashCode() : 0);
        return result;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    private static final String JSON_NAME = "Name";
    private static final String JSON_CODE = "Code";
    private static final String JSON_IS_SUPPORTED = "IsSupported";
    private static final String JSON_MEASUREMENT_CODE = "MeasurementCode";
    private static final String JSON_FLAG_URL = "FlagUrl";
    private static final String JSON_DEFAULT_CURRENCY = "DefaultCurrency";

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put(JSON_NAME, name);
            json.put(JSON_CODE, code);
            json.put(JSON_IS_SUPPORTED, isSupported);
            json.put(JSON_MEASUREMENT_CODE, measurementCode);
            json.put(JSON_FLAG_URL, flagUrl);
            if (defaultCurrency != null) {
                json.put(JSON_DEFAULT_CURRENCY, defaultCurrency.toJSON());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public void fromJSON(JSONObject json) {
        name = json.optString(JSON_NAME);
        code = json.optString(JSON_CODE);
        isSupported = json.optBoolean(JSON_IS_SUPPORTED);
        measurementCode = json.optString(JSON_MEASUREMENT_CODE);
        flagUrl = json.optString(JSON_FLAG_URL);
        defaultCurrency = JsonUtils.fromJson(Currency.class, json.optJSONObject(JSON_DEFAULT_CURRENCY));
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
        dest.writeString(name);
        dest.writeString(code);
        dest.writeByte((byte) (isSupported ? 1 : 0));
        dest.writeString(measurementCode);
        dest.writeString(flagUrl);
        dest.writeParcelable(defaultCurrency, 0);
    }

    public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>() {

        public Country createFromParcel(Parcel source) {
            return new Country(source);
        }

        public Country[] newArray(int size) {
            return new Country[size];
        }

    };

    private Country(Parcel in) {
        name = in.readString();
        code = in.readString();
        isSupported = in.readByte() == 1;
        measurementCode = in.readString();
        flagUrl = in.readString();
        defaultCurrency = in.readParcelable(Currency.class.getClassLoader());
    }

}