package com.laptrinhjavaweb.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "renttype")
public class RentType extends BaseEntity {
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "rentTypes", fetch = FetchType.LAZY)
    private Set<BuildingEntity> buildings = new HashSet<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BuildingEntity> getBuildings() {
        return buildings;
    }

    public void setBuildings(Set<BuildingEntity> buildings) {
        this.buildings = buildings;
    }
}
