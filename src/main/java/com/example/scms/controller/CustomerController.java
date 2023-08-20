package com.example.scms.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.scms.model.Customer;
import com.example.scms.model.Transactions;
import com.example.scms.service.CustomerService;

import jakarta.validation.Valid;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/add/customer")
	public ResponseEntity<?> addCustomer(@RequestBody @Valid Customer customer, BindingResult br){
		if(br.hasErrors()) {
			List<String> errors = br.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
	        return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);
		}
		if(customerService.checkIfCustomerExists(customer)) {
			return new ResponseEntity<String>("Customer Already Exists", HttpStatus.BAD_REQUEST);
		}
		Customer savedCustomer = customerService.addCustomer(customer);
		if(savedCustomer!=null)
			return new ResponseEntity<Customer>(savedCustomer, HttpStatus.CREATED);
		else
			return new ResponseEntity<String>("Not Able to Add Customer", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/customers")
	public ResponseEntity<?> getAllCustomers(){
		return new ResponseEntity<List<Customer>>(customerService.getAllCustomers(), HttpStatus.OK);
	}
	
	@GetMapping("/customer/{customerName}")
	public ResponseEntity<?> searchCustomer(@PathVariable String customerName){
		return new ResponseEntity<Customer>(customerService.getCustomerByName(customerName), HttpStatus.OK);
	}
	
//	@GetMapping("/getTransactionsByCustomer/{customerId}")
//	public ResponseEntity<?> getTransactionsByCustomer(@PathVariable int customerId){
//		return new ResponseEntity<List<Transactions>>(customerService.getAllTransactionsByCustomerId(customerId), HttpStatus.OK);
//	}
	
	@GetMapping("/getAllCustomersByOldCredit/{offset}/{pageSize}")
	public ResponseEntity<?> getAllCustomersByOldCredit(@PathVariable int offset, @PathVariable int pageSize){
		return new ResponseEntity<Set<Customer>>(customerService.getOldCreditGivenCustomers(), HttpStatus.OK);
	}
}
