package com.laptrinhjavaweb.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.request.BuildingAssignmentRequest;
import com.laptrinhjavaweb.dto.response.BuildingSearchResponse;
import com.laptrinhjavaweb.myexception.FieldRequiredException;
import com.laptrinhjavaweb.service.BuildingService;

@RestController
@RequestMapping("/api/building")
public class BuildingAPI {

	@Autowired
	private BuildingService buildingService;
	
	@GetMapping
	public List<BuildingSearchResponse> findAll(
	@RequestParam(required = false) Map<String, String> params, @RequestParam(required = false) List<String> types) {
		return buildingService.findAll(params, types);
	}

	@GetMapping("/{id}")
	public List<BuildingDTO> getBuildingDetail(@PathVariable long id) {

		return null;
	}

	@PostMapping
	public BuildingDTO createBuilding(@RequestBody BuildingDTO buildingDTO) {
		try {
			validateBuilding(buildingDTO);
			return buildingDTO;
		} catch (FieldRequiredException e) {
			throw e;
		}
	}

	@PutMapping
	public BuildingDTO updateBuilding() {

		return null;
	}

	@DeleteMapping
	public void deleteBuilding(@RequestBody String[] ids) {
		System.out.println(ids);
	}
	
	@PostMapping("/assignment")
	public void assignBuildingToStaff(@RequestBody BuildingAssignmentRequest buildingAssignmentRequest) {
		
	}

	private void validateBuilding(BuildingDTO buildingDTO) {
		if (buildingDTO.getName() == null) {
			throw new FieldRequiredException("Building name is required!");
		}
	}

}