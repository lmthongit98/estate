package com.laptrinhjavaweb.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.response.BuildingSearchResponse;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.entity.DistrictEntity;
import com.laptrinhjavaweb.repository.DistrictRepository;

@Component
public class BuildingConverter {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private DistrictRepository districtRepository;

	public BuildingDTO convertToDTOFromEntity(BuildingEntity entity) {
		BuildingDTO dto = modelMapper.map(entity, BuildingDTO.class);
		return dto;
	}

	public BuildingSearchResponse covertToBuildingSearchResponseFromEnity(BuildingEntity entity) {
		BuildingSearchResponse response = modelMapper.map(entity, BuildingSearchResponse.class);
		response.setAddress(response.getStreet() + ", " + response.getWard() + ", " + entity.getDistrict().getName());
		return response;
	}

}
