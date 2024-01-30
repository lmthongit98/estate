package com.laptrinhjavaweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rentarea")
@Getter
@Setter
public class RentAreaEntity extends BaseEntity {

    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "buildingid")
    private BuildingEntity building;

}
