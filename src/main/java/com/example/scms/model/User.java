package com.example.scms.model;



import com.example.scms.helper.Password;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int userId;
	@jakarta.validation.constraints.Size(min=3, message="Username must be minimum 3 characters")
	private String userName;
	@jakarta.validation.constraints.Email
	private String userEmail;
	@Password
	private String userPassword;
	private String userRole="user";
	private boolean isApproved;
	
	
	public User() {
		super();
	}

	
	
	
	
	public User(int userId,  String userName,
			 String userEmail, String userPassword, String userRole, boolean isApproved) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userRole = userRole;
		this.isApproved = isApproved;
	}





	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userEmail=" + userEmail + ", userPassword="
				+ userPassword + "]";
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}





	public String getUserRole() {
		return userRole;
	}





	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}





	public boolean isApproved() {
		return isApproved;
	}





	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	
	
	
	

}
