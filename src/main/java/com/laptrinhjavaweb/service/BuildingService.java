package com.laptrinhjavaweb.service;

import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.dto.response.BuildingSearchResponse;

public interface BuildingService {
	List<BuildingSearchResponse> findAll(Map<String, String> params, List<String> types);
}
