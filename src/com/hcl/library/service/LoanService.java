package com.hcl.library.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response.Status;

import com.hcl.library.dao.LoanDao;
import com.hcl.library.dto.BookDto;
import com.hcl.library.dto.CustomerDto;
import com.hcl.library.dto.LoanDTO;
import com.hcl.library.exceptions.CustomerLoanException;
import com.hcl.library.exceptions.LoanEmptyFieldException;
import com.hcl.library.exceptions.LoanException;
import com.hcl.library.model.bo.BookBO;
import com.hcl.library.model.bo.LoanBO;
import com.hcl.library.model.enums.StatusBook;
import com.hcl.library.model.enums.StatusLoan;
import com.hcl.library.model.po.BookPO;
import com.hcl.library.model.po.CustomerPO;
import com.hcl.library.model.po.LoanPO;
import com.hcl.library.service.rest.request.Loan;
import com.hcl.library.service.rest.request.ReturnLoan;

public class LoanService {
	private static LoanService loanService;
	private LoanDao loanDao;
	private BookService bookService;
	private CustomerService customerService;
	private StaffService staffService;

	private LoanService() {
		this.loanDao = new LoanDao();
		this.bookService = BookService.getInstance();
		this.customerService = CustomerService.getInstance();
		this.staffService = StaffService.getInstance();
	}

	public static LoanService getLoanService() {
		return loanService == null ? loanService = new LoanService() : loanService;
	}

	public int createLoan(Loan loan) throws LoanException {
		LoanBO loanBO = fillAllLoanInfoRequired(loan);
		
		findActiveLoanByCustomerId(loanBO.getCustomer().getId());
		
		checkLoanFields(loanBO);
		
		loanBO.setBooks(removeBooksNotAvailableToLoan(loanBO.getBooks()));
		if (loan.getBooks().size() > 0) {
			LoanPO newLoan = getPersistenceObject(loanBO);
			loanDao.create(newLoan);
			loanBooks(loanBO.getBooks());

		}

		return findActiveLoanByCustomerId(loanBO.getCustomer().getId()).getId();

	}

	public void returnLoan(ReturnLoan returnLoan) throws CustomerLoanException {
		LoanPO loan = findActiveLoanByCustomerId(returnLoan.getIdLoan());
		BookPO bookToReturn = bookService.findById(returnLoan.getIdBook());
		if (loan.getBooks().contains(bookToReturn)) {
			bookToReturn.setStatus(StatusBook.AVAILABLE);
			bookService.updateBook(bookToReturn);
			if (loan.getBooks().size() == 1) {
				loan.setStatus(StatusLoan.Finished);
				updateLoan(loan);
			}
		}
	}

	private void updateLoan(LoanPO loan) {
		loanDao.update(loan);
	}

	private LoanPO findActiveLoanByCustomerId(int id) throws CustomerLoanException {
		LoanPO loan;
		try {
			loan = loanDao.findActiveLoanByCustomerId(id);
		} catch (NoResultException e) {
			throw new CustomerLoanException("This customer already has an active loan");
		}
		return loan;

	}

	private LoanPO getPersistenceObject(LoanBO loan) {
		return LoanDTO.map(loan);
	}

	private List<BookBO> removeBooksNotAvailableToLoan(List<BookBO> bookList) {
		return bookList.stream().map(book -> BookDto.map(bookService.findById(book.getId())))
				.filter(book -> bookService.findById(book.getId()).getStatus() == StatusBook.AVAILABLE)
				.collect(Collectors.toList());
	}

	private void checkLoanFields(LoanBO loan) throws LoanException {
		if (loanHasNulls(loan)) {
			throw new LoanEmptyFieldException();
		}
	}

	private boolean loanHasNulls(LoanBO loan) {
		return Stream
				.of(loan.getStaff(), loan.getCustomer(), loan.getDateOfLoan(), loan.getReturnDate(), loan.getBooks())
				.anyMatch(x -> x == null);
	}

	private void loanBooks(List<BookBO> books) {
		for (BookBO book : books) {
			book.setStatus(StatusBook.LOANED);
			bookService.updateBook(book);
		}
	}

	private LoanBO fillAllLoanInfoRequired(Loan loan) {
		LoanBO loanBO = new LoanBO();
		loanBO.setCustomer(CustomerDto.map(customerService.findByCurp(loan.getCustomerCurp())));
		loanBO.setStaff(staffService.findByUserName(loan.getStaffUsername()));
		loanBO.setStatus(StatusLoan.Active);
		loanBO.setDateOfLoan(LocalDate.now());
		loanBO.setReturnDate(LocalDate.now().plusDays(10));
		loanBO.setBooks(loan.getBooks().stream().map(book -> BookDto.map(bookService.findById(book)))
				.collect(Collectors.toList()));

		return loanBO;

	}

	public List<LoanBO> findAll() {
		List<LoanPO> loanFound = loanDao.findAll();
		if (loanFound != null) {
			return LoanDTO.mapLoanListToBO(loanFound);
		}
		return null;
	}

	public LoanBO getLoanDetails(int id) {
		return LoanDTO.map(loanDao.findById(id));
	}

}
