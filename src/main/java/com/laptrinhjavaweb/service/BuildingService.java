package com.laptrinhjavaweb.service;

import java.util.List;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.BuildingSearchDTO;

public interface BuildingService {
	List<BuildingDTO> searchBuildings(BuildingSearchDTO buildingSearchDTO);
}
