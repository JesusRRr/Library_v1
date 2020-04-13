package com.hcl.library.dao;

import com.hcl.library.generics.GenericCrudImpl;
import com.hcl.library.model.po.AuthorPO;

public class AuthorDao extends GenericCrudImpl<AuthorPO>{
	
	public AuthorDao() {
		super(AuthorPO.class);
	}


}
