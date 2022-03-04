package com.example.demo.utils;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.demo.model.AuthToken;
import com.example.demo.model.JwtUserDetails;
import com.example.demo.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	
	
	
	public AuthToken generateToken(User user) {
		Claims claims = Jwts.claims().setSubject(user.getUserId());
		claims.setIssuedAt(new Date(System.currentTimeMillis()));
		claims.setExpiration(new Date(System.currentTimeMillis() + (300 *1000)));
//		claims.put("userName", user.getUserName());
//		claims.put("role","customer");
		return  AuthToken.builder().token( Jwts.builder().setClaims(claims)
//				.signWith(SignatureAlgorithm.ES512, "263gd53feds6te6")
				.compact()).
				build();
		
	}
	
	public JwtUserDetails validateToken(AuthToken authToken) {
		return null;

	}

}
