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
import com.rest.crm.util.Constants;


/**
 * This class provides business level api's for supporting customer REST end-points.
 */
@Service
public class CustomerServiceImpl implements CustomerService{

	
	@Autowired 
	private CustomerRepository customerRepository;
	private static Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	

	/**
	 * This method returns all customers.
	 */
	public ResponseEntity<List<Customer>> getAllCustomers(){
		return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
	}
	
	/**
	 * This method adds a customers.
	 * @return List of customers along with HTTP status code.
	 * @exception CustomerException indicating if add customer operations fails.
	 */
	public ResponseEntity<String> addCustomer(Customer customer) throws CustomerException{
		logger.info("Adding customer record firstName : "+customer.getFirstName() + " email: "+customer.getEmail());
		try{
			customerRepository.save(customer);
			return new ResponseEntity<String>("added", HttpStatus.OK);			
		}catch(DataAccessException ex) {
			throw new CustomerException(Constants.ADD_ERROR, HttpStatus.OK);
		}
	}
	
	/**
	 * This method returns a customer.
	 * @return Customer details along with HTTP status code.
	 * @exception CustomerException indicating no records are found.
	 */
	public ResponseEntity<Customer> getCustomerByEmail(String email) throws CustomerException{
		logger.info("Get customer by email:"+email);
		Customer customer = customerRepository.findDistinctCustomerByEmail(email);
		if(customer == null) {
			throw new CustomerException(Constants.NO_RECORDS_FOUND+email, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(customer, HttpStatus.OK);
		}
	}
	
	/**
	 * This method updates a customer. 
	 * It first checks if a record exists to give the customer id then updates the customer. 
	 * @return Operations status updated along with HTTP status code.
	 * @exception CustomerException indicating customer not found if no records can be found for a give id.
	 */
	public ResponseEntity<String> updateCustomerById(Customer newCustomer, Long id) throws CustomerException{
		logger.info("Update customer : "+id);
		Optional<Customer> existingCutomerOptional = customerRepository.findById(id);
		if(existingCutomerOptional.isEmpty()) {
			throw new CustomerException(Constants.CUSTOMER_NOT_FOUND+id, HttpStatus.NOT_FOUND);
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
	
	/**
	 * This method deletes a customer. 
	 * It first checks if a record exists to give the customer id then updates the customer. 
	 * @return Operations status deleted along with HTTP status code.
	 * @exception CustomerException indicating customer not found if no records can be found for a give id.
	 */
	public ResponseEntity<String> deleteCustomerById(Long id) throws CustomerException{
		logger.info("Delete customer : "+id);
		Optional<Customer> existingCutomerOptional = customerRepository.findById(id);
		if(existingCutomerOptional.isEmpty()) {
			throw new CustomerException(Constants.CUSTOMER_NOT_FOUND+id, HttpStatus.NOT_FOUND);
		}else {
			customerRepository.delete(existingCutomerOptional.get());
			return new ResponseEntity<String>("deleted", HttpStatus.OK);
		}
	}
}
