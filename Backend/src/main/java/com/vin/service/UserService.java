package com.vin.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vin.entity.User;
import com.vin.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userR;
	
	
	public String registerService(User user) {
		if(!(user == null)) {
			userR.save(user);
			return "Success";
		}else {
			return "Try Again";
		}
	}


	public int loginService(User user) {
		Optional<User> userO = userR.findByname(user.getName());
		if(userO.isPresent()) {
			User userTemp = userO.get();
			Boolean resp = userTemp.getPassword().equals(user.getPassword());
			if(resp) {
				return userTemp.getId();
			}
		}
		return 0;
	}
}
