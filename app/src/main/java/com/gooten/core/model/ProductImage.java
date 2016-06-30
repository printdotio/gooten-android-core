package com.gooten.core.model;

import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;
import com.gooten.core.utils.JsonUtils;

public class ProductImage implements Parcelable, JSONSerializable {

    private String url;
    private String desc;
    private int index;
    private int id;
    private boolean isPrimary;
    private int fixedHeight;
    private int fixedWidth;
    private String[] types;

    public ProductImage() {
        // NOP
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public int getFixedHeight() {
        return fixedHeight;
    }

    public void setFixedHeight(int fixedHeight) {
        this.fixedHeight = fixedHeight;
    }

    public int getFixedWidth() {
        return fixedWidth;
    }

    public void setFixedWidth(int fixedWidth) {
        this.fixedWidth = fixedWidth;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductImage that = (ProductImage) o;

        if (index != that.index) return false;
        if (id != that.id) return false;
        if (isPrimary != that.isPrimary) return false;
        if (fixedHeight != that.fixedHeight) return false;
        if (fixedWidth != that.fixedWidth) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;
        return Arrays.equals(types, that.types);

    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + index;
        result = 31 * result + id;
        result = 31 * result + (isPrimary ? 1 : 0);
        result = 31 * result + fixedHeight;
        result = 31 * result + fixedWidth;
        result = 31 * result + Arrays.hashCode(types);
        return result;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    private static final String JSON_URL = "Url";
    private static final String JSON_DESCRIPTION = "Description";
    private static final String JSON_IMAGE_TYPES = "ImageTypes";
    private static final String JSON_FIXED_HEIGHT = "FixedHeight";
    private static final String JSON_FIXED_WIDTH = "FixedWidth";

    @Override
    public void fromJSON(JSONObject json) {
        url = json.optString(JSON_URL);
        desc = json.optString(JSON_DESCRIPTION);
        types = JsonUtils.stringArrayFromJson(json.optJSONArray(JSON_IMAGE_TYPES));
        fixedHeight = json.optInt(JSON_FIXED_HEIGHT);
        fixedWidth = json.optInt(JSON_FIXED_WIDTH);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(JSON_URL, url);
            jsonObject.putOpt(JSON_DESCRIPTION, desc);
            if (types != null) {
                jsonObject.putOpt(JSON_IMAGE_TYPES, JsonUtils.stringArrayToJson(types));
            }
            jsonObject.putOpt(JSON_FIXED_HEIGHT, fixedHeight);
            jsonObject.putOpt(JSON_FIXED_WIDTH, fixedWidth);
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
        dest.writeString(url);
        dest.writeString(desc);
        dest.writeInt(index);
        dest.writeInt(id);
        dest.writeByte((byte) (isPrimary ? 1 : 0));
        dest.writeInt(fixedHeight);
        dest.writeInt(fixedWidth);
        dest.writeStringArray(types);
    }

    public static final Creator<ProductImage> CREATOR = new Creator<ProductImage>() {

        public ProductImage createFromParcel(Parcel source) {
            return new ProductImage(source);
        }

        public ProductImage[] newArray(int size) {
            return new ProductImage[size];
        }

    };

    private ProductImage(Parcel in) {
        url = in.readString();
        desc = in.readString();
        index = in.readInt();
        id = in.readInt();
        isPrimary = (in.readByte() == 1);
        fixedHeight = in.readInt();
        fixedWidth = in.readInt();
        types = in.createStringArray();
    }

}