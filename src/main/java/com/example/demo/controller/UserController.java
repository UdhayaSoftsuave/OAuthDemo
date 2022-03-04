package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repo.UserRepository;
import com.example.demo.model.AuthToken;
import com.example.demo.model.JwtUserDetails;
import com.example.demo.model.User;
import com.example.demo.model.UserLoginDTO;
import com.example.demo.utils.JwtUtils;

@RestController
@RequestMapping("/auth/user")
public class UserController {
	
	@Autowired
	private UserRepository repository;
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	private AuthToken register(@RequestBody User user) {
		User user2 =repository.save(user);
		
		return jwtUtils.generateToken(user2);
		
	}
	
	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	private AuthToken signIn(@RequestBody UserLoginDTO user) {
		 Optional<User> existUser =repository.findByUserName(user.getUserName());
		 if (existUser.isPresent()) {
			User users = existUser.get();
			if (users.getPassword().equals(user.getPassword())) {
				return jwtUtils.generateToken(repository.save(users)); 
			}
		}
		
		throw new UsernameNotFoundException("User not found ");
		
	}
	
	@GetMapping("/validate")
	private JwtUserDetails validate(@RequestHeader(HttpHeaders.AUTHORIZATION)  AuthToken authToken) {
		return jwtUtils.validateToken(authToken);
		
	}


}
