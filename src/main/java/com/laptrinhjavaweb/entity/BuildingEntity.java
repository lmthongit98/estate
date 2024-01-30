package com.laptrinhjavaweb.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "building")
@Getter
@Setter
public class BuildingEntity extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "street")
    private String street;

    @Column(name = "ward")
    private String ward;

    @Column(name = "district")
    private String district;

    @Column(name = "structure")
    private String stucture;

    @Column(name = "numberofbasement")
    private Integer numberOfBasement;

    @Column(name = "floorarea")
    private Integer floorArea;

    @Column(name = "managername")
    private String managerName;

    @Column(name = "managerphone")
    private String managerPhone;

    @Column(name = "type")
    private String type;

    @Column(name = "direction")
    private String direction;

    @Column(name = "level")
    private String level;

    @Column(name = "rentprice")
    private Integer rentPrice;

    @Column(name = "rentpricedescription")
    private String rentPriceDescription;

    @Column(name = "servicefee")
    private String serviceFee;

    @Column(name = "carfee")
    private String carFee;

    @Column(name = "motorbikefee")
    private String motorbikeFee;

    @Column(name = "overtimefee")
    private String overtimeFee;

    @Column(name = "waterfee")
    private String waterFee;

    @Column(name = "electricityfee")
    private String electricityFee;

    @Column(name = "deposit")
    private String deposit;

    @Column(name = "payment")
    private String payment;

    @Column(name = "renttime")
    private String rentTime;

    @Column(name = "decorationtime")
    private String decorationTime;

    @Column(name = "brokeragefee")
    private Integer brokerAgeFee;

    @Column(name = "note")
    private String note;

    @Column(name = "linkofbuilding")
    private String linkOfBuilding;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "building")
    private Set<RentAreaEntity> rentAreas = new HashSet<>();

    @ManyToMany(mappedBy = "assignedBuildings", fetch = FetchType.LAZY)
    private Set<UserEntity> assignees = new HashSet<>();

}
