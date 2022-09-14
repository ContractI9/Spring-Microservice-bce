package com.microservice.order.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.microservice.order.entity.OrderEntity;


public interface OrderRepository extends JpaRepository<OrderEntity,Long> , JpaSpecificationExecutor<OrderEntity> {
 public OrderEntity findDistinctBySerialNumber(long serialNumber );
 public List<OrderEntity> findByUserId(long userId);

}
