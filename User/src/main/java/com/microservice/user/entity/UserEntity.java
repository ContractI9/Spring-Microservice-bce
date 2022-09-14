package com.microservice.user.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="user_table")
@Data
public class UserEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;
	@Column(unique=true)
	private String username;
	@Column
	private String email;
	@Column
	private String password;
	@Column
	private int age;
	@Column
	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	private List<RoleEntity> roles;
}