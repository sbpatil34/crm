package com.rest.crm.entity;

import jakarta.validation.constraints.Size;

public class CustomerId {

	@Size(min = 1)
	private long customerId;

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(@Size(min = 1) long customerId) {
		this.customerId = customerId;
	}
}
