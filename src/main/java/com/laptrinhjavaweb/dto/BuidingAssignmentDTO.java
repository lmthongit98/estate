package com.laptrinhjavaweb.dto;

import java.util.List;

public class BuidingAssignmentDTO {
	private List<Long> ids;
	private Long buildingId;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}

}
