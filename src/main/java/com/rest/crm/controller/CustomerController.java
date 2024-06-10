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
import com.rest.crm.service.CustomerServiceImpl;
import com.rest.crm.util.Constants;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;


/**
 * This class is a REST controller for customer end-point.
 * It implements CRUD operations on customer entities.
 */
@Validated
@RestController
@RequestMapping(Constants.BASE_URL)
public class CustomerController {

	@Autowired
	private CustomerServiceImpl customerService;
	
	/**
	 * This REST end-point returns all customers.   
	 * @return List of customers along with HTTP status code
	 */
	@GetMapping(Constants.CUSTOMERS)
	public  ResponseEntity<List<Customer>> getAllCustomers() {
		 return customerService.getAllCustomers();
	}
	
	/**
	 * This REST end-point supports adding new customer.   
	 * It will throw validation errors if
 	 *	- Customer first name is null.
 	 *  - Customer last name is null.
 	 *	- Email is null or not in correct format aaa@sss.com.
 	 *  - Email should be unique. 
 	 *  - Phone number is not provided or not valid phone number.
 	 *     
	 * @return List of customers along with HTTP status code.
	 * @exception CustomerException indicating if add customer operations fails.
	 */
	@PostMapping(Constants.CUSTOMERS)
	public  ResponseEntity<String> addCustomer(@Valid @RequestBody Customer customer) throws CustomerException{
		return customerService.addCustomer(customer);
	}
	
	/**
	 * This REST end-point supports finding a customer by email id.   
	 * @return Operation status 'added' along with HTTP status code.
	 * @exception CustomerException indicating no records found in case of email id  don't have any customer records.
	 */
	@GetMapping(Constants.CUSTOMERS_EMAIL)
	public  ResponseEntity<Customer> getCustomerByEmail(@Valid EmailId emailId) throws CustomerException{
		return customerService.getCustomerByEmail(emailId.getEmailId());
	}
	
	/**
	 * This REST end-point supports updating a customer by id.   
	 * @return Operation status 'updated' along with HTTP status code.
	 * @exception CustomerException indicating no records found in case id  don't have any customer records.
	 */
	@PutMapping(Constants.CUSTOMERS_ID)
	public  ResponseEntity<String> updateCustomerById(@Valid @RequestBody Customer newCustomer, @Valid CustomerId custId) throws CustomerException {
		return customerService.updateCustomerById(newCustomer, custId.getId());
	}
	
	/**
	 * This REST end-point supports deleting a customer by id.   
	 * @return Operation status 'deleted' along with HTTP status code.
	 * @exception CustomerException indicating no records found in case id  don't have any customer records.
	 */
	
	@DeleteMapping(Constants.CUSTOMERS_ID)
	public  ResponseEntity<String> deleteCustomerById(@Valid CustomerId custId) throws CustomerException {
		return customerService.deleteCustomerById(custId.getId());
	}
}
