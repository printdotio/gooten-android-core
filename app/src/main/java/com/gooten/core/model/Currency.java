package com.gooten.core.model;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;

public class Currency implements Parcelable, JSONSerializable {

    public static Currency findByCode(List<Currency> currencies, String code) {
        if (currencies != null && code != null) {
            for (Currency c : currencies) {
                if (c != null && code.equalsIgnoreCase(c.code)) {
                    return c;
                }
            }
        }
        return null;
    }

    private String name;
    private String code;
    private String format;

    public Currency() {
        // NOP
    }

    public Currency(String name, String code, String format) {
        this.name = name;
        this.code = code;
        this.format = format;
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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Currency currency = (Currency) o;

        if (name != null ? !name.equals(currency.name) : currency.name != null) return false;
        if (code != null ? !code.equals(currency.code) : currency.code != null) return false;
        return format != null ? format.equals(currency.format) : currency.format == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (format != null ? format.hashCode() : 0);
        return result;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================


    private static final String JSON_NAME = "Name";
    private static final String JSON_CODE = "Code";
    private static final String JSON_FORMAT = "Format";

    @Override
    public void fromJSON(JSONObject json) {
        name = json.optString(JSON_NAME);
        code = json.optString(JSON_CODE);
        format = json.optString(JSON_FORMAT);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put(JSON_NAME, name);
            json.put(JSON_CODE, code);
            json.put(JSON_FORMAT, format);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
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
        dest.writeString(format);
    }

    public static final Parcelable.Creator<Currency> CREATOR = new Parcelable.Creator<Currency>() {

        public Currency createFromParcel(Parcel source) {
            return new Currency(source);
        }

        public Currency[] newArray(int size) {
            return new Currency[size];
        }

    };

    private Currency(Parcel in) {
        name = in.readString();
        code = in.readString();
        format = in.readString();
    }
}