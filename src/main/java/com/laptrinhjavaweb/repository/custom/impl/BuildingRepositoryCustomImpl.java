package com.laptrinhjavaweb.repository.custom.impl;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.laptrinhjavaweb.enums.SpecialSearchParams;
import com.laptrinhjavaweb.repository.custom.BuildingRepositoryCustom;
import org.springframework.stereotype.Repository;

import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.constant.SystemConstant;
import com.laptrinhjavaweb.entity.BuildingEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class BuildingRepositoryCustomImpl implements BuildingRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<BuildingEntity> findByConditions(BuildingSearchBuilder builder) {
		StringBuilder sql = new StringBuilder("SELECT * FROM building b\n");
		buildJoinQuery(builder, sql);
		sql.append(SystemConstant.WHERE_ONE_EQUALS_ONE);
		buildWhereClausePart1(builder, sql);
		buildWhereClausePart2(builder, sql);
		sql.append(" group by b.id");

		Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);

		return query.getResultList();
	}

	private void buildJoinQuery(BuildingSearchBuilder builder, StringBuilder query) {
		if(builder.getStaffId() != null) {
			query.append(" INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid");
		}
	}

	private void buildWhereClausePart1(BuildingSearchBuilder builder, StringBuilder query) {
		Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				String fieldName = field.getName();
				Object fieldValue = field.get(builder);
				String selectedColumn = SystemConstant.BUILDING_ALIAS + fieldName.toLowerCase();

				if(getSpecialSearchParams().contains(fieldName)) {
					continue;
				}
				if (fieldValue == null) {
					continue;
				}
				if (field.getType().equals(String.class)) {
					String convertedValue = "'%" + fieldValue + "%'";
					query.append(SystemConstant.AND_STATEMENT)
							.append(selectedColumn)
							.append(SystemConstant.LIKE_OPERATOR)
							.append(convertedValue);
				} else if (field.getType().equals(Integer.class)) {
					query.append(SystemConstant.AND_STATEMENT)
							.append(selectedColumn)
							.append(SystemConstant.EQUAL_OPERATOR)
							.append(fieldValue);
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	private void buildWhereClausePart2(BuildingSearchBuilder builder, StringBuilder query) {
		if (builder.getCostRentFrom() != null) {
			query.append(SystemConstant.AND_STATEMENT).append("b.rentprice >= ")
					.append(builder.getCostRentFrom());
		}
		if (builder.getCostRentTo() != null) {
			query.append(SystemConstant.AND_STATEMENT).append("b.rentprice <= ")
					.append(builder.getCostRentTo());
		}

		if (builder.getAreaRentFrom() != null || builder.getAreaRentTo() != null) {
			query.append(SystemConstant.AND_STATEMENT)
					.append("EXISTS (SELECT * FROM rentarea ra where b.id = ra.building_id");
			if (builder.getAreaRentFrom() != null) {
				query.append(SystemConstant.AND_STATEMENT).append("ra.value >=")
						.append(builder.getAreaRentFrom());
			}
			if (builder.getAreaRentTo() != null) {
				query.append(SystemConstant.AND_STATEMENT).append("ra.value <= ")
						.append(builder.getAreaRentTo());
			}
			query.append(")");
		}
		
		if(builder.getBuildingTypes() != null && !builder.getBuildingTypes().isEmpty()) {
			String sqlType = builder.getBuildingTypes()
					.stream()
					.map(type -> "b.type like '%" + type + "%'")
					.collect(Collectors.joining(SystemConstant.OR_STATEMENT));

			query.append(SystemConstant.AND_STATEMENT)
					.append("(")
					.append(sqlType)
					.append(")");
		}
		
		if(builder.getStaffId() != null) {
			query.append(SystemConstant.AND_STATEMENT)
					.append("assignmentbuilding.staffid = ")
					.append(builder.getStaffId());
		}
		
		if(builder.getDistrict() != null) {
			query.append(SystemConstant.AND_STATEMENT)
					.append("b.district = '")
					.append(builder.getDistrict())
					.append("'");
		}

	}

	private List<String> getSpecialSearchParams() {
		List<String> params = new ArrayList<>();
		for (SpecialSearchParams item : SpecialSearchParams.values()) {
			params.add(item.getValue());
		}
		return params;
	}

}
