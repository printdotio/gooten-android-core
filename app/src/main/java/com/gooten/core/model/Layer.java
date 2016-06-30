package com.gooten.core.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;
import com.gooten.core.utils.StringUtils;

public class Layer implements Parcelable, JSONSerializable {

    public enum LayerType {
        IMAGE, DESIGN, TEXT
    }

    @SuppressLint("RtlHardcoded")
    public enum FontHAlignment {
        LEFT, CENTER, RIGHT
    }

    public enum FontVAlignment {
        TOP, MIDDLE, BOTTOM
    }

    private String id;
    private LayerType type;
    private int zIndex;
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private String color;
    private String fontName;
    private int fontSize;
    private FontHAlignment fontHAlignment;
    private FontVAlignment fontVAlignment;
    private String defaultText;
    private String backgroundImageUrl;
    private String overlayImageUrl;
    private boolean includeInPrint;

    public Layer(){
        // NOP
    }

    public Layer(String id, LayerType type, int zIndex, int x1, int x2, int y1, int y2, String color, String fontName, int fontSize, FontHAlignment fontHAlignment, FontVAlignment fontVAlignment, String defaultText, String backgroundImageUrl, String overlayImageUrl, boolean includeInPrint) {
        this.id = id;
        this.type = type;
        this.zIndex = zIndex;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.color = color;
        this.fontName = fontName;
        this.fontSize = fontSize;
        this.fontHAlignment = fontHAlignment;
        this.fontVAlignment = fontVAlignment;
        this.defaultText = defaultText;
        this.backgroundImageUrl = backgroundImageUrl;
        this.overlayImageUrl = overlayImageUrl;
        this.includeInPrint = includeInPrint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LayerType getType() {
        return type;
    }

    public void setType(LayerType type) {
        this.type = type;
    }

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public FontHAlignment getFontHAlignment() {
        return fontHAlignment;
    }

    public void setFontHAlignment(FontHAlignment fontHAlignment) {
        this.fontHAlignment = fontHAlignment;
    }

    public FontVAlignment getFontVAlignment() {
        return fontVAlignment;
    }

    public void setFontVAlignment(FontVAlignment fontVAlignment) {
        this.fontVAlignment = fontVAlignment;
    }

    public String getDefaultText() {
        return defaultText;
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public String getOverlayImageUrl() {
        return overlayImageUrl;
    }

    public void setOverlayImageUrl(String overlayImageUrl) {
        this.overlayImageUrl = overlayImageUrl;
    }

    public boolean isIncludeInPrint() {
        return includeInPrint;
    }

    public void setIncludeInPrint(boolean includeInPrint) {
        this.includeInPrint = includeInPrint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Layer layer = (Layer) o;

        if (zIndex != layer.zIndex) return false;
        if (x1 != layer.x1) return false;
        if (x2 != layer.x2) return false;
        if (y1 != layer.y1) return false;
        if (y2 != layer.y2) return false;
        if (fontSize != layer.fontSize) return false;
        if (includeInPrint != layer.includeInPrint) return false;
        if (id != null ? !id.equals(layer.id) : layer.id != null) return false;
        if (type != layer.type) return false;
        if (color != null ? !color.equals(layer.color) : layer.color != null) return false;
        if (fontName != null ? !fontName.equals(layer.fontName) : layer.fontName != null)
            return false;
        if (fontHAlignment != layer.fontHAlignment) return false;
        if (fontVAlignment != layer.fontVAlignment) return false;
        if (defaultText != null ? !defaultText.equals(layer.defaultText) : layer.defaultText != null)
            return false;
        if (backgroundImageUrl != null ? !backgroundImageUrl.equals(layer.backgroundImageUrl) : layer.backgroundImageUrl != null)
            return false;
        return overlayImageUrl != null ? overlayImageUrl.equals(layer.overlayImageUrl) : layer.overlayImageUrl == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + zIndex;
        result = 31 * result + x1;
        result = 31 * result + x2;
        result = 31 * result + y1;
        result = 31 * result + y2;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (fontName != null ? fontName.hashCode() : 0);
        result = 31 * result + fontSize;
        result = 31 * result + (fontHAlignment != null ? fontHAlignment.hashCode() : 0);
        result = 31 * result + (fontVAlignment != null ? fontVAlignment.hashCode() : 0);
        result = 31 * result + (defaultText != null ? defaultText.hashCode() : 0);
        result = 31 * result + (backgroundImageUrl != null ? backgroundImageUrl.hashCode() : 0);
        result = 31 * result + (overlayImageUrl != null ? overlayImageUrl.hashCode() : 0);
        result = 31 * result + (includeInPrint ? 1 : 0);
        return result;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    private static final String JSON_ID = "Id";
    private static final String JSON_TYPE = "Type";
    private static final String JSON_Z_INDEX = "ZIndex";
    private static final String JSON_X1 = "X1";
    private static final String JSON_Y1 = "Y1";
    private static final String JSON_X2 = "X2";
    private static final String JSON_Y2 = "Y2";
    private static final String JSON_COLOR = "Color";
    private static final String JSON_FONT_NAME = "FontName";
    private static final String JSON_FONT_SIZE = "FontSize";
    private static final String JSON_FONT_H_ALIGNMENT = "FontHAlignment";
    private static final String JSON_FONT_V_ALIGNMENT = "FontVAlignment";
    private static final String JSON_DEFAULT_TEXT = "DefaultText";
    private static final String JSON_BACKGROUND_IMAGE_URL = "BackgroundImageUrl";
    private static final String JSON_OVERLAY_IMAGE_URL = "OverlayImageUrl";
    private static final String JSON_INCLUDE_IN_PRINT = "IncludeInPrint";

    @Override
    public void fromJSON(JSONObject json) {
        id = json.optString(JSON_ID);
        type = LayerType.valueOf(StringUtils.toUpperCase(json.optString(JSON_TYPE)));
        zIndex = json.optInt(JSON_Z_INDEX);
        x1 = json.optInt(JSON_X1);
        y1 = json.optInt(JSON_Y1);
        x2 = json.optInt(JSON_X2);
        y2 = json.optInt(JSON_Y2);
        color = json.optString(JSON_COLOR);
        fontName = json.optString(JSON_FONT_NAME);
        fontSize = json.optInt(JSON_FONT_SIZE);
        fontHAlignment = FontHAlignment.valueOf(StringUtils.toUpperCase(json.optString(JSON_FONT_H_ALIGNMENT)));
        fontVAlignment = FontVAlignment.valueOf(StringUtils.toUpperCase(json.optString(JSON_FONT_V_ALIGNMENT)));
        defaultText = json.optString(JSON_DEFAULT_TEXT);
        backgroundImageUrl = json.optString(JSON_BACKGROUND_IMAGE_URL);
        overlayImageUrl = json.optString(JSON_OVERLAY_IMAGE_URL);
        includeInPrint = json.optBoolean(JSON_INCLUDE_IN_PRINT, type == LayerType.IMAGE || type == LayerType.IMAGE);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(JSON_ID, id);
            jsonObject.putOpt(JSON_TYPE, type.name());
            jsonObject.putOpt(JSON_Z_INDEX, zIndex);
            jsonObject.putOpt(JSON_X1, x1);
            jsonObject.putOpt(JSON_Y1, y1);
            jsonObject.putOpt(JSON_X2, x2);
            jsonObject.putOpt(JSON_Y2, y2);
            jsonObject.putOpt(JSON_COLOR, color);
            jsonObject.putOpt(JSON_FONT_NAME, fontName);
            jsonObject.putOpt(JSON_FONT_SIZE, fontSize);
            if (fontHAlignment != null) {
                jsonObject.putOpt(JSON_FONT_H_ALIGNMENT, fontHAlignment.name());
            }
            if (fontVAlignment != null) {
                jsonObject.putOpt(JSON_FONT_V_ALIGNMENT, fontVAlignment.name());
            }
            jsonObject.putOpt(JSON_DEFAULT_TEXT, defaultText);
            jsonObject.putOpt(JSON_BACKGROUND_IMAGE_URL, backgroundImageUrl);
            jsonObject.putOpt(JSON_OVERLAY_IMAGE_URL, overlayImageUrl);
            jsonObject.putOpt(JSON_INCLUDE_IN_PRINT, includeInPrint);
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
        dest.writeString(type != null ? type.name() : null);
        dest.writeInt(x1);
        dest.writeInt(x2);
        dest.writeInt(y1);
        dest.writeInt(y2);
        dest.writeString(color);
        dest.writeString(fontName);
        dest.writeInt(fontSize);
        dest.writeString(fontHAlignment != null ? fontHAlignment.name() : null);
        dest.writeString(fontVAlignment != null ? fontVAlignment.name() : null);
        dest.writeString(defaultText);
        dest.writeString(backgroundImageUrl);
        dest.writeString(overlayImageUrl);
        dest.writeByte((byte) (includeInPrint ? 1 : 0));
    }

    public static final Parcelable.Creator<Layer> CREATOR = new Parcelable.Creator<Layer>() {

        public Layer createFromParcel(Parcel source) {
            return new Layer(source);
        }

        public Layer[] newArray(int size) {
            return new Layer[size];
        }

    };

    private Layer(Parcel in) {
        id = in.readString();
        type = LayerType.valueOf(in.readString());
        x1 = in.readInt();
        x2 = in.readInt();
        y1 = in.readInt();
        y2 = in.readInt();
        color = in.readString();
        fontName = in.readString();
        fontSize = in.readInt();
        fontHAlignment = FontHAlignment.valueOf(in.readString());
        fontVAlignment = FontVAlignment.valueOf(in.readString());
        defaultText = in.readString();
        backgroundImageUrl = in.readString();
        overlayImageUrl = in.readString();
        includeInPrint = in.readByte() == 1;
    }
}
