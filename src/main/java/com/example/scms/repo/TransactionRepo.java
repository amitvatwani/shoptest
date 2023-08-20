package com.example.scms.repo;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.scms.model.Transactions;

public interface TransactionRepo extends JpaRepository<Transactions, Integer> {
	
	@Query("from Transactions t where t.customer.customerId=?1 order by t.transactionDate desc")
	Page<Transactions> getCustomerTransactions(int customerId, Pageable pageable);

}
