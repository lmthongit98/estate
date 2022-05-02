package com.laptrinhjavaweb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "building")
public class BuildingEntity extends BaseEntity {
	@Column(name = "name")
	private String name;
	
	@Column(name = "street")
	private String street;
	
	@Column(name = "ward")
	private String ward;
	
	@Column(name = "districtid")
	private Long districtId;
	
	@Column(name = "stucture")
	private String stucture;
	
    @Column(name = "numberofbasement")
	private Integer numberOfBasement;
    
    @Column(name = "floorarea")
	private Integer floorArea;
    
    @Column(name = "managername")
	private String managerName;
    
    @Column(name = "managerphone")
	private String managerPhone;
    
    @Column(name = "rentprice")
	private Integer rentPrice;
    
//	private String direction;
//	private String level;
//	private String rentPriceDescription;
//	private String serviceFee;
//	private String carFee;
//	private String motorbikeFee;
//	private String overtimeFee;
//	private String waterFee;
//	private String electricityFee;
//	private String deposit;
//	private String payment;
//	private String rentTime;
//	private String decorationTime;
//	private Integer brokerAgeFee;
//	private String note;
//	private String linkOfBuilding;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumberOfBasement() {
		return numberOfBasement;
	}

	public void setNumberOfBasement(Integer numberOfBasement) {
		this.numberOfBasement = numberOfBasement;
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

	public String getStucture() {
		return stucture;
	}

	public void setStucture(String stucture) {
		this.stucture = stucture;
	}

	public Integer getFloorArea() {
		return floorArea;
	}

	public void setFloorArea(Integer floorArea) {
		this.floorArea = floorArea;
	}

	public Integer getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(Integer rentPrice) {
		this.rentPrice = rentPrice;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerPhone() {
		return managerPhone;
	}

	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

}
