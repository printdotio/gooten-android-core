package com.gooten.core.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;
import com.gooten.core.utils.JsonUtils;

public class ShipItem implements Parcelable, JSONSerializable {

    public static final ShipItem findForSKU(List<ShipItem> shipItems, String SKU) {
        if (shipItems != null && SKU != null) {
            for (ShipItem item : shipItems) {
                if (item.skus.contains(SKU)) {
                    return item;
                }
            }
        }
        return null;
    }

    private List<String> skus;
    private List<ShipOption> shipOptions;

    public ShipItem() {
        // NOP
    }

    public List<String> getSkus() {
        return skus;
    }

    public List<ShipOption> getShipOptions() {
        return shipOptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipItem shipItem = (ShipItem) o;

        if (skus != null ? !skus.equals(shipItem.skus) : shipItem.skus != null) return false;
        return shipOptions != null ? shipOptions.equals(shipItem.shipOptions) : shipItem.shipOptions == null;

    }

    @Override
    public int hashCode() {
        int result = skus != null ? skus.hashCode() : 0;
        result = 31 * result + (shipOptions != null ? shipOptions.hashCode() : 0);
        return result;
    }


    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    private static final String JSON_SKUS = "SKUs";
    private static final String JSON_SHIP_OPTIONS = "ShipOptions";

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(JSON_SKUS, JsonUtils.listToJson(skus));
            JsonUtils.putOpt(jsonObject, JSON_SHIP_OPTIONS, shipOptions);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void fromJSON(JSONObject json) {
        skus = JsonUtils.stringListFromJson(json.optJSONArray(JSON_SKUS), new ArrayList<String>());
        shipOptions = JsonUtils.fromJsonArray(ShipOption.class, json.optJSONArray(JSON_SHIP_OPTIONS), new ArrayList<ShipOption>(0));
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
        dest.writeStringList(skus);
        dest.writeTypedList(shipOptions);
    }

    public static final Parcelable.Creator<ShipItem> CREATOR = new Parcelable.Creator<ShipItem>() {

        public ShipItem createFromParcel(Parcel source) {
            return new ShipItem(source);
        }

        public ShipItem[] newArray(int size) {
            return new ShipItem[size];
        }

    };

    private ShipItem(Parcel in) {
        skus = new ArrayList<String>();
        in.readStringList(skus);
        shipOptions = in.createTypedArrayList(ShipOption.CREATOR);
    }

}
