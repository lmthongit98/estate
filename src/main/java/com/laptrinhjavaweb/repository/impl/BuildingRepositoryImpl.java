package com.laptrinhjavaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
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
import com.laptrinhjavaweb.util.StringUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {

	private String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	private String USER = "root";
	private String PASS = "123456";

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
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
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

	@Override
	public String findDistrictById(Long districtId) {
		StringBuilder sql = new StringBuilder("SELECT d.name");
		sql.append(" FROM district d");
		sql.append(" WHERE d.id = " + districtId);
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());) {
			while (rs.next()) {
				return rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private boolean isValid(Map<String, String> params, String field) {
		return params.containsKey(field) && !StringUtil.isNullOrEmpty(params.get(field));
	}

	private void buildQueryWithoutJoin(Map<String, String> params, StringBuilder whereQuery) {
		whereQuery.append(" " + SystemContant.WHERE_TRUE);

		if (isValid(params, "buildingName")) {
			whereQuery.append(" AND b.name like '%" + params.get("buildingName") + "%'");
		}

		if (isValid(params, "floorArea")) {
			whereQuery.append(" AND b.floorarea = " + Integer.parseInt(params.get("floorArea")));
		}

		if (isValid(params, "street")) {
			whereQuery.append(" AND b.street like '%" + params.get("street") + "%'");
		}

		if (isValid(params, "ward")) {
			whereQuery.append(" AND b.ward like '%" + params.get("ward") + "%'");
		}

		if (isValid(params, "numberOfBasement")) {
			whereQuery.append(" AND b.numberofbasement = " + Integer.parseInt(params.get("numberOfBasement")));
		}

		if (isValid(params, "managerName")) {
			whereQuery.append(" AND b.managername like '%" + params.get("managerName") + "%'");
		}

		if (isValid(params, "managerPhone")) {
			whereQuery.append(" AND b.managerphone like '%" + params.get("managerPhone") + "%'");
		}

		if (isValid(params, "rentPriceFrom") && isValid(params, "rentPriceTo")) {
			whereQuery.append(" AND b.rentprice >= " + Integer.parseInt(params.get("rentPriceFrom"))
					+ " AND b.rentprice <= " + Integer.parseInt(params.get("rentPriceTo")));
		} else if (isValid(params, "rentPriceFrom")) {
			whereQuery.append(" AND b.rentprice >= " + Integer.parseInt(params.get("rentPriceFrom")));
		} else if (isValid(params, "rentPriceFrom")) {
			whereQuery.append(" AND b.rentprice <= " + Integer.parseInt(params.get("rentPriceTo")));
		}

	}

	private void buildQueryWithJoin(Map<String, String> params, List<String> types, StringBuilder whereQuery,
			StringBuilder joinQuery) {

		if (isValid(params, "districtCode")) {
			whereQuery.append(" AND district.code like '%" + params.get("districtCode") + "%'");
		}

		if (types != null && types.size() > 0) {
			joinQuery.append(" LEFT JOIN buildingrenttype ON b.id = buildingrenttype.buildingid");
			joinQuery.append(" LEFT JOIN renttype ON buildingrenttype.renttypeid = renttype.id");
			whereQuery.append(" AND (");
			for (int i = 0; i < types.size(); i++) {
				if (i >= types.size() - 1) {
					whereQuery.append("renttype.code = '" + types.get(i) + "'");
				} else {
					whereQuery.append("renttype.code = '" + types.get(i) + "' OR ");
				}
			}
			whereQuery.append(")");
		}

		if (isValid(params, "employeeId")) {
			joinQuery.append(" LEFT JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid");
			joinQuery.append(" LEFT JOIN user ON assignmentbuilding.staffid = user.id");
			whereQuery.append(" AND user.id = " + Integer.parseInt(params.get("employeeId")));
		}

		if (isValid(params, "areaFrom") && isValid(params, "areaTo")) {
			joinQuery.append(" JOIN rentarea ON b.id = rentarea.buildingid");
			whereQuery.append(" AND rentarea.value >= " + Integer.parseInt(params.get("areaFrom")) + " AND rentarea.value <= "
					+ Integer.parseInt(params.get("areaTo")));
		} else if (isValid(params, "areaFrom") ) {
			joinQuery.append(" JOIN rentarea ON b.id = rentarea.buildingid");
			whereQuery.append(" AND rentarea.value >= " + Integer.parseInt(params.get("areaFrom")));

		} else if (isValid(params, "areaTo")) {
			joinQuery.append(" JOIN rentarea ON b.id = rentarea.buildingid");
			whereQuery.append(" AND rentarea.value <= " + Integer.parseInt(params.get("areaTo")));
		}

	}

}
