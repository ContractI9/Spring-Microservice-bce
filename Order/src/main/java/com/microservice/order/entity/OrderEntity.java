package com.microservice.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="order_table")
@Data
public class OrderEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long order_id;
	@Column
	private String orderName;
	@Column(unique=true, updatable=false )
	private long serialNumber;
	@Column
	private long userId;


}