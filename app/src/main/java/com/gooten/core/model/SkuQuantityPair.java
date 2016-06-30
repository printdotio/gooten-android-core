package com.gooten.core.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;

public class SkuQuantityPair implements Parcelable, JSONSerializable {

    private String sku;
    private int quantity;

    public SkuQuantityPair() {
        // NOP
    }

    public SkuQuantityPair(String sku, int quantity) {
        this.sku = sku;
        this.quantity = quantity;
    }

    public String getSKU() {
        return sku;
    }

    public void setSKU(String sku) {
        this.sku = sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkuQuantityPair that = (SkuQuantityPair) o;

        if (quantity != that.quantity) return false;
        return sku != null ? sku.equals(that.sku) : that.sku == null;

    }

    @Override
    public int hashCode() {
        int result = sku != null ? sku.hashCode() : 0;
        result = 31 * result + quantity;
        return result;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SKU", sku);
            jsonObject.put("Quantity", quantity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void fromJSON(JSONObject json) {
        sku = json.optString("SKU");
        quantity = json.optInt("Quantity");
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
        dest.writeString(sku);
        dest.writeInt(quantity);
    }

    public static final Creator<SkuQuantityPair> CREATOR = new Creator<SkuQuantityPair>() {

        public SkuQuantityPair createFromParcel(Parcel source) {
            return new SkuQuantityPair(source);
        }

        public SkuQuantityPair[] newArray(int size) {
            return new SkuQuantityPair[size];
        }

    };

    private SkuQuantityPair(Parcel in) {
        sku = in.readString();
        quantity = in.readInt();
    }

}