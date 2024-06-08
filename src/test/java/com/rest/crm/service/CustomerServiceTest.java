package com.rest.crm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.rest.crm.entity.Customer;
import com.rest.crm.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
	
	@Mock
	CustomerRepository customerRepository;
	@InjectMocks
	CustomerService customerService;
	
	@Test
	void testGetAllCutomers() {
		List<Customer> customers = new ArrayList<Customer>() ;
		Customer firstCustomer =  new Customer();
		firstCustomer.setFirstName("teast");
		firstCustomer.setEmail("teast@email.com");
		customers.add(firstCustomer);
		when(customerRepository.findAll()).thenReturn(customers);
		ResponseEntity<List<Customer>> returnedResponse = customerService.getAllCutomers();
		assertEquals(returnedResponse.getBody().size(), customers.size());
		assertEquals(returnedResponse.getBody().get(0).getFirstName(), customers.get(0).getFirstName());
	}

}
