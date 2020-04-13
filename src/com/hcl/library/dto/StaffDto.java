package com.hcl.library.dto;

import com.hcl.library.model.bo.StaffBO;
import com.hcl.library.model.po.LoanPO;
import com.hcl.library.model.po.StaffPO;

public class StaffDto {
	public static final StaffBO map(StaffPO persistenceStaff) {
		StaffBO staff = new StaffBO();
		staff.setId(persistenceStaff.getId());
		staff.setName(persistenceStaff.getName());
		staff.setLastName(persistenceStaff.getLastName());
		staff.setCurp(persistenceStaff.getCurp());
		staff.setUserName(persistenceStaff.getUserName());
		staff.setPassword(persistenceStaff.getPassword());
		
		return staff;
	}
	public static final StaffPO map(StaffBO businessStaff) {
		StaffPO staff = new StaffPO();
		staff.setId(businessStaff.getId());
		staff.setName(businessStaff.getName());
		staff.setLastName(businessStaff.getLastName());
		staff.setCurp(businessStaff.getCurp());
		staff.setUserName(businessStaff.getUserName());
		staff.setPassword(businessStaff.getPassword());
		
		return staff;
	}	
	
}
