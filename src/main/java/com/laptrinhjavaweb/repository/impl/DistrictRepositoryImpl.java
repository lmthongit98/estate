package com.laptrinhjavaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.laptrinhjavaweb.entity.DistrictEntity;
import com.laptrinhjavaweb.repository.DistrictRepository;
import com.laptrinhjavaweb.util.ConnectionUtil;

@Repository
public class DistrictRepositoryImpl implements DistrictRepository {

	@Override
	public DistrictEntity findById(Long id) {
		StringBuilder sql = new StringBuilder("SELECT *");
		sql.append(" FROM district d")
		.append(" WHERE d.id = " + id);
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());) {
			while (rs.next()) {
				DistrictEntity entity = new DistrictEntity();
				entity.setId(rs.getLong("id"));
				entity.setCode(rs.getString("code"));
				entity.setName(rs.getString("name"));
				return entity;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
