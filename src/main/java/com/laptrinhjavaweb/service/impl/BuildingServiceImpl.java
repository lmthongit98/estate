package com.laptrinhjavaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.BuildingSearchDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.repository.BuildingRepository;
import com.laptrinhjavaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	private BuildingRepository buildingRepository;

	@Override
	public List<BuildingDTO> searchBuildings(BuildingSearchDTO buildingSearchDTO) {
		List<BuildingEntity> buildings = buildingRepository.searchBuildings(buildingSearchDTO);
		List<BuildingDTO> buildingDTOs = new ArrayList<BuildingDTO>();

		for (BuildingEntity building : buildings) {
			BuildingDTO dto = new BuildingDTO();
			dto.setId(building.getId());
			dto.setName(building.getName());
			dto.setNumOfBasement(building.getNumberOfBasement());
			dto.setAddress(building.getStreet() + ", " + building.getWard());
			dto.setFloorArea(building.getFloorArea());
			dto.setRentPrice(building.getRentPrice());
			dto.setDistrict(building.getDistrict().getName());
			dto.setAreas(building.getAreas().stream().map(area -> area.getValue()).collect(Collectors.toList()));
			dto.setTypes(building.getTypes().stream().map(type -> type.getName()).collect(Collectors.toList()));
			dto.setEmployees(building.getUsers().stream().map(user -> user.getFullName()).collect(Collectors.toList()));
			buildingDTOs.add(dto);
		}

		return buildingDTOs;
	}

}
