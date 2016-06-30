package com.gooten.core.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.gooten.core.utils.JsonUtils;
import com.gooten.core.utils.StringUtils;

public class ShippingPricesResponse {

    public static ShippingPricesResponse fromJson(String jsonString) {
        if (StringUtils.isNotEmpty(jsonString)) {
            try {
                return new ShippingPricesResponse(new JSONObject(jsonString));
            } catch (Exception e) {
                // NOP
            }
        }
        return null;
    }

    private List<ShipItem> result;
    private boolean hadError;
    private List<Error> errors;

    public ShippingPricesResponse(JSONObject json) {
        hadError = json.optBoolean("HadError");
        if (hadError) {
            errors = JsonUtils.fromJsonArray(Error.class, json.optJSONArray("Errors"));
        }
        result = JsonUtils.fromJsonArray(ShipItem.class, json.optJSONArray("Result"), new ArrayList<ShipItem>(0));
    }

    public List<ShipItem> getResult() {
        return result;
    }

    public boolean isHadError() {
        return hadError;
    }

    public List<Error> getErrors() {
        return errors;
    }

}
