package com.laptrinhjavaweb.entity;

import javax.persistence.*;

@Entity
@Table(name = "rentarea")
public class RentAreaEntity extends BaseEntity {

    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinColumn(name = "buildingid")
    private BuildingEntity building;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BuildingEntity getBuilding() {
        return building;
    }

    public void setBuilding(BuildingEntity building) {
        this.building = building;
    }
}
