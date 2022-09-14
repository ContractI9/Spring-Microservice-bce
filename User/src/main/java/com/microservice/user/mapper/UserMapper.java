package com.microservice.user.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.microservice.user.dto.UserDto;
import com.microservice.user.entity.UserEntity;



@Mapper(componentModel = "spring")
public interface UserMapper {
	
	UserDto toUserDto(UserEntity user);
	UserEntity toUser(UserDto userDto);
	List<UserDto> toUserDtos(List<UserEntity> users);

}