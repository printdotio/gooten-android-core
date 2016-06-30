package com.gooten.core.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;

public class PriceInfo implements Parcelable, JSONSerializable {

    private double price;
    private String currencyCode;
    private String formattedPrice;
    private String currencyFormat;
    private int currencyDigits;

    public PriceInfo() {
        // NOP
    }

    public PriceInfo(double price, String currencyCode, String formattedPrice, String currencyFormat, int currencyDigits) {
        this.price = price;
        this.currencyCode = currencyCode;
        this.formattedPrice = formattedPrice;
        this.currencyFormat = currencyFormat;
        this.currencyDigits = currencyDigits;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getFormattedPrice() {
        return formattedPrice;
    }

    public void setFormattedPrice(String formattedPrice) {
        this.formattedPrice = formattedPrice;
    }

    public String getCurrencyFormat() {
        return currencyFormat;
    }

    public void setCurrencyFormat(String currencyFormat) {
        this.currencyFormat = currencyFormat;
    }

    public int getCurrencyDigits() {
        return currencyDigits;
    }

    public void setCurrencyDigits(int currencyDigits) {
        this.currencyDigits = currencyDigits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceInfo priceInfo = (PriceInfo) o;

        if (Double.compare(priceInfo.price, price) != 0) return false;
        if (currencyDigits != priceInfo.currencyDigits) return false;
        if (currencyCode != null ? !currencyCode.equals(priceInfo.currencyCode) : priceInfo.currencyCode != null)
            return false;
        if (formattedPrice != null ? !formattedPrice.equals(priceInfo.formattedPrice) : priceInfo.formattedPrice != null)
            return false;
        return currencyFormat != null ? currencyFormat.equals(priceInfo.currencyFormat) : priceInfo.currencyFormat == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(price);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (currencyCode != null ? currencyCode.hashCode() : 0);
        result = 31 * result + (formattedPrice != null ? formattedPrice.hashCode() : 0);
        result = 31 * result + (currencyFormat != null ? currencyFormat.hashCode() : 0);
        result = 31 * result + currencyDigits;
        return result;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    private static final String JSON_PRICE = "Price";
    private static final String JSON_FORMATTED_PRICE = "FormattedPrice";
    private static final String JSON_CURRENCY_FORMAT = "CurrencyFormat";
    private static final String JSON_CURRENCY_DIGITS = "CurrencyDigits";
    private static final String JSON_CURRENCY_CODE = "CurrencyCode";

    @Override
    public void fromJSON(JSONObject json) {
        price = json.optDouble(JSON_PRICE);
        currencyCode = json.optString(JSON_CURRENCY_CODE);
        formattedPrice = json.optString(JSON_FORMATTED_PRICE);
        currencyFormat = json.optString(JSON_CURRENCY_FORMAT);
        currencyDigits = json.optInt(JSON_CURRENCY_DIGITS);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(JSON_PRICE, price);
            jsonObject.putOpt(JSON_CURRENCY_CODE, currencyCode);
            jsonObject.putOpt(JSON_FORMATTED_PRICE, formattedPrice);
            jsonObject.putOpt(JSON_CURRENCY_FORMAT, currencyFormat);
            jsonObject.putOpt(JSON_CURRENCY_DIGITS, currencyDigits);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
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
        dest.writeDouble(price);
        dest.writeString(currencyCode);
        dest.writeString(formattedPrice);
        dest.writeString(currencyFormat);
        dest.writeInt(currencyDigits);
    }

    public static final Creator<PriceInfo> CREATOR = new Creator<PriceInfo>() {

        public PriceInfo createFromParcel(Parcel source) {
            return new PriceInfo(source);
        }

        public PriceInfo[] newArray(int size) {
            return new PriceInfo[size];
        }

    };

    private PriceInfo(Parcel in) {
        price = in.readDouble();
        currencyCode = in.readString();
        formattedPrice = in.readString();
        currencyFormat = in.readString();
        currencyDigits = in.readInt();
    }

}
