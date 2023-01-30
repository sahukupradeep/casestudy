package com.user.payload.response;

public class SearchUserResponse {

	private String userName;
	private String firstName;
	private String lastName;
	private String address;

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

}
