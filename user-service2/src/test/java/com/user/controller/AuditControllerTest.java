package com.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.user.UserServiceApplication;
import com.user.entity.Audit;
import com.user.service.AuditService;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = { UserServiceApplication.class })
class AuditControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private AuditService auditServiceMock;

	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void testAddAudit() throws Exception {

		Audit audit = new Audit();

		when(auditServiceMock.addAudit(any(Audit.class))).thenReturn(audit);

		mockMvc.perform(post("/api/audit/add").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"userName\": \"userName\", \"activity\": \"activity\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	@Test
	void testAddAuditNull() throws Exception {

		when(auditServiceMock.addAudit(any(Audit.class))).thenReturn(null);

		mockMvc.perform(post("/api/audit/add").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"userName\": \"userName\", \"activity\": \"activity\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
	}

	/*
	 * @Test void testDownloadExcel() throws Exception {
	 * 
	 * String userName = "username"; ByteArrayInputStream in = new
	 * ByteArrayInputStream(null);
	 * when(auditServiceMock.loadFile(userName)).thenReturn(in);
	 * 
	 * mockMvc.perform(get("/api/audit/download?userName=" +
	 * userName).accept(MediaType.APPLICATION_JSON)) .andExpect(status().isOk()); }
	 */

	@Test
	void testGetByUserName() throws Exception {

		String userName = "username";
		Audit audit = new Audit();
		List<Audit> list = List.of(audit);
		when(auditServiceMock.getByUserName(userName)).thenReturn(list);

		mockMvc.perform(get("/api/audit/get/userName?userName=" + userName).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
