package com.rest.crm.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.crm.entity.Customer;
import com.rest.crm.service.CustomerServiceImpl;


@TestMethodOrder(OrderAnnotation.class)
@ComponentScan(basePackages = "com.rest.crm")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = {CustomerControllerTest.class})

public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private CustomerServiceImpl customerService;
	
	@Mock
	Customer mockCustomer;
	
	@InjectMocks
	CustomerController customerController;
	Customer customer;
	
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();  
		customer =  new Customer("Mr", "Jr", "first", "middle", "last", "em@g.com", "1234567890");
	}
	
	@Test
	void testGetAllCustomers() throws Exception {
		List<Customer> myList =  new ArrayList<Customer>();
		myList.add(customer);		
		when(customerService.getAllCustomers()).thenReturn(new ResponseEntity<>(myList, HttpStatus.OK));
		MvcResult result = this.mockMvc.perform(get("/crm/v1/customers"))
					.andDo(print()).andExpect(status().isOk()).andReturn();		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode returnList = mapper.readTree(result.getResponse().getContentAsString());
		assertEquals(returnList.size(), 1);
	}
	
	@Test
	void testAddCustomer() throws Exception {
		when(customerService.addCustomer(customer)).thenReturn(new ResponseEntity<String>("added", HttpStatus.OK));
		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(customer);
		this.mockMvc.perform(post("/crm/v1/customers")
							.content(jsonBody)
							.contentType(MediaType.APPLICATION_JSON))
								.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	void testGetCustomerByEmail() throws Exception {
		when(customerService.getCustomerByEmail("em@g.com")).thenReturn(new ResponseEntity<Customer>(customer, HttpStatus.OK));
		MvcResult result = this.mockMvc.perform(get("/crm/v1/customers/em@g.com"))
					.andDo(print()).andExpect(status().isOk()).andReturn();	
		ObjectMapper mapper = new ObjectMapper();
		JsonNode returnCustomer = mapper.readTree(result.getResponse().getContentAsString());
		assertEquals(returnCustomer.get("firstName").textValue(), "first");
		assertEquals(returnCustomer.get("lastName").textValue(), "last");
	}
	
	@Test void testUpdateCustomerById() throws Exception {
		  when(customerService.updateCustomerById(customer, Long.valueOf(123))).thenReturn(new ResponseEntity<String>("updated", HttpStatus.OK)); 
		  ObjectMapper mapper = new ObjectMapper(); 
		  String jsonBody = mapper.writeValueAsString(customer);
		  Long id = Long.valueOf(123);
		  this.mockMvc.perform(put("/crm/v1/customers/{customerId}", id)
			        .content(jsonBody)
			        .contentType(MediaType.APPLICATION_JSON))
			        .andExpect(status().isOk()); 
	}
	
	@Test void testDeleteCustomerById() throws Exception {
		  when(customerService.updateCustomerById(customer, Long.valueOf(123))).thenReturn(new ResponseEntity<String>("updated", HttpStatus.OK)); 
		  ObjectMapper mapper = new ObjectMapper(); 
		  String jsonBody = mapper.writeValueAsString(customer);
		  Long id = Long.valueOf(123);
		  this.mockMvc.perform(put("/crm/v1/customers/{customerId}", id)
			        .content(jsonBody)
			        .contentType(MediaType.APPLICATION_JSON))
			        .andExpect(status().isOk()); 
	}
}
