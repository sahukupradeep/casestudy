package com.user.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.user.entity.Audit;

@SpringBootTest
class ExcelUtilsTest {

	@Autowired
	private ExcelUtils excelUtils;

	@BeforeEach
	void setUp() throws Exception {

	}

	@Test
	void testAuditsToExcel() throws IOException {

		Audit audit = new Audit();
		audit.setCreatedDate(LocalDateTime.now());

		List<Audit> list = List.of(audit);

		Assertions.assertEquals(ByteArrayInputStream.class, excelUtils.auditsToExcel(list).getClass());
	}

}
