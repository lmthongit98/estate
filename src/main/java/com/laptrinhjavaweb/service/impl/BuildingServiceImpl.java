package com.laptrinhjavaweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.repository.BuildingRepository;
import com.laptrinhjavaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	private BuildingRepository buildingRepository;

	@Override
	public List<BuildingDTO> findAll() {
		List<BuildingEntity> buildings = buildingRepository.findAll();
		List<BuildingDTO> buildingDTOs = new ArrayList<BuildingDTO>();

		for (BuildingEntity building : buildings) {
			BuildingDTO dto = new BuildingDTO();
			dto.setName(building.getName());
			dto.setNumOfBasement(building.getNumberOfBasement());
			dto.setAddress(building.getStreet() + ", " + building.getWard());
			buildingDTOs.add(dto);
		}

		return buildingDTOs;
	}

}
