package com.laptrinhjavaweb.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.laptrinhjavaweb.entity.RentAreaEntity;
import com.laptrinhjavaweb.enums.BuildingTypesEnum;
import com.laptrinhjavaweb.enums.DistrictsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.dto.response.BuildingSearchResponse;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.repository.BuildingRepository;
import com.laptrinhjavaweb.service.BuildingService;
import com.laptrinhjavaweb.util.MapUtil;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	private BuildingRepository buildingRepository;

	@Autowired
	private BuildingConverter buildingConverter;


	@Override
	public Map<String, String> getDistricts() {
		Map<String, String> districts = new HashMap<>();
		for (DistrictsEnum item: DistrictsEnum.values()) {
			districts.put(item.toString(), item.getDistrictValue());
		}
		return districts;
	}

	@Override
	public Map<String, String> getBuildingTypes() {
		Map<String, String> buildingTypes = new HashMap<>();
		for (BuildingTypesEnum item: BuildingTypesEnum.values()) {
			buildingTypes.put(item.toString(), item.getBuildingTypeValue());
		}
		return buildingTypes;
	}

	@Transactional
	@Override
	public List<BuildingSearchResponse> findAll(Map<String, String> params, List<String> types) {
		List<BuildingSearchResponse> responses = new ArrayList<>();

		BuildingSearchBuilder builder = convertParamsToBuilder(params, types);
		
		List<BuildingEntity> buildingEntities = buildingRepository.findByConditions(builder);
		for (BuildingEntity entity : buildingEntities) {
			BuildingSearchResponse response = buildingConverter.covertToBuildingSearchResponseFromEnity(entity);

			response.setAddress(response.getAddress() + ", " + getDistricts().getOrDefault(entity.getDistrict(), null));

			Set<RentAreaEntity> rentAreas = entity.getRentAreas();
			if(rentAreas != null && !rentAreas.isEmpty()) {
				String rentAreaStr = rentAreas.stream()
						.map(RentAreaEntity::getValue)
						.collect(Collectors.joining(", "));
				response.setRentArea(rentAreaStr);
			}
			responses.add(response);
		}

		return responses;
	}

	private BuildingSearchBuilder convertParamsToBuilder(Map<String, String> params, List<String> types) {
		String name =  MapUtil.getValue(params, "name", String.class);
		String street = MapUtil.getValue(params, "street", String.class);
		Integer numberOfBasement = MapUtil.getValue(params, "numberOfBasement", Integer.class);
		Integer floorArea = MapUtil.getValue(params, "floorArea", Integer.class);
		String districtCode = MapUtil.getValue(params, "districtCode", String.class);
		Long staffId = MapUtil.getValue(params, "staffId", Long.class);
		String managerName = MapUtil.getValue(params, "managerName", String.class);
		Integer rentPriceFrom = MapUtil.getValue(params, "rentPriceFrom", Integer.class);
		Integer rentPriceTo = MapUtil.getValue(params, "rentPriceTo", Integer.class);
		Integer areaFrom = MapUtil.getValue(params, "areaFrom", Integer.class);
		Integer areaTo = MapUtil.getValue(params, "areaTo", Integer.class);


		return new BuildingSearchBuilder.Builder()
										.setName(name)
										.setStreet(street)
										.setNumberOfBasement(numberOfBasement)
										.setBuildingArea(floorArea)
										.setDistrict(districtCode)
										.setStaffId(staffId)
										.setManagerName(managerName)
										.setCostRentFrom(rentPriceFrom)
										.setCostRentTo(rentPriceTo)
										.setAreaRentFrom(areaFrom)
										.setAreaRentTo(areaTo)
										.setBuildingTypes(types)
										.build();
	}

}
