package com.gooten.core.model;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;
import com.gooten.core.utils.JsonUtils;

public class Space implements Parcelable, JSONSerializable {

    private String id;
    private int index;
    private int finalX1;
    private int finalX2;
    private int finalY1;
    private int finalY2;
    private int defaultRotation;
    private String description;
    private List<Layer> layers;

    public Space(){
        // NOP
    }

    public Space(String id, int index, int finalX1, int finalX2, int finalY1, int finalY2, int defaultRotation, String description, List<Layer> layers) {
        this.id = id;
        this.index = index;
        this.finalX1 = finalX1;
        this.finalX2 = finalX2;
        this.finalY1 = finalY1;
        this.finalY2 = finalY2;
        this.defaultRotation = defaultRotation;
        this.description = description;
        this.layers = layers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getFinalX1() {
        return finalX1;
    }

    public void setFinalX1(int finalX1) {
        this.finalX1 = finalX1;
    }

    public int getFinalX2() {
        return finalX2;
    }

    public void setFinalX2(int finalX2) {
        this.finalX2 = finalX2;
    }

    public int getFinalY1() {
        return finalY1;
    }

    public void setFinalY1(int finalY1) {
        this.finalY1 = finalY1;
    }

    public int getFinalY2() {
        return finalY2;
    }

    public void setFinalY2(int finalY2) {
        this.finalY2 = finalY2;
    }

    public int getDefaultRotation() {
        return defaultRotation;
    }

    public void setDefaultRotation(int defaultRotation) {
        this.defaultRotation = defaultRotation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void setLayers(List<Layer> layers) {
        this.layers = layers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Space space = (Space) o;

        if (index != space.index) return false;
        if (finalX1 != space.finalX1) return false;
        if (finalX2 != space.finalX2) return false;
        if (finalY1 != space.finalY1) return false;
        if (finalY2 != space.finalY2) return false;
        if (defaultRotation != space.defaultRotation) return false;
        if (id != null ? !id.equals(space.id) : space.id != null) return false;
        if (description != null ? !description.equals(space.description) : space.description != null)
            return false;
        return layers != null ? layers.equals(space.layers) : space.layers == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + index;
        result = 31 * result + finalX1;
        result = 31 * result + finalX2;
        result = 31 * result + finalY1;
        result = 31 * result + finalY2;
        result = 31 * result + defaultRotation;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (layers != null ? layers.hashCode() : 0);
        return result;
    }

    public int getImageLayersCount() {
        int result = layers.size();
        for (Layer layerResponse : layers) {
            if (layerResponse.getType() != Layer.LayerType.IMAGE) {
                result--;
            }
        }
        return result;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    private static final String JSON_ID = "Id";
    private static final String JSON_INDEX = "Index";
    private static final String JSON_FINAL_X1 = "FinalX1";
    private static final String JSON_FINAL_X2 = "FinalX2";
    private static final String JSON_FINAL_Y1 = "FinalY1";
    private static final String JSON_FINAL_Y2 = "FinalY2";
    private static final String JSON_DEFAULT_ROTATION = "DefaultRotation";
    private static final String JSON_DESCRIPTION = "Description";
    private static final String JSON_LAYERS = "Layers";

    @Override
    public void fromJSON(JSONObject json) {
        id = json.optString(JSON_ID);
        index = json.optInt(JSON_INDEX);
        finalX1 = json.optInt(JSON_FINAL_X1);
        finalX2 = json.optInt(JSON_FINAL_X2);
        finalY1 = json.optInt(JSON_FINAL_Y1);
        finalY2 = json.optInt(JSON_FINAL_Y2);
        defaultRotation = json.optInt(JSON_DEFAULT_ROTATION);
        description = json.optString(JSON_DESCRIPTION);
        layers = JsonUtils.fromJsonArray(Layer.class, json.optJSONArray(JSON_LAYERS));
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(JSON_ID, id);
            jsonObject.putOpt(JSON_INDEX, index);
            jsonObject.put(JSON_FINAL_X1, finalX1);
            jsonObject.put(JSON_FINAL_X2, finalX2);
            jsonObject.put(JSON_FINAL_Y1, finalY1);
            jsonObject.put(JSON_FINAL_Y2, finalY2);
            jsonObject.putOpt(JSON_DEFAULT_ROTATION, defaultRotation);
            jsonObject.putOpt(JSON_DESCRIPTION, description);
            JsonUtils.putOpt(jsonObject, JSON_LAYERS, layers);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
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
        dest.writeString(id);
        dest.writeInt(index);
        dest.writeInt(finalX1);
        dest.writeInt(finalX2);
        dest.writeInt(finalY1);
        dest.writeInt(finalY2);
        dest.writeInt(defaultRotation);
        dest.writeString(description);
        dest.writeTypedList(layers);
    }

    public static final Parcelable.Creator<Space> CREATOR = new Parcelable.Creator<Space>() {

        public Space createFromParcel(Parcel source) {
            return new Space(source);
        }

        public Space[] newArray(int size) {
            return new Space[size];
        }

    };

    private Space(Parcel in) {
        id = in.readString();
        index = in.readInt();
        finalX1 = in.readInt();
        finalX2 = in.readInt();
        finalY1 = in.readInt();
        finalY2 = in.readInt();
        defaultRotation = in.readInt();
        description = in.readString();
        layers = in.createTypedArrayList(Layer.CREATOR);
    }
}
