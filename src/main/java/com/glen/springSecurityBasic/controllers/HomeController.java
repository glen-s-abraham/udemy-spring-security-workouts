package com.glen.springSecurityBasic.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("api/home")
	public String getHome() {
		return "Welcome to spring security without security";
	}
}
