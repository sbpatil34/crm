package com.rest.crm.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class Customer {
	private @Id
	@GeneratedValue Long id;
	
	private String suffix;
	
	private String prefix;
	
	@NotBlank(message = "First name is required.")
	private String firstName;
	
	private String middleName;
	
	@NotBlank(message = "Last name is required.")
	private String lastName;
	
	@Column(unique = true)
	@NotBlank(message = "Email name is required.")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+[a-zA-Z0-9.-]", message = "Not a valid email id")
	private String email;
	
	@NotBlank(message = "Phone number is required.")
	@Pattern(regexp = "^\\d{10}$", message = "Not a valid phone number")
	private String phoneNumber;
	
	public Customer() {}
	
	public Customer(String suffix, String prefix, String firstName, String middleName, String lastName, String email, String phoneNumber) {
		this.suffix = suffix;
		this.prefix = prefix;
		this.firstName	= firstName;
		this.middleName = middleName;
		this.lastName = lastName;	
		this.email	= email;
		this.phoneNumber = phoneNumber;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
