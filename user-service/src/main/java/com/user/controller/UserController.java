package com.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.entity.User;
import com.user.payload.request.SignReq;
import com.user.payload.response.MessageResponse;
import com.user.payload.response.UserResponse;
import com.user.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user/")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@PostMapping("signin")
	public ResponseEntity<MessageResponse> signin(@RequestBody SignReq user) {

		logger.info("signin user");

		ResponseEntity<MessageResponse> response = userService.signin(user);

		return response;

	}

	@PostMapping("register")
	public ResponseEntity<MessageResponse> registerUser(@RequestBody User user) {

		logger.info("register user");

		ResponseEntity<MessageResponse> response = userService.registerUser(user);

		return response;

	}

	@PutMapping("change/psw")
	public ResponseEntity<MessageResponse> changePsw(@RequestBody User user) {

		logger.info("change password");

		ResponseEntity<MessageResponse> response = userService.changePsw(user);

		return response;
	}

	@PutMapping("update/profile")
	public ResponseEntity<MessageResponse> updateProfile(@RequestBody User user) {

		logger.info("update user profile");

		ResponseEntity<MessageResponse> response = userService.updateProfile(user);

		return response;
	}

	@GetMapping("get/username")
	public ResponseEntity<User> getByUserName(@RequestParam String userName) {

		logger.info("get user by userName");

		User user = userService.getByUserName(userName);

		return ResponseEntity.ok(user);
	}
	
	@GetMapping("get/profile/username")
	public ResponseEntity<UserResponse> getProfileByUserName(@RequestParam String userName) {

		logger.info("get user by userName");

		UserResponse user = userService.getProfileByUserName(userName);

		return ResponseEntity.ok(user);
	}
	

}
