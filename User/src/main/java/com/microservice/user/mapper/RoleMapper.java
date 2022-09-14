package com.microservice.user.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.microservice.user.dto.RoleDto;
import com.microservice.user.entity.RoleEntity;

@Mapper(componentModel = "spring")
public interface RoleMapper {
		
		RoleDto toRoleDto(RoleEntity role);
		RoleEntity toRole(RoleDto roleDto);
		List<RoleDto> toRoleDtos(List<RoleEntity> roles);

	
}
