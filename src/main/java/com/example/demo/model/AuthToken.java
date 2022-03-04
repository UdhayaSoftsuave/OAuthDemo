package com.example.demo.model;

import lombok.Builder;

@Builder
public class AuthToken {
	
	private String token ;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

}
