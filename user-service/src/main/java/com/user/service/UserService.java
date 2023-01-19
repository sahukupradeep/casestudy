package com.user.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.user.constant.AppConstant;
import com.user.entity.Audit;
import com.user.entity.User;
import com.user.payload.request.SignReq;
import com.user.payload.response.MessageResponse;
import com.user.payload.response.UserResponse;
import com.user.repository.UserRepository;
import com.user.util.EncryptDecryptUtil;
import com.user.util.MaskUtil;

@Service
public class UserService {

	private Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EncryptDecryptUtil encryptDecryptUtil;

	@Autowired
	private MaskUtil maskUtil;

	@Autowired
	private AuditService auditService;

	public ResponseEntity<MessageResponse> registerUser(User user) {

		logger.info("register user");
		try {
			if (userRepository.existsByUserName(user.getUserName())) {
				logger.error("Error: Username is already exist!");
				this.runAudit(user.getUserName(), AppConstant.REGISTER_ACT, "Error: Username is already exist!");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already exist!"));
			}

			if (userRepository.existsByEmail(user.getEmail())) {
				logger.error("Error: Email is already exist!");
				this.runAudit(user.getUserName(), AppConstant.REGISTER_ACT, "Error: Email is already exist!");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already exist!"));
			}

			if (userRepository.existsByPhone(user.getPhone())) {
				logger.error("Error: Phone is already exist!");
				this.runAudit(user.getUserName(), AppConstant.REGISTER_ACT, "Error: Phone is already exist!");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Phone is already exist!"));
			}

			user.setEmail(encryptDecryptUtil.encrypt(user.getEmail()));
			user.setPassword(encryptDecryptUtil.encrypt(user.getPassword()));
			user.setPhone(encryptDecryptUtil.encrypt(user.getPhone()));
			user.setDob(encryptDecryptUtil.encrypt(user.getDob()));

			user.setCreatedDate(LocalDateTime.now());
			user.setStatus(AppConstant.STATUS_ACTIVE);
			user.setPswType(AppConstant.VALID_PSW);

			userRepository.save(user);

			this.runAudit(user.getUserName(), AppConstant.REGISTER_ACT, AppConstant.SUCCESS_MSG);

			logger.info("User registered successfully!");
			return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("User registered successfullY!"));
		} catch (Exception e) {
			
			logger.error("registerUser() : Exception occured, message={}",e.getMessage(),e);
			this.runAudit(user.getUserName(), AppConstant.REGISTER_ACT, e.getLocalizedMessage());
			return ResponseEntity.internalServerError().body(new MessageResponse(e.getLocalizedMessage()));
		}

	}

	public ResponseEntity<MessageResponse> signin(SignReq user) {

		logger.info("sign in " + user.getUserName());
		try {
			String password = encryptDecryptUtil.encrypt(user.getPassword());

			Optional<User> optional = userRepository.findByUserNameAndPassword(user.getUserName(), password);

			if (optional.isPresent()) {
				logger.info("User login successfullY!");
				this.runAudit(user.getUserName(), AppConstant.LOGIN_ACT, AppConstant.SUCCESS_MSG);
				return ResponseEntity.ok(new MessageResponse("User login successfullY!"));
			}
			logger.warn("Invalid username and password");
			this.runAudit(user.getUserName(), AppConstant.LOGIN_ACT, "Invalid username and password");
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new MessageResponse("Invalid username and password"));
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			this.runAudit(user.getUserName(), AppConstant.LOGIN_ACT, e.getLocalizedMessage());
			return ResponseEntity.internalServerError().body(new MessageResponse(e.getLocalizedMessage()));
		}

	}

	public ResponseEntity<MessageResponse> changePsw(User user) {

		logger.info("change password");

		try {
			Optional<User> optional = userRepository.findByUserName(user.getUserName());

			if (optional.isEmpty()) {
				logger.error("Error: User not found!");
				this.runAudit(user.getUserName(), AppConstant.CHANGE_PSW_ACT, "Error: User not found!");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Error: User not found!"));
			}

			User existUser = optional.get();

			if (user.getPassword().equals(existUser.getPassword())) {
				logger.error("Error: Oldpassword and newpassword are same!");

				this.runAudit(user.getUserName(), AppConstant.CHANGE_PSW_ACT,
						"Error: Oldpassword and newpassword are same!");

				return ResponseEntity.badRequest()
						.body(new MessageResponse("Error: Oldpassword and newpassword are same!"));
			}

			existUser.setPassword(encryptDecryptUtil.encrypt(user.getPassword()));
			existUser.setUpdatedDate(LocalDateTime.now());

			userRepository.save(existUser);

			logger.info("Password changed successfully!");
			this.runAudit(user.getUserName(), AppConstant.CHANGE_PSW_ACT, AppConstant.SUCCESS_MSG);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new MessageResponse("Password changed successfullY!"));
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			this.runAudit(user.getUserName(), AppConstant.REGISTER_ACT, e.getLocalizedMessage());
			return ResponseEntity.internalServerError().body(new MessageResponse(e.getLocalizedMessage()));
		}
	}

	public ResponseEntity<MessageResponse> updateProfile(User user) {

		logger.info("update user profile");
		try {
			Optional<User> existUser = userRepository.findByUserName(user.getUserName());

			if (existUser.isEmpty()) {
				logger.error("Error: User not found!");
				this.runAudit(user.getUserName(), AppConstant.UPDATE_ACT, "Error: User not found!");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Error: User not found!"));
			}

			user.setEmail(encryptDecryptUtil.encrypt(user.getEmail()));
			user.setPassword(encryptDecryptUtil.encrypt(user.getPassword()));
			user.setPhone(encryptDecryptUtil.encrypt(user.getPhone()));
			user.setDob(encryptDecryptUtil.encrypt(user.getDob()));

			user.setId(existUser.get().getId());
			user.setPassword(existUser.get().getPassword());
			user.setRoleId(existUser.get().getRoleId());
			user.setCreatedDate(existUser.get().getCreatedDate());
			user.setUpdatedDate(LocalDateTime.now());

			userRepository.save(user);
			logger.info("User update successfully!");
			this.runAudit(user.getUserName(), AppConstant.UPDATE_ACT, AppConstant.UPDATE_ACT);
			return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("User update successfullY!"));
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			this.runAudit(user.getUserName(), AppConstant.UPDATE_ACT, e.getLocalizedMessage());
			return ResponseEntity.internalServerError().body(new MessageResponse(e.getLocalizedMessage()));
		}
	}

	public User getByUserName(String userName) {

		try {
			Optional<User> optional = userRepository.findByUserName(userName);
			if (optional.isPresent()) {

				User user = optional.get();

				// user.setPassword(encryptDecryptUtil.decrypt(user.getPassword()));

				String mail = encryptDecryptUtil.decrypt(user.getEmail());
				String phone = encryptDecryptUtil.decrypt(user.getPhone());
				String dob = encryptDecryptUtil.decrypt(user.getDob());

				user.setEmail(mail);
				user.setPhone(phone);
				user.setDob(dob);
				this.runAudit(userName, AppConstant.FATCH_ACT, AppConstant.SUCCESS_MSG);
				return user;
			}
			this.runAudit(userName, AppConstant.FATCH_ACT, AppConstant.FAIL_MSG);
			return null;
		} catch (Exception e) {
			this.runAudit(userName, AppConstant.FATCH_ACT, e.getLocalizedMessage());
			return null;
		}

	}

	public UserResponse getProfileByUserName(String userName) {

		try {
			Optional<User> optional = userRepository.findByUserName(userName);
			if (optional.isPresent()) {

				User user = optional.get();


				String mail = encryptDecryptUtil.decrypt(user.getEmail());
				String phone = encryptDecryptUtil.decrypt(user.getPhone());
				String dob = encryptDecryptUtil.decrypt(user.getDob());

				user.setEmail(maskUtil.maskEmail(mail));
				user.setPhone(maskUtil.maskPhone(phone));
				user.setDob(maskUtil.maskDate(dob));

				UserResponse userResponse = new UserResponse();
				BeanUtils.copyProperties(user, userResponse);

				this.runAudit(userName, AppConstant.FATCH_ACT, AppConstant.SUCCESS_MSG);
				return userResponse;
			}
			this.runAudit(userName, AppConstant.FATCH_ACT, AppConstant.FAIL_MSG);
			return null;
		} catch (Exception e) {
			this.runAudit(userName, AppConstant.FATCH_ACT, e.getLocalizedMessage());
			return null;
		}

	}


	private void runAudit(String userName, String activity, String msg) {

		Thread auditThread = new Thread(() -> {
			this.auditService.addAudit(userName, activity, msg);
		});
		auditThread.start();

	}

}