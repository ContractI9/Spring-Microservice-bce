package com.microservice.order.dto;

import lombok.Data;

@Data
public class OrderDto {
	private long order_id;
	private String orderName;
	private long serialNumber;
	private long userId;

}