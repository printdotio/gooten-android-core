package com.gooten.core.api.cache;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Cache entry.
 * 
 * @author Vlado
 */
public class APICacheEntry {

   public static APICacheEntry fromJson(JSONObject json) {
      APICacheEntry result = null;
      if (json != null) {
         try {
            return new APICacheEntry(json);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
      return result;
   }

   private final String data;
   private final long creationTime;
   private final long validityDuration;

   APICacheEntry(String data, long validityDuration) {
      this.data = data;
      this.creationTime = System.currentTimeMillis();
      this.validityDuration = validityDuration;
   }

   APICacheEntry(JSONObject json) throws JSONException {
      data = json.getString("data");
      creationTime = json.getLong("creationTime");
      validityDuration = json.getLong("validityDuration");
   }

   public String get() {
      return data;
   }

   public boolean isValid() {
      return creationTime + validityDuration > System.currentTimeMillis();
   }

   protected boolean isLaterThan(APICacheEntry that) {
      return creationTime > that.creationTime;
   }

   public JSONObject toJson() {
      JSONObject json = new JSONObject();
      try {
         json.put("data", data);
         json.put("creationTime", creationTime);
         json.put("validityDuration", validityDuration);
      } catch (JSONException e) {
         e.printStackTrace();
      }
      return json;
   }

}
