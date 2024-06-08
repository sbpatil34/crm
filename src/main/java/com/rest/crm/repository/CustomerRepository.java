package com.rest.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.crm.entity.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findDistinctCustomerByEmail(String email);
	 
}
