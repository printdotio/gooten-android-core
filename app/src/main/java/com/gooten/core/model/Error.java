package com.gooten.core.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;

public class Error implements Parcelable, JSONSerializable {

    private String errorMessage;
    private String propertyName;
    private String attemptedValue;

    public Error() {
        // NOP
    }

    public Error(String errorMessage, String propertyName, String attemptedValue) {
        this.errorMessage = errorMessage;
        this.propertyName = propertyName;
        this.attemptedValue = attemptedValue;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getAttemptedValue() {
        return attemptedValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Error error = (Error) o;

        if (errorMessage != null ? !errorMessage.equals(error.errorMessage) : error.errorMessage != null)
            return false;
        if (propertyName != null ? !propertyName.equals(error.propertyName) : error.propertyName != null)
            return false;
        return attemptedValue != null ? attemptedValue.equals(error.attemptedValue) : error.attemptedValue == null;

    }

    @Override
    public int hashCode() {
        int result = errorMessage != null ? errorMessage.hashCode() : 0;
        result = 31 * result + (propertyName != null ? propertyName.hashCode() : 0);
        result = 31 * result + (attemptedValue != null ? attemptedValue.hashCode() : 0);
        return result;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("ErrorMessage", errorMessage);
            json.put("PropertyName", propertyName);
            json.put("AttemptedValue", attemptedValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public void fromJSON(JSONObject json) {
        errorMessage = json.optString("ErrorMessage");
        propertyName = json.optString("PropertyName");
        attemptedValue = json.optString("AttemptedValue");
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
        dest.writeString(errorMessage);
        dest.writeString(propertyName);
        dest.writeString(attemptedValue);
    }

    public static final Parcelable.Creator<Error> CREATOR = new Parcelable.Creator<Error>() {

        public Error createFromParcel(Parcel source) {
            return new Error(source);
        }

        public Error[] newArray(int size) {
            return new Error[size];
        }

    };

    private Error(Parcel in) {
        errorMessage = in.readString();
        propertyName = in.readString();
        attemptedValue = in.readString();
    }

}