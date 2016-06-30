package com.gooten.core.model;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;
import com.gooten.core.utils.JsonUtils;

public class ShippingPricesRequest implements JSONSerializable, Parcelable {

    private String shipToPostalCode;
    private String shipToCountry;
    private String shipToState;
    private String currencyCode;
    private String languageCode;
    private List<SkuQuantityPair> items;

    public ShippingPricesRequest() {
        // NOP
    }

    public ShippingPricesRequest(String shipToPostalCode, String shipToCountry, String shipToState, String currencyCode, String languageCode, List<SkuQuantityPair> items) {
        this.shipToPostalCode = shipToPostalCode;
        this.shipToCountry = shipToCountry;
        this.shipToState = shipToState;
        this.currencyCode = currencyCode;
        this.languageCode = languageCode;
        this.items = items;
    }

    public String getShipToPostalCode() {
        return shipToPostalCode;
    }

    public void setShipToPostalCode(String shipToPostalCode) {
        this.shipToPostalCode = shipToPostalCode;
    }

    public String getShipToCountry() {
        return shipToCountry;
    }

    public void setShipToCountry(String shipToCountry) {
        this.shipToCountry = shipToCountry;
    }

    public String getShipToState() {
        return shipToState;
    }

    public void setShipToState(String shipToState) {
        this.shipToState = shipToState;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public List<SkuQuantityPair> getItems() {
        return items;
    }

    public void setItems(List<SkuQuantityPair> items) {
        this.items = items;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ShipToPostalCode", shipToPostalCode);
            jsonObject.put("ShipToCountry", shipToCountry);
            jsonObject.put("ShipToState", shipToState);
            jsonObject.put("CurrencyCode", currencyCode);
            jsonObject.put("LanguageCode", languageCode);
            JsonUtils.putOpt(jsonObject, "Items", items);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void fromJSON(JSONObject json) {
        shipToPostalCode = json.optString("ShipToPostalCode");
        shipToCountry = json.optString("ShipToCountry");
        shipToState = json.optString("ShipToState");
        currencyCode = json.optString("CurrencyCode");
        languageCode = json.optString("LanguageCode");
        items = JsonUtils.fromJsonArray(SkuQuantityPair.class, json.optJSONArray("Items"));
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
        dest.writeString(shipToPostalCode);
        dest.writeString(shipToCountry);
        dest.writeString(shipToState);
        dest.writeString(currencyCode);
        dest.writeString(languageCode);
        dest.writeTypedList(items);
    }

    public static final Creator<ShippingPricesRequest> CREATOR = new Creator<ShippingPricesRequest>() {

        public ShippingPricesRequest createFromParcel(Parcel source) {
            return new ShippingPricesRequest(source);
        }

        public ShippingPricesRequest[] newArray(int size) {
            return new ShippingPricesRequest[size];
        }

    };

    private ShippingPricesRequest(Parcel in) {
        shipToPostalCode = in.readString();
        shipToCountry = in.readString();
        shipToState = in.readString();
        currencyCode = in.readString();
        languageCode = in.readString();
        items = in.createTypedArrayList(SkuQuantityPair.CREATOR);
    }
}
