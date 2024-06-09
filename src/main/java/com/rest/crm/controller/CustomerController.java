package com.rest.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.rest.crm.entity.Customer;
import com.rest.crm.entity.CustomerId;
import com.rest.crm.entity.EmailId;
import com.rest.crm.exception.CustomerException;
import com.rest.crm.service.CustomerService;

import jakarta.validation.Valid;


@Validated
@RestController
@RequestMapping("/crm/v1")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customers")
	public  ResponseEntity<List<Customer>> getAllCustomers() {
		 return customerService.getAllCustomers();
	}
	
	@PostMapping("/customers")
	public  ResponseEntity<String> addCustomer(@Valid @RequestBody Customer customer) throws CustomerException{
		return customerService.addCustomer(customer);
	}
	
	@GetMapping("/customers/{emailId}")
	public  ResponseEntity<Customer> getCustomerByEmail(@Valid EmailId emailId) throws CustomerException{
		return customerService.getCustomerByEmail(emailId.getEmailId());
	}
	
	@PutMapping("/customers/{id}")
	public  ResponseEntity<String> updateCustomerById(@Valid @RequestBody Customer newCustomer, @Valid CustomerId custId) throws CustomerException {
		return customerService.updateCustomerById(newCustomer, custId.getId());
	}
	
	@DeleteMapping("/customers/{id}")
	public  ResponseEntity<String> deleteCustomerById(@Valid CustomerId custId) throws CustomerException {
		return customerService.deleteCustomerById(custId.getId());
	}

}
