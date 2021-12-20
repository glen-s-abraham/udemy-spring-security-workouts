package com.glen.springSecurityBasic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
		http.authorizeRequests()
		.antMatchers("/myAccount").authenticated()
		.antMatchers("/myBalance").authenticated()
		.antMatchers("/myLoans").authenticated()
		.antMatchers("/myCards").authenticated()
		.antMatchers("/notices").permitAll()
		.antMatchers("/contact").permitAll()
		.and()
		.formLogin()
		.and()
		.httpBasic();
	}
}
