package com.laptrinhjavaweb.entity;

import java.util.Date;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "createddate")
	@CreatedDate
	private Date createdDate;

	@Column(name = "modifieddate")
	@LastModifiedDate
	private Date modifiedDate;

	@Column(name = "createdby")
	@CreatedBy
	private String createdBy;

	@Column(name = "modifiedby")
	@LastModifiedBy
	private String modifiedBy;
}
