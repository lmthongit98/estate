package com.laptrinhjavaweb.repository;

import java.util.List;
import com.laptrinhjavaweb.dto.BuildingSearchDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;

public interface BuildingRepository {
	List<BuildingEntity> searchBuildings(BuildingSearchDTO buildingSearchDTO);

	String findDistrictByBuidlingId(Long id);
}
