package com.laptrinhjavaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.laptrinhjavaweb.constant.SystemContant;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.repository.BuildingRepository;
import com.laptrinhjavaweb.util.ConnectionUtil;
import com.laptrinhjavaweb.util.ValidateUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {

	@Override
	public List<BuildingEntity> searchBuildings(Map<String, String> params, List<String> types) {
		StringBuilder finalQuery = new StringBuilder();
		StringBuilder joinQuery = new StringBuilder();
		StringBuilder whereQuery = new StringBuilder();
		
		finalQuery.append("SELECT distinct b.id as buildingid, b.name as buildingname, b.districtid,")
		.append(" b.street, b.ward, b.numberofbasement, b.floorarea, b.rentprice, b.managername, b.managerphone")
		.append(" FROM building b")
		.append(" INNER JOIN district ON b.districtid = district.id");

		buildQueryWithJoin(params, types, whereQuery, joinQuery);
		buildQueryWithoutJoin(params, whereQuery);
		
		finalQuery.append(joinQuery).append(whereQuery);
		
		List<BuildingEntity> results = new ArrayList<BuildingEntity>();
		// Open a connection
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(finalQuery.toString());) {
			while (rs.next()) {
				BuildingEntity building = new BuildingEntity();
				building.setId(rs.getLong("buildingid"));
				building.setName(rs.getString("buildingname"));
				building.setNumberOfBasement(rs.getInt("numberofbasement"));
				building.setStreet(rs.getString("street"));
				building.setWard(rs.getString("ward"));
				building.setDistrictId(rs.getLong("districtid"));
				building.setFloorArea(rs.getInt("floorarea"));
				building.setRentPrice(rs.getInt("rentprice"));
				building.setManagerName(rs.getString("managername"));
				building.setManagerPhone(rs.getString("managerphone"));

				results.add(building);
			}

			return results;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void buildQueryWithoutJoin(Map<String, String> params, StringBuilder whereQuery) {
		
		String buildingName = params.getOrDefault("buildingName", null);
		if (ValidateUtil.isNotBlank(buildingName)) {
			whereQuery.append(" AND b.name like '%").append(buildingName).append("%'");
		}
		
		String floorArea = params.getOrDefault("floorArea", null);
		if (ValidateUtil.isNotBlank(floorArea)) {
			whereQuery.append(" AND b.floorarea = ").append(Integer.parseInt(floorArea));
		}

		String street = params.getOrDefault("street", null);
		if (ValidateUtil.isNotBlank(street)) {
			whereQuery.append(" AND b.street like '%").append(street).append("%'");
		}
		
		String ward = params.getOrDefault("ward", null);
		if (ValidateUtil.isNotBlank(ward)) {
			whereQuery.append(" AND b.ward like '%").append(ward).append("%'");
		}

		String numberOfBasement = params.getOrDefault("numberOfBasement", null);
		if (ValidateUtil.isNotBlank(numberOfBasement)) {
			whereQuery.append(" AND b.numberofbasement = ").append(Integer.parseInt(numberOfBasement));
		}
		
		String managerName = params.getOrDefault("managerName", null);
		if (ValidateUtil.isNotBlank(managerName)) {
			whereQuery.append(" AND b.managername like '%").append(managerName).append("%'");
		}

		String managerPhone = params.getOrDefault("managerPhone", null);
		if (ValidateUtil.isNotBlank(managerPhone)) {
			whereQuery.append(" AND b.managerphone like '%").append(managerPhone).append("%'");
		}
		
		String rentPriceFrom = params.getOrDefault("rentPriceFrom", null);
		String rentPriceTo = params.getOrDefault("rentPriceTo", null);	
		if(ValidateUtil.isNotBlank(rentPriceFrom)) {
			whereQuery.append(" AND b.rentprice >= ").append(Integer.parseInt(rentPriceFrom));
		}
		if(ValidateUtil.isNotBlank(rentPriceTo)) {
			whereQuery.append(" AND b.rentprice <= ").append(Integer.parseInt(rentPriceTo));
		}
	}

	private void buildQueryWithJoin(Map<String, String> params, List<String> types, StringBuilder whereQuery,
			StringBuilder joinQuery) {
		
		whereQuery.append(" " + SystemContant.WHERE_TRUE);
		
		String districtCode = params.getOrDefault("districtCode", null);
		if (ValidateUtil.isNotBlank(districtCode)) {
			whereQuery.append(" AND district.code like '%").append(districtCode).append("%'");
		}
		
		String employeeId = params.getOrDefault("employeeId", null);
		if (ValidateUtil.isNotBlank(employeeId)) {
			joinQuery.append(" INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid");
			joinQuery.append(" INNER JOIN user ON assignmentbuilding.staffid = user.id");
			whereQuery.append(" AND user.id = ").append(Integer.parseInt(employeeId));
		}

		if (ValidateUtil.<String>isNotBlank(types)) {
			joinQuery.append(" INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid")
			.append(" INNER JOIN renttype ON buildingrenttype.renttypeid = renttype.id");
	
			List<String> conditions = new ArrayList<String>();
			types.forEach(type -> conditions.add("renttype.code = '" + type + "'"));
			
			whereQuery.append(" AND (").append(String.join(" OR ", conditions)).append(")");
		}
		
		
		String areaFrom = params.getOrDefault("areaFrom", null);
		String areaTo = params.getOrDefault("areaTo", null);
		if(ValidateUtil.isNotBlank(areaFrom) || ValidateUtil.isNotBlank(areaTo)) {
			joinQuery.append(" JOIN rentarea ON b.id = rentarea.buildingid");
			if(ValidateUtil.isNotBlank(areaFrom)) {
				whereQuery.append(" AND rentarea.value >= ").append(Integer.parseInt(areaFrom));
			} 
			if(ValidateUtil.isNotBlank(areaTo)){
				whereQuery.append(" AND rentarea.value <= ").append(Integer.parseInt(areaTo));
			}
			
		}

	}

}
