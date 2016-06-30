package com.gooten.core.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.gooten.core.utils.JsonUtils;
import com.gooten.core.utils.StringUtils;

public class ProductVariantsResponse {

    public static ProductVariantsResponse fromJson(String jsonString) {
        if (StringUtils.isNotEmpty(jsonString)) {
            try {
                return new ProductVariantsResponse(new JSONObject(jsonString));
            } catch (Exception e) {
                // NOP
            }
        }
        return null;
    }

    private static final String JSON_PRODUCT_VARIANTS = "ProductVariants";
    private static final String JSON_OPTIONS = "Options";

    private List<ProductVariant> productVariants;
    private List<ProductOption> productOptions;

    private ProductVariantsResponse(JSONObject json) {
        productVariants = JsonUtils.fromJsonArray(ProductVariant.class, json.optJSONArray(JSON_PRODUCT_VARIANTS), new ArrayList<ProductVariant>(0));
        productOptions = JsonUtils.fromJsonArray(ProductOption.class, json.optJSONArray(JSON_OPTIONS), new ArrayList<ProductOption>(0));
    }

    public List<ProductVariant> getProductVariants() {
        return productVariants;
    }

    public List<ProductOption> getProductOptions() {
        return productOptions;
    }

}
