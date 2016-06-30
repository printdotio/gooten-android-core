package com.gooten.core.api.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import com.gooten.core.utils.StringUtils;
import com.gooten.core.utils.Utils;

/**
 * Encapsulates mechanism for caching API responses.
 * 
 * @author Vlado
 */
public class APICache {

   /**
    * Defines period (in millisecond) after which outdated cache entries are
    * cleared from cache.
    */
   private final long cacheClearPeriod = TimeUnit.MINUTES.toMillis(1);

   private HashMap<String, APICacheEntry> cache;
   private Runnable clearCacheTask;
   private long lastCacheClearTime;

   public APICache() {
      cache = new HashMap<String, APICacheEntry>();
      lastCacheClearTime = System.currentTimeMillis();
      clearCacheTask = new Runnable() {

         @Override
         public void run() {
            synchronized (cache) {
               Iterator<Entry<String, APICacheEntry>> iter = cache.entrySet().iterator();
               while (iter.hasNext()) {
                  Entry<String, APICacheEntry> entry = iter.next();
                  if (!entry.getValue().isValid()) {
                     iter.remove();
                  }
               }
            }
         }
      };
   }

   private void checkAndClear() {
      if (System.currentTimeMillis() > lastCacheClearTime + cacheClearPeriod) {
         lastCacheClearTime = System.currentTimeMillis();
         Utils.execute(clearCacheTask);
      }
   }

   protected APICacheEntry get(String key) {
      checkAndClear();
      synchronized (cache) {
         return cache.get(key);
      }
   }

   protected void put(String key, APICacheEntry entry) {
      synchronized (cache) {
         cache.put(key, entry);
      }
   }

   public void invalidate() {
      synchronized (cache) {
         cache.clear();
         lastCacheClearTime = System.currentTimeMillis();
      }
   }

   private static Map<String, APICacheEntry> deserialize(String jsonStr) {
      Map<String, APICacheEntry> result = null;
      if (StringUtils.isNotBlank(jsonStr)) {
         try {
            JSONObject mapJson = new JSONObject(jsonStr);
            result = new LinkedHashMap<String, APICacheEntry>(mapJson.length());
            Iterator<String> kIter = mapJson.keys();
            while (kIter.hasNext()) {
               String key = kIter.next();
               APICacheEntry entry = APICacheEntry.fromJson(mapJson.getJSONObject(key));
               if (entry != null) {
                  result.put(key, entry);
               }
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
      return result;
   }

   private static String serialize(Map<String, APICacheEntry> cache) {
      if (cache != null) {
         JSONObject object = new JSONObject();
         for (Entry<String, APICacheEntry> entry : cache.entrySet()) {
            try {
               object.put(entry.getKey(), entry.getValue().toJson());
            } catch (JSONException e) {
               e.printStackTrace();
            }
         }
         return object.toString();
      }
      return null;
   }

   /**
    * Loads data to this cache from the supplied {@code APICacheDataConserver}.
    */
   public void load(APICacheDataConserver conserver) {
      Map<String, APICacheEntry> thatCache = deserialize(conserver.loadCacheData());
      if (thatCache != null) {
         synchronized (cache) {
            for (Entry<String, APICacheEntry> entry : thatCache.entrySet()) {
               APICacheEntry entryVal = entry.getValue();
               if (entryVal.isValid()) {
                  APICacheEntry thisEntryVal = cache.get(entry.getKey());
                  if (thisEntryVal == null || entryVal.isLaterThan(thisEntryVal)) {
                     cache.put(entry.getKey(), entryVal);
                  }
               }
            }
         }
      }
   }

   /**
    * Stores this cache data to the supplied {@code APICacheDataConserver}.
    */
   public void store(APICacheDataConserver conserver) {
      synchronized (cache) {
         conserver.storeCacheData(serialize(cache));
      }
   }

   public APICacheEntryHolder getCache(long validity, String request) {
      return new APICacheEntryHolder(this, request, validity);
   }

}
