package com.example.scms.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int customerId;
	@jakarta.validation.constraints.Size(min=3, message="CustomerName must be minimum 3 characters")
	private String customerName;
	@jakarta.validation.constraints.Size(min=10,max = 10, message="Contact Number must be of size 10")
	private String customerContact;
	private int customerWallet;
	@JsonManagedReference
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Transactions> customerWalletHistory;
	
	public Customer() {
		super();
	}
	
	public Customer(int customerId, String customerName, String customerContact, int customerWallet, List<Transactions> customerWalletHistory) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerContact = customerContact;
		this.customerWallet = customerWallet;
		this.customerWalletHistory = customerWalletHistory;
	}
	
	
	
	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", customerContact="
				+ customerContact + ", customerWallet=" + customerWallet + ", customerWalletHistory="
				+ customerWalletHistory + "]";
	}

	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerContact() {
		return customerContact;
	}
	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}
	public int getCustomerWallet() {
		return customerWallet;
	}
	public void setCustomerWallet(int customerWallet) {
		this.customerWallet = customerWallet;
	}
	public List<Transactions> getCustomerWalletHistory() {
		return customerWalletHistory;
	}
	public void setCustomerWalletHistory(List<Transactions> customerWalletHistory) {
		this.customerWalletHistory = customerWalletHistory;
	}
	
}
