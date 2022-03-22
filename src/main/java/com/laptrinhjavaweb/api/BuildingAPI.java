package com.laptrinhjavaweb.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.request.BuildingAssignmentRequest;
import com.laptrinhjavaweb.dto.response.BuildingSearchResponse;
import com.laptrinhjavaweb.myexception.FieldRequiredException;
import com.laptrinhjavaweb.service.BuildingService;

@RestController
public class BuildingAPI {

	@Autowired
	private BuildingService buildingService;

	@GetMapping("/api/building/search")
	public List<BuildingSearchResponse> searchBuidlings(@RequestParam Map<String, String> customQuery, @RequestParam(required = false) List<String> types) {
		return buildingService.searchBuildings(customQuery, types);
	}

	@GetMapping("/api/building/{id}")
	public List<BuildingDTO> getBuidlingDetail(@PathVariable long id) {

		return null;
	}

	@PostMapping("/api/building")
	public BuildingDTO createBuilding(@RequestBody BuildingDTO buildingDTO) {
		try {
			validateBuilding(buildingDTO);
			return buildingDTO;
		} catch (FieldRequiredException e) {
			throw e;
		}
	}

	private void validateBuilding(BuildingDTO buildingDTO) {
		if (buildingDTO.getName() == null) {
			throw new FieldRequiredException("Building name is requied!");
		}
	}

	@PutMapping("/api/building")
	public BuildingDTO updateBuilding() {

		return null;
	}

	@DeleteMapping("/api/building")
	public void deleteBuilding(@RequestBody String[] ids) {
		System.out.println(ids);
	}
	
	@PostMapping("/api/building/assignment")
	public void assignBuildingToStaff(@RequestBody BuildingAssignmentRequest buildingAssignmentRequest) {
		
	}

}