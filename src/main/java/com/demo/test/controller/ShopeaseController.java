package com.demo.test.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.test.repository.ShopeaseRepository;
import com.demo.test.utilities.commonQueryAPIUtils;

@RestController
@CrossOrigin
public class ShopeaseController {
	
	@Autowired
	ShopeaseRepository repo;
	
	@GetMapping("/demo")
	public Map<String, Object> getCourtOrderDetailsReport() {
		System.err.println("kosie");
		return commonQueryAPIUtils.apiService("products", repo.test());
	}
	
	@GetMapping("/sai")
	String sai() {
		return "hiiiiiiiiiiiiiii";
		
	}
  	

}
