package com.user.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
import com.user.payload.request.RoleStatusReq;
import com.user.payload.request.SignReq;
import com.user.payload.request.TempPswRequest;
import com.user.payload.request.UpdatePswReq;
import com.user.payload.response.MessageResponse;
import com.user.payload.response.SearchUserResponse;
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
	public ResponseEntity<MessageResponse> changePsw(@RequestBody UpdatePswReq user) {

		logger.info("change password " + user.getUserName());

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

	@GetMapping("search")
	public ResponseEntity<List<SearchUserResponse>> search(@RequestParam(required = false) String userName,
			@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName,
			@RequestParam(required = false) String dob) {

		logger.info("get user by userName");

		List<SearchUserResponse> users = userService.search(userName, firstName, lastName, dob);

		return ResponseEntity.ok(users);
	}

	@GetMapping("v1/search")
	public ResponseEntity<List<SearchUserResponse>> search1(@RequestParam(required = false) String userName,
			@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName,
			@RequestParam(required = false) String dob, @RequestParam(required = false) String searchDate) {

		logger.info("get user by userName");

		LocalDateTime createdDate=null;
		if(searchDate!=null && searchDate.trim()!="") {
			LocalDate localDate = LocalDate.parse(searchDate);

			createdDate = localDate.atStartOfDay();

		}
		
		List<SearchUserResponse> users = userService.search1(userName, firstName, lastName, dob, createdDate);

		return ResponseEntity.ok(users);
	}

	@GetMapping("get/profile/username")
	public ResponseEntity<UserResponse> getProfileByUserName(@RequestParam String userName) {

		logger.info("get user by userName");

		UserResponse user = userService.getProfileByUserName(userName);

		return ResponseEntity.ok(user);
	}

	@PutMapping("send/temp/psw")
	public ResponseEntity<MessageResponse> sendTempPsw(@RequestBody TempPswRequest user) {

		logger.info("send temp password");

		ResponseEntity<MessageResponse> response = userService.sendTempPsw(user);

		return response;
	}

	@PutMapping("update/role/status")
	public ResponseEntity<MessageResponse> updateRoleStatus(@RequestBody RoleStatusReq user) {

		logger.info("update user role and status");

		ResponseEntity<MessageResponse> response = userService.updateRoleStatus(user);

		return response;
	}

}
