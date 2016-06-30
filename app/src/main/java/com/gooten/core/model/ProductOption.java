package com.gooten.core.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;
import com.gooten.core.utils.JsonUtils;

public class ProductOption implements Parcelable, JSONSerializable {

    public static ProductOption findByName(List<ProductOption> options, String name) {
        if (options != null && name != null) {
            for (ProductOption option : options) {
                if (option != null && name.equalsIgnoreCase(option.name)) {
                    return option;
                }
            }
        }
        return null;
    }

    private String name;
    private List<Option> values;

    public ProductOption() {
        // NOP
    }

    public ProductOption(String name, List<Option> values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Option> getValues() {
        return values;
    }

    public void setValues(List<Option> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductOption that = (ProductOption) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return values != null ? values.equals(that.values) : that.values == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (values != null ? values.hashCode() : 0);
        return result;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    private static final String JSON_NAME = "Name";
    private static final String JSON_VALUES = "Values";

    @Override
    public void fromJSON(JSONObject json) {
        name = json.optString(JSON_NAME);
        values = JsonUtils.fromJsonArray(Option.class, json.optJSONArray(JSON_VALUES), new ArrayList<Option>(0));
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(JSON_NAME, name);
            JsonUtils.putOpt(jsonObject, JSON_VALUES, values);
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
        dest.writeString(name);
        dest.writeTypedList(values);
    }

    public static final Creator<ProductOption> CREATOR = new Creator<ProductOption>() {

        public ProductOption createFromParcel(Parcel source) {
            return new ProductOption(source);
        }

        public ProductOption[] newArray(int size) {
            return new ProductOption[size];
        }

    };

    private ProductOption(Parcel in) {
        name = in.readString();
        values = in.createTypedArrayList(Option.CREATOR);
    }

}
