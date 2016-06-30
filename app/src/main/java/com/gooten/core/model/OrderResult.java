package com.gooten.core.model;

import java.util.List;

import org.json.JSONObject;

import com.gooten.core.utils.JsonUtils;

public class OrderResult {

    public static OrderResult fromJson(String jsonStr) {
        try {
            return new OrderResult(new JSONObject(jsonStr));
        } catch (Exception e) {
            // NOP
        }
        return null;
    }

    private String id;
    private boolean hadError;
    private List<Error> errors;

    private OrderResult(JSONObject json) {
        id = json.optString("Id");
        hadError = json.optBoolean("HadError");
        if (hadError) {
            errors = JsonUtils.fromJsonArray(Error.class, json.optJSONArray("Errors"));
        }
    }

    public String getId() {
        return id;
    }

    public boolean isHadError() {
        return hadError;
    }

    public List<Error> getErrors() {
        return errors;
    }

}
