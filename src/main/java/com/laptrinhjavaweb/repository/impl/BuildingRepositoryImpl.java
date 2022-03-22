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
	public List<BuildingEntity> searchBuildings(Map<String, String> customQuery, List<String> types) {
		StringBuilder sql = new StringBuilder(
				"SELECT distinct building.id as buildingid, building.name as buildingname,");
		sql.append(" street, ward, numberofbasement, floorarea, rentprice, managername, managerphone");
		sql.append(" FROM building");
		sql.append(" JOIN district ON building.districtid = district.id");
		sql.append(" JOIN rentarea ON building.id = rentarea.buildingid");
		sql.append(" LEFT JOIN buildingrenttype ON building.id = buildingrenttype.buildingid");
		sql.append(" LEFT JOIN renttype ON buildingrenttype.renttypeid = renttype.id");
		sql.append(" LEFT JOIN assignmentbuilding ON building.id = assignmentbuilding.buildingid");
		sql.append(" LEFT JOIN user ON assignmentbuilding.staffid = user.id");
		sql.append(" " + SystemContant.WHERE_TRUE);

		if (isValid(customQuery, "buildingname")) {
			sql.append(" AND building.name like '%" + customQuery.get("buildingName") + "%'");
		}

		if (isValid(customQuery, "floorArea")) {
			sql.append(" AND building.floorarea = " + Integer.parseInt(customQuery.get("floorArea")));
		}

		if (isValid(customQuery, "districtCode")) {
			sql.append(" AND district.code like '%" + customQuery.get("districtCode") + "%'");
		}

		if (isValid(customQuery, "street")) {
			sql.append(" AND building.street like '%" + customQuery.get("street") + "%'");
		}

		if (isValid(customQuery, "ward")) {
			sql.append(" AND building.ward like '%" + customQuery.get("ward") + "%'");
		}

		if (isValid(customQuery, "numOfBasement")) {
			sql.append(" AND building.numberofbasement = " + Integer.parseInt(customQuery.get("numOfBasement")));
		}

		if (isValid(customQuery, "employeeId")) {
			sql.append(" AND user.id = " + Integer.parseInt(customQuery.get("employeeId")));
		}
		
		if(isValid(customQuery, "managerName")) {
			sql.append(" AND building.managername like '%" + customQuery.get("managerName") + "%'");
		}
		
		if(isValid(customQuery, "managerPhone")) {
			sql.append(" AND building.managerphone like '%" + customQuery.get("managerPhone") + "%'");
		}

		if (types!= null && types.size() > 0) {
			sql.append(" AND (");
			for (int i = 0; i < types.size(); i++) {
				if (i >= types.size() - 1) {
					sql.append("renttype.code = '" + types.get(i) + "'");
				} else {
					sql.append("renttype.code = '" + types.get(i) + "' OR ");
				}
			}
			sql.append(")");
		}

		if (isValid(customQuery, "areaFrom") && isValid(customQuery, "areaTo")) {
			sql.append(" AND building.floorarea >= " + Integer.parseInt(customQuery.get("areaFrom")) + " AND building.floorarea <= "
					+ Integer.parseInt(customQuery.get("areaTo")));
		} else if (isValid(customQuery, "areaFrom") ) {
			sql.append(" AND building.floorarea >= " + Integer.parseInt(customQuery.get("areaFrom")));

		} else if (isValid(customQuery, "areaTo")) {
			sql.append(" AND building.floorarea <= " + Integer.parseInt(customQuery.get("areaTo")));
		}

		if (isValid(customQuery, "rentPriceFrom") && isValid(customQuery, "rentPriceFrom")) {
			sql.append(" AND building.rentprice >= " + Integer.parseInt(customQuery.get("rentPriceFrom"))
					+ " AND building.rentprice <= " + Integer.parseInt(customQuery.get("rentPriceTo")));
		} else if (isValid(customQuery, "rentPriceFrom")) {
			sql.append(" AND building.rentprice >= " + Integer.parseInt(customQuery.get("rentPriceFrom")));
		} else if (isValid(customQuery, "rentPriceFrom")) {
			sql.append(" AND building.rentprice <= " + Integer.parseInt(customQuery.get("rentPriceTo")));
		}

		String query = sql.toString();
		List<BuildingEntity> results = new ArrayList<BuildingEntity>();
		// Open a connection
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				BuildingEntity building = new BuildingEntity();
				building.setId(rs.getLong("buildingid"));
				building.setName(rs.getString("buildingname"));
				building.setNumberOfBasement(rs.getInt("numberofbasement"));
				building.setStreet(rs.getString("street"));
				building.setWard(rs.getString("ward"));
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
	public String findDistrictByBuidlingId(Long id) {
		StringBuilder sql = new StringBuilder("SELECT district.name as districtname FROM estatebasic.district");
		sql.append(" JOIN building ON district.id = building.districtid");
		sql.append(" WHERE building.id = " + id);
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());) {
			while (rs.next()) {
				return rs.getString("districtname");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private boolean isValid(Map<String, String> customQuery, String field) {
		return customQuery.containsKey(field) && !StringUtil.isNullOrEmpty(customQuery.get(field));
	}

}
