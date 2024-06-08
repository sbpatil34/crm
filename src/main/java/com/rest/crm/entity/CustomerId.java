package com.rest.crm.entity;

import jakarta.validation.constraints.Size;

public class CustomerId {
	 
	@Size(min = 1)
    private long id;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
