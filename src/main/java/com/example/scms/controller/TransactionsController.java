package com.example.scms.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.scms.model.Transactions;
import com.example.scms.model.User;
import com.example.scms.service.TransactionsService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
public class TransactionsController {

	@Autowired
	private TransactionsService transactionsService;
	
	@PostMapping("/create/transaction")
	public ResponseEntity<?> createTransaction(@RequestBody @Valid Transactions transaction, BindingResult br, HttpSession session){
		if(br.hasErrors()) {
			List<String> errors = br.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
	        return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);
        }
		User user = (User) session.getAttribute("user");
		if(user !=null) {
			Transactions successfullTransaction = transactionsService.saveTransaction(transaction, user);
			if(successfullTransaction!=null)
				return new ResponseEntity<Transactions>(transaction, HttpStatus.OK);
			else
				return new ResponseEntity<String>("Transaction Failed", HttpStatus.BAD_REQUEST);
		}
		else
			return new ResponseEntity<String>("Please Login in First", HttpStatus.BAD_REQUEST);
			
	}
	
	@GetMapping("/getTransactionsByCustomer/{customerId}/{offset}/{pageSize}")
	public ResponseEntity<?> getTransactionsByCustomer(@PathVariable int customerId, @PathVariable int offset, @PathVariable int pageSize){
		return new ResponseEntity<Page<Transactions>>(transactionsService.getAllTransactionsByCustomerId(customerId, offset, pageSize), HttpStatus.OK);
	}
	
	
	
	
	
}
