package com.microservice.user.control;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.microservice.user.entity.UserEntity;
import com.microservice.user.exception.IllegalActionException;
import com.microservice.user.exception.ItemNotFoundException;
import com.microservice.user.exception.UpdatedRoleException;
import com.microservice.user.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	private static final String KEY="USERCHECK";
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user= userRepository.findDistinctByUsername(username);
		if (user==null) {
			throw new ItemNotFoundException("Username not found!");
		}
		Collection<SimpleGrantedAuthority> authority=new ArrayList<>();
		user.getRoles().forEach(role -> {authority.add(new SimpleGrantedAuthority(role.getName()));});
		return new User (user.getUsername(),user.getPassword(),authority);

	}
	public boolean checkChangedRoles(int id) {
		Object testVar=redisTemplate.opsForValue().get(id+"");	
		if(testVar!=null) { //==id
			//throw new UpdatedRoleException("You no longer have access to this resource");
			return false;
		
		}
		return true;
	}

}
