package com.gooten.core.model;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;
import com.gooten.core.utils.JsonUtils;

public class ProductInfo implements Parcelable, JSONSerializable {

    public static int countWhereContentType(List<ProductInfo> productsInfoList, String contentType) {
        int count = 0;
        if (productsInfoList != null) {
            for (ProductInfo info : productsInfoList) {
                if (info != null && contentType.equalsIgnoreCase(info.contentType)) {
                    count++;
                }
            }
        }
        return count;
    }

    private String contentType;
    private String[] content;
    private int index;
    private String key;

    public ProductInfo() {
        // NOP
    }

    public ProductInfo(String contentType, String[] content, int index, String key) {
        this.contentType = contentType;
        this.content = content;
        this.index = index;
        this.key = key;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String[] getContent() {
        return content;
    }

    public void setContent(String[] content) {
        this.content = content;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductInfo that = (ProductInfo) o;

        if (index != that.index) return false;
        if (contentType != null ? !contentType.equals(that.contentType) : that.contentType != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(content, that.content)) return false;
        return key != null ? key.equals(that.key) : that.key == null;

    }

    @Override
    public int hashCode() {
        int result = contentType != null ? contentType.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(content);
        result = 31 * result + index;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        return result;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    private static final String JSON_INDEX = "Index";
    private static final String JSON_KEY = "Key";
    private static final String JSON_CONTENT = "Content";
    private static final String JSON_CONTENT_TYPE = "ContentType";


    @Override
    public void fromJSON(JSONObject json) {
        index = json.optInt(JSON_INDEX);
        key = json.optString(JSON_KEY);
        contentType = json.optString(JSON_CONTENT_TYPE);
        content = JsonUtils.stringArrayFromJson(json.optJSONArray(JSON_CONTENT));
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(JSON_CONTENT_TYPE, contentType);
            jsonObject.putOpt(JSON_INDEX, index);
            jsonObject.putOpt(JSON_KEY, key);
            if (content != null) {
                jsonObject.putOpt(JSON_CONTENT, JsonUtils.stringArrayToJson(content));
            }
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
        dest.writeString(contentType);
        dest.writeStringArray(content);
        dest.writeInt(index);
        dest.writeString(key);
    }

    public static final Creator<ProductInfo> CREATOR = new Creator<ProductInfo>() {

        public ProductInfo createFromParcel(Parcel source) {
            return new ProductInfo(source);
        }

        public ProductInfo[] newArray(int size) {
            return new ProductInfo[size];
        }

    };

    private ProductInfo(Parcel in) {
        contentType = in.readString();
        content = in.createStringArray();
        index = in.readInt();
        key = in.readString();
    }

}
