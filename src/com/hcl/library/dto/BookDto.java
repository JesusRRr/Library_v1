package com.hcl.library.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.hcl.library.model.bo.BookBO;
import com.hcl.library.model.po.BookPO;

public class BookDto {
	public static final BookBO map(BookPO persistenceBook) {
		BookBO book = new BookBO();
		book.setId(persistenceBook.getId());
		book.setName(persistenceBook.getName());
		book.setIsbn(persistenceBook.getIsbn());
		book.setEdition(persistenceBook.getEdition());
		book.setEditorial(persistenceBook.getEditorial());
		book.setCategory(persistenceBook.getCategory());
		book.setLanguage(persistenceBook.getLanguage());
		book.setBookcover(persistenceBook.getBookcover());
		book.setStatus(persistenceBook.getStatus());
		book.setAuthors(AuthorDto.mapAuthorListToBO(persistenceBook.getAuthors()));
	
		
		return book;
	}
	
	public static final BookPO map(BookBO businessBook) {
		BookPO book = new BookPO();
		book.setId(businessBook.getId());
		book.setName(businessBook.getName());
		book.setEdition(businessBook.getEdition());
		book.setEditorial(businessBook.getEditorial());
		book.setIsbn(businessBook.getIsbn());
		book.setCategory(businessBook.getCategory());
		book.setLanguage(businessBook.getLanguage());
		book.setBookcover(businessBook.getBookcover());
		book.setStatus(businessBook.getStatus());
		book.setAuthors(AuthorDto.mapAuthorListToPO(businessBook.getAuthors()));
	
		return book;
	}
	
	public static final List<BookPO> mapBookListToPO(List<BookBO> books){
		return books.stream().map(book -> {
			BookPO newBook = new BookPO();
			newBook.setId(book.getId());
			newBook.setName(book.getName());
			newBook.setIsbn(book.getIsbn());
			newBook.setEdition(book.getEdition());
			newBook.setEditorial(book.getEditorial());
			newBook.setCategory(book.getCategory());
			newBook.setLanguage(book.getLanguage());
			newBook.setBookcover(book.getBookcover());
			newBook.setStatus(book.getStatus());
			newBook.setAuthors(AuthorDto.mapAuthorListToPO(book.getAuthors()));
		
			
			return newBook;
		})
				.collect(Collectors.toList());
		
	}
	
	public static final List<BookBO> mapBookListToBO(List<BookPO> books){
		return books.stream().map(book -> {
			BookBO newBook = new BookBO();
			newBook.setName(book.getName());
			newBook.setIsbn(book.getIsbn());
			newBook.setEdition(book.getEdition());
			newBook.setEditorial(book.getEditorial());
			newBook.setCategory(book.getCategory());
			newBook.setLanguage(book.getLanguage());
			newBook.setBookcover(book.getBookcover());
			newBook.setStatus(book.getStatus());
			newBook.setAuthors(AuthorDto.mapAuthorListToBO(book.getAuthors()));
			
			return newBook;
		})
				.collect(Collectors.toList());
		
	}
	

}
