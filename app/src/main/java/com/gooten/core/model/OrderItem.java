package com.gooten.core.model;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gooten.core.types.JSONSerializable;
import com.gooten.core.utils.JsonUtils;

public class OrderItem implements JSONSerializable {

    private Integer quantity;
    private String sku;
    private Integer shipCarrierMethodId;
    private List<Image> images;
    private AbstractMap<String, String> meta;

    public OrderItem() {
        // NOP
    }

    public OrderItem(Integer quantity, String sku) {
        this(quantity, sku, null);
    }

    public OrderItem(Integer quantity, String sku, Integer shipCarrierMethodId) {
        this.quantity = quantity;
        this.sku = sku;
        this.shipCarrierMethodId = shipCarrierMethodId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getShipCarrierMethodId() {
        return shipCarrierMethodId;
    }

    public void setShipCarrierMethodId(Integer shipCarrierMethodId) {
        this.shipCarrierMethodId = shipCarrierMethodId;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public AbstractMap<String, String> getMeta() {
        return meta;
    }

    public void setMeta(AbstractMap<String, String> meta) {
        this.meta = meta;
    }

    private void addMetaData(String key, String value) {
        if (meta == null) {
            meta = new LinkedHashMap<String, String>(1);
        }
        meta.put(key, value);
    }

    private String getMetaData(String key) {
        if (meta != null) {
            return meta.get(key);
        }
        return null;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    private static final String JSON_QUANTITY = "Quantity";
    private static final String JSON_SKU = "SKU";
    private static final String JSON_SHIP_CARRIER_METHOD_ID = "ShipCarrierMethodId";
    private static final String JSON_IMAGES = "Images";
    private static final String JSON_META = "Meta";

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(JSON_QUANTITY, quantity);
            jsonObject.put(JSON_SKU, sku);
            jsonObject.put(JSON_SHIP_CARRIER_METHOD_ID, shipCarrierMethodId);

            if (images != null) {
                JSONArray jsonArrayItems = new JSONArray();
                for (Image item : images) {
                    jsonArrayItems.put(item.toJson());
                }
                jsonObject.put(JSON_IMAGES, jsonArrayItems);
            }

            jsonObject.put(JSON_META, JsonUtils.stringMapToJson(meta));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void fromJSON(JSONObject json) {
        quantity = json.optInt(JSON_QUANTITY);
        sku = json.optString(JSON_SKU);
        shipCarrierMethodId = json.optInt(JSON_SHIP_CARRIER_METHOD_ID);
        images = new ArrayList<Image>();
        JSONArray imagesJsonArray = json.optJSONArray(JSON_IMAGES);
        if (imagesJsonArray != null) {
            int imagesCount = imagesJsonArray.length();
            for (int i = 0; i < imagesCount; i++) {
                JSONObject imageJson = imagesJsonArray.optJSONObject(i);
                if (imageJson != null) {
                    images.add(Image.fromJson(imageJson));
                }
            }
        }
        meta = JsonUtils.stringMapFromJson(json.optJSONObject(JSON_META));
    }

}
