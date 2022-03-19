package com.laptrinhjavaweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.BuildingSearchDTO;
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
	public List<BuildingDTO> searchBuildings(BuildingSearchDTO buildingSearchDTO) {
		List<BuildingEntity> buildings = buildingRepository.searchBuildings(buildingSearchDTO);
		List<BuildingDTO> buildingDTOs = new ArrayList<BuildingDTO>();

		for (BuildingEntity building : buildings) {
			BuildingDTO dto = buildingCoverter.convertToDTOFromEntity(building);
			String district = buildingRepository.findDistrictByBuidlingId(building.getId());
			dto.setAddress(dto.getAddress() + ", " + district);
			buildingDTOs.add(dto);
		}

		return buildingDTOs;
	}

}
