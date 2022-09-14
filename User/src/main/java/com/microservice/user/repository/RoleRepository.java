package com.microservice.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.user.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
	public RoleEntity findByName(String name);

}
