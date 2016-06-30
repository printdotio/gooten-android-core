package com.gooten.core.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gooten.core.utils.JsonUtils;

public class OrderPriceResult {

    public static OrderPriceResult fromJson(String jsonStr) {
        try {
            return new OrderPriceResult(new JSONObject(jsonStr));
        } catch (Exception e) {
            // NOP
        }
        return null;
    }

    private PriceInfo items;
    private PriceInfo shipping;
    private PriceInfo tax;
    private List<CouponData> coupons;
    private boolean hadCouponApply;
    private boolean hadError;

    public OrderPriceResult(JSONObject json) {
        hadCouponApply = json.optBoolean("HadCouponApply");
        hadError = json.optBoolean("HadError");
        items = JsonUtils.fromJson(PriceInfo.class, json.optJSONObject("Items"));
        shipping = JsonUtils.fromJson(PriceInfo.class, json.optJSONObject("Shipping"));
        tax = JsonUtils.fromJson(PriceInfo.class, json.optJSONObject("Tax"));

        JSONArray couponsArray = json.optJSONArray("Coupons");
        if (couponsArray != null) {
            coupons = new ArrayList<CouponData>(couponsArray.length());
            for (int i = 0; i < couponsArray.length(); i++) {
                CouponData couponData = CouponData.fromJsonObj(couponsArray.optJSONObject(i));
                if (couponData != null) {
                    coupons.add(couponData);
                }
            }
        }
        if (coupons == null) {
            coupons = new ArrayList<CouponData>(0);
        }
    }

    public PriceInfo getItems() {
        return items;
    }

    public void setItems(PriceInfo items) {
        this.items = items;
    }

    public PriceInfo getShipping() {
        return shipping;
    }

    public void setShipping(PriceInfo shipping) {
        this.shipping = shipping;
    }

    public PriceInfo getTax() {
        return tax;
    }

    public void setTax(PriceInfo tax) {
        this.tax = tax;
    }

    public List<CouponData> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<CouponData> coupons) {
        this.coupons = coupons;
    }

    public boolean isHadCouponApply() {
        return hadCouponApply;
    }

    public void setHadCouponApply(boolean hadCouponApply) {
        this.hadCouponApply = hadCouponApply;
    }

    public boolean isHadError() {
        return hadError;
    }

    public void setHadError(boolean hadError) {
        this.hadError = hadError;
    }

    public static class CouponData {

        public static CouponData findByCouponCode(List<CouponData> coupons, String couponCode) {
            if (coupons != null && couponCode != null) {
                for (CouponData coupon : coupons) {
                    if (coupon != null && coupon.couponInfo != null && couponCode.equals(coupon.couponInfo.couponCode)) {
                        return coupon;
                    }
                }
            }
            return null;
        }

        public static CouponData fromJsonObj(JSONObject json) {
            CouponData result = null;
            if (json != null) {
                result = new CouponData(json);
            }
            return result;
        }

        private PriceInfo couponSavings;
        private CouponInfo couponInfo;
        private Boolean applied;

        public CouponData(JSONObject json) {
            couponSavings = JsonUtils.fromJson(PriceInfo.class, json.optJSONObject("CouponSavings"));
            couponInfo = CouponInfo.fromJson(json.optJSONObject("CouponInfo"));
            applied = json.optBoolean("HadCouponApply");
        }

        public PriceInfo getCouponSavings() {
            return couponSavings;
        }

        public CouponInfo getCouponInfo() {
            return couponInfo;
        }

        public Boolean isApplied() {
            return applied;
        }

    }

    public static class CouponInfo {

        public static CouponInfo fromJson(JSONObject json) {
            CouponInfo result = null;
            if (json != null) {
                result = new CouponInfo(json);
            }
            return result;
        }

        private String couponCode;
        private String couponType;
        private int percentOff;
        private boolean isCouponSingleUse;
        private List<String> appliedToSkus;

        private CouponInfo(JSONObject json) {
            this.couponCode = json.optString("CouponCode");
            this.couponType = json.optString("CouponType");
            this.percentOff = json.optInt("PercentOff");
            this.isCouponSingleUse = json.optBoolean("IsCouponSingleUse");
            this.appliedToSkus = JsonUtils.stringListFromJson(json.optJSONArray("AppliedToSkus"), new ArrayList<String>(0));
        }

        public String getCouponCode() {
            return couponCode;
        }

        public String getCouponType() {
            return couponType;
        }

        public int getPercentOff() {
            return percentOff;
        }

        public boolean isCouponSingleUse() {
            return isCouponSingleUse;
        }

        public List<String> getAppliedToSkus() {
            return appliedToSkus;
        }

    }

}
