package com.hcl.library.service;

import java.util.List;

import com.hcl.library.dao.AuthorDao;
import com.hcl.library.dao.BookDao;
import com.hcl.library.dto.BookDto;
import com.hcl.library.model.bo.AuthorBO;
import com.hcl.library.model.bo.BookBO;
import com.hcl.library.model.enums.StatusBook;
import com.hcl.library.model.po.AuthorPO;
import com.hcl.library.model.po.BookPO;

public class BookService {
	private static BookService instance;
	private BookDao bookDao;
	private AuthorDao authorDao;
	private AuthorService authorService;
	
	private BookService() {
		bookDao = new BookDao();
		authorDao = new AuthorDao();
		authorService=AuthorService.getInstance();

	}
	
	public static BookService getInstance() {
		if(instance==null) {
			instance = new BookService();
		}
		return instance;
	}

	public boolean createBook(BookBO book) {
		BookPO persistenceBook=getPersistenceBook(book);
		BookBO bookFound = findByIsbn(persistenceBook.getIsbn());
		if (bookFound == null) {
			return bookDao.create(persistenceBook);
		} else {
			System.out.println("book already exist");

			return false;
		}
	}
	
	public void updateBook(BookBO book) {
		BookPO persistenceBook=getPersistenceBook(book);
		
		bookDao.update(persistenceBook);
	}

	public BookBO findByName(String name) {
		BookPO bookFound=bookDao.find(bookDao.criteriaOfSearching(name,"getName"));
		return getBusinessBook(bookFound);
	}
	
	public BookBO findByIsbn(String isbn) {
		BookPO bookFound=bookDao.find(bookDao.criteriaOfSearching(isbn, "getIsbn"));
		return getBusinessBook(bookFound);
	}
	
	public List<BookBO> findByEdition(String edition) {
		List<BookPO> booksFound=bookDao.findAll(bookDao.criteriaOfSearching(edition, "getEdition"));
		if(booksFound!=null) {
			return getBusinessList(booksFound);
		}else {
			return null;
		}
	}
	
	public List<BookBO> findByEditorial(String editorial) {
		List<BookPO> booksFound=bookDao.findAll(bookDao.criteriaOfSearching(editorial, "getEditorial"));
		return getBusinessList(booksFound);
	}
	
	public List<BookBO> findByCategory(String category) {
		List<BookPO> booksFound=bookDao.findAll(bookDao.criteriaOfSearching(category,"getCategory"));
		return getBusinessList(booksFound);
	}
	
	public List<BookBO> findByLanguage(String language) {
		List<BookPO> booksFound= bookDao.findAll(bookDao.criteriaOfSearching(language, "getLanguage"));
		return getBusinessList(booksFound);
	}
	
	public List<BookBO> findByAuthor(String author){
		AuthorPO authorFound = authorDao.find(authorDao.criteriaOfSearching(author, "getFullName"));
		if(authorFound!=null) {
			return getBusinessList(authorFound.getBooks());
		}
		return null;
	}
	public void addAuthor(BookBO book, AuthorBO author) {
		AuthorBO authorFound=authorService.findByName(author.getFullName());
		if(authorFound==null) {
			book.getAuthors().add((author));
			updateBook(book);
		}else {
			System.out.println("Author already exist");

		}
	}
	
	public void changeStatus(BookBO book, StatusBook status) {
		book.setStatus(status);
	}
	
	private BookPO getPersistenceBook(BookBO book) {
		if(book!=null) {
			return BookDto.map(book);
		}else {
			return null;
		}
	}
	
	private BookBO getBusinessBook(BookPO book) {
		if(book!=null) {
			return BookDto.map(book);
		}else {
			return null;
		}
	}
	
	private List<BookBO> getBusinessList(List<BookPO> books) {
		if(books!=null) {
			return BookDto.mapBookListToBO(books);
		}else {
			return null;
		}
	}
	
}
