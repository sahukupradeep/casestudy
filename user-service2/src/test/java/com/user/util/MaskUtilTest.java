package com.user.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MaskUtilTest {

	@Autowired
	MaskUtil maskUtil;

	@BeforeEach
	void setUp() throws Exception {

	}

	@Test
	void testMaskEmail() {
		String mail = "gemail@gmail.com";

		Assertions.assertEquals("g*****@gmail.com", maskUtil.maskEmail(mail));
	}

	@Test
	void testMaskPhone() {
		String phone = "1234567890";

		Assertions.assertEquals("1********0", maskUtil.maskPhone(phone));
	}

	@Test
	void testMaskDate() {
		String date = "1996-04-30";

		Assertions.assertEquals("**-**-**96", maskUtil.maskDate(date));
	}

}
