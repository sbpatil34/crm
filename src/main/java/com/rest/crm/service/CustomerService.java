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
	private static Logger logger = LoggerFactory.getLogger(CustomerService.class);
	
	public ResponseEntity<List<Customer>> getAllCustomers(){
		return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
	}
	
	public ResponseEntity<String> addCustomer(Customer customer) throws CustomerException{
		logger.info("Adding customer record firstName : "+customer.getFirstName());
		logger.info("email:"+customer.getEmail());
		logger.info("phoneNumber:" + customer.getPhoneNumber());	
		try{
			customerRepository.save(customer);
			return new ResponseEntity<String>("added", HttpStatus.OK);			
		}catch(DataAccessException ex) {
			throw new CustomerException("Error while adding a customer, please contact support for more help", HttpStatus.OK);
		}
	}
		
	public ResponseEntity<Customer> getCustomerByEmail(String email) throws CustomerException{
		logger.info("Get customer by email:"+email);
		Customer customer = customerRepository.findDistinctCustomerByEmail(email);
		if(customer == null) {
			throw new CustomerException("No records are found for a email "+email, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(customer, HttpStatus.OK);
		}
	}
	
	public ResponseEntity<String> updateCustomerById(Customer newCustomer, Long id) throws CustomerException{
		logger.info("Update customer : "+id);
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
		logger.info("Delete customer : "+id);
		Optional<Customer> existingCutomerOptional = customerRepository.findById(id);
		if(existingCutomerOptional.isEmpty()) {
			throw new CustomerException("Customer not found "+id, HttpStatus.NOT_FOUND);
		}else {
			customerRepository.delete(existingCutomerOptional.get());
			return new ResponseEntity<String>("deleted", HttpStatus.OK);
		}
	}
}
