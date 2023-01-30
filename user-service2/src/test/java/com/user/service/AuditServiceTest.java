package com.user.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.user.entity.Audit;
import com.user.repository.AuditRepository;
import com.user.util.ExcelUtils;

@SpringBootTest
class AuditServiceTest {

	@Autowired
	private AuditService auditService;

	@MockBean
	private AuditRepository auditRepositoryMock;

	@MockBean
	private ExcelUtils excelUtilsMock;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testAddAuditAudit() {
		Audit audit = new Audit();
		audit.setId(1L);
		audit.setActivity("Login");
		audit.setMessage("Success");

		when(auditRepositoryMock.save(audit)).thenReturn(audit);

		Assertions.assertEquals(audit, auditService.addAudit(audit));
	}

	@Test
	void testLoadFile() throws IOException {
		String userName = "username";

		Audit audit = new Audit();
		audit.setUserName("username");

		List<Audit> list = List.of(audit);
		when(auditRepositoryMock.findByUserName(userName)).thenReturn(list);

		ByteArrayInputStream in = mock(ByteArrayInputStream.class);
		when(excelUtilsMock.auditsToExcel(list)).thenReturn(in);

		Assertions.assertEquals(in, auditService.loadFile(userName));

	}

	@Test
	void testLoadFileException() throws IOException {
		String userName = "username";

		Audit audit = new Audit();
		audit.setUserName("username");

		List<Audit> list = List.of(audit);
		when(auditRepositoryMock.findByUserName(userName)).thenReturn(list);

		when(excelUtilsMock.auditsToExcel(list)).thenThrow(IOException.class);

		Assertions.assertEquals(null, auditService.loadFile(userName));

	}

	@Test
	void testGetByUserName() {
		String userName = "username";

		Audit audit = new Audit();
		audit.setUserName("username");

		List<Audit> list = List.of(audit);
		when(auditRepositoryMock.findByUserName(userName)).thenReturn(list);

		Assertions.assertEquals(list, auditService.getByUserName(userName));

	}

	@Test
	void testAddAuditStringStringString() {

		Audit audit = new Audit();
		audit.setId(1L);
		audit.setUserName("username");
		audit.setActivity("Login");
		audit.setMessage("Success");

		when(auditRepositoryMock.save(audit)).thenReturn(audit);

		Assertions.assertEquals(null,
				auditService.addAudit(audit.getUserName(), audit.getActivity(), audit.getMessage()));
	}

}
