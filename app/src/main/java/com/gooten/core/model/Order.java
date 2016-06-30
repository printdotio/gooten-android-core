package com.gooten.core.model;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.gooten.core.utils.JsonUtils;

public class Order {

    public static final String META_SOURCE = "Source";
    public static final String META_VERSION = "Version";

    private static final String JSON_SHIP_TO_ADDRESS = "ShipToAddress";
    private static final String JSON_BILLING_ADDRESS = "BillingAddress";
    private static final String JSON_ITEMS = "Items";
    private static final String JSON_PAYMENT = "Payment";
    private static final String JSON_SOURCE_ID = "SourceId";
    private static final String JSON_IS_PRE_SUBMIT = "IsPreSubmit";
    private static final String JSON_COUPON_CODES = "CouponCodes";
    private static final String JSON_META = "Meta";
    private static final String JSON_IS_TEST = "isInTestMode";

    private Address shipToAddress;
    private Address billingAddress;
    private List<OrderItem> items;
    private Payment payment;
    private String sourceId;
    private boolean isPreSubmit;
    private boolean isTestingOrder;
    private List<String> couponCodes;
    private AbstractMap<String, String> meta;

    public Order() {
        // NOP
    }

    public Order(Address shipToAddress, Address billingAddress, List<OrderItem> items, Payment payment, boolean isPreSubmit, List<String> couponCodes, boolean isTestingOrder) {
        this.shipToAddress = shipToAddress;
        this.billingAddress = billingAddress;
        this.items = items;
        this.payment = payment;
        this.isPreSubmit = isPreSubmit;
        this.couponCodes = couponCodes;
        this.isTestingOrder = isTestingOrder;
    }

    private Order(JSONObject json) {
        shipToAddress = JsonUtils.fromJson(Address.class, json.optJSONObject(JSON_SHIP_TO_ADDRESS));
        billingAddress = JsonUtils.fromJson(Address.class, json.optJSONObject(JSON_BILLING_ADDRESS));
        payment = JsonUtils.fromJson(Payment.class, json.optJSONObject(JSON_PAYMENT));
        sourceId = json.optString(JSON_SOURCE_ID);
        isPreSubmit = json.optBoolean(JSON_IS_PRE_SUBMIT);
        couponCodes = JsonUtils.stringListFromJson(json.optJSONArray(JSON_COUPON_CODES));
        isTestingOrder = json.optBoolean(JSON_IS_TEST);
        meta = JsonUtils.stringMapFromJson(json.optJSONObject(JSON_META));
        items = JsonUtils.fromJsonArray(OrderItem.class, json.optJSONArray(JSON_ITEMS), new ArrayList<OrderItem>(0));
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            JsonUtils.putOpt(jsonObject, JSON_SHIP_TO_ADDRESS, shipToAddress);
            JsonUtils.putOpt(jsonObject, JSON_BILLING_ADDRESS, billingAddress);
            JsonUtils.putOpt(jsonObject, JSON_PAYMENT, payment);
            jsonObject.put(JSON_SOURCE_ID, sourceId);
            jsonObject.put(JSON_IS_PRE_SUBMIT, isPreSubmit);
            jsonObject.put(JSON_COUPON_CODES, JsonUtils.listToJson(couponCodes));
            jsonObject.put(JSON_META, JsonUtils.stringMapToJson(meta));
            jsonObject.put(JSON_IS_TEST, isTestingOrder);
            JsonUtils.putOpt(jsonObject, JSON_ITEMS, items);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public Address getShipToAddress() {
        return shipToAddress;
    }

    public void setShipToAddress(Address shipToAddress) {
        this.shipToAddress = shipToAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public boolean isPreSubmit() {
        return isPreSubmit;
    }

    public void setPreSubmit(boolean preSubmit) {
        isPreSubmit = preSubmit;
    }

    public boolean isTestingOrder() {
        return isTestingOrder;
    }

    public void setTestingOrder(boolean testingOrder) {
        isTestingOrder = testingOrder;
    }

    public List<String> getCouponCodes() {
        return couponCodes;
    }

    public void setCouponCodes(List<String> couponCodes) {
        this.couponCodes = couponCodes;
    }

    public AbstractMap<String, String> getMeta() {
        return meta;
    }

    public void setMeta(AbstractMap<String, String> meta) {
        this.meta = meta;
    }

    public void addMetaData(String key, String value) {
        if (meta == null) {
            meta = new LinkedHashMap<String, String>(1);
        }
        meta.put(key, value);
    }

}
