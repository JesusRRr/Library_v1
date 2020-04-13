package com.hcl.library.dao;

import com.hcl.library.generics.GenericCrudImpl;
import com.hcl.library.model.po.StaffPO;

public class StaffDao extends GenericCrudImpl<StaffPO>{
	
	public StaffDao() {
		super(StaffPO.class);
	}
	
}
