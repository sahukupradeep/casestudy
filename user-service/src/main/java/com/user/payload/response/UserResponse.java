package com.user.payload.response;

public class UserResponse {

	private Long id;

	private String userName;

	private String firstName;

	private String lastName;

	private String email;

	private String phone;

	private String dob;

	private String address;

	private Integer status;

	private Integer roleId;

	public UserResponse() {
		super();
	}

	public UserResponse(Long id, String userName, String firstName, String lastName, String email, String phone,
			String dob, String address, Integer status, Integer roleId) {
		super();
		this.id = id;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.dob = dob;
		this.address = address;
		this.status = status;
		this.roleId = roleId;
	}

	public UserResponse(String userName, String firstName, String lastName, String address) {
		super();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
