package com.microservice.user.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.user.criteria.SearchCriteria;
import com.microservice.user.dto.JWTRequest;
import com.microservice.user.dto.JWTResponse;
import com.microservice.user.dto.OrderDto;
import com.microservice.user.dto.RoleDto;
import com.microservice.user.dto.UserDto;
import com.microservice.user.entity.RoleEntity;
import com.microservice.user.entity.UserEntity;
import com.microservice.user.exception.EmptyFieldException;
import com.microservice.user.exception.IllegalActionException;
import com.microservice.user.exception.IncorrectFieldException;
import com.microservice.user.exception.ItemNotFoundException;
import com.microservice.user.mapper.RoleMapper;
import com.microservice.user.mapper.UserMapper;
import com.microservice.user.repository.RoleRepository;
import com.microservice.user.repository.UserRepository;
import com.microservice.user.specification.UserSpecification;
import com.microservice.user.utility.JWTUtility;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private FeignService feignService;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JWTUtility jwtUtility;
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	private static final String KEY="USERCHECK";

	public UserDto saveUser(UserDto userDto, String password) {
		if (password == null || password.equals(""))
			throw new EmptyFieldException("Please enter a password");
		UserEntity user = new UserEntity();
		BeanUtils.copyProperties(userDto, user);
		BCryptPasswordEncoder bcryptpasswordencoder = new BCryptPasswordEncoder();
		String encryptedPassword = bcryptpasswordencoder.encode(password);
		user.setPassword(encryptedPassword);
		UserEntity user1 = userRepository.findDistinctByUsername(user.getUsername());
		if (user1 != null && user1 != user)
			throw new DataIntegrityViolationException("This username already exists");
		user = userRepository.save(user);
		userDto.setId(user.getId());
		return userDto;
	}

	public UserDto getUser(long id) {
		UserDto userDto = toUserDto(userRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("User not found : Incorrect id")));
		return userDto;
	}

	public List<UserDto> getUsers(List<SearchCriteria> searchCriteria) {
		UserSpecification userSpecification = new UserSpecification();
		searchCriteria.stream().map(searchCriterion -> new SearchCriteria(searchCriterion.getKey(),
				searchCriterion.getValue(), searchCriterion.getOperation())).forEach(userSpecification::add);
		List<UserEntity> msGenreList = userRepository.findAll(userSpecification);
		List<UserDto> userDtos = toUserDtos(msGenreList);
		return userDtos;
	}

	public UserDto updateUser(UserDto userDto, String password) {
		UserEntity user = userRepository.findById(userDto.getId())
				.orElseThrow(() -> new ItemNotFoundException("User not found : Incorrect id"));

		user = toUser(userDto);
		UserEntity user1 = userRepository.findDistinctByUsername(user.getUsername());
		if (user1 != null && user1.getId() != user.getId())
			throw new DataIntegrityViolationException("This username already exists");

		if (password != null) {
			BCryptPasswordEncoder bcryptpasswordencoder = new BCryptPasswordEncoder();
			String encryptedPassword = bcryptpasswordencoder.encode(password);
			user.setPassword(encryptedPassword);
		}
		userRepository.save(user);

		userDto = toUserDto(user);
		return userDto;

	}

	public boolean deleteUser(long id) {
		userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("User not found : Incorrect id"));
		userRepository.deleteById(id);
		return true;

	}

	public UserDto toUserDto(UserEntity user) {
		return userMapper.toUserDto(user);

	}

	public UserEntity toUser(UserDto userDto) {
		long id = userDto.getId();
		userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("User not found : Incorrect id"));
		String password = userRepository.getById(id).getPassword();
		UserEntity user = userMapper.toUser(userDto);
		user.setPassword(password);
		return user;

	}

	public List<UserDto> toUserDtos(List<UserEntity> users) {
		return userMapper.toUserDtos(users);
	}

	public List<OrderDto> getUserOrders(long userId) {

		return feignService.getOrder(userId);
	}

	public List<OrderDto> filterOrder(List<SearchCriteria> searchCriteria) {

		return feignService.getAllOrders(searchCriteria);
	}

	public void saveRole(RoleDto roleDto) {
		RoleEntity check=roleRepository.findByName(roleDto.getName());
		if(check==null) {
			
			roleRepository.save(roleMapper.toRole(roleDto));
		}
		else
			throw new IllegalActionException("This role already exists!");
	}
	public void assignRole(String role,String username) {
		RoleEntity check=roleRepository.findByName(role);
		UserEntity user=userRepository.findDistinctByUsername(username);
		if(check==null)
			throw new ItemNotFoundException("Role { "+role+" } doesn't exist");
		if(user==null)
			throw new ItemNotFoundException("UserName { "+username+" } doesn't exist");
		 
		if(!user.getRoles().contains(check)) {
			 user.getRoles().add(check);
		 }
		userRepository.save(user);

	}

	public JWTResponse authenticateUser(JWTRequest jwtRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
			}
			catch (Exception e) {
				
				throw new IncorrectFieldException("Invalid Credentials");
			}
			UserEntity user=userRepository.findDistinctByUsername(jwtRequest.getUsername());
			boolean isValid=userDetailsServiceImpl.checkChangedRoles((int)user.getId());
			if(!isValid)
				redisTemplate.delete(user.getId()+"");
			final UserDetails userDetails= userDetailsServiceImpl.loadUserByUsername(jwtRequest.getUsername());
			String token=jwtUtility.generateToken(userDetails,user.getId());
			return new JWTResponse(token);
	}

	public void deleteRole(String role, String username) {
		RoleEntity check=roleRepository.findByName(role);
		UserEntity user=userRepository.findDistinctByUsername(username);
		if(check==null)
			throw new ItemNotFoundException("Role { "+role+" } doesn't exist");
		if(user==null)
			throw new ItemNotFoundException("UserName { "+username+" } doesn't exist");
		 user.getRoles().remove(check);
		 userRepository.save(user);
		 
		// redisTemplate.opsForHash().put(KEY, user.getId(), user.getId());
		 redisTemplate.opsForValue().set(user.getId()+"", user.getId());
		 System.out.println("---------------------------->"+ user.getId());
	}

	
}