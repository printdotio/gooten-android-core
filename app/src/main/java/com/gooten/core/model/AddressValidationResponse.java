package com.gooten.core.model;

import org.json.JSONObject;

import com.gooten.core.utils.JsonUtils;
import com.gooten.core.utils.StringUtils;

public class AddressValidationResponse {

    public static AddressValidationResponse fromJson(String jsonString) {
        if (StringUtils.isNotEmpty(jsonString)) {
            try {
                return new AddressValidationResponse(new JSONObject(jsonString));
            } catch (Exception e) {
                // NOP
            }
        }
        return null;
    }

    private boolean isValid;
    private String reason;
    private int score;
    private Address proposedAddress;

    private AddressValidationResponse(JSONObject json) {
        isValid = json.optBoolean("IsValid");
        reason = json.optString("Reason");
        score = json.optInt("Score");
        proposedAddress = JsonUtils.fromJson(Address.class, json.optJSONObject("ProposedAddress"));
    }

    public boolean isValid() {
        return isValid;
    }

    public String getReason() {
        return reason;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Address getProposedAddress() {
        return proposedAddress;
    }

}
