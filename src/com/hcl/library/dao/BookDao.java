package com.hcl.library.dao;

import java.util.List;

import com.hcl.library.generics.GenericCrudImpl;
import com.hcl.library.model.po.AuthorPO;
import com.hcl.library.model.po.BookPO;

public class BookDao extends GenericCrudImpl<BookPO>{
	
	public BookDao() {
		super(BookPO.class);
	}
	
	
	
	
}
