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
	public List<BuildingSearchResponse> searchBuildings(Map<String, String> params, List<String> types) {

		List<BuildingSearchResponse> responses = new ArrayList<BuildingSearchResponse>();

		if (params.isEmpty() && (types == null || types.isEmpty())) {
			return responses;
		}

		List<BuildingEntity> buildingEntitys = buildingRepository.searchBuildings(params, types);
		for (BuildingEntity entity : buildingEntitys) {
			BuildingSearchResponse response = buildingCoverter.covertToBuildingSearchResponseFromEnity(entity);
			responses.add(response);
		}

		return responses;
	}

}
