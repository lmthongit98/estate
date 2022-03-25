package com.laptrinhjavaweb.repository;

import com.laptrinhjavaweb.entity.DistrictEntity;

public interface DistrictRepository {
	DistrictEntity findById(Long id);
}
