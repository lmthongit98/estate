package com.laptrinhjavaweb.repository.custom.impl;

import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.constant.SystemConstant;
import com.laptrinhjavaweb.entity.*;
import com.laptrinhjavaweb.enums.SpecialSearchParams;
import com.laptrinhjavaweb.repository.custom.BuildingRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class BuildingRepositoryCustomImpl extends QuerydslRepositorySupport implements BuildingRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public BuildingRepositoryCustomImpl() {
        super(BuildingEntity.class);
    }

    public List<BuildingEntity> findByConditions(BuildingSearchBuilder builder) {
        QBuildingEntity building = QBuildingEntity.buildingEntity;

        int pageNumber = 1;
        int pageSize = 10;

        long offset = (pageNumber - 1) * pageSize;

        JPQLQuery<BuildingEntity> query = from(building)
                .limit(pageSize)
                .offset(offset)
                .select(building);

        if (builder.getName() != null) {
            query = query.where(building.name.likeIgnoreCase(builder.getName()));
        }

        if (builder.getNumberOfBasement() != null) {
            query = query.where(building.numberOfBasement.eq(builder.getNumberOfBasement()));
        }

        if (builder.getDistrict() != null) {
            query = query.where(building.district.eq(builder.getDistrict()));
        }

        if (builder.getCostRentFrom() != null) {
            query = query.where(building.rentPrice.goe(builder.getCostRentFrom()));
        }

        if (builder.getCostRentTo() != null) {
            query = query.where(building.rentPrice.loe(builder.getCostRentTo()));
        }

        if (builder.getAreaRentFrom() != null || builder.getAreaRentTo() != null) {
            QRentAreaEntity rentArea = QRentAreaEntity.rentAreaEntity;
            JPQLQuery<RentAreaEntity> jpqlQuery = from(rentArea).where(rentArea.building.id.eq(building.id));
            if (builder.getAreaRentFrom() != null) {
                jpqlQuery.where(rentArea.value.goe(builder.getAreaRentFrom().toString()));
            }
            if (builder.getAreaRentTo() != null) {
                jpqlQuery.where(rentArea.value.loe(builder.getAreaRentTo().toString()));
            }
            query = query.where(jpqlQuery.exists());
        }

        if (!CollectionUtils.isEmpty(builder.getBuildingTypes())) {
            BooleanExpression condition = null;
            for (String buildingType : builder.getBuildingTypes()) {
                if (condition == null) {
                    condition = building.type.likeIgnoreCase(buildingType);
                } else {
                    condition = condition.or(building.type.likeIgnoreCase(buildingType));
                }
            }
            query = query.where(condition);
        }

        if (builder.getStaffId() != null) {
            QUserEntity user = QUserEntity.userEntity;
            query = query.innerJoin(building.assignees, user).where(user.id.eq(builder.getStaffId()));
        }

        return query.orderBy(building.id.asc()).fetch();
    }

//    @Override
//    public List<BuildingEntity> findByConditions(BuildingSearchBuilder builder) {
//        StringBuilder sql = new StringBuilder("SELECT * FROM building b\n");
//        buildJoinQuery(builder, sql);
//        sql.append(SystemConstant.WHERE_ONE_EQUALS_ONE);
//        buildWhereClausePart1(builder, sql);
//        buildWhereClausePart2(builder, sql);
//        sql.append(" group by b.id");
//
//        Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
//
//        return query.getResultList();
//    }

    private void buildJoinQuery(BuildingSearchBuilder builder, StringBuilder query) {
        if (builder.getStaffId() != null) {
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

                if (getSpecialSearchParams().contains(fieldName)) {
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

        if (builder.getBuildingTypes() != null && !builder.getBuildingTypes().isEmpty()) {
            String sqlType = builder.getBuildingTypes()
                    .stream()
                    .map(type -> "b.type like '%" + type + "%'")
                    .collect(Collectors.joining(SystemConstant.OR_STATEMENT));

            query.append(SystemConstant.AND_STATEMENT)
                    .append("(")
                    .append(sqlType)
                    .append(")");
        }

        if (builder.getStaffId() != null) {
            query.append(SystemConstant.AND_STATEMENT)
                    .append("assignmentbuilding.staffid = ")
                    .append(builder.getStaffId());
        }

        if (builder.getDistrict() != null) {
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
