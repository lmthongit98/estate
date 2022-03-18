package com.laptrinhjavaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.laptrinhjavaweb.dto.BuildingSearchDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.entity.DistrictEntity;
import com.laptrinhjavaweb.entity.RentAreaEntity;
import com.laptrinhjavaweb.entity.RentTypeEntity;
import com.laptrinhjavaweb.entity.UserEntity;
import com.laptrinhjavaweb.repository.BuildingRepository;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {

	private String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	private String USER = "root";
	private String PASS = "123456";

	@Override
	public List<BuildingEntity> searchBuildings(BuildingSearchDTO buildingSearchDTO) {
		StringBuilder sql = new StringBuilder("SELECT building.id as buildingid, building.name as buildingname,");
		sql.append(
				" street, ward, numberofbasement, floorarea, rentprice, district.code as districtcode, district.name as districtname,");
		sql.append(" rentarea.id as areaid, rentarea.value as area,");
		sql.append(" renttype.code as typecode, renttype.name as typename,");
		sql.append(" user.id as userid, user.fullname as userfullname");
		sql.append(" FROM building");
		sql.append(" JOIN district ON building.districtid = district.id");
		sql.append(" JOIN rentarea ON building.id = rentarea.buildingid");
		sql.append(" LEFT JOIN buildingrenttype ON building.id = buildingrenttype.buildingid");
		sql.append(" LEFT JOIN renttype ON buildingrenttype.renttypeid = renttype.id");
		sql.append(" LEFT JOIN assignmentbuilding ON building.id = assignmentbuilding.buildingid");
		sql.append(" LEFT JOIN user ON assignmentbuilding.staffid = user.id");

		if (buildingSearchDTO.getBuildingName() == null) {
			buildingSearchDTO.setBuildingName("");
		}
		sql.append(" WHERE building.name like '%" + buildingSearchDTO.getBuildingName() + "%'");
		
		if (buildingSearchDTO.getFloorArea() != null) {
			sql.append(" AND building.floorarea = " + buildingSearchDTO.getFloorArea());
		}
		
		if (buildingSearchDTO.getDistrictCode() != null) {
			sql.append(" AND district.code like '%" + buildingSearchDTO.getDistrictCode() + "%'");
		}
		
		if (buildingSearchDTO.getStreet() != null) {
			sql.append(" AND building.street like '%" + buildingSearchDTO.getStreet() + "%'");
		}
		
		if (buildingSearchDTO.getWard() != null) {
			sql.append(" AND building.ward like '%" + buildingSearchDTO.getWard() + "%'");
		}
		
		if (buildingSearchDTO.getNumOfBasement() != null) {
			sql.append(" AND building.numberofbasement = '" + buildingSearchDTO.getNumOfBasement() + "'");
		}
		
		if(buildingSearchDTO.getEmployeeId() != null) {
			sql.append(" AND user.id = " + buildingSearchDTO.getEmployeeId());
		}
		
		if(buildingSearchDTO.getBuildingTypeCodes() != null && buildingSearchDTO.getBuildingTypeCodes().size() > 0) {
			sql.append(" AND (");
			for(int i = 0; i < buildingSearchDTO.getBuildingTypeCodes().size(); i++) {
				if(i >= buildingSearchDTO.getBuildingTypeCodes().size() - 1){
					sql.append("renttype.code = '" + buildingSearchDTO.getBuildingTypeCodes().get(i) + "'");
				}else {
					sql.append("renttype.code = '" + buildingSearchDTO.getBuildingTypeCodes().get(i) + "' OR ");
				}
			}
			sql.append(")");
		}
		
		if (buildingSearchDTO.getAreaFrom() != null && buildingSearchDTO.getAreaTo() != null) {
			sql.append(" AND building.floorarea >= " + buildingSearchDTO.getAreaFrom() + " AND building.floorarea <= "
					+ buildingSearchDTO.getAreaTo());
		} else if (buildingSearchDTO.getAreaFrom() != null) {
			sql.append(" AND building.floorarea >= " + buildingSearchDTO.getAreaFrom());

		} else if (buildingSearchDTO.getAreaTo() != null) {
			sql.append(" AND building.floorarea <= " + buildingSearchDTO.getAreaTo());
		}
		
		if (buildingSearchDTO.getRentPriceFrom() != null && buildingSearchDTO.getRentPriceTo() != null) {
			sql.append(" AND building.rentprice >= " + buildingSearchDTO.getRentPriceFrom()
					+ " AND building.rentprice <= " + buildingSearchDTO.getRentPriceTo());
		} else if (buildingSearchDTO.getRentPriceFrom() != null) {
			sql.append(" AND building.rentprice >= " + buildingSearchDTO.getRentPriceFrom());
		} else if (buildingSearchDTO.getRentPriceTo() != null) {
			sql.append(" AND building.rentprice <= " + buildingSearchDTO.getRentPriceTo());
		}

		String query = sql.toString();
		List<BuildingEntity> results = new ArrayList<BuildingEntity>();
		// Open a connection
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				BuildingEntity building = new BuildingEntity();
				DistrictEntity district = new DistrictEntity();
				List<RentAreaEntity> areas = new ArrayList<RentAreaEntity>();
				List<RentTypeEntity> types = new ArrayList<RentTypeEntity>();
				RentTypeEntity type = new RentTypeEntity();
				RentAreaEntity area = new RentAreaEntity();
				List<UserEntity> users = new ArrayList<UserEntity>();
				UserEntity user = new UserEntity();
				// set area
				area.setId(rs.getLong("areaid"));
				area.setValue(rs.getInt("area"));
				// set type
				type.setCode(rs.getString("typecode"));
				type.setName(rs.getString("typename"));
				// set user
				user.setId(rs.getLong("userid"));
				user.setFullName(rs.getString("userfullname"));

				Long buildingId = rs.getLong("buildingid");
				Optional<BuildingEntity> buidlingOptional = results.stream().filter(bd -> bd.getId() == buildingId)
						.findAny();
				if (buidlingOptional.isPresent()) {
					BuildingEntity buildingEnity = buidlingOptional.get();
					addArea(results, area, buildingEnity);
					addType(results, type, buildingEnity);
					addUser(results, user, buildingEnity);
					continue;
				}

				types.add(type);
				areas.add(area);
				users.add(user);
				// set district
				district.setCode(rs.getString("districtcode"));
				district.setName(rs.getString("districtname"));
				// set building
				building.setId(buildingId);
				building.setName(rs.getString("buildingname"));
				building.setNumberOfBasement(rs.getInt("numberofbasement"));
				building.setStreet(rs.getString("street"));
				building.setWard(rs.getString("ward"));
				building.setFloorArea(rs.getInt("floorarea"));
				building.setRentPrice(rs.getInt("rentprice"));
				building.setAreas(areas);
				building.setTypes(types);
				building.setUsers(users);

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

	private void addUser(List<BuildingEntity> results, UserEntity user, BuildingEntity buildingEnity) {
		List<UserEntity> users = buildingEnity.getUsers();
		if (!users.contains(user)) {
			users.add(user);
			buildingEnity.setUsers(users);
			updateResults(results, buildingEnity);
		}
	}

	private void addType(List<BuildingEntity> results, RentTypeEntity type, BuildingEntity buildingEnity) {
		List<RentTypeEntity> types = buildingEnity.getTypes();
		if (!types.contains(type)) {
			types.add(type);
			buildingEnity.setTypes(types);
			updateResults(results, buildingEnity);
		}
	}

	private void addArea(List<BuildingEntity> results, RentAreaEntity area, BuildingEntity buildingEnity) {
		List<RentAreaEntity> areas = buildingEnity.getAreas();
		if (!areas.contains(area)) {
			areas.add(area);
			buildingEnity.setAreas(areas);
			updateResults(results, buildingEnity);
		}
	}

	private void updateResults(List<BuildingEntity> results, BuildingEntity buildingEnity) {
		int index = results.indexOf(buildingEnity);
		results.set(index, buildingEnity);
	}

}
