package com.user.payload.request;

public class RoleStatusReq {
	
	private String userName;
	
	private Boolean isAdmin;
	
	private Boolean isDisable;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Boolean getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(Boolean isDisable) {
		this.isDisable = isDisable;
	}
	
	

}
