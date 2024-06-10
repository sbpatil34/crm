package com.rest.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.crm.entity.Customer;

/**
 * This interface declare persistence API for customer REST end-point.
 * This is using CRUD operations provided by spring JPA framework.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	/**
	 * Declaration for finding unique customer by email.   
	 * @return Customer details
	 */
	public Customer findDistinctCustomerByEmail(String email);
	 
}
