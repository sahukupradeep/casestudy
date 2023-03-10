package com.user.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.user.constant.AppConstant;
import com.user.entity.User;
import com.user.payload.request.RoleStatusReq;
import com.user.payload.request.SignReq;
import com.user.payload.request.TempPswRequest;
import com.user.payload.request.UpdatePswReq;
import com.user.payload.response.MessageResponse;
import com.user.payload.response.SearchUserResponse;
import com.user.payload.response.UserResponse;
import com.user.repository.UserRepository;
import com.user.util.EncryptDecryptUtil;
import com.user.util.MaskUtil;

@SpringBootTest
class UserServiceTest {

	@Autowired
	private UserService userService;

//	@MockBean
//	private UserService userServiceMock;

	@MockBean
	private UserRepository userRepositoryMock;

	@MockBean
	private EncryptDecryptUtil encryptDecryptUtilMock;

	@MockBean
	private MaskUtil maskUtilMock;

	@MockBean
	private AuditService auditServiceMock;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testRegisterUserExistUser() {

		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new MessageResponse());

		User user = new User();

		user.setUserName("username");

		when(encryptDecryptUtilMock.encrypt("username")).thenReturn("username");

		when(userRepositoryMock.existsByUserName(user.getUserName())).thenReturn(true);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.registerUser(user).getStatusCodeValue());
	}

	@Test
	void testRegisterUserExistEmail() {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new MessageResponse());

		User user = new User();
		user.setUserName("username");
		user.setEmail("email@gmail.com");

		when(encryptDecryptUtilMock.encrypt("username")).thenReturn("username");
		when(encryptDecryptUtilMock.encrypt("email@gmail.com")).thenReturn("email@gmail.com");

		when(userRepositoryMock.existsByUserName(user.getUserName())).thenReturn(false);

		when(userRepositoryMock.existsByEmail(user.getEmail())).thenReturn(true);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.registerUser(user).getStatusCodeValue());
	}

	@Test
	void testRegisterUserExitPhone() {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new MessageResponse());
		User user = new User();
		user.setUserName("username");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		when(encryptDecryptUtilMock.encrypt("username")).thenReturn("username");
		when(encryptDecryptUtilMock.encrypt("email@gmail.com")).thenReturn("email@gmail.com");
		when(encryptDecryptUtilMock.encrypt("9087654321")).thenReturn("9087654321");
		when(userRepositoryMock.existsByUserName(user.getUserName())).thenReturn(false);

		when(userRepositoryMock.existsByEmail(user.getEmail())).thenReturn(false);

		when(userRepositoryMock.existsByPhone(user.getPhone())).thenReturn(true);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.registerUser(user).getStatusCodeValue());
	}

	@Test
	void testRegisterUser() {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.CREATED)
				.body(new MessageResponse());
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		when(userRepositoryMock.existsByUserName(user.getUserName())).thenReturn(false);

		when(userRepositoryMock.existsByEmail(user.getEmail())).thenReturn(false);

		when(userRepositoryMock.existsByPhone(user.getPhone())).thenReturn(false);

		when(encryptDecryptUtilMock.encrypt("123")).thenReturn("123");

		when(userRepositoryMock.save(user)).thenReturn(user);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.registerUser(user).getStatusCodeValue());
	}

	@Test
	void testRegisterUserException() {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new MessageResponse());
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		when(userRepositoryMock.existsByUserName(user.getUserName())).thenReturn(false);

		when(userRepositoryMock.existsByEmail(user.getEmail())).thenReturn(false);

		when(userRepositoryMock.existsByPhone(user.getPhone())).thenReturn(false);

		when(encryptDecryptUtilMock.encrypt("123")).thenReturn("123");

		when(userRepositoryMock.save(user)).thenThrow(RuntimeException.class);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.registerUser(user).getStatusCodeValue());
	}

	@Test
	void testSigninNotFound() {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new MessageResponse());
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		SignReq req = new SignReq();
		req.setPassword("313131");
		req.setUserName("pradeep");

		Optional<User> optional = Optional.empty();

		when(encryptDecryptUtilMock.encrypt("123")).thenReturn("123");
		when(userRepositoryMock.findByUserNameAndPassword(user.getUserName(), user.getPassword())).thenReturn(optional);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.signin(req).getStatusCodeValue());

	}

	@Test
	void testSigninTemp() {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.OK).body(new MessageResponse());
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");
		user.setPswType(AppConstant.TEMP_PSW);
		user.setStatus(1);

		SignReq req = new SignReq();
		req.setPassword("6565655");
		req.setUserName("username");

		Optional<User> optional = Optional.of(user);

		when(encryptDecryptUtilMock.encrypt("6565655")).thenReturn("6565655");
		when(userRepositoryMock.findByUserNameAndPasswordActive(user.getUserName(), user.getPassword()))
				.thenReturn(optional);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.signin(req).getStatusCodeValue());
	}

	@Test
	void testSignin() {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.OK).body(new MessageResponse());
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");
		user.setStatus(1);

		SignReq req = new SignReq();
		req.setPassword("6565655");
		req.setUserName("username");

		Optional<User> optional = Optional.of(user);

		when(encryptDecryptUtilMock.encrypt("6565655")).thenReturn("6565655");
		when(userRepositoryMock.findByUserNameAndPasswordActive(user.getUserName(), user.getPassword()))
				.thenReturn(optional);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.signin(req).getStatusCodeValue());
	}

	@Test
	void testSigninException() {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new MessageResponse());
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		SignReq req = new SignReq();
		req.setPassword("6565655");
		req.setUserName("username");

		when(encryptDecryptUtilMock.encrypt("6565655")).thenReturn("6565655");
		when(userRepositoryMock.findByUserNameAndPasswordActive(user.getUserName(), user.getPassword()))
				.thenThrow(RuntimeException.class);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.signin(req).getStatusCodeValue());
	}

	@Test
	void testChangePswNotFound() {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new MessageResponse());
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		UpdatePswReq req = new UpdatePswReq();
		req.setNewPassword("3647688");
		req.setPassword("6565655");
		req.setUserName("username");

		Optional<User> optional = Optional.empty();

		when(userRepositoryMock.findByUserName(user.getUserName())).thenReturn(optional);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.changePsw(req).getStatusCodeValue());
	}

	@Test
	void testChangePswMismatchPsw() {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new MessageResponse());
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		UpdatePswReq req = new UpdatePswReq();
		req.setNewPassword("3647688");
		req.setPassword("65615655");
		req.setUserName("username");

		Optional<User> optional = Optional.of(user);

		when(userRepositoryMock.findByUserName(user.getUserName())).thenReturn(optional);
		when(encryptDecryptUtilMock.encrypt("65615655")).thenReturn("65615655");

		Assertions.assertEquals(response.getStatusCodeValue(), userService.changePsw(req).getStatusCodeValue());
	}

	@Test
	void testChangePsw() {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.CREATED)
				.body(new MessageResponse());
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		UpdatePswReq req = new UpdatePswReq();
		req.setNewPassword("3647688");
		req.setPassword("6565655");
		req.setUserName("username");

		Optional<User> optional = Optional.of(user);

		when(encryptDecryptUtilMock.encrypt("6565655")).thenReturn("6565655");
		when(userRepositoryMock.findByUserName(user.getUserName())).thenReturn(optional);
		when(userRepositoryMock.save(user)).thenReturn(user);
		Assertions.assertEquals(response.getStatusCodeValue(), userService.changePsw(req).getStatusCodeValue());
	}

	@Test
	void testChangePswException() {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new MessageResponse());
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		UpdatePswReq req = new UpdatePswReq();
		req.setNewPassword("3647688");
		req.setPassword("6565655");
		req.setUserName("username");

		Optional<User> optional = Optional.of(user);

		when(encryptDecryptUtilMock.encrypt("6565655")).thenReturn("6565655");
		when(userRepositoryMock.findByUserName(user.getUserName())).thenReturn(optional);
		when(userRepositoryMock.save(user)).thenThrow(RuntimeException.class);
		Assertions.assertEquals(response.getStatusCodeValue(), userService.changePsw(req).getStatusCodeValue());
	}

	@Test
	void testUpdateProfileNotFound() {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new MessageResponse());
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		Optional<User> optional = Optional.empty();

		when(userRepositoryMock.findByUserName(user.getUserName())).thenReturn(optional);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.updateProfile(user).getStatusCodeValue());
	}

	@Test
	void testUpdateProfile() {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.CREATED)
				.body(new MessageResponse());
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		Optional<User> optional = Optional.of(user);

		when(userRepositoryMock.findByUserName(user.getUserName())).thenReturn(optional);
		when(encryptDecryptUtilMock.encrypt("6565655")).thenReturn("6565655");
		when(userRepositoryMock.save(user)).thenReturn(user);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.updateProfile(user).getStatusCodeValue());
	}

	@Test
	void testUpdateProfileException() {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new MessageResponse());
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		Optional<User> optional = Optional.of(user);

		when(userRepositoryMock.findByUserName(user.getUserName())).thenReturn(optional);
		when(encryptDecryptUtilMock.encrypt("6565655")).thenReturn("6565655");
		when(userRepositoryMock.save(user)).thenThrow(RuntimeException.class);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.updateProfile(user).getStatusCodeValue());
	}

	@Test
	void testGetByUserNameNotFound() {
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		Optional<User> optional = Optional.empty();

		when(userRepositoryMock.findByUserName(user.getUserName())).thenReturn(optional);

		Assertions.assertEquals(null, userService.getByUserName(user.getUserName()));
	}

	@Test
	void testGetByUserName() {
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");
		user.setDob("30-04-1996");

		Optional<User> optional = Optional.of(user);

		when(userRepositoryMock.findByUserName(user.getUserName())).thenReturn(optional);
		when(encryptDecryptUtilMock.decrypt("6565655")).thenReturn("6565655");

		Assertions.assertEquals(user, userService.getByUserName(user.getUserName()));
	}

	@Test
	void testGetByUserNameException() {
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");
		user.setDob("30-04-1996");

		when(userRepositoryMock.findByUserName(user.getUserName())).thenThrow(RuntimeException.class);

		Assertions.assertEquals(null, userService.getByUserName(user.getUserName()));
	}

	@Test
	void testGetProfileByUserNameNotFound() {
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		Optional<User> optional = Optional.empty();

		when(userRepositoryMock.findByUserName(user.getUserName())).thenReturn(optional);

		Assertions.assertEquals(null, userService.getProfileByUserName(user.getUserName()));
	}

	@Test
	void testGetProfileByUserName() {
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");
		user.setDob("30-04-1996");

		Optional<User> optional = Optional.of(user);

		when(userRepositoryMock.findByUserName(user.getUserName())).thenReturn(optional);
		when(encryptDecryptUtilMock.decrypt("6565655")).thenReturn("6565655");

		when(maskUtilMock.maskEmail(user.getEmail())).thenReturn(user.getEmail());
		when(maskUtilMock.maskPhone(user.getPhone())).thenReturn(user.getPhone());
		when(maskUtilMock.maskDate(user.getDob())).thenReturn(user.getDob());

		UserResponse response = new UserResponse();

		Assertions.assertEquals(response.getClass(), userService.getProfileByUserName(user.getUserName()).getClass());
	}

	@Test
	void testGetProfileByUserNameException() {
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");
		user.setDob("30-04-1996");

		when(userRepositoryMock.findByUserName(user.getUserName())).thenThrow(RuntimeException.class);

		Assertions.assertEquals(null, userService.getProfileByUserName(user.getUserName()));
	}

	@Test
	void testSearchEmpty() {
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		List<SearchUserResponse> list = List.of();

		when(userRepositoryMock.search(user.getUserName(), user.getFirstName(), user.getLastName(), user.getDob()))
				.thenReturn(list);

		Assertions.assertEquals(null,
				userService.search(user.getUserName(), user.getFirstName(), user.getLastName(), user.getDob()));
	}

	@Test
	void testSearchNull() {
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		List<SearchUserResponse> list = null;

		when(userRepositoryMock.search(user.getUserName(), user.getFirstName(), user.getLastName(), user.getDob()))
				.thenReturn(list);

		Assertions.assertEquals(null,
				userService.search(user.getUserName(), user.getFirstName(), user.getLastName(), user.getDob()));
	}

	@Test
	void testSearch() {
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");

		user.setPhone("9087654321");

		SearchUserResponse response = new SearchUserResponse();
		List<SearchUserResponse> list = List.of(response);

		when(userRepositoryMock.search(user.getUserName(), user.getFirstName(), user.getLastName(), user.getDob()))
				.thenReturn(list);

		Assertions.assertEquals(list,
				userService.search(user.getUserName(), user.getFirstName(), user.getLastName(), user.getDob()));
	}

	@Test
	void testSearchException() {
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		when(userRepositoryMock.search(user.getUserName(), user.getFirstName(), user.getLastName(), user.getDob()))
				.thenThrow(RuntimeException.class);

		Assertions.assertEquals(null,
				userService.search(user.getUserName(), user.getFirstName(), user.getLastName(), user.getDob()));
	}

	@Test
	void testSendTempPswNotFound() {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new MessageResponse());

		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		TempPswRequest req = new TempPswRequest();
		Optional<User> optional = Optional.empty();

		when(encryptDecryptUtilMock.decrypt("6565655")).thenReturn("6565655");
		when(userRepositoryMock.findByUserNameAndEmailAndPhoneAndDob(user.getUserName(), user.getFirstName(),
				user.getLastName(), user.getDob())).thenReturn(optional);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.sendTempPsw(req).getStatusCodeValue());
	}

	@Test
	void testSendTempPsw() {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.OK).body(new MessageResponse());

		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");
		user.setDob("30-04-1996");

		TempPswRequest req = new TempPswRequest();
		req.setDob("30-04-1996");
		req.setEmail("email@gmail.com");
		req.setPhone("9087654321");
		req.setUserName("username");
		Optional<User> optional = Optional.of(user);

		when(encryptDecryptUtilMock.encrypt("9087654321")).thenReturn("9087654321");
		when(encryptDecryptUtilMock.encrypt("30-04-1996")).thenReturn("30-04-1996");
		when(encryptDecryptUtilMock.encrypt("email@gmail.com")).thenReturn("email@gmail.com");
		when(userRepositoryMock.findByUserNameAndEmailAndPhoneAndDob(user.getUserName(), user.getEmail(),
				user.getPhone(), user.getDob())).thenReturn(optional);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.sendTempPsw(req).getStatusCodeValue());
	}

	@Test
	void testSendTempPswException() {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new MessageResponse());

		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");
		user.setDob("30-04-1996");

		TempPswRequest req = new TempPswRequest();
		req.setDob("30-04-1996");
		req.setEmail("email@gmail.com");
		req.setPhone("9087654321");
		req.setUserName("username");

		when(encryptDecryptUtilMock.encrypt("9087654321")).thenReturn("9087654321");
		when(encryptDecryptUtilMock.encrypt("30-04-1996")).thenReturn("30-04-1996");
		when(encryptDecryptUtilMock.encrypt("email@gmail.com")).thenReturn("email@gmail.com");
		when(userRepositoryMock.findByUserNameAndEmailAndPhoneAndDob(user.getUserName(), user.getEmail(),
				user.getPhone(), user.getDob())).thenThrow(RuntimeException.class);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.sendTempPsw(req).getStatusCodeValue());
	}

	@Test
	void testUpdateRoleStatusNF() {

		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new MessageResponse());

		RoleStatusReq req = new RoleStatusReq();
		req.setIsAdmin(true);
		req.setIsDisable(true);
		req.setUserName("username");

		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");
		user.setDob("30-04-1996");

		Optional<User> optional = Optional.empty();

		when(userRepositoryMock.findByUserName(user.getUserName())).thenReturn(optional);
		when(userRepositoryMock.save(user)).thenReturn(user);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.updateRoleStatus(req).getStatusCodeValue());
	}

	@Test
	void testUpdateRoleStatusFalse() {

		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.CREATED)
				.body(new MessageResponse());

		RoleStatusReq req = new RoleStatusReq();
		req.setIsAdmin(false);
		req.setIsDisable(false);
		req.setUserName("username");

		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");
		user.setDob("30-04-1996");

		Optional<User> optional = Optional.of(user);

		when(userRepositoryMock.findByUserName(user.getUserName())).thenReturn(optional);
		when(userRepositoryMock.save(user)).thenReturn(user);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.updateRoleStatus(req).getStatusCodeValue());
	}

	@Test
	void testUpdateRoleStatus() {

		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.CREATED)
				.body(new MessageResponse());

		RoleStatusReq req = new RoleStatusReq();
		req.setIsAdmin(true);
		req.setIsDisable(true);
		req.setUserName("username");

		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");
		user.setDob("30-04-1996");

		Optional<User> optional = Optional.of(user);

		when(userRepositoryMock.findByUserName(user.getUserName())).thenReturn(optional);
		when(userRepositoryMock.save(user)).thenReturn(user);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.updateRoleStatus(req).getStatusCodeValue());
	}

	@Test
	void testUpdateRoleStatusException() {

		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new MessageResponse());

		RoleStatusReq req = new RoleStatusReq();
		req.setIsAdmin(true);
		req.setIsDisable(true);
		req.setUserName("username");

		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");
		user.setDob("30-04-1996");

		Optional<User> optional = Optional.of(user);

		when(userRepositoryMock.findByUserName(user.getUserName())).thenReturn(optional);
		when(userRepositoryMock.save(user)).thenThrow(RuntimeException.class);

		Assertions.assertEquals(response.getStatusCodeValue(), userService.updateRoleStatus(req).getStatusCodeValue());
	}

	@Test
	void testSearch1Empty() {
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		List<SearchUserResponse> list = List.of();

		when(userRepositoryMock.search1(user.getUserName(), user.getFirstName(), user.getLastName(), user.getDob(),
				null,null)).thenReturn(list);

		Assertions.assertEquals(null,
				userService.search1(user.getUserName(), user.getFirstName(), user.getLastName(), user.getDob(), null));
	}

	@Test
	void testSearch1Null() {
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		List<SearchUserResponse> list = null;

		when(userRepositoryMock.search1(user.getUserName(), user.getFirstName(), user.getLastName(), user.getDob(),
				null, null)).thenReturn(list);

		Assertions.assertEquals(null,
				userService.search1(user.getUserName(), user.getFirstName(), user.getLastName(), user.getDob(), LocalDateTime.now()));
	}

	@Test
	void testSearch1() {
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");

		user.setPhone("9087654321");

		SearchUserResponse response = new SearchUserResponse();
		List<SearchUserResponse> list = List.of(response);

		when(userRepositoryMock.search1(user.getUserName(), user.getFirstName(), user.getLastName(), user.getDob(),
				null, null)).thenReturn(list);

		Assertions.assertEquals(list,
				userService.search1(user.getUserName(), user.getFirstName(), user.getLastName(), user.getDob(), null));
	}

	@Test
	void testSearch1Exception() {
		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");

		when(userRepositoryMock.search1(user.getUserName(), user.getFirstName(), user.getLastName(), user.getDob(),
				null, null)).thenThrow(RuntimeException.class);

		Assertions.assertEquals(null, userService.search1(user.getUserName(), user.getFirstName(), user.getLastName(),
				user.getDob(), null));
	}

	@Test
	void testSchedularUserUpate() {

		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");
		user.setLoginDate(LocalDateTime.now().minusDays(3));

		List<User> list = List.of(user);

		when(userRepositoryMock.findAllWithLoginDateTimeBefore(any(LocalDateTime.class))).thenReturn(list);

		userService.schedularUserUpate();
	}

	@Test
	void testSchedularUserUpateException() {

		User user = new User();
		user.setId(1L);
		user.setUserName("username");
		user.setPassword("6565655");
		user.setFirstName("pradeep");
		user.setLastName("Sahu");
		user.setEmail("email@gmail.com");
		user.setPhone("9087654321");
		user.setLoginDate(LocalDateTime.now().minusDays(3));

		when(userRepositoryMock.findAllWithLoginDateTimeBefore(any(LocalDateTime.class)))
				.thenThrow(RuntimeException.class);

		userService.schedularUserUpate();
	}

}
