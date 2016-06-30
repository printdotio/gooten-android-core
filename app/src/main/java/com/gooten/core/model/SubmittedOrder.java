package com.gooten.core.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gooten.core.utils.JsonUtils;

public class SubmittedOrder {

    public static SubmittedOrder fromJson(String jsonStr) {
        try {
            return new SubmittedOrder(new JSONObject(jsonStr));
        } catch (Exception e) {
            // NOP
        }
        return null;
    }

    private static final String JSON_ID = "Id";
    private static final String JSON_ITEMS = "Items";
    private static final String JSON_TOTAL = "Total";

    private boolean isValidOrderId;
    private String id;
    private List<Item> items;
    private PriceInfo total;

    private SubmittedOrder(JSONObject json) throws JSONException {
        if (json.has(JSON_ID)) {
            isValidOrderId = true;
            id = json.getString(JSON_ID);
            total = JsonUtils.fromJson(PriceInfo.class, json.getJSONObject(JSON_TOTAL));
            JSONArray itemsArray = json.getJSONArray(JSON_ITEMS);
            items = new ArrayList<Item>(itemsArray.length());
            for (int i = 0; i < itemsArray.length(); i++) {
                items.add(new Item(itemsArray.getJSONObject(i)));
            }
        }
    }

    public boolean isValidOrderId() {
        return isValidOrderId;
    }

    public String getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public PriceInfo getTotal() {
        return total;
    }

    public static class Item {

        private static final String JSON_PRODUCT_ID = "ProductId";
        private static final String JSON_PRODUCT = "Product";
        private static final String JSON_QUANTITY = "Quantity";
        private static final String JSON_STATUS = "Status";
        private static final String JSON_PRICE = "Price";

        private int productId;
        private String productName;
        private int quantity;
        private String status;
        private PriceInfo price;

        private Item(JSONObject json) throws JSONException {
            productId = json.getInt(JSON_PRODUCT_ID);
            productName = json.getString(JSON_PRODUCT);
            quantity = json.getInt(JSON_QUANTITY);
            status = json.getString(JSON_STATUS);
            price = JsonUtils.fromJson(PriceInfo.class, json.getJSONObject(JSON_PRICE));
        }

        public int getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public int getQuantity() {
            return quantity;
        }

        public String getStatus() {
            return status;
        }

        public PriceInfo getPrice() {
            return price;
        }

    }

}
