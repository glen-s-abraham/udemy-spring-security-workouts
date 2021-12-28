package com.glen.springSecurityBasic.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.glen.springSecurityBasic.entities.Authority;
import com.glen.springSecurityBasic.entities.User;
import com.glen.springSecurityBasic.repository.UserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

	UserRepository userRepository;
	PasswordEncoder passwordEncoder;
	
	
	@Autowired
	public CustomAuthenticationProvider(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		List<User> user = userRepository.findByUsername(username);
		if(user.size()>0) {
			if(passwordEncoder.matches(password,user.get(0).getPassword())) {
//				List <GrantedAuthority> authorities = new ArrayList<>();
//				authorities.add(new SimpleGrantedAuthority(user.get(0).getRole()));
				return new UsernamePasswordAuthenticationToken(username, password, getGrantedAuthorities(user.get(0).getAuthorities()));
			}else {
				throw new BadCredentialsException("Invalid password");
			}
		}else {
			throw new BadCredentialsException("Invalid Details");
		}
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities){
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for(Authority authority:authorities) {
			grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
		}
		return grantedAuthorities;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
