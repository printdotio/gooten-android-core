package com.gooten.core.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Image {

    public static Image fromJson(JSONObject json) {
        try {
            return new Image(json);
        } catch (Exception e) {
            // NOP
        }
        return null;
    }

    private String url;
    private int index;
    private String thumbnailUrl;
    private String manipCommand;
    private String spaceId;

    public Image() {
        // NOP
    }

    public Image(int index, String thumbnailUrl, String manipCommand, String spaceId) {
        this.index = index;
        this.thumbnailUrl = thumbnailUrl;
        this.manipCommand = manipCommand;
        this.spaceId = spaceId;
    }

    private Image(JSONObject json) {
        url = json.optString("Url");
        index = json.optInt("Index");
        thumbnailUrl = json.optString("ThumbnailUrl");
        manipCommand = json.optString("ManipCommand");
        spaceId = json.optString("SpaceId");
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Url", url);
            jsonObject.put("Index", index);
            jsonObject.put("ThumbnailUrl", thumbnailUrl);
            jsonObject.put("ManipCommand", manipCommand);
            jsonObject.put("SpaceId", spaceId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getManipCommand() {
        return manipCommand;
    }

    public void setManipCommand(String manipCommand) {
        this.manipCommand = manipCommand;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }
}
