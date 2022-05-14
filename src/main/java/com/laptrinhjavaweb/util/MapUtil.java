package com.laptrinhjavaweb.util;

import java.util.Map;

public class MapUtil {
	@SuppressWarnings("unchecked")
	public static <T> T getValue(Map<String, String> params, String key, Class<T> tClass) {
		String value = params.getOrDefault(key, null);

		if (value == null || value.equals("")) {
			return null;
		}
		
		if (tClass.getName().equals("java.lang.Long")) {
			return (T) Long.valueOf(value);
		}
		
		if (tClass.getName().equals("java.lang.Integer")) {
			return (T) Integer.valueOf(value);
		}

		return (T) value;
	}
}
