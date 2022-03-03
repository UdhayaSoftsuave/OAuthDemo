package com.example.demo.utils;

import org.springframework.stereotype.Component;

import com.example.demo.model.AuthToken;
import com.example.demo.model.JwtUserDetails;
import com.example.demo.model.User;

@Component
public class JwtUtils {
	
	public AuthToken generateToken(User user) {
		return null;
		
	}
	
	public JwtUserDetails validateToken(AuthToken authToken) {
		return null;

	}

}
