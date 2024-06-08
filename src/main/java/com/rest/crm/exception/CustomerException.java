package com.rest.crm.exception;

import org.springframework.http.HttpStatus;

public class CustomerException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private HttpStatus httpsStatus;
  
	public CustomerException(String message, HttpStatus httpsStatus) {
		super(message);
		this.httpsStatus = httpsStatus;
	}

	public HttpStatus getHttpsStatus() {
		return this.httpsStatus;
	}  
}