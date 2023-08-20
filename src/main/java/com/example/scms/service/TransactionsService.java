package com.example.scms.service;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.scms.model.Customer;
import com.example.scms.model.Transactions;
import com.example.scms.model.User;
import com.example.scms.repo.TransactionRepo;

@Service
public class TransactionsService {
	
	@Autowired
	private TransactionRepo transactionRepo;
	@Autowired 
	private CustomerService customerService; 

	public Transactions saveTransaction(Transactions transaction, User user) {
		Customer customer =  customerService.getCustomerById(transaction.getCustomer().getCustomerId());
		System.out.println("here in tran service" + customer);
		if(transaction.getTransactionType().equals("credit")) {
			customer.setCustomerWallet(customer.getCustomerWallet()-transaction.getTransactionAmount());
			System.out.println("1");
			customerService.updateCustomer(customer);
		}
		else if(transaction.getTransactionType().equals("debit")) {
			customer.setCustomerWallet(customer.getCustomerWallet()+transaction.getTransactionAmount());
			customerService.updateCustomer(customer);
		}
		else {
			return null;
		}
		transaction.setTransactionDate(new Date());
		transaction.setReportingUser(user);
		return transactionRepo.save(transaction);
	}

	public Page<Transactions> getAllTransactionsByCustomerId(int customerId, int offset, int pageSize) {
		Pageable pageable = PageRequest.of(offset, pageSize);
		return transactionRepo.getCustomerTransactions(customerId, pageable);
	}
}
