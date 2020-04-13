package com.hcl.library.service;

import com.hcl.library.dao.StaffDao;
import com.hcl.library.dto.StaffDto;
import com.hcl.library.model.bo.StaffBO;
import com.hcl.library.model.po.StaffPO;

public class StaffService {
	private static StaffService instance;
	private StaffDao staffDao;
	
	private StaffService(){
		staffDao = new StaffDao();
	}
	
	public static StaffService getInstance() {
		if(instance==null) {
			instance=new StaffService();
		}
		return instance;
	}
	
	public boolean saveStaff(StaffBO staff) {
		StaffPO persistenceStaff = getPersistenceStaff(staff);
		StaffBO staffFound = findByCurp(staff.getCurp());
		if(staffFound==null) {
			return staffDao.create(persistenceStaff);
		}
		return false;
	}
	
	public StaffBO findByCurp(String curp) {
		StaffPO staffFound=staffDao.find(staffDao.criteriaOfSearching(curp, "getCurp"));
		return getBusinessStaff(staffFound);
	}
	
	public StaffBO findBName(String name) {
		StaffPO staffFound=staffDao.find(staffDao.criteriaOfSearching(name, "getFullName"));
		return getBusinessStaff(staffFound);
	}
	
	public StaffBO findByUserName(String userName) {
		StaffPO staffFound=staffDao.find(staffDao.criteriaOfSearching(userName,"getUserName"));
		return getBusinessStaff(staffFound);
	}
	
	private StaffPO getPersistenceStaff(StaffBO staff) {
		if(staff!=null) {
			return StaffDto.map(staff);
		}else {
			return null;
		}
	}
	
	private StaffBO getBusinessStaff(StaffPO staff) {
		if(staff!=null) {
			return StaffDto.map(staff);
		}else {
			return null;
		}
	}
	
}
