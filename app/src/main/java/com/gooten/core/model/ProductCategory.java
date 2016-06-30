package com.gooten.core.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;

public class ProductCategory implements Parcelable, JSONSerializable {

    public static void sortById(List<ProductCategory> categories) {
        Collections.sort(categories, new Comparator<ProductCategory>() {

            @Override
            public int compare(ProductCategory lhs, ProductCategory rhs) {
                if (lhs == null && rhs == null) {
                    return 0;
                } else if (lhs == null) {
                    return -rhs.id;
                } else if (rhs == null) {
                    return lhs.id;
                }
                return lhs.id - rhs.id;
            }
        });
    }

    public static ProductCategory findById(List<ProductCategory> categories, int id) {
        if (categories != null) {
            for (ProductCategory cat : categories) {
                if (cat != null && cat.id == id) {
                    return cat;
                }
            }
        }
        return null;
    }

    private int id;
    private String name;

    public ProductCategory() {
        // NOP
    }

    public ProductCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductCategory that = (ProductCategory) o;

        if (id != that.id) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    private static final String JSON_ID = "Id";
    private static final String JSON_NAME = "Name";

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(JSON_ID, id);
            jsonObject.putOpt(JSON_NAME, name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void fromJSON(JSONObject json) {
        id = json.optInt(JSON_ID);
        name = json.optString(JSON_NAME);
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
        dest.writeString(name);
    }

    public static final Creator<ProductCategory> CREATOR = new Creator<ProductCategory>() {

        public ProductCategory createFromParcel(Parcel source) {
            return new ProductCategory(source);
        }

        public ProductCategory[] newArray(int size) {
            return new ProductCategory[size];
        }

    };

    private ProductCategory(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

}