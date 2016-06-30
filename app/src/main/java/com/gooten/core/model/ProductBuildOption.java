package com.gooten.core.model;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;
import com.gooten.core.utils.JsonUtils;

public class ProductBuildOption implements Parcelable, JSONSerializable {

    public static ProductBuildOption findByName(List<ProductBuildOption> options, String optionName) {
        if (options != null && optionName != null) {
            for (ProductBuildOption option : options) {
                if (option != null && optionName.equalsIgnoreCase(option.name)) {
                    return option;
                }
            }
        }
        return null;
    }

    private String name;
    private String imageUrl;
    private boolean isDefault;
    private List<Space> spaces;

    public ProductBuildOption(String name, String imageUrl, boolean isDefault, List<Space> spaces) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.isDefault = isDefault;
        this.spaces = spaces;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public List<Space> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<Space> spaces) {
        this.spaces = spaces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductBuildOption that = (ProductBuildOption) o;

        if (isDefault != that.isDefault) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null)
            return false;
        return spaces != null ? spaces.equals(that.spaces) : that.spaces == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (isDefault ? 1 : 0);
        result = 31 * result + (spaces != null ? spaces.hashCode() : 0);
        return result;
    }

    public int getTotalImageLayersCount() {
        int totalImageLayersCount = 0;
        for (Space spaceResponse : spaces) {
            totalImageLayersCount += spaceResponse.getImageLayersCount();
        }
        return totalImageLayersCount;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    private static final String JSON_NAME = "Name";
    private static final String JSON_IMAGE_URL = "ImageUrl";
    private static final String JSON_IS_DEFAULT = "IsDefault";
    private static final String JSON_SPACES = "Spaces";

    @Override
    public void fromJSON(JSONObject json) {
        name = json.optString(JSON_NAME);
        imageUrl = json.optString(JSON_IMAGE_URL);
        isDefault = json.optBoolean(JSON_IS_DEFAULT);
        spaces = JsonUtils.fromJsonArray(Space.class, json.optJSONArray(JSON_SPACES));
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(JSON_NAME, name);
            jsonObject.put(JSON_IMAGE_URL, imageUrl);
            jsonObject.put(JSON_IS_DEFAULT, isDefault);
            JsonUtils.putOpt(jsonObject, JSON_SPACES, spaces);
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
        dest.writeString(imageUrl);
        dest.writeByte((byte) (isDefault ? 1 : 0));
        dest.writeTypedList(spaces);
    }

    public static final Parcelable.Creator<ProductBuildOption> CREATOR = new Parcelable.Creator<ProductBuildOption>() {

        public ProductBuildOption createFromParcel(Parcel source) {
            return new ProductBuildOption(source);
        }

        public ProductBuildOption[] newArray(int size) {
            return new ProductBuildOption[size];
        }

    };

    private ProductBuildOption(Parcel in) {
        name = in.readString();
        imageUrl = in.readString();
        isDefault = in.readByte() == 1;
        spaces = in.createTypedArrayList(Space.CREATOR);
    }
}
