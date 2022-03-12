package com.laptrinhjavaweb.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.laptrinhjavaweb.dto.BuildingDTO;

@RestController
public class UserAPI {

	@GetMapping("/api/staffs")
	public List<BuildingDTO> getStaffs(@RequestParam("building_id") Long buildingId) {

		return null;
	}

}
