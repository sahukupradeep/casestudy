package com.user.payload.response;

import java.time.LocalDateTime;

public class SearchUserResponse {

	private String userName;
	private String firstName;
	private String lastName;
	private String address;
	private Integer status;
	private LocalDateTime loginDate;

	public SearchUserResponse() {
		super();
	}

	public SearchUserResponse(String userName, String firstName, String lastName, String address) {
		super();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}

	public SearchUserResponse(String userName, String firstName, String lastName, String address, Integer status,
			LocalDateTime loginDate) {
		super();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.status = status;
		this.loginDate = loginDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public LocalDateTime getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(LocalDateTime loginDate) {
		this.loginDate = loginDate;
	}

}
