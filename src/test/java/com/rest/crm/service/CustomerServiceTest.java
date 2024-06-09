package com.rest.crm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rest.crm.entity.Customer;
import com.rest.crm.exception.CustomerException;
import com.rest.crm.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
	
	@Mock
	CustomerRepository customerRepository;
	
	@Mock
	Customer mockCustomer;
	
	@InjectMocks
	CustomerService customerService;
	
	@Test
	void testGetAllCutomers() {
		List<Customer> customers = new ArrayList<Customer>() ;
		Customer customer =  new Customer("Mr", "Jr", "first", "middle", "last", "em@g.com", "1234567890");
		customers.add(customer);
		when(customerRepository.findAll()).thenReturn(customers);
		ResponseEntity<List<Customer>> resp = customerService.getAllCustomers();
		assertEquals(resp.getBody().size(), customers.size());
		assertEquals(resp.getBody().get(0).getSuffix(), "Mr");
		assertEquals(resp.getBody().get(0).getPrefix(), "Jr");
		assertEquals(resp.getBody().get(0).getFirstName(), "first");
		assertEquals(resp.getBody().get(0).getMiddleName(), "middle");
		assertEquals(resp.getBody().get(0).getLastName(), "last");
		assertEquals(resp.getBody().get(0).getEmail(), "em@g.com");
		assertEquals(resp.getBody().get(0).getPhoneNumber(), "1234567890");
		assertEquals(resp.getStatusCode(), HttpStatus.OK);
	}
	
	@Test()
	void testAddCustomer() throws CustomerException {
		Customer customer =  new Customer("Mr", "Jr", "first", "middle", "last", "em@g.com", "1234567890");
		when(customerRepository.save(customer)).thenReturn(customer);
		ResponseEntity<String> resp = customerService.addCustomer(customer);
		assertEquals(resp.getBody(), "added");
		assertEquals(resp.getStatusCode(), HttpStatus.OK);
	}
	
	@Test()
	void testAddCustomerThrowsException() {
		when(customerRepository.save(mockCustomer)).thenThrow(new DuplicateKeyException(""));
		Exception  exception =  assertThrows(CustomerException.class, () -> {
			customerService.addCustomer(mockCustomer);
		});
		assertEquals(CustomerException.class, exception.getClass());
		String  expectedMessage = "Error while adding a customer, please contact support for more help";
	    assertEquals(exception.getMessage(), expectedMessage);
	}
	
	@Test()
	void testGetCustomerByEmail() throws CustomerException {
		Customer customer =  new Customer("Mr", "Jr", "first", "middle", "last", "em@g.com", "1234567890");
		when(customerRepository.findDistinctCustomerByEmail("")).thenReturn(customer);
		ResponseEntity<Customer> resp = customerService.getCustomerByEmail("");
		assertEquals(resp.getBody().getFirstName(), "first");
		assertEquals(resp.getBody().getMiddleName(), "middle");
		assertEquals(resp.getBody().getLastName(), "last");
		assertEquals(resp.getBody().getEmail(), "em@g.com");
		assertEquals(resp.getBody().getPhoneNumber(), "1234567890");
		assertEquals(resp.getStatusCode(), HttpStatus.OK);
	}
	
	@Test()
	void testGetCustomerByEmailNoCustomerFound() throws CustomerException {
		when(customerRepository.findDistinctCustomerByEmail("test@g.com")).thenReturn(null);
		Exception  exception =  assertThrows(CustomerException.class, () -> {
			customerService.getCustomerByEmail("test@g.com");
		});
		assertEquals(CustomerException.class, exception.getClass());
		String  expectedMessage = "No records are found for a email "+"test@g.com";
	    assertEquals(exception.getMessage(), expectedMessage);
	}
	
	@Test()
	void testUpdateCustomerById() throws CustomerException {
		Optional<Customer> existingCutomerOptional = Optional.of(mockCustomer);
		when(customerRepository.findById(123L)).thenReturn(existingCutomerOptional);
		ResponseEntity<String> resp = customerService.updateCustomerById(mockCustomer, 123L);
		assertTrue(existingCutomerOptional.isPresent());
		assertEquals(resp.getBody(), "updated");
		assertEquals(resp.getStatusCode(), HttpStatus.OK);
	}
	
	@Test()
	void testUpdateCustomerByIdCustomerNotFound() throws CustomerException {
		Optional<Customer> emptyOptional = Optional.empty();
		when(customerRepository.findById(123L)).thenReturn(emptyOptional);
		Exception  exception =  assertThrows(CustomerException.class, () -> {
			customerService.updateCustomerById(mockCustomer, 123L);
		});
		assertEquals(CustomerException.class, exception.getClass());
		String  expectedMessage = "Customer not found "+123L;
	    assertEquals(exception.getMessage(), expectedMessage);
	}
	
	@Test()
	void testDeleteCustomerById() throws CustomerException {
		Optional<Customer> existingCutomerOptional = Optional.of(mockCustomer);
		when(customerRepository.findById(123L)).thenReturn(existingCutomerOptional);
		doNothing().when(customerRepository).delete(mockCustomer);
		ResponseEntity<String> resp = customerService.deleteCustomerById(123L);
		assertEquals(resp.getBody(), "deleted");
		assertEquals(resp.getStatusCode(), HttpStatus.OK);
	}
	
	@Test()
	void testDeleteCustomerByIdCustomerNotFound() throws CustomerException {
		Optional<Customer> emptyOptional = Optional.empty();
		when(customerRepository.findById(123L)).thenReturn(emptyOptional);
		Exception  exception =  assertThrows(CustomerException.class, () -> {
			customerService.deleteCustomerById(123L);
		});
		assertEquals(CustomerException.class, exception.getClass());
		String  expectedMessage = "Customer not found "+123L;
	    assertEquals(exception.getMessage(), expectedMessage);
	}
}
