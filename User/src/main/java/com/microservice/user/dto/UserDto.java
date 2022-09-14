package com.microservice.user.dto;

import java.util.ArrayList;

import com.microservice.user.entity.RoleEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private long id;
	private String email;
	private String username;
	private int age;
	private ArrayList<RoleEntity> roles;
}

