package com.laptrinhjavaweb.util;

import java.util.List;

public class ValidateUtil {
	public static boolean isNotBlank(String value) {
		return value != null && !value.isEmpty();
	}
	
	public static <T> boolean isNotBlank(List<T> list) {
		return list != null && !list.isEmpty();
	}
}
