package com.microservice.user.control;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.microservice.user.criteria.SearchCriteria;
import com.microservice.user.dto.OrderDto;

@FeignClient(
name="ORDER"


)
public interface FeignService {
	
	@RequestMapping(method=RequestMethod.GET,
			value="/orderapp/orders/{id}",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public  List<OrderDto> getOrder(@PathVariable(value="id") long id);	
	@RequestMapping(method=RequestMethod.POST,
			value="/orderapp/",
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public  List<OrderDto> getAllOrders(@RequestBody List<SearchCriteria> searchCriteria);	
}
