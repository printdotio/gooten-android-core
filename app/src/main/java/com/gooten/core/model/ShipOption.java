package com.gooten.core.model;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;
import com.gooten.core.utils.JsonUtils;

public class ShipOption implements JSONSerializable, Parcelable {

    public static ShipOption findById(List<ShipOption> options, int id) {
        if (options != null) {
            for (ShipOption option : options) {
                if (option != null && option.id == id) {
                    return option;
                }
            }
        }
        return null;
    }

    private int id;
    private String methodType;
    private String name;
    private String carrierName;
    private String carrierLogoUrl;
    private PriceInfo price;
    private int daysToDelivery;

    public ShipOption() {
        // NOP
    }

    public int getId() {
        return id;
    }

    public String getMethodType() {
        return methodType;
    }

    public String getName() {
        return name;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public String getCarrierLogoUrl() {
        return carrierLogoUrl;
    }

    public PriceInfo getPrice() {
        return price;
    }

    public int getDaysToDelivery() {
        return daysToDelivery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipOption that = (ShipOption) o;

        if (id != that.id) return false;
        if (daysToDelivery != that.daysToDelivery) return false;
        if (methodType != null ? !methodType.equals(that.methodType) : that.methodType != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (carrierName != null ? !carrierName.equals(that.carrierName) : that.carrierName != null)
            return false;
        if (carrierLogoUrl != null ? !carrierLogoUrl.equals(that.carrierLogoUrl) : that.carrierLogoUrl != null)
            return false;
        return price != null ? price.equals(that.price) : that.price == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (methodType != null ? methodType.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (carrierName != null ? carrierName.hashCode() : 0);
        result = 31 * result + (carrierLogoUrl != null ? carrierLogoUrl.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + daysToDelivery;
        return result;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    private static final String JSON_ID = "Id";
    private static final String JSON_METHOD_TYPE = "MethodType";
    private static final String JSON_NAME = "Name";
    private static final String JSON_CARRIER_NAME = "CarrierName";
    private static final String JSON_CARRIER_LOGO_URL = "CarrierLogoUrl";
    private static final String JSON_PRICE = "Price";
    private static final String JSON_EST_BUSNESS_DAYS_TIL_DELIVERY = "EstBusinessDaysTilDelivery";

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(JSON_ID, id);
            jsonObject.putOpt(JSON_METHOD_TYPE, methodType);
            jsonObject.putOpt(JSON_NAME, name);
            jsonObject.putOpt(JSON_CARRIER_NAME, carrierName);
            jsonObject.putOpt(JSON_CARRIER_LOGO_URL, carrierLogoUrl);
            JsonUtils.putOpt(jsonObject, JSON_PRICE, price);
            jsonObject.putOpt(JSON_EST_BUSNESS_DAYS_TIL_DELIVERY, daysToDelivery);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void fromJSON(JSONObject json) {
        id = json.optInt(JSON_ID);
        methodType = json.optString(JSON_METHOD_TYPE);
        name = json.optString(JSON_NAME);
        carrierName = json.optString(JSON_CARRIER_NAME);
        carrierLogoUrl = json.optString(JSON_CARRIER_LOGO_URL);
        price = JsonUtils.fromJson(PriceInfo.class, json.optJSONObject(JSON_PRICE));
        daysToDelivery = json.optInt(JSON_EST_BUSNESS_DAYS_TIL_DELIVERY);
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
        dest.writeInt(id);
        dest.writeString(methodType);
        dest.writeString(name);
        dest.writeString(carrierName);
        dest.writeString(carrierLogoUrl);
        dest.writeParcelable(price, 0);
        dest.writeInt(daysToDelivery);
    }

    public static final Parcelable.Creator<ShipOption> CREATOR = new Parcelable.Creator<ShipOption>() {

        public ShipOption createFromParcel(Parcel source) {
            return new ShipOption(source);
        }

        public ShipOption[] newArray(int size) {
            return new ShipOption[size];
        }

    };

    private ShipOption(Parcel in) {
        id = in.readInt();
        methodType = in.readString();
        name = in.readString();
        carrierName = in.readString();
        carrierLogoUrl = in.readString();
        price = in.readParcelable(PriceInfo.class.getClassLoader());
        daysToDelivery = in.readInt();
    }

}
