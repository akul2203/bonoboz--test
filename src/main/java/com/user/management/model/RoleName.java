package com.user.management.model;

public enum RoleName {
	ADMIN("This role is for  who have authority to create user, delete user and view list"), 
	USER("This role is for who have authority to view user list");
	String description;

	RoleName(String description) {
		this.description = description;
	}

	RoleName() {
		this.description = "Role of providing authority";
	}

	public String getDescription() {
		return description;
	}
}
