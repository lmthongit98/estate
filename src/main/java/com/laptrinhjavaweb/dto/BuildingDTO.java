package com.laptrinhjavaweb.dto;

import java.util.List;

public class BuildingDTO {

	private String name;
	private Integer numOfBasement;
	private String address;
	private List<String> types;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumOfBasement() {
		return numOfBasement;
	}

	public void setNumOfBasement(Integer numOfBasement) {
		this.numOfBasement = numOfBasement;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
