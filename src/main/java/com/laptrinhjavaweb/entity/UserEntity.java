package com.laptrinhjavaweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "user")
@Getter
@Setter
public class UserEntity extends BaseEntity {

    @Column(name = "username", nullable = false, unique = true)
    private String userName;

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Setter
    @Column(name = "email", unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "userid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "roleid", nullable = false))
    private Set<RoleEntity> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "assignmentbuilding",
            joinColumns = @JoinColumn(name = "staffid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "buildingid", nullable = false))
    private Set<BuildingEntity> assignedBuildings = new HashSet<>();

}
