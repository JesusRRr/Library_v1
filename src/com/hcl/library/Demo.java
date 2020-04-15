package com.hcl.library;

import com.hcl.library.service.BookService;

public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BookService.getInstance().findAll().forEach(b->System.out.println(b));
	}

}
