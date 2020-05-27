package com.hcl.library.service;

import java.util.List;

import com.hcl.library.dao.AuthorDao;
import com.hcl.library.dao.BookDao;
import com.hcl.library.dto.BookDto;
import com.hcl.library.exceptions.InvalidCharacterException;
import com.hcl.library.exceptions.InvalidFieldException;
import com.hcl.library.exceptions.IsbnException;
import com.hcl.library.exceptions.AuthorException;
import com.hcl.library.exceptions.EmptyFieldException;
import com.hcl.library.exceptions.StatusBookException;
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

	public boolean createBook(BookBO book) throws InvalidFieldException{
		isBookCorrect(book);
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
	
	public void updateBook(BookPO book) {
		bookDao.update(book);
	}
	
	public BookPO findById(int id) {
		BookPO bookFound=bookDao.findById(id);
		
		return bookFound;
	}

	public BookPO findByName(String name) {
		return bookDao.find(bookDao.criteriaOfSearching(name,"getName"));
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
	/*
	public List<BookBO> findByAuthor(String author){
		AuthorPO authorFound = authorDao.find(authorDao.criteriaOfSearching(author, "getFullName"));
		if(authorFound!=null) {
			return getBusinessList(authorFound.getBooks());
		}
		return null;
	}
	*/
	public void addAuthor(BookBO book, AuthorBO author) {
		AuthorBO authorFound=authorService.findByName(author.getFullName());
		if(authorFound==null) {
			book.getAuthors().add((author));
			updateBook(book);
		}else {
			System.out.println("Author already exist");

		}
	}
	
	public List<BookPO> findAll(){
		List<BookPO> booksFound= bookDao.findAll();
		if(booksFound!=null) {
			return booksFound;
		}
		return null;
	}
	
	public void changeStatus(BookBO book, StatusBook status) {
		book.setStatus(status);
	}
	
	public void isbnIsCorrect(String isbn) throws IsbnException{
		if(isbn==null) {
			throw new IsbnException("Isbn can't be omitted");
		}
		
		isbn = isbn.replace("-","");
		
		if(isbn.equals("")) {
			throw new IsbnException("Isbn can't be omitted");
		}
		
		if(isbn.length()!=13) {
			throw new IsbnException("Isbn has 13 digits");
		}
		
		if(!isbn.matches("\\d+")) {
			throw new IsbnException("Isbn only can have digits");
		}
	}
	
	
	public void isStringFieldCorrect(String field, String attribute) throws InvalidFieldException{
		System.out.println("field"+field);
		if(field.equals("")) {
			throw new EmptyFieldException("empty field: "+attribute);
		}
		field=field.replace(" ", "");
		if(!field.matches("[a-zA-Z0-9]+")) {
			throw new InvalidCharacterException("Invalid character at: "+field);
		}
	}
	
	public void isStatusCorrect(StatusBook status) throws StatusBookException{
		String statusValue= status.toString();
		
		if(!statusValue.equals("AVAILABLE")){
			throw new StatusBookException(statusValue +" is not a valid status for a new book");
		}
	}
	
	public void isAuthorCorrect(AuthorBO author) throws AuthorException{
		
		if(author.getName().equals("") || author.getLastName().equals("")) {
			throw new AuthorException("Author can't be assignated without a full name");
		}
	}
	
	public void areAuthorsCorrect(List<AuthorBO> authors) throws InvalidFieldException{
		
		if(authors.size()==0) {
			throw new AuthorException("Book can't be created without an Author");
		}
		for(AuthorBO author:authors) {
			isAuthorCorrect(author);
			isStringFieldCorrect(author.getName(),"Author name");
			isStringFieldCorrect(author.getLastName(),"Author last name");
			isStringFieldCorrect(author.getNacionality(),"nationality");
		}
	}

	
	public void isBookCorrect(BookBO book) throws InvalidFieldException{
			isStringFieldCorrect(book.getName(),"name");
			isbnIsCorrect(book.getIsbn());
			isStringFieldCorrect(book.getEdition(),"edition");
			isStringFieldCorrect(book.getEditorial(),"editorial");
			isStringFieldCorrect(book.getCategory(),"category");
			isStringFieldCorrect(book.getLanguage(),"language");
			isStatusCorrect(book.getStatus());
			areAuthorsCorrect(book.getAuthors());
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
