package com.gooten.core.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;
import com.gooten.core.utils.JsonUtils;

public class ProductVariant implements Parcelable, JSONSerializable {

    public static ProductVariant findBySku(List<ProductVariant> variants, String sku) {
        if (variants != null && sku != null) {
            for (ProductVariant variant : variants) {
                if (variant != null && sku.equalsIgnoreCase(variant.sku)) {
                    return variant;
                }
            }
        }
        return null;
    }

    public static boolean haveSamePrice(List<ProductVariant> productVariants) {
        if (productVariants != null && !productVariants.isEmpty()) {
            double price = productVariants.get(0).priceInfo.getPrice();
            for (ProductVariant variant : productVariants) {
                if (variant != null && variant.priceInfo.getPrice() != price) {
                    return false;
                }
            }
        }
        return true;
    }

    private PriceInfo priceInfo;
    private String sku;
    private boolean hasTemplates;
    private int maxImages;
    private List<Option> options;

    public ProductVariant() {
        // NOP
    }

    public ProductVariant(PriceInfo priceInfo, String sku, boolean hasTemplates, int maxImages, List<Option> options) {
        this.priceInfo = priceInfo;
        this.sku = sku;
        this.hasTemplates = hasTemplates;
        this.maxImages = maxImages;
        this.options = options;
    }

    public PriceInfo getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(PriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public boolean isHasTemplates() {
        return hasTemplates;
    }

    public void setHasTemplates(boolean hasTemplates) {
        this.hasTemplates = hasTemplates;
    }

    public int getMaxImages() {
        return maxImages;
    }

    public void setMaxImages(int maxImages) {
        this.maxImages = maxImages;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductVariant that = (ProductVariant) o;

        if (hasTemplates != that.hasTemplates) return false;
        if (maxImages != that.maxImages) return false;
        if (priceInfo != null ? !priceInfo.equals(that.priceInfo) : that.priceInfo != null)
            return false;
        if (sku != null ? !sku.equals(that.sku) : that.sku != null) return false;
        return options != null ? options.equals(that.options) : that.options == null;

    }

    @Override
    public int hashCode() {
        int result = priceInfo != null ? priceInfo.hashCode() : 0;
        result = 31 * result + (sku != null ? sku.hashCode() : 0);
        result = 31 * result + (hasTemplates ? 1 : 0);
        result = 31 * result + maxImages;
        result = 31 * result + (options != null ? options.hashCode() : 0);
        return result;
    }
    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    private static final String JSON_OPTIONS = "Options";
    private static final String JSON_PRICE_INFO = "PriceInfo";
    private static final String JSON_SKU = "Sku";
    private static final String JSON_HAS_TEMPLATES = "HasTemplates";
    private static final String JSON_MAX_IMAGES = "MaxImages";

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            JsonUtils.putOpt(jsonObject, JSON_PRICE_INFO, priceInfo);
            jsonObject.putOpt(JSON_SKU, sku);
            jsonObject.putOpt(JSON_HAS_TEMPLATES, hasTemplates);
            jsonObject.putOpt(JSON_MAX_IMAGES, maxImages);
            JsonUtils.putOpt(jsonObject, JSON_OPTIONS, options);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void fromJSON(JSONObject json) {
        priceInfo = JsonUtils.fromJson(PriceInfo.class, json.optJSONObject(JSON_PRICE_INFO));
        sku = json.optString(JSON_SKU);
        hasTemplates = json.optBoolean(JSON_HAS_TEMPLATES);
        maxImages = json.optInt(JSON_MAX_IMAGES);
        options = JsonUtils.fromJsonArray(Option.class, json.optJSONArray(JSON_OPTIONS), new ArrayList<Option>(0));
    }

    // ==================================================================================
    // Methods to make class Parcelable
    // ==================================================================================

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel pc, int flags) {
        pc.writeTypedList(options);
        pc.writeParcelable(priceInfo, 0);
        pc.writeString(sku);
        pc.writeByte((byte) (hasTemplates ? 1 : 0));
        pc.writeInt(maxImages);
    }

    public static final Creator<ProductVariant> CREATOR = new Creator<ProductVariant>() {

        public ProductVariant createFromParcel(Parcel pc) {
            return new ProductVariant(pc);
        }

        public ProductVariant[] newArray(int size) {
            return new ProductVariant[size];
        }

    };

    private ProductVariant(Parcel pc) {
        options = pc.createTypedArrayList(Option.CREATOR);
        priceInfo = pc.readParcelable(PriceInfo.class.getClassLoader());
        sku = pc.readString();
        hasTemplates = (pc.readByte() == 1);
        maxImages = pc.readInt();
    }

}
