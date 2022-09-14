package com.microservice.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="role_table")
@Data
public class RoleEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;
	@Column
	private String name;
}