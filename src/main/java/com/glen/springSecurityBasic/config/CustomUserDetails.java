package com.glen.springSecurityBasic.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.glen.springSecurityBasic.entities.SecurityUser;
import com.glen.springSecurityBasic.entities.User;
import com.glen.springSecurityBasic.repository.UserRepository;

@Service
public class CustomUserDetails implements UserDetailsService{
	
	private UserRepository userRepository;
	
	@Autowired
	public CustomUserDetails(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<User> user = userRepository.findByUsername(username);
		if(user.size() == 0)
			throw new UsernameNotFoundException(username+" does not exist");
		return new SecurityUser(user.get(0));
	}

}
