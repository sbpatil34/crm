package com.rest.crm.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.rest.crm.entity.Customer;
import com.rest.crm.exception.CustomerException;
import com.rest.crm.repository.CustomerRepository;


@Service
public class CustomerService {

	@Autowired 
	private CustomerRepository customerRepository;
	
	public ResponseEntity<List<Customer>> getAllCutomers(){
		return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
	}
	
	public ResponseEntity<String> addCutomer(Customer customer) throws CustomerException{
		Logger logger = LoggerFactory.getLogger(CustomerService.class);
		System.out.println("-------------------Sandeep");
		logger.info("-------------------Sandeep");
		logger.debug("----------------Sandeep");
		logger.info("Info level - Hello Logback {}", "Sandeep");
		try{
			customerRepository.save(customer);
			return new ResponseEntity<String>("added", HttpStatus.OK);			
		}catch(DataAccessException ex) {
			throw new CustomerException("Error while adding a customer, please contact support for more help", HttpStatus.OK);
		}
	}
		
	public ResponseEntity<Customer> getCustomerByEmail(String email) throws CustomerException{
		Customer customer = customerRepository.findDistinctCustomerByEmail(email);
		if(customer == null) {
			throw new CustomerException("No records are found for a email "+email, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(customer, HttpStatus.OK);
		}
	}
	
	public ResponseEntity<String> updateCustomerById(Customer newCustomer, Long id) throws CustomerException{
		Optional<Customer> existingCutomerOptional = customerRepository.findById(id);
		if(existingCutomerOptional.isEmpty()) {
			throw new CustomerException("Customer not found "+id, HttpStatus.NOT_FOUND);
		}else {
			Customer existingCustomer = existingCutomerOptional.get();
			existingCustomer.setPrefix(newCustomer.getPrefix());
			existingCustomer.setSuffix(newCustomer.getSuffix());
			existingCustomer.setFirstName(newCustomer.getFirstName());
			existingCustomer.setLastName(newCustomer.getLastName());
			existingCustomer.setEmail(newCustomer.getEmail());
			existingCustomer.setPhoneNumber(newCustomer.getPhoneNumber());
			customerRepository.save(existingCustomer);
			return new ResponseEntity<String>("updated", HttpStatus.OK);
		}
	}
	
	public ResponseEntity<String> deleteCustomerById(Long id) throws CustomerException{
		Optional<Customer> existingCutomerOptional = customerRepository.findById(id);
		if(existingCutomerOptional.isEmpty()) {
			throw new CustomerException("Customer not found "+id, HttpStatus.NOT_FOUND);
		}else {
			customerRepository.delete(existingCutomerOptional.get());
			return new ResponseEntity<String>("deleted", HttpStatus.OK);
		}
	}
}
