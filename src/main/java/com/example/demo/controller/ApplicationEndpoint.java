package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/applicationendpoint")
public class ApplicationEndpoint {

	@RequestMapping(value = "/getRequest", method = RequestMethod.GET)
	public String getRequest() {
		
		return "Hello World!";
	}
}
