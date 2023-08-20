package com.example.scms.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scms.model.Customer;
import com.example.scms.model.Transactions;
import com.example.scms.repo.CustomerRepo;

@Service	
public class CustomerService {
	
	@Autowired
	private CustomerRepo customerRepo;

	public boolean checkIfCustomerExists(Customer customer) {
		if(customerRepo.findByCustomerName(customer.getCustomerName()) != null ||
		customerRepo.findByCustomerContact(customer.getCustomerContact()) != null)
			return true;
		return false;
	}
	
	public List<Customer> getAllCustomers(){
		return customerRepo.findAll();
	}

	public Customer addCustomer(Customer customer) {
		return customerRepo.save(customer);
	}

	public Customer getCustomerByName(String customerName) {
		return customerRepo.findByCustomerNameContainingIgnoreCase(customerName);
	}
	
	public Customer getCustomerById(int customerId) {
		return customerRepo.findById(customerId).get();
	}

	public Customer updateCustomer(Customer customer) {
		System.out.println("here in cust service" + customer);
		return customerRepo.save(customer);
	}

//	public List<Transactions> getAllTransactionsByCustomerId(int customerId) {
//		Customer customer = customerRepo.findById(customerId).get();
//		return customer.getCustomerWalletHistory();
//	}
	
	public Set<Customer> getOldCreditGivenCustomers(){
		return customerRepo.findOldCreditGivenCustomer();
	}
}
