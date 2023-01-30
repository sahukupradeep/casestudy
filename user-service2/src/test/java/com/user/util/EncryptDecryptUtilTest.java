package com.user.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EncryptDecryptUtilTest {

	@Autowired
	private EncryptDecryptUtil encryptDecryptUtil;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testEncrypt() {
		String str = "1234";

		Assertions.assertEquals("HN6ojfC9pZ8=", encryptDecryptUtil.encrypt(str));
	}

	@Test
	void testEncryptException() {
		String str = null;

		Assertions.assertEquals(null, encryptDecryptUtil.encrypt(str));
	}

	@Test
	void testDecrypt() {
		String str = "HN6ojfC9pZ8=";

		Assertions.assertEquals("1234", encryptDecryptUtil.decrypt(str));
	}

	@Test
	void testDecryptException() {
		String str = null;

		Assertions.assertEquals(null, encryptDecryptUtil.decrypt(str));
	}

}
