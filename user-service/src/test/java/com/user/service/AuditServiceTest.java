package com.user.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.user.entity.Audit;
import com.user.entity.User;
import com.user.repository.AuditRepository;
import com.user.repository.UserRepository;
import com.user.util.ExcelUtils;

@SpringBootTest
class AuditServiceTest {

	@Autowired
	private AuditService auditService;

	@MockBean
	private UserRepository userRepositoryMock;

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
	void testAddAuditAuditException() {
		Audit audit = new Audit();
		audit.setId(1L);
		audit.setActivity("Login");
		audit.setMessage("Success");

		when(auditRepositoryMock.save(audit)).thenThrow(RuntimeException.class);

		Assertions.assertEquals(null, auditService.addAudit(audit));
	}

	@Test
	void testLoadFile() throws IOException {
		String userName = "username";

		Audit audit = new Audit();
		audit.setUserName("username");

		List<Audit> list = List.of(audit);
		when(auditRepositoryMock.findByUserNameOrderByCreatedDateDesc(userName)).thenReturn(list);

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
		when(auditRepositoryMock.findByUserNameOrderByCreatedDateDesc(userName)).thenReturn(list);

		when(excelUtilsMock.auditsToExcel(list)).thenThrow(IOException.class);

		Assertions.assertEquals(null, auditService.loadFile(userName));

	}

	@Test
	void testGetByUserName() {
		String userName = "username";

		Audit audit = new Audit();
		audit.setUserName("username");

		List<Audit> list = List.of(audit);
		when(auditRepositoryMock.findByUserNameOrderByCreatedDateDesc(userName)).thenReturn(list);

		Assertions.assertEquals(list, auditService.getByUserName(userName));

	}

	@Test
	void testGetByUserNameException() {
		String userName = "username";

		Audit audit = new Audit();
		audit.setUserName("username");

		when(auditRepositoryMock.findByUserNameOrderByCreatedDateDesc(userName)).thenThrow(RuntimeException.class);

		Assertions.assertEquals(null, auditService.getByUserName(userName));

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

	@Test
	void testAddAuditStringStringStringUser() {

		Audit audit = new Audit();
		audit.setId(1L);
		audit.setUserName("username");
		audit.setActivity("Login");
		audit.setMessage("Success");

		User user = new User();
		user.setUserName("pradeep");
		user.setFirstName("Pradeep");
		user.setLastName("Sahu");

		Optional<User> optional = Optional.of(user);

		when(userRepositoryMock.findByUserName(user.getUserName())).thenReturn(optional);

		when(auditRepositoryMock.save(audit)).thenReturn(audit);

		Assertions.assertEquals(null,
				auditService.addAudit(audit.getUserName(), audit.getActivity(), audit.getMessage()));
	}

	@Test
	void testAddAuditStringStringStringException() {

		Audit audit = new Audit();
		audit.setId(1L);
		audit.setUserName("username");
		audit.setActivity("Login");
		audit.setMessage("Success");

		when(auditRepositoryMock.save(any(Audit.class))).thenThrow(RuntimeException.class);

		Assertions.assertEquals(null,
				auditService.addAudit(audit.getUserName(), audit.getActivity(), audit.getMessage()));
	}

	
	@Test
	void testSearchDownloadNFEmpty() {

		Audit audit = new Audit();
		audit.setId(1L);
		audit.setUserName("username");
		audit.setActivity("Login");
		audit.setMessage("Success");

		List<Audit> list = List.of();

		when(auditRepositoryMock.search(audit.getFirstName(), null, null, null, null)).thenReturn(list);

		Assertions.assertEquals(null, auditService.searchDownload(audit.getUserName(), null, null, ""));
	}
	
	@Test
	void testSearchDownloadNFNull() {

		Audit audit = new Audit();
		audit.setId(1L);
		audit.setUserName("username");
		audit.setActivity("Login");
		audit.setMessage("Success");

		List<Audit> list = null;

		when(auditRepositoryMock.search(audit.getFirstName(), null, null, null, null)).thenReturn(list);

		Assertions.assertEquals(list, auditService.searchDownload(audit.getUserName(), null, null, null));
	}
	@Test
	void testSearchDownload() {

		Audit audit = new Audit();
		audit.setId(1L);
		audit.setUserName("username");
		audit.setActivity("Login");
		audit.setMessage("Success");
		audit.setCreatedDate(LocalDateTime.now());

		List<Audit> list = List.of(audit);

		when(auditRepositoryMock.search(audit.getFirstName(), null, null, null, null)).thenReturn(list);

		Assertions.assertEquals(null, auditService.searchDownload(audit.getUserName(), null, null, "2023-01-23"));
	}
	
	@Test
	void testSearchDownloadException() {

		Audit audit = new Audit();
		audit.setId(1L);
		audit.setUserName("username");
		audit.setActivity("Login");
		audit.setMessage("Success");


		when(auditRepositoryMock.search(audit.getFirstName(), null, null, null, null)).thenThrow(RuntimeException.class);

		Assertions.assertEquals(null, auditService.searchDownload(audit.getUserName(), null, null, null));
	}
	
	@Test
	void testSearchDownloadV1NFEmpty() {

		Audit audit = new Audit();
		audit.setId(1L);
		audit.setUserName("username");
		audit.setActivity("Login");
		audit.setMessage("Success");

		List<Audit> list = List.of();

		when(auditRepositoryMock.search(audit.getFirstName(), null, null, null, null)).thenReturn(list);

		Assertions.assertEquals(null, auditService.searchDownloadV1(audit.getUserName(), null, null, "",""));
	}
	
	@Test
	void testSearchDownloadV1NFNull() {

		Audit audit = new Audit();
		audit.setId(1L);
		audit.setUserName("username");
		audit.setActivity("Login");
		audit.setMessage("Success");

		List<Audit> list = null;

		when(auditRepositoryMock.search(audit.getFirstName(), null, null, null, null)).thenReturn(list);

		Assertions.assertEquals(list, auditService.searchDownloadV1(audit.getUserName(), null, null, null,null));
	}
	@Test
	void testSearchDownloadV1() {

		Audit audit = new Audit();
		audit.setId(1L);
		audit.setUserName("username");
		audit.setActivity("Login");
		audit.setMessage("Success");
		audit.setCreatedDate(LocalDateTime.now());

		List<Audit> list = List.of(audit);

		when(auditRepositoryMock.search(audit.getFirstName(), null, null, null, null)).thenReturn(list);

		Assertions.assertEquals(null, auditService.searchDownloadV1(audit.getUserName(), null, null, "2023-01-23","2023-01-24"));
	}
	
	@Test
	void testSearchDownloadV1Exception() {

		Audit audit = new Audit();
		audit.setId(1L);
		audit.setUserName("username");
		audit.setActivity("Login");
		audit.setMessage("Success");


		when(auditRepositoryMock.search(audit.getFirstName(), null, null, null, null)).thenThrow(RuntimeException.class);

		Assertions.assertEquals(null, auditService.searchDownloadV1(audit.getUserName(), null, null, null,null));
	}
}
