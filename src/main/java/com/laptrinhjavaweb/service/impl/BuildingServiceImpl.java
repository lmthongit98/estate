package com.laptrinhjavaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.dto.response.BuildingSearchResponse;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.repository.BuildingRepository;
import com.laptrinhjavaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired
	private BuildingConverter buildingCoverter;

	@Override
	public List<BuildingSearchResponse> searchBuildings(Map<String, String> customQuery, List<String> types) {
		List<BuildingEntity> buildingEntitys = buildingRepository.searchBuildings(customQuery, types);
		List<BuildingSearchResponse> responses = new ArrayList<BuildingSearchResponse>();

		for (BuildingEntity entity : buildingEntitys) {
			BuildingSearchResponse response = buildingCoverter.covertToBuildingSearchResponseFromEnity(entity);
			String district = buildingRepository.findDistrictByBuidlingId(entity.getId());
			response.setAddress(response.getAddress() + ", " + district);
			responses.add(response);
		}

		return responses;
	}

}
