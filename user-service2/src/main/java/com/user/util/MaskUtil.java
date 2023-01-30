package com.user.util;

import org.springframework.stereotype.Component;

@Component
public class MaskUtil {

	public String maskEmail(String mail) {
		int at = mail.indexOf("@");
		mail = mail.toLowerCase();
		return mail.charAt(0) + "*****" + mail.substring(at);

	}
	public String maskPhone(String phone) {
		phone = phone.replaceAll("[^0-9]", "");
        return phone.substring(phone.length()-10,(phone.length()+1-10)) + "********" + phone.substring(phone.length() - 1);

	}
	public String maskDate(String date) {
		date=date.substring(2,4);
		return "**-**-**"+date;
	}

}
