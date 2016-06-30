package com.gooten.core.utils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gooten.core.types.JSONSerializable;

/**
 * Holds JSON transformation utilities.
 *
 * @author Vlado
 */
public class JsonUtils {

    public static <T extends JSONSerializable> T fromJsonString(Class<T> clazz, String json) {
        if (json != null) {
            try {
                return fromJson(clazz, new JSONObject(json));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T extends JSONSerializable> T fromJson(Class<T> clazz, JSONObject json) {
        if (json != null) {
            try {
                T val = clazz.newInstance();
                val.fromJSON(json);
                return val;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T extends JSONSerializable> List<T> fromJsonArray(Class<T> clazz, JSONArray array) {
        return fromJsonArray(clazz, array, null);
    }

    public static <T extends JSONSerializable> List<T> fromJsonArray(Class<T> clazz, JSONArray array, List<T> defaultTo) {
        List<T> result = defaultTo;
        if (array != null) {
            result = new ArrayList<T>(array.length());
            for (int i = 0; i < array.length(); i++) {
                T val = fromJson(clazz, array.optJSONObject(i));
                if (val != null) {
                    result.add(val);
                }
            }
        }
        return result;
    }


    public static void putOpt(JSONObject json, String key, JSONSerializable object) {
        if (object != null) {
            try {
                json.putOpt(key, object.toJSON());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T extends JSONSerializable> void putOpt(JSONObject json, String key, List<T> objects) {
        if (objects != null) {
            try {
                JSONArray jsonArrayItems = new JSONArray();
                for (T item : objects) {
                    jsonArrayItems.put(item.toJSON());
                }
                json.putOpt(key, jsonArrayItems);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates {@code JSONObject} with keys value pairs mapped by
     * {@code AbstractMap}.
     *
     * @param map The {@code AbstractMap} that will be transformed to
     *            {@code JSONObject}.
     * @return New {@code JSONObject} with keys value pairs mapped by
     * {@code AbstractMap}, or {@code null} if supplied {@code map} is
     * {@code null}.
     * @see #stringMapFromJson(JSONObject)
     */
    public static JSONObject stringMapToJson(AbstractMap<String, String> map) {
        JSONObject result = null;
        if (map != null) {
            result = new JSONObject();
            try {
                for (Entry<String, String> entry : map.entrySet()) {
                    result.put(entry.getKey(), entry.getValue());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Creates {@code AbstractMap} with keys value pairs held by
     * {@code JSONObject}'s properties.
     *
     * @param object The {@code JSONObject} that will be transformed to
     *               {@code AbstractMap}
     * @return New {@code AbstractMap} with keys value pairs held by
     * {@code JSONObject}'s properties, or {@code null} if supplied
     * {@code object} is {@code null}.
     * @see #stringMapFromJsonStr(String)
     */
    public static AbstractMap<String, String> stringMapFromJson(JSONObject object) {
        AbstractMap<String, String> map = null;
        if (object != null) {
            map = new HashMap<String, String>(object.length());
            try {
                @SuppressWarnings("unchecked")
                Iterator<String> iter = object.keys();
                while (iter.hasNext()) {
                    String key = iter.next();
                    map.put(key, object.getString(key));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * Creates {@code AbstractMap} with keys value pairs held by
     * {@code JSONObject}'s properties.
     *
     * @param str The JSON string of {@code JSONObject} that will be transformed
     *            to {@code AbstractMap}
     * @return New {@code AbstractMap} with keys value pairs held by
     * {@code JSONObject}'s properties, or {@code null} if supplied
     * {@code object} is {@code null}.
     * @see #stringMapFromJson(JSONObject)
     */
    public static AbstractMap<String, String> stringMapFromJsonStr(String str) {
        try {
            return stringMapFromJson(new JSONObject(str));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates {@code JSONArray} holding elements from supplied {@code List
     * <Object>}. {@code List<Object>} elements are transformed by calling
     * {@code toString()} upon them.
     *
     * @param list The {@code List<Object>} that will be transformed to
     *             {@code JSONArray}
     * @return New {@code JSONArray} holding elements from supplied
     * {@code List<Object>}, or {@code null} if supplied {@code list} is
     * {@code null}.
     */
    public static JSONArray listToJson(List<? extends Object> list) {
        JSONArray result = null;
        if (list != null) {
            result = new JSONArray();
            for (Object val : list) {
                result.put(val.toString());
            }
        }
        return result;
    }

    /**
     * Creates {@code List<String>} holding elements from supplied
     * {@code JSONArray}.
     *
     * @param array The {@code JSONArray} that will be transformed to
     *              {@code List<String>}.
     * @return New {@code List<String>} from supplied {@code JSONArray}, or
     * {@code null} if supplied {@code array} is {@code null}.
     */
    public static List<String> stringListFromJson(JSONArray array) {
        return stringListFromJson(array, null);
    }

    /**
     * Creates {@code List<String>} holding elements from supplied
     * {@code JSONArray}.
     *
     * @param array     The {@code JSONArray} that will be transformed to
     *                  {@code List<String>}.
     * @param defaultTo The default value returned if supplied {@code JSONArray} is
     *                  {@code null};
     * @return New {@code List<String>} from supplied {@code JSONArray}, or
     * {@code null} if supplied {@code array} is {@code null}.
     */
    public static List<String> stringListFromJson(JSONArray array, List<String> defaultTo) {
        List<String> result = defaultTo;
        if (array != null) {
            int count = array.length();
            result = new ArrayList<String>(count);
            for (int i = 0; i < count; i++) {
                try {
                    result.add(array.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String[] stringArrayFromJson(JSONArray array) {
        String[] result = null;
        if (array != null) {
            int count = array.length();
            result = new String[count];
            for (int i = 0; i < count; i++) {
                result[i] = array.optString(i);
            }
        }
        return result;
    }

    public static JSONArray stringArrayToJson(String[] strings) {
        JSONArray result = null;
        if (strings != null) {
            result = new JSONArray();
            for (String val : strings) {
                result.put(val);
            }
        }
        return result;
    }

}
