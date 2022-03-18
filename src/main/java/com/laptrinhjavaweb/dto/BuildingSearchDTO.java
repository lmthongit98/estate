package com.laptrinhjavaweb.dto;

import java.util.List;

public class BuildingSearchDTO {
	private String buildingName;
	private Integer floorArea;
	private String districtCode;
	private String street;
	private String ward;
	private Integer numOfBasement;
	private Integer areaFrom;
	private Integer areaTo;
	private Integer rentPriceTo;
	private Integer rentPriceFrom;
	private Long employeeId;
	private List<String> buildingTypeCode;

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public Integer getFloorArea() {
		return floorArea;
	}

	public void setFloorArea(Integer floorArea) {
		this.floorArea = floorArea;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public Integer getNumOfBasement() {
		return numOfBasement;
	}

	public void setNumOfBasement(Integer numOfBasement) {
		this.numOfBasement = numOfBasement;
	}

	public Integer getAreaFrom() {
		return areaFrom;
	}

	public void setAreaFrom(Integer areaFrom) {
		this.areaFrom = areaFrom;
	}

	public Integer getAreaTo() {
		return areaTo;
	}

	public void setAreaTo(Integer areaTo) {
		this.areaTo = areaTo;
	}

	public Integer getRentPriceTo() {
		return rentPriceTo;
	}

	public void setRentPriceTo(Integer rentPriceTo) {
		this.rentPriceTo = rentPriceTo;
	}

	public Integer getRentPriceFrom() {
		return rentPriceFrom;
	}

	public void setRentPriceFrom(Integer rentPriceFrom) {
		this.rentPriceFrom = rentPriceFrom;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public List<String> getBuildingTypeCode() {
		return buildingTypeCode;
	}

	public void setBuildingTypeCode(List<String> buildingTypeCode) {
		this.buildingTypeCode = buildingTypeCode;
	}

}
