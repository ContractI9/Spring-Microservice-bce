package com.microservice.order.mapper;
import java.util.List;

import org.mapstruct.Mapper;

import com.microservice.order.dto.OrderDto;
import com.microservice.order.entity.OrderEntity;

@Mapper(componentModel = "spring")
public interface OrderMapper {
	
	OrderDto toOrderDto(OrderEntity Order);
	OrderEntity toOrder(OrderDto OrderDto);
	List<OrderDto> toOrderDtos(List<OrderEntity> Orders);
	

}
