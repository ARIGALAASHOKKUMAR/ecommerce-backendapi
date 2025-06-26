package com.demo.test.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.demo.test.request.UserRequest;

@Service
public interface userService {
	
	public ResponseEntity<?> loginUser( UserRequest user);
	 public ResponseEntity<?> createUser(UserRequest user) ;
}
