package com.gooten.core.model;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;
import com.gooten.core.utils.JsonUtils;

public class Product implements Parcelable, JSONSerializable {

    public static Product findById(List<Product> products, int id) {
        if (products != null) {
            for (Product product : products) {
                if (product != null && product.id == id) {
                    return product;
                }
            }
        }
        return null;
    }

    private int id;
    private String name;
    private int price;
    private int minHeight;
    private int minWidth;
    private int minPhotos;
    private int maxPhotos;
    private boolean hasAvailableProductVariants;
    private boolean isFeatured;
    private boolean isComingSoon;
    private List<ProductInfo> info;
    private List<ProductImage> productImages;
    private PriceInfo priceInfo;
    private PriceInfo retailPrice;
    private List<ProductCategory> categories;

    public Product() {
        // NOP
    }

    public Product(int id, String name, int price, int minHeight, int minWidth, int minPhotos, int maxPhotos, boolean hasAvailableProductVariants, boolean isFeatured, boolean isComingSoon, List<ProductInfo> info, List<ProductImage> productImages, PriceInfo priceInfo, PriceInfo retailPrice, List<ProductCategory> categories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.minHeight = minHeight;
        this.minWidth = minWidth;
        this.minPhotos = minPhotos;
        this.maxPhotos = maxPhotos;
        this.hasAvailableProductVariants = hasAvailableProductVariants;
        this.isFeatured = isFeatured;
        this.isComingSoon = isComingSoon;
        this.info = info;
        this.productImages = productImages;
        this.priceInfo = priceInfo;
        this.retailPrice = retailPrice;
        this.categories = categories;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public int getMinPhotos() {
        return minPhotos;
    }

    public void setMinPhotos(int minPhotos) {
        this.minPhotos = minPhotos;
    }

    public int getMaxPhotos() {
        return maxPhotos;
    }

    public void setMaxPhotos(int maxPhotos) {
        this.maxPhotos = maxPhotos;
    }

    public boolean isHasAvailableProductVariants() {
        return hasAvailableProductVariants;
    }

    public void setHasAvailableProductVariants(boolean hasAvailableProductVariants) {
        this.hasAvailableProductVariants = hasAvailableProductVariants;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public boolean isComingSoon() {
        return isComingSoon;
    }

    public void setComingSoon(boolean comingSoon) {
        isComingSoon = comingSoon;
    }

    public List<ProductInfo> getInfo() {
        return info;
    }

    public void setInfo(List<ProductInfo> info) {
        this.info = info;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public PriceInfo getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(PriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    public PriceInfo getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(PriceInfo retailPrice) {
        this.retailPrice = retailPrice;
    }

    public List<ProductCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ProductCategory> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (price != product.price) return false;
        if (minHeight != product.minHeight) return false;
        if (minWidth != product.minWidth) return false;
        if (minPhotos != product.minPhotos) return false;
        if (maxPhotos != product.maxPhotos) return false;
        if (hasAvailableProductVariants != product.hasAvailableProductVariants) return false;
        if (isFeatured != product.isFeatured) return false;
        if (isComingSoon != product.isComingSoon) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (info != null ? !info.equals(product.info) : product.info != null) return false;
        if (productImages != null ? !productImages.equals(product.productImages) : product.productImages != null)
            return false;
        if (priceInfo != null ? !priceInfo.equals(product.priceInfo) : product.priceInfo != null)
            return false;
        if (retailPrice != null ? !retailPrice.equals(product.retailPrice) : product.retailPrice != null)
            return false;
        return categories != null ? categories.equals(product.categories) : product.categories == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + minHeight;
        result = 31 * result + minWidth;
        result = 31 * result + minPhotos;
        result = 31 * result + maxPhotos;
        result = 31 * result + (hasAvailableProductVariants ? 1 : 0);
        result = 31 * result + (isFeatured ? 1 : 0);
        result = 31 * result + (isComingSoon ? 1 : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (productImages != null ? productImages.hashCode() : 0);
        result = 31 * result + (priceInfo != null ? priceInfo.hashCode() : 0);
        result = 31 * result + (retailPrice != null ? retailPrice.hashCode() : 0);
        result = 31 * result + (categories != null ? categories.hashCode() : 0);
        return result;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    private static final String JSON_ID = "Id";
    private static final String JSON_NAME = "Name";
    private static final String JSON_MIN_HEIGHT = "MinHeight";
    private static final String JSON_MIN_WIDTH = "MinWidth";
    private static final String JSON_MIN_PHOTOS = "MinPhotos";
    private static final String JSON_MAX_PHOTOS = "MaxPhotos";
    private static final String JSON_HAS_AVAILABLE_PRODUCT_VARIANTS = "HasAvailableProductVariants";
    private static final String JSON_IS_FEATURED = "IsFeatured";
    private static final String JSON_IS_COMING_SOON = "IsComingSoon";
    private static final String JSON_INFO = "Info";
    private static final String JSON_IMAGES = "Images";
    private static final String JSON_CATEGORIES = "Categories";
    private static final String JSON_PRICE_INFO = "PriceInfo";
    private static final String JSON_RETAIL_PRICE = "RetailPrice";

    @Override
    public void fromJSON(JSONObject json) {
        id = json.optInt(JSON_ID);
        name = json.optString(JSON_NAME);
        minHeight = json.optInt(JSON_MIN_HEIGHT);
        minWidth = json.optInt(JSON_MIN_WIDTH);
        minPhotos = json.optInt(JSON_MIN_PHOTOS);
        maxPhotos = json.optInt(JSON_MAX_PHOTOS);
        hasAvailableProductVariants = json.optBoolean(JSON_HAS_AVAILABLE_PRODUCT_VARIANTS);
        isFeatured = json.optBoolean(JSON_IS_FEATURED);
        isComingSoon = json.optBoolean(JSON_IS_COMING_SOON);
        priceInfo = JsonUtils.fromJson(PriceInfo.class, json.optJSONObject(JSON_PRICE_INFO));
        retailPrice = JsonUtils.fromJson(PriceInfo.class, json.optJSONObject(JSON_RETAIL_PRICE));
        categories = JsonUtils.fromJsonArray(ProductCategory.class, json.optJSONArray(JSON_CATEGORIES));
        info = JsonUtils.fromJsonArray(ProductInfo.class, json.optJSONArray(JSON_INFO));
        productImages = JsonUtils.fromJsonArray(ProductImage.class, json.optJSONArray(JSON_INFO));
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(JSON_ID, id);
            jsonObject.putOpt(JSON_NAME, name);
            jsonObject.putOpt(JSON_MIN_HEIGHT, minHeight);
            jsonObject.putOpt(JSON_MIN_WIDTH, minWidth);
            jsonObject.putOpt(JSON_MIN_PHOTOS, minPhotos);
            jsonObject.putOpt(JSON_MAX_PHOTOS, maxPhotos);
            jsonObject.putOpt(JSON_HAS_AVAILABLE_PRODUCT_VARIANTS, hasAvailableProductVariants);
            jsonObject.putOpt(JSON_IS_FEATURED, isFeatured);
            jsonObject.putOpt(JSON_IS_COMING_SOON, isComingSoon);
            JsonUtils.putOpt(jsonObject, JSON_CATEGORIES, categories);
            JsonUtils.putOpt(jsonObject, JSON_PRICE_INFO, priceInfo);
            JsonUtils.putOpt(jsonObject, JSON_RETAIL_PRICE, retailPrice);
            JsonUtils.putOpt(jsonObject, JSON_INFO, info);
            JsonUtils.putOpt(jsonObject, JSON_IMAGES, productImages);
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
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeInt(minHeight);
        dest.writeInt(minWidth);
        dest.writeInt(minPhotos);
        dest.writeInt(maxPhotos);
        dest.writeByte((byte) (hasAvailableProductVariants ? 1 : 0));
        dest.writeByte((byte) (isFeatured ? 1 : 0));
        dest.writeByte((byte) (isComingSoon ? 1 : 0));
        dest.writeTypedList(info);
        dest.writeTypedList(productImages);
        dest.writeParcelable(priceInfo, 0);
        dest.writeParcelable(retailPrice, 0);
        dest.writeTypedList(categories);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {

        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }

    };

    private Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readInt();
        minHeight = in.readInt();
        minWidth = in.readInt();
        minPhotos = in.readInt();
        maxPhotos = in.readInt();
        hasAvailableProductVariants = (in.readByte() == 1);
        isFeatured = (in.readByte() == 1);
        isComingSoon = (in.readByte() == 1);
        info = in.createTypedArrayList(ProductInfo.CREATOR);
        productImages = in.createTypedArrayList(ProductImage.CREATOR);
        priceInfo = in.readParcelable(PriceInfo.class.getClassLoader());
        retailPrice = in.readParcelable(PriceInfo.class.getClassLoader());
        categories = in.createTypedArrayList(ProductCategory.CREATOR);
    }

}