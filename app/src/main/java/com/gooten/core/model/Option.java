package com.gooten.core.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;

public class Option implements Parcelable, JSONSerializable {

    public static Option findByName(List<Option> options, String name) {
        if (options != null && name != null) {
            for (Option option : options) {
                if (option != null && name.equalsIgnoreCase(option.name)) {
                    return option;
                }
            }
        }
        return null;
    }

    public static Option findByValue(List<Option> options, String value) {
        if (options != null && value != null) {
            for (Option option : options) {
                if (option != null && value.equalsIgnoreCase(option.value)) {
                    return option;
                }
            }
        }
        return null;
    }

    public static Option findOption(List<Option> options, Option searchOption) {
        if (options != null && searchOption != null) {
            for (Option option : options) {
                if (option != null && searchOption.optionId.equalsIgnoreCase(option.optionId) && searchOption.valueId.equalsIgnoreCase(option.valueId)) {
                    return option;
                }
            }
        }
        return null;
    }

    public static void sortBySortValue(List<Option> options) {
        Comparator<Option> sortValueComp = new Comparator<Option>() {

            @Override
            public int compare(Option lhs, Option rhs) {
                if (lhs == null && rhs == null) {
                    return 0;
                } else if (lhs == null) {
                    return -rhs.sortValue;
                } else if (rhs == null) {
                    return lhs.sortValue;
                }
                return lhs.sortValue - rhs.sortValue;
            }
        };
        Collections.sort(options, sortValueComp);
    }

    private String optionId;
    private String valueId;
    private String name;
    private String value;
    private String cmValue;
    private String imageUrl;
    private String imageType;
    private int sortValue;

    public Option() {
        // NOP
    }

    public Option(String optionId, String valueId, String name, String value, String cmValue, String imageUrl, String imageType, int sortValue) {
        this.optionId = optionId;
        this.valueId = valueId;
        this.name = name;
        this.value = value;
        this.cmValue = cmValue;
        this.imageUrl = imageUrl;
        this.imageType = imageType;
        this.sortValue = sortValue;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCmValue() {
        return cmValue;
    }

    public void setCmValue(String cmValue) {
        this.cmValue = cmValue;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public int getSortValue() {
        return sortValue;
    }

    public void setSortValue(int sortValue) {
        this.sortValue = sortValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        if (sortValue != option.sortValue) return false;
        if (optionId != null ? !optionId.equals(option.optionId) : option.optionId != null)
            return false;
        if (valueId != null ? !valueId.equals(option.valueId) : option.valueId != null)
            return false;
        if (name != null ? !name.equals(option.name) : option.name != null) return false;
        if (value != null ? !value.equals(option.value) : option.value != null) return false;
        if (cmValue != null ? !cmValue.equals(option.cmValue) : option.cmValue != null)
            return false;
        if (imageUrl != null ? !imageUrl.equals(option.imageUrl) : option.imageUrl != null)
            return false;
        return imageType != null ? imageType.equals(option.imageType) : option.imageType == null;

    }

    @Override
    public int hashCode() {
        int result = optionId != null ? optionId.hashCode() : 0;
        result = 31 * result + (valueId != null ? valueId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (cmValue != null ? cmValue.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (imageType != null ? imageType.hashCode() : 0);
        result = 31 * result + sortValue;
        return result;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    private static final String JSON_OPTION_ID = "OptionId";
    private static final String JSON_VALUE_ID = "ValueId";
    private static final String JSON_NAME = "Name";
    private static final String JSON_VALUE = "Value";
    private static final String JSON_IMAGE_URL = "ImageUrl";
    private static final String JSON_IMAGE_TYPE = "ImageType";
    private static final String JSON_CM_VALUE = "CmValue";
    private static final String JSON_SORT_VALUE = "SortValue";

    @Override
    public void fromJSON(JSONObject json) {
        optionId = json.optString(JSON_OPTION_ID);
        valueId = json.optString(JSON_VALUE_ID);
        name = json.optString(JSON_NAME);
        value = json.optString(JSON_VALUE);
        imageUrl = json.optString(JSON_IMAGE_URL);
        imageType = json.optString(JSON_IMAGE_TYPE);
        cmValue = json.optString(JSON_CM_VALUE);
        sortValue = json.optInt(JSON_SORT_VALUE);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(JSON_OPTION_ID, optionId);
            jsonObject.putOpt(JSON_VALUE_ID, valueId);
            jsonObject.putOpt(JSON_NAME, name);
            jsonObject.putOpt(JSON_VALUE, value);
            jsonObject.putOpt(JSON_IMAGE_URL, imageUrl);
            jsonObject.putOpt(JSON_IMAGE_TYPE, imageType);
            jsonObject.putOpt(JSON_CM_VALUE, cmValue);
            jsonObject.putOpt(JSON_SORT_VALUE, sortValue);
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
        dest.writeString(optionId);
        dest.writeString(valueId);
        dest.writeString(name);
        dest.writeString(value);
        dest.writeString(cmValue);
        dest.writeString(imageUrl);
        dest.writeString(imageType);
        dest.writeInt(sortValue);
    }

    public static final Creator<Option> CREATOR = new Creator<Option>() {

        public Option createFromParcel(Parcel source) {
            return new Option(source);
        }

        public Option[] newArray(int size) {
            return new Option[size];
        }

    };

    private Option(Parcel in) {
        optionId = in.readString();
        valueId = in.readString();
        name = in.readString();
        value = in.readString();
        cmValue = in.readString();
        imageUrl = in.readString();
        imageType = in.readString();
        sortValue = in.readInt();
    }

}
