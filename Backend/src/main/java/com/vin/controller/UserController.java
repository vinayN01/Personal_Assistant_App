package com.vin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vin.entity.User;
import com.vin.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class UserController {
	
@Autowired
private UserService userS;


@PostMapping("/signup")
public String registerUser(@RequestBody User user) {
	String result = userS.registerService(user);
	return result;
}

@PostMapping("/login")
public Boolean loginUser(@RequestBody User user, HttpSession session) {
	int result = userS.loginService(user);
	if(result!=0) {
		session.setAttribute("userId", result);
		return true;
	}
	return false;
}


}
