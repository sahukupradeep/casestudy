package com.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.user.UserServiceApplication;
import com.user.entity.User;
import com.user.payload.request.SignReq;
import com.user.payload.request.TempPswRequest;
import com.user.payload.request.UpdatePswReq;
import com.user.payload.response.MessageResponse;
import com.user.payload.response.SearchUserResponse;
import com.user.payload.response.UserResponse;
import com.user.service.UserService;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = { UserServiceApplication.class })
class UserControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private UserService userServiceMock;

	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void testSignin() throws Exception {

		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.OK).body(new MessageResponse());

		when(userServiceMock.signin(any(SignReq.class))).thenReturn(response);

		mockMvc.perform(post("/api/user/signin").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"userName\": \"userName\", \"password\": \"password\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void testRegisterUser() throws Exception {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.CREATED)
				.body(new MessageResponse());

		when(userServiceMock.registerUser(any(User.class))).thenReturn(response);

		mockMvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"userName\": \"userName\", \"password\": \"password\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	@Test
	void testChangePsw() throws Exception {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.CREATED)
				.body(new MessageResponse());

		when(userServiceMock.changePsw(any(UpdatePswReq.class))).thenReturn(response);

		mockMvc.perform(put("/api/user/change/psw").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"userName\": \"userName\", \"password\": \"password\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	@Test
	void testUpdateProfile() throws Exception {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.CREATED)
				.body(new MessageResponse());

		when(userServiceMock.updateProfile(any(User.class))).thenReturn(response);

		mockMvc.perform(put("/api/user/update/profile").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"userName\": \"userName\", \"password\": \"password\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	@Test
	void testGetByUserName() throws Exception {

		User user = new User();

		when(userServiceMock.getByUserName("")).thenReturn(user);

		mockMvc.perform(get("/api/user/get/username?userName=username").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testSearch() throws Exception {

		List<SearchUserResponse> list = List.of(new SearchUserResponse());

		when(userServiceMock.search(null, null, null, null)).thenReturn(list);

		mockMvc.perform(get("/api/user/search?userName=username").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testGetProfileByUserName() throws Exception {
		UserResponse user = new UserResponse();

		when(userServiceMock.getProfileByUserName(null)).thenReturn(user);

		mockMvc.perform(get("/api/user/get/profile/username?userName=username").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testSendTempPsw() throws Exception {
		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.CREATED)
				.body(new MessageResponse());

		when(userServiceMock.sendTempPsw(any(TempPswRequest.class))).thenReturn(response);

		mockMvc.perform(put("/api/user/send/temp/psw").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"userName\": \"userName\", \"password\": \"password\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

}
