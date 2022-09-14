package com.microservice.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.microservice.user.control.UserService;
import com.microservice.user.dto.UserDto;




@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private UserService userService;
	@Test
	void checkIfCreateUserResturnsUser() throws Exception {
		UserDto userDtoreq= new UserDto(0,"maro@gmail.com","maro",23,new ArrayList<>());
		UserDto userDtores= new UserDto(1,"maro@gmail.com","maro",23,new ArrayList<>());
		//when(userService.saveUser(userDtoreq, "maro")).thenReturn(userDtores);
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = (ObjectWriter) mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(userDtoreq) ;

	    	MvcResult result=mvc.perform(post("/test/createuser?pass=maro").contentType(MediaType.APPLICATION_JSON)
	        .content(requestJson))
	    	.andDo(print())
	    	.andExpect(status().isOk())
	        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
	        .andReturn();
	   
	 
	}

	@Test
	void checkUnauthenticatedUserAccess() throws Exception {
		mvc.perform(get("/test/users").contentType(MediaType.APPLICATION_JSON).content("[]"))
		.andDo(print())
		.andExpect(status().isUnauthorized());
		
	}
	@Test
	@WithMockUser(authorities = { "ADMIN", "USER" })
	void checkAuthenticatedUserAccess() throws Exception {
		mvc.perform(get("/test/users").contentType(MediaType.APPLICATION_JSON).content("[]"))
		.andDo(print())
		.andExpect(status().isOk());
	}
	@Test
	@WithMockUser(authorities = {"USER" })
	void checkUsersWithoutAccess() throws Exception {
		mvc.perform(get("/test/users").contentType(MediaType.APPLICATION_JSON).content("[]"))
		.andDo(print())
		.andExpect(status().isForbidden());
	}

}
