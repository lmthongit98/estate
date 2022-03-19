package com.laptrinhjavaweb.service;

import java.util.List;

import com.laptrinhjavaweb.dto.request.BuildingSearchRequest;
import com.laptrinhjavaweb.dto.response.BuildingSearchResponse;

public interface BuildingService {
	List<BuildingSearchResponse> searchBuildings(BuildingSearchRequest buildingSearchRequest);
}
