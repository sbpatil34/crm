package com.rest.crm.entity;

import jakarta.validation.constraints.Size;

public class EmailId {
	
	@Size(min = 3)
	private String emailId;
	
	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}	
}
