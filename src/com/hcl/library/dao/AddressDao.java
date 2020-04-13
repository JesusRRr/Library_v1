package com.hcl.library.dao;

import com.hcl.library.generics.GenericCrudImpl;
import com.hcl.library.model.po.AddressPO;

public class AddressDao extends GenericCrudImpl<AddressPO>{

	public AddressDao() {
		super(AddressPO.class);
	}
  
}
