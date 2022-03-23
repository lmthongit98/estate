package com.laptrinhjavaweb.repository;

import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.entity.BuildingEntity;

public interface BuildingRepository {
	List<BuildingEntity> searchBuildings(Map<String, String> params, List<String> types);

	String findDistrictById(Long districtId);
}
