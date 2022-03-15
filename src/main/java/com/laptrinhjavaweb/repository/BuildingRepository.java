package com.laptrinhjavaweb.repository;

import java.util.List;

import com.laptrinhjavaweb.entity.BuildingEntity;

public interface BuildingRepository {
	List<BuildingEntity> findAll();
}
