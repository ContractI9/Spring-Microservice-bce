package com.microservice.user.boundry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.user.control.UserService;
import com.microservice.user.criteria.SearchCriteria;
import com.microservice.user.dto.AssignRoleDto;
import com.microservice.user.dto.JWTRequest;
import com.microservice.user.dto.JWTResponse;
import com.microservice.user.dto.OrderDto;
import com.microservice.user.dto.RoleDto;
import com.microservice.user.dto.UserDto;
import com.microservice.user.exception.IncorrectFieldException;
import com.microservice.user.utility.JWTUtility;

@RestController
@RequestMapping("/test")
public class UserController {
	@Autowired
	private UserService userService;
	

	
	@GetMapping("/users")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public List<UserDto> getAllUsers(@RequestBody List<SearchCriteria> searchCriteria) {
		return userService.getUsers(searchCriteria);
	}

	@PostMapping("/createuser")
	@PreAuthorize("permitAll()")
	public UserDto createUser(@RequestParam(name = "pass", required = false) String userPassword, @RequestBody UserDto userDto) {

		return userService.saveUser(userDto, userPassword);

	}
	@GetMapping("/hello")
	@PreAuthorize("permitAll()")
	public  ResponseEntity<?> hello() {
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/users/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<UserDto> updateUser(@PathVariable(value = "id") long userId,
			@RequestParam(name = "pass", required = false) String userPassword, @RequestBody UserDto userDto) {
		userDto.setId(userId);
		return ResponseEntity.ok().body(userService.updateUser(userDto, userPassword));

	}
	
	@GetMapping("/users/{id}/orders")
	@PreAuthorize("isAuthenticated()")
	public List<OrderDto> getUserOrders(@PathVariable(value = "id") long userId) {
		return userService.getUserOrders(userId);

	}
	
	
	@GetMapping("/users/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<UserDto> getUserById(@PathVariable(value = "id") long userId) {

		return ResponseEntity.ok().body(userService.getUser(userId));
	}

	@DeleteMapping("/users/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "id") long userId) {
		userService.deleteUser(userId);
		return ResponseEntity.ok().build();
	}
	@PostMapping("/filterorder")
	@PreAuthorize("isAuthenticated()")
	public List<OrderDto> filter(@RequestBody List<SearchCriteria> searchCriteria) {
		return userService.filterOrder(searchCriteria);

	}
	@PostMapping("/authenticate")
	@PreAuthorize("permitAll()")
	public JWTResponse authenticate(@RequestBody JWTRequest jwtRequest) {
		return userService.authenticateUser(jwtRequest);
	}
	@PostMapping("/addrole")
	@PreAuthorize("permitAll()")
	public ResponseEntity<String> saveRole(@RequestBody RoleDto roleDto){
		userService.saveRole(roleDto);
		return ResponseEntity.ok("Role added successfully");
	}
	@PostMapping("/assignrole")
	@PreAuthorize("permitAll()")
	public ResponseEntity<String> assignRole(@RequestBody AssignRoleDto assignRoleDto){
		userService.assignRole(assignRoleDto.getRole(), assignRoleDto.getUsername());
		return ResponseEntity.ok("Role assigned successfully");
	}
	@DeleteMapping("/deleterole")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<String> updateUser(@RequestBody AssignRoleDto assignRoleDto) {
		userService.deleteRole(assignRoleDto.getRole(), assignRoleDto.getUsername());
		return ResponseEntity.ok("Role deleted successfully");
		

	}
}
