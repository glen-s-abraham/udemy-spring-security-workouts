package com.glen.springSecurityBasic.config;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	/*
	 * Secured endpoints
	 *-/myAccount
	 *-/myBalance
	 *-/myLoans
	 *-/myCards
	 *UnSecured Endpoints
	 *-/notices
	 *-/contact
	 */

	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors()
		.configurationSource(new CorsConfigurationSource() {
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration cors = new CorsConfiguration();
				cors.setAllowedOrigins(Collections.singletonList("*"));
				cors.setAllowedMethods(Collections.singletonList("*"));
				cors.setAllowedHeaders(Collections.singletonList("*"));
				cors.setMaxAge(3600L);
				cors.setAllowCredentials(true);
				return cors;
			}
		})
		.and()
		.csrf()
		.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.and()
		.authorizeRequests()
		.antMatchers("/myAccount").hasAnyAuthority("WRITE")
		.antMatchers("/myBalance").hasAuthority("READ")
		.antMatchers("/myLoans").hasAnyAuthority("DELETE")
		.antMatchers("/myCards").authenticated()
		.antMatchers("/notices").permitAll()
		.antMatchers("/contact").permitAll()
		.and()
		.formLogin()
		.and()
		.httpBasic();
		
	}
	
	
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//		.withUser("admin").password("password").authorities("admin")
//		.and().withUser("user").password("password").authorities("read")
//		.and().passwordEncoder(NoOpPasswordEncoder.getInstance());
//	}
	
//	protected void configure(AuthenticationManagerBuilder auth)throws Exception{
//		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//		UserDetails admin = User.withUsername("admin").password("password").authorities("admin").build();
//		UserDetails user = User.withUsername("user").password("password").authorities("read").build();
//		userDetailsManager.createUser(admin);
//		userDetailsManager.createUser(user);
//		auth.userDetailsService(userDetailsManager);
//		System.out.println(userDetailsService());
//	}
	

	
//	@Bean
//	public UserDetailsService userDetailsService(DataSource datasource) {
//		return new JdbcUserDetailsManager(datasource);
//	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		//return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
	}
	
	
	
}
