package com.anadi.springdemo.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anadi.springdemo.entity.Customer;
import com.anadi.springdemo.service.CustomerService;

//Rest controller for customer
@RestController
@RequestMapping("/api") // base mapping
public class CustomerRestController {
	
	//autowiring the customer service dependency spring will automatically register this
	@Autowired
	private CustomerService customerService;
	
	//Get method to fetch customer
	@GetMapping("/customers")
	public List<Customer> getCustomers(){
		
		//jackson will convert this POJO data to json
		return customerService.getCustomers();
	}
	
	//Get method to fetch a single customer
	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {
		//We have the method to get a single customer 
		Customer theCustomer =customerService.getCustomer(customerId);
		if(theCustomer == null) {
			throw new CustomerNotFoundException("Customer not found" +customerId);
		}
		return theCustomer;
	}
	
	// Add mapping for post/customers - add new customers
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer theCustomer) {
		theCustomer.setId(0); //IF THE ID is 0 then hibernate will insert a customer only.
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	@PutMapping("/customers") //TO UPDATE A CUSTOMER
	public Customer updateCustomer(@RequestBody Customer theCustomer) {
		
		customerService.saveCustomer(theCustomer); // NOTICE HERE WE ARE NOT SETTING ID TO 0 because we are updating a customer
		
		return theCustomer;
	}
	
	//Delete a customer
	@DeleteMapping("customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) {
		//Check if customer exist first
		Customer tempCustomer =customerService.getCustomer(customerId);
		if(tempCustomer ==null) {
			throw new CustomerNotFoundException("Customer not found " + customerId);
		}
		customerService.deleteCustomer(customerId);
		return "Deleted Customer with id " +customerId;
	}
	//NOTE :: CHECK POSTMAN TO SEE DIFFERENT METHODS
}
