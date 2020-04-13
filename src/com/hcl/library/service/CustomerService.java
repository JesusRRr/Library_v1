package com.hcl.library.service;

import java.util.Optional;
import java.util.function.Predicate;

import com.hcl.library.dao.CustomerDao;
import com.hcl.library.dto.AuthorDto;
import com.hcl.library.dto.BookDto;
import com.hcl.library.dto.CustomerDto;
import com.hcl.library.model.bo.AuthorBO;
import com.hcl.library.model.bo.BookBO;
import com.hcl.library.model.bo.CustomerBO;
import com.hcl.library.model.po.AuthorPO;
import com.hcl.library.model.po.BookPO;
import com.hcl.library.model.po.CustomerPO;

public class CustomerService {
	private CustomerDao customerDao;
	private static CustomerService instance;
	
	private CustomerService() {
		this.customerDao = new CustomerDao();
	}
	
	public static CustomerService getInstance() {
		if(instance == null) {
			instance = new CustomerService();
		}
		
		return instance;
	}
	
	//create the curstomer if does not exist 
	public boolean creatCustomer(CustomerBO customer) {
		CustomerPO persistenceCustomer=getPersistenceCustomer(customer);
		CustomerBO customerFound = findByName(persistenceCustomer.getName());
		if (customerFound == null) {
			return customerDao.create(persistenceCustomer);
		} else {
			return false;
		}
	}
	
	private CustomerPO getPersistenceCustomer(CustomerBO customer) {
		if(customer!=null) {
			return CustomerDto.map(customer);
		}else {
			return null;
		}
	}
	//update a customer already create
	public void updateCustomer(CustomerBO customer) {
		CustomerPO persistenceCustomer = getPersistenceCustomer(customer);
		customerDao.update(persistenceCustomer);
 	}
	
	//search a customer
	public CustomerBO findByName(String name) {
		CustomerPO customerFound = customerDao.find(customerDao.criteriaOfSearching(name, "getName"));
		return getBusinessCustomer(customerFound);
	}
	
	public CustomerPO findByCurp(String curp) {
		return customerDao.find(customerDao.criteriaOfSearching(curp, "getCurp"));
	}
	
	private CustomerBO getBusinessCustomer(CustomerPO customer) {
		if(customer!=null) {
			return CustomerDto.map(customer);
		}else {
			return null;
		}
	}
}
