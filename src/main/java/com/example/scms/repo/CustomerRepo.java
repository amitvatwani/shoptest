package com.example.scms.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.scms.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
	Customer findByCustomerName(String customerName);
	Customer findByCustomerContact(String customerName);
	Customer findByCustomerNameContainingIgnoreCase(String customerName);
	@Query("from Customer c, Transactions t where c.customerId=t.customer and c.customerWallet<0 order by t.transactionDate asc")
	Set<Customer> findOldCreditGivenCustomer();
}
