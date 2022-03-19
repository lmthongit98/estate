package com.laptrinhjavaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.laptrinhjavaweb.dto.request.BuildingSearchRequest;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.repository.BuildingRepository;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {

	private String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	private String USER = "root";
	private String PASS = "123456";

	@Override
	public List<BuildingEntity> searchBuildings(BuildingSearchRequest buildingSearchRequest) {
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

		if (buildingSearchRequest.getBuildingName() == null) {
			buildingSearchRequest.setBuildingName("");
		}
		sql.append(" WHERE building.name like '%" + buildingSearchRequest.getBuildingName() + "%'");

		if (buildingSearchRequest.getFloorArea() != null) {
			sql.append(" AND building.floorarea = " + buildingSearchRequest.getFloorArea());
		}

		if (buildingSearchRequest.getDistrictCode() != null) {
			sql.append(" AND district.code like '%" + buildingSearchRequest.getDistrictCode() + "%'");
		}

		if (buildingSearchRequest.getStreet() != null) {
			sql.append(" AND building.street like '%" + buildingSearchRequest.getStreet() + "%'");
		}

		if (buildingSearchRequest.getWard() != null) {
			sql.append(" AND building.ward like '%" + buildingSearchRequest.getWard() + "%'");
		}

		if (buildingSearchRequest.getNumOfBasement() != null) {
			sql.append(" AND building.numberofbasement = '" + buildingSearchRequest.getNumOfBasement() + "'");
		}

		if (buildingSearchRequest.getEmployeeId() != null) {
			sql.append(" AND user.id = " + buildingSearchRequest.getEmployeeId());
		}
		
		if(buildingSearchRequest.getManagerName() != null) {
			sql.append(" AND building.managername like '%" + buildingSearchRequest.getManagerName() + "%'");
		}
		
		if(buildingSearchRequest.getManagerPhone() != null) {
			sql.append(" AND building.managerphone like '%" + buildingSearchRequest.getManagerPhone() + "%'");
		}

		if (buildingSearchRequest.getBuildingTypeCodes() != null && buildingSearchRequest.getBuildingTypeCodes().size() > 0) {
			sql.append(" AND (");
			for (int i = 0; i < buildingSearchRequest.getBuildingTypeCodes().size(); i++) {
				if (i >= buildingSearchRequest.getBuildingTypeCodes().size() - 1) {
					sql.append("renttype.code = '" + buildingSearchRequest.getBuildingTypeCodes().get(i) + "'");
				} else {
					sql.append("renttype.code = '" + buildingSearchRequest.getBuildingTypeCodes().get(i) + "' OR ");
				}
			}
			sql.append(")");
		}

		if (buildingSearchRequest.getAreaFrom() != null && buildingSearchRequest.getAreaTo() != null) {
			sql.append(" AND building.floorarea >= " + buildingSearchRequest.getAreaFrom() + " AND building.floorarea <= "
					+ buildingSearchRequest.getAreaTo());
		} else if (buildingSearchRequest.getAreaFrom() != null) {
			sql.append(" AND building.floorarea >= " + buildingSearchRequest.getAreaFrom());

		} else if (buildingSearchRequest.getAreaTo() != null) {
			sql.append(" AND building.floorarea <= " + buildingSearchRequest.getAreaTo());
		}

		if (buildingSearchRequest.getRentPriceFrom() != null && buildingSearchRequest.getRentPriceTo() != null) {
			sql.append(" AND building.rentprice >= " + buildingSearchRequest.getRentPriceFrom()
					+ " AND building.rentprice <= " + buildingSearchRequest.getRentPriceTo());
		} else if (buildingSearchRequest.getRentPriceFrom() != null) {
			sql.append(" AND building.rentprice >= " + buildingSearchRequest.getRentPriceFrom());
		} else if (buildingSearchRequest.getRentPriceTo() != null) {
			sql.append(" AND building.rentprice <= " + buildingSearchRequest.getRentPriceTo());
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

}
