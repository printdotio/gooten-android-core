package com.gooten.core.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;

public class Payment implements JSONSerializable, Parcelable {

    private String payPalPayKey;
    private String braintreeEnryptedCCNumber;
    private String braintreeEnryptedCCExpDate;
    private String braintreeEnryptedCVV;
    private Double total;
    private String currencyCode;

    public Payment() {
        // NOP
    }

    public Payment(String payPalPayKey, String braintreeEnryptedCCNumber, String braintreeEnryptedCCExpDate, String braintreeEnryptedCVV, double total, String currencyCode) {
        this.payPalPayKey = payPalPayKey;
        this.braintreeEnryptedCCNumber = braintreeEnryptedCCNumber;
        this.braintreeEnryptedCCExpDate = braintreeEnryptedCCExpDate;
        this.braintreeEnryptedCVV = braintreeEnryptedCVV;
        this.total = total;
        this.currencyCode = currencyCode;
    }

    public String getPayPalPayKey() {
        return payPalPayKey;
    }

    public void setPayPalPayKey(String payPalPayKey) {
        this.payPalPayKey = payPalPayKey;
    }

    public String getBraintreeEnryptedCCNumber() {
        return braintreeEnryptedCCNumber;
    }

    public void setBraintreeEnryptedCCNumber(String braintreeEnryptedCCNumber) {
        this.braintreeEnryptedCCNumber = braintreeEnryptedCCNumber;
    }

    public String getBraintreeEnryptedCCExpDate() {
        return braintreeEnryptedCCExpDate;
    }

    public void setBraintreeEnryptedCCExpDate(String braintreeEnryptedCCExpDate) {
        this.braintreeEnryptedCCExpDate = braintreeEnryptedCCExpDate;
    }

    public String getBraintreeEnryptedCVV() {
        return braintreeEnryptedCVV;
    }

    public void setBraintreeEnryptedCVV(String braintreeEnryptedCVV) {
        this.braintreeEnryptedCVV = braintreeEnryptedCVV;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (Double.compare(payment.total, total) != 0) return false;
        if (payPalPayKey != null ? !payPalPayKey.equals(payment.payPalPayKey) : payment.payPalPayKey != null)
            return false;
        if (braintreeEnryptedCCNumber != null ? !braintreeEnryptedCCNumber.equals(payment.braintreeEnryptedCCNumber) : payment.braintreeEnryptedCCNumber != null)
            return false;
        if (braintreeEnryptedCCExpDate != null ? !braintreeEnryptedCCExpDate.equals(payment.braintreeEnryptedCCExpDate) : payment.braintreeEnryptedCCExpDate != null)
            return false;
        if (braintreeEnryptedCVV != null ? !braintreeEnryptedCVV.equals(payment.braintreeEnryptedCVV) : payment.braintreeEnryptedCVV != null)
            return false;
        return currencyCode != null ? currencyCode.equals(payment.currencyCode) : payment.currencyCode == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = payPalPayKey != null ? payPalPayKey.hashCode() : 0;
        result = 31 * result + (braintreeEnryptedCCNumber != null ? braintreeEnryptedCCNumber.hashCode() : 0);
        result = 31 * result + (braintreeEnryptedCCExpDate != null ? braintreeEnryptedCCExpDate.hashCode() : 0);
        result = 31 * result + (braintreeEnryptedCVV != null ? braintreeEnryptedCVV.hashCode() : 0);
        temp = Double.doubleToLongBits(total);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (currencyCode != null ? currencyCode.hashCode() : 0);
        return result;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("PayPalPayKey", payPalPayKey);
            jsonObject.put("BraintreeEncryptedCCNumber", braintreeEnryptedCCNumber);
            jsonObject.put("BraintreeEncryptedCCExpDate", braintreeEnryptedCCExpDate);
            jsonObject.put("BraintreeEncryptedCCV", braintreeEnryptedCVV); //Typo is on server; should be CVV
            jsonObject.put("CurrencyCode", currencyCode);
            if (total != null) {
                jsonObject.put("Total", total);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void fromJSON(JSONObject json) {
        payPalPayKey = json.optString("PayPalPayKey");
        braintreeEnryptedCCNumber = json.optString("BraintreeEncryptedCCNumber");
        braintreeEnryptedCCExpDate = json.optString("BraintreeEncryptedCCExpDate");
        braintreeEnryptedCVV = json.optString("BraintreeEncryptedCCV"); //Typo is on server; should be CVV
        currencyCode = json.optString("CurrencyCode");
        if (json.has("Total")) {
            total = json.optDouble("Total");
        }
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
        dest.writeString(payPalPayKey);
        dest.writeString(braintreeEnryptedCCNumber);
        dest.writeString(braintreeEnryptedCCExpDate);
        dest.writeString(braintreeEnryptedCVV);
        dest.writeString(currencyCode);
        dest.writeDouble(total != null ? total : -1);
    }

    public static final Parcelable.Creator<Payment> CREATOR = new Parcelable.Creator<Payment>() {

        public Payment createFromParcel(Parcel source) {
            return new Payment(source);
        }

        public Payment[] newArray(int size) {
            return new Payment[size];
        }

    };

    private Payment(Parcel in) {
        payPalPayKey = in.readString();
        braintreeEnryptedCCNumber = in.readString();
        braintreeEnryptedCCExpDate = in.readString();
        braintreeEnryptedCVV = in.readString();
        currencyCode = in.readString();
        total = in.readDouble();
        if (total == -1) {
            total = null;
        }
    }

}
