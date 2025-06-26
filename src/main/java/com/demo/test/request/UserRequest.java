package com.demo.test.request;

import lombok.Data;

@Data
public class UserRequest {
	
	    private Long id;

	    private String name;

	    private String email;

	    private String password;

	    private Integer role;


}