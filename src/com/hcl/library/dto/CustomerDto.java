package com.hcl.library.dto;

import com.hcl.library.model.bo.CustomerBO;
import com.hcl.library.model.po.CustomerPO;

public class CustomerDto {


	public static final CustomerBO map(CustomerPO persistenceCustomer) {
		CustomerBO customer = new CustomerBO();
		
		customer.setId(persistenceCustomer.getId());
		customer.setName(persistenceCustomer.getName());
		customer.setLastName(persistenceCustomer.getLastName());
		customer.setBirthday(persistenceCustomer.getBirthday());
		customer.setCurp(persistenceCustomer.getCurp());
		customer.setPhone(persistenceCustomer.getPhone());
		customer.setEmail(persistenceCustomer.getEmail());
		customer.setAddress(AddressDto.map(persistenceCustomer.getAddress()));
	
		return customer;
	}
	
	public static final CustomerPO map(CustomerBO businessCustomer) {
		CustomerPO customer = new CustomerPO();
		
		customer.setId(businessCustomer.getId());
		customer.setName(businessCustomer.getName());
		customer.setLastName(businessCustomer.getLastName());
		customer.setBirthday(businessCustomer.getBirthday());
		customer.setCurp(businessCustomer.getCurp());
		customer.setPhone(businessCustomer.getPhone());
		customer.setEmail(businessCustomer.getEmail());
		customer.setAddress(AddressDto.map(businessCustomer.getAddress()));
		
		return customer;
	}
}
