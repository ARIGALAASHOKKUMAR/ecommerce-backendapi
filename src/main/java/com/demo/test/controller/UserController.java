package com.demo.test.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.test.repository.UserRepository;
import com.demo.test.request.UserRequest;
import com.demo.test.service.userService;
import com.demo.test.utilities.commonQueryAPIUtils;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	userService userService;

	@Autowired
	private UserRepository userRepo;
	
	@PostMapping("/user-login")
	public ResponseEntity<?> loginUser(@RequestBody UserRequest user) {
		return userService.loginUser(user);
	}

	@PostMapping("/user-register")
	public ResponseEntity<?> createUser(@RequestBody UserRequest user) {
		return userService.createUser(user);
	}

	@GetMapping("/user-details")
	public Map<String, Object> userDetails(@RequestParam String email) {
		return commonQueryAPIUtils.apiService("user", userRepo.userDetails(email));
	}

}
