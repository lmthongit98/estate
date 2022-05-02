package com.laptrinhjavaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laptrinhjavaweb.entity.DistrictEntity;

@Repository
public interface DistrictRepository extends JpaRepository<DistrictEntity, Long> {

}
