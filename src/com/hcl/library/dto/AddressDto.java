package com.hcl.library.dto;

import com.hcl.library.model.bo.AddressBO;
import com.hcl.library.model.po.AddressPO;

public class AddressDto {
	
	public static final AddressBO map(AddressPO persistanceAddress) {
		AddressBO address = new AddressBO();
		address.setId(persistanceAddress.getId());
		address.setStreet(persistanceAddress.getStreet());
		address.setNumber(persistanceAddress.getNumber());
		address.setPostalCode(persistanceAddress.getNumber());
		address.setCity(persistanceAddress.getCity());
		address.setState(persistanceAddress.getState());
		address.setCountry(persistanceAddress.getCountry());
		
		return address;
	}

	public static final AddressPO map(AddressBO businessAddress) {
		AddressPO address = new AddressPO();
		address.setId(businessAddress.getId());
		address.setStreet(businessAddress.getStreet());
		address.setNumber(businessAddress.getNumber());
		address.setPostalCode(businessAddress.getPostalCode());
		address.setCity(businessAddress.getCity());
		address.setState(businessAddress.getState());
		address.setCountry(businessAddress.getCountry());
		
		return address;
	}
}
