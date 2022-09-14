package com.microservice.order.boundry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.order.control.OrderService;
import com.microservice.order.criteria.SearchCriteria;
import com.microservice.order.dto.OrderDto;

@RestController
@RequestMapping()
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/")
	public List<OrderDto> getAllOrders(@RequestBody List<SearchCriteria> searchCriteria){
		return orderService.getOrders(searchCriteria);
	}
	@GetMapping("/")
	public List<OrderDto> getAllOrders(){
		return orderService.getOrders();
	}
	@GetMapping("/orders/{id}")
	public  List<OrderDto> getOrder(@PathVariable(value="id") long id){
		return orderService.getUserOrder(id);
	}
	@PostMapping("/orders")
	public OrderDto getOrder(@RequestBody OrderDto orderDto){
		return orderService.saveOrder(orderDto);
	}
	@PutMapping("/orders/{id}")
	public OrderDto getOrder(@PathVariable(value="id") long id,@RequestBody OrderDto orderDto){
		return orderService.updateOrder(id, orderDto);
	}
	@DeleteMapping("/orders/{id}")
	public ResponseEntity deleteUser(@PathVariable(value = "id") long id) {
		orderService.deleteOrder(id);
		return ResponseEntity.ok().build();
	}
	
	

}
