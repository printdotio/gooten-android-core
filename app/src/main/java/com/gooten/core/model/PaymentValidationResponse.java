package com.gooten.core.model;

import org.json.JSONObject;

public class PaymentValidationResponse {

    public static PaymentValidationResponse fromJson(String jsonStr) {
        try {
            return new PaymentValidationResponse(new JSONObject(jsonStr));
        } catch (Exception e) {
            // NOP
        }
        return null;
    }

    private boolean isValid;

    private PaymentValidationResponse(JSONObject json) {
        isValid = json.optBoolean("IsValid");
    }

    public boolean isValid() {
        return isValid;
    }

}
