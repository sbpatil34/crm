package com.rest.crm.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.rest.crm.entity.Customer;
import com.rest.crm.exception.CustomerException;

public interface CustomerService {
	
	public ResponseEntity<List<Customer>> getAllCustomers();
	
	public ResponseEntity<String> addCustomer(Customer customer) throws CustomerException;
	
	public ResponseEntity<Customer> getCustomerByEmail(String email) throws CustomerException;
	
	public ResponseEntity<String> updateCustomerById(Customer newCustomer, Long id) throws CustomerException;
	
	public ResponseEntity<String> deleteCustomerById(Long id) throws CustomerException;	

}
