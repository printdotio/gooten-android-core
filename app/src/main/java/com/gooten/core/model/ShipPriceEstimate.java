package com.gooten.core.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;
import com.gooten.core.utils.JsonUtils;

public class ShipPriceEstimate implements Parcelable, JSONSerializable {

    private boolean canShipExpedited;
    private int estShipDays;
    private String vendorCountryCode;
    private PriceInfo maxPrice;
    private PriceInfo minPrice;

    public ShipPriceEstimate() {
        // NOP
    }

    public boolean isCanShipExpedited() {
        return canShipExpedited;
    }

    public void setCanShipExpedited(boolean canShipExpedited) {
        this.canShipExpedited = canShipExpedited;
    }

    public int getEstShipDays() {
        return estShipDays;
    }

    public void setEstShipDays(int estShipDays) {
        this.estShipDays = estShipDays;
    }

    public String getVendorCountryCode() {
        return vendorCountryCode;
    }

    public void setVendorCountryCode(String vendorCountryCode) {
        this.vendorCountryCode = vendorCountryCode;
    }

    public PriceInfo getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(PriceInfo maxPrice) {
        this.maxPrice = maxPrice;
    }

    public PriceInfo getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(PriceInfo minPrice) {
        this.minPrice = minPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipPriceEstimate that = (ShipPriceEstimate) o;

        if (canShipExpedited != that.canShipExpedited) return false;
        if (estShipDays != that.estShipDays) return false;
        if (vendorCountryCode != null ? !vendorCountryCode.equals(that.vendorCountryCode) : that.vendorCountryCode != null)
            return false;
        if (maxPrice != null ? !maxPrice.equals(that.maxPrice) : that.maxPrice != null)
            return false;
        return minPrice != null ? minPrice.equals(that.minPrice) : that.minPrice == null;

    }

    @Override
    public int hashCode() {
        int result = (canShipExpedited ? 1 : 0);
        result = 31 * result + estShipDays;
        result = 31 * result + (vendorCountryCode != null ? vendorCountryCode.hashCode() : 0);
        result = 31 * result + (maxPrice != null ? maxPrice.hashCode() : 0);
        result = 31 * result + (minPrice != null ? minPrice.hashCode() : 0);
        return result;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    private static final String JSON_CAN_SHIP_EXPEDITED = "CanShipExpedited";
    private static final String JSON_EST_SHIP_DAYS = "EstShipDays";
    private static final String JSON_VENDOR_COUNTRY_CODE = "VendorCountryCode";
    private static final String JSON_MAX_PRICE = "MaxPrice";
    private static final String JSON_MIN_PRICE = "MinPrice";

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(JSON_CAN_SHIP_EXPEDITED, canShipExpedited);
            jsonObject.putOpt(JSON_EST_SHIP_DAYS, estShipDays);
            jsonObject.putOpt(JSON_VENDOR_COUNTRY_CODE, vendorCountryCode);
            JsonUtils.putOpt(jsonObject, JSON_MAX_PRICE, maxPrice);
            JsonUtils.putOpt(jsonObject, JSON_MIN_PRICE, minPrice);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void fromJSON(JSONObject json) {
        canShipExpedited = json.optBoolean(JSON_CAN_SHIP_EXPEDITED);
        estShipDays = json.optInt(JSON_EST_SHIP_DAYS);
        vendorCountryCode = json.optString(JSON_VENDOR_COUNTRY_CODE);
        maxPrice = JsonUtils.fromJson(PriceInfo.class, json.optJSONObject(JSON_MAX_PRICE));
        minPrice = JsonUtils.fromJson(PriceInfo.class, json.optJSONObject(JSON_MIN_PRICE));
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
        dest.writeByte((byte) (canShipExpedited ? 1 : 0));
        dest.writeInt(estShipDays);
        dest.writeString(vendorCountryCode);
        dest.writeParcelable(maxPrice, 0);
        dest.writeParcelable(minPrice, 0);
    }

    public static final Parcelable.Creator<ShipPriceEstimate> CREATOR = new Parcelable.Creator<ShipPriceEstimate>() {

        public ShipPriceEstimate createFromParcel(Parcel source) {
            return new ShipPriceEstimate(source);
        }

        public ShipPriceEstimate[] newArray(int size) {
            return new ShipPriceEstimate[size];
        }

    };

    private ShipPriceEstimate(Parcel in) {
        canShipExpedited = in.readByte() == 1;
        estShipDays = in.readInt();
        vendorCountryCode = in.readString();
        maxPrice = in.readParcelable(PriceInfo.class.getClassLoader());
        minPrice = in.readParcelable(PriceInfo.class.getClassLoader());
    }

}