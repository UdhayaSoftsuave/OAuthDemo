package com.example.demo.utils;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.model.AuthToken;
import com.example.demo.model.JwtUserDetails;
import com.example.demo.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	
	@Value("${jwt.private-key}")
	public String secrectKey;
	
	public AuthToken generateToken(User user) {
		Claims claims = Jwts.claims().setSubject(user.getUserId());
		claims.setIssuedAt(new Date(System.currentTimeMillis()));
		claims.setExpiration(new Date(System.currentTimeMillis() + (300 *1000)));
		claims.put("userName", user.getUserName());
		claims.put("role","customer");
		return  AuthToken.builder().token( Jwts.builder().setClaims(claims)
				.signWith(SignatureAlgorithm.HS512,secrectKey)
				.compact()).
				build();
		
	}
	
	public JwtUserDetails validateToken(String authToken) {
		try {
			Claims claims = Jwts.parser().setSigningKey(secrectKey).parseClaimsJws(authToken).getBody();
			Date expireDate = claims.getExpiration();
			if (expireDate.before(new Date(System.currentTimeMillis()))) {
				throw new RuntimeException("token is expired");
			}
			return JwtUserDetails
						.builder()
						.userName((String)claims.get("userName"))
						.roles((String)claims.get("role"))
						.userId(claims.getSubject())
						.build();
		} catch (Exception e) {
			throw new RuntimeException("token is not valid");
		}
	}

}
