package com.user.util;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Main {

	public static void main(String[] args) throws Exception
	{
		DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
								.ofPattern("dd/MM/uuuu'T'HH:mm:ss:SSSXXXXX");

		//Date string with offset information
		String dateString = "03/08/2019T16:20:17:717+05:30";

		//Instance with given offset
		OffsetDateTime odtInstanceAtOffset = OffsetDateTime.parse(dateString, DATE_TIME_FORMATTER);

		//Instance in UTC
		OffsetDateTime odtInstanceAtUTC = odtInstanceAtOffset.withOffsetSameInstant(ZoneOffset.UTC);

		//Formatting to string
		String dateStringInUTC = odtInstanceAtUTC.format(DATE_TIME_FORMATTER);

		System.out.println(odtInstanceAtOffset);
		System.out.println(odtInstanceAtUTC);
		System.out.println(dateStringInUTC);

		//Convert OffsetDateTime to instant which is in UTC
		System.out.println(odtInstanceAtOffset.toInstant());
	}
}