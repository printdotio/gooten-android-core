package com.gooten.core;

import org.json.JSONException;
import org.json.JSONObject;

import com.gooten.core.types.Environment;
import com.gooten.core.types.JSONSerializable;

/**
 * Holds Gooten Core SDK configuration.
 */
public class GTNConfig implements JSONSerializable {

    private String recipeId;
    private boolean allProductsAndVariants;
    private Environment environment;
    private String countryCode;
    private String currencyCode;
    private String languageCode;

    /**
     * Constructs new Gooten SDK configuration with default values.
     */
    public GTNConfig() {
        loadDefaults();
    }

    /**
     * Loads default values to this {@code GTNConfig}.
     */
    public void loadDefaults() {
        recipeId = null;
        allProductsAndVariants = false;
        environment = Environment.LIVE;
        countryCode = null;
        currencyCode = null;
        languageCode = null;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) {
        recipeId = jsonObject.optString("recipeId", null);
        allProductsAndVariants = jsonObject.optBoolean("allProductsAndVariants", false);
        environment = Environment.valueOf(jsonObject.optString("environment", Environment.LIVE.name()));
        countryCode = jsonObject.optString("countryCode", null);
        currencyCode = jsonObject.optString("currencyCode", null);
        languageCode = jsonObject.optString("languageCode", null);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt("recipeId", recipeId);
            jsonObject.putOpt("allProductsAndVariants", allProductsAndVariants);
            jsonObject.putOpt("environment", environment != null ? environment.name() : null);
            jsonObject.putOpt("countryCode", countryCode);
            jsonObject.putOpt("currencyCode", currencyCode);
            jsonObject.putOpt("languageCode", languageCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /* --- Properties --- */

    public String getRecipeId() {
        return recipeId;
    }

    /**
     * Sets the partner's Gooten API key, provided by
     * <a href="www.gooten.com">Gooten</a>. <br/>
     * <br/>
     * <b>NOTE:</b> These keys must be set in order to start GTN SDK.
     *
     * @param recipeId The partner's API_KEY.
     */
    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public boolean isAllProductsAndVariants() {
        return allProductsAndVariants;
    }

    public void setAllProductsAndVariants(boolean allProductsAndVariants) {
        this.allProductsAndVariants = allProductsAndVariants;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /* --- Properties END --- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GTNConfig gtnConfig = (GTNConfig) o;

        if (allProductsAndVariants != gtnConfig.allProductsAndVariants) return false;
        if (recipeId != null ? !recipeId.equals(gtnConfig.recipeId) : gtnConfig.recipeId != null)
            return false;
        if (environment != gtnConfig.environment) return false;
        if (countryCode != null ? !countryCode.equals(gtnConfig.countryCode) : gtnConfig.countryCode != null)
            return false;
        if (currencyCode != null ? !currencyCode.equals(gtnConfig.currencyCode) : gtnConfig.currencyCode != null)
            return false;
        return languageCode != null ? languageCode.equals(gtnConfig.languageCode) : gtnConfig.languageCode == null;

    }

    @Override
    public int hashCode() {
        int result = recipeId != null ? recipeId.hashCode() : 0;
        result = 31 * result + (allProductsAndVariants ? 1 : 0);
        result = 31 * result + (environment != null ? environment.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (currencyCode != null ? currencyCode.hashCode() : 0);
        result = 31 * result + (languageCode != null ? languageCode.hashCode() : 0);
        return result;
    }
}
