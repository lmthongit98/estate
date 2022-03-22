package com.laptrinhjavaweb.service;

import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.dto.response.BuildingSearchResponse;

public interface BuildingService {
	List<BuildingSearchResponse> searchBuildings(Map<String, String> customQuery, List<String> types);
}
