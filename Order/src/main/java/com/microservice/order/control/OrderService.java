package com.microservice.order.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.order.criteria.SearchCriteria;
import com.microservice.order.dto.OrderDto;
import com.microservice.order.entity.OrderEntity;
import com.microservice.order.exception.IllegalActionException;
import com.microservice.order.exception.ItemNotFoundException;
import com.microservice.order.mapper.OrderMapper;
import com.microservice.order.repository.OrderRepository;
import com.microservice.order.specification.OrderSpecification;
@Service
public class OrderService {
		@Autowired
		private OrderRepository orderRepository;
		@Autowired
		private OrderMapper orderMapper;
		
		public List<OrderDto> getOrders(){
			return orderMapper.toOrderDtos(orderRepository.findAll());
		}
		public List<OrderDto> getOrders(List<SearchCriteria> searchCriteria){
			OrderSpecification orderSpecification = new OrderSpecification();
	        searchCriteria.stream().map(searchCriterion -> new SearchCriteria(searchCriterion.getKey(),
	        		searchCriterion.getValue(), searchCriterion.getOperation())).forEach(orderSpecification::add) ;
	        List<OrderEntity> msGenreList = orderRepository.findAll(orderSpecification);
			return orderMapper.toOrderDtos(msGenreList);
		}
		public  List<OrderDto> getUserOrder(long id) {
			return orderMapper.toOrderDtos(orderRepository.findByUserId(id) );
		}
		
		public OrderDto saveOrder(OrderDto orderDto) {
			OrderEntity order=new OrderEntity();	
			order=orderMapper.toOrder(orderDto);
			OrderEntity order1=orderRepository.findDistinctBySerialNumber(orderDto.getSerialNumber());
			if(order1!=null)
				throw new IllegalActionException("serial number already exists");
			orderRepository.save(order);
			orderDto.setOrder_id(order.getOrder_id());
			return orderDto;
			
			
		}
		public OrderDto updateOrder(long id, OrderDto orderDto) {
			OrderEntity order=orderRepository.findById(id).orElseThrow(()
					-> new ItemNotFoundException("Order not found : Incorrect id"));
			if(order.getUserId()!=orderDto.getUserId())
				throw new IllegalActionException("Cannot perform this action");
			
			orderDto.setSerialNumber(order.getSerialNumber());
			orderDto.setOrder_id(id);
			OrderEntity newOrder=orderMapper.toOrder(orderDto);
			orderRepository.save(newOrder);
			return orderDto;
			
		}
		public void deleteOrder(long id) {
			OrderEntity order=orderRepository.findById(id).orElseThrow(()
					-> new ItemNotFoundException("Order not found : Incorrect id"));
			orderRepository.deleteById(id);
			
			
		}
		public List<OrderDto> toOrderDtos(List<OrderEntity> Orders){
			return orderMapper.toOrderDtos(Orders);
		}
	
}
