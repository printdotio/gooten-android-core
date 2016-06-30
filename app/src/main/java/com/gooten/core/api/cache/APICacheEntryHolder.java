package com.gooten.core.api.cache;

/**
 * Holds cache entry for unique API request.
 * 
 * @author Vlado
 */
public class APICacheEntryHolder {

	private final String key;
	private final APICache cache;
	private final Long validityDuration;
	private APICacheEntry entry;

	protected APICacheEntryHolder(APICache cache, String key, Long validityDuration) {
		this.cache = cache;
		this.key = key;
		this.validityDuration = validityDuration;
		entry = cache.get(key);
	}

	public String get() {
		return entry.get();
	}

	public boolean isHit() {
		return entry != null && entry.isValid();
	}

	public void put(String object) {
		cache.put(key, new APICacheEntry(object, validityDuration));
	}

}
