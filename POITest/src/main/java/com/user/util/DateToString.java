package com.user.util;
// Java Program to convert date to string
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

public class DateToString {
	public static void main(String args[])
	{
		// used to fetch current date and time
		Date date = Calendar.getInstance().getTime();
	
		// specify the format yyyy-mm-dd to print current
		// date to as an argument
		DateFormat date_format = new SimpleDateFormat("yyyy-mm-dd");
	
		// print date in the specified format
	//	String date_string = date_format.format(LocalDate.now());
	
		// printing date in string
		System.out.println(convertToUtc(LocalDateTime.now()));
		System.out.println("Date to String : "+LocalDateTime.now());
	}
	
	
	static LocalDateTime convertToUtc(LocalDateTime time) {
	    return time.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
	}
}
