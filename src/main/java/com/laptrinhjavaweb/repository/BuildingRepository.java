package com.laptrinhjavaweb.repository;

import java.util.List;

import com.laptrinhjavaweb.dto.request.BuildingSearchRequest;
import com.laptrinhjavaweb.entity.BuildingEntity;

public interface BuildingRepository {
	List<BuildingEntity> searchBuildings(BuildingSearchRequest buildingSearchDTO);

	String findDistrictByBuidlingId(Long id);
}
