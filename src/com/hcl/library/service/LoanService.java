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
import com.hcl.library.exceptions.BookUnavailableToLoanException;
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

	public LoanBO getLoanDetails(int id) {
		return LoanDTO.map(loanDao.findById(id));
	}

	public List<LoanBO> findAll() {
		List<LoanPO> loanFound = loanDao.findAll();
		if (loanFound != null) {
			return LoanDTO.mapLoanListToBO(loanFound);
		}
		return null;
	}

	public int createLoan(Loan loan) throws LoanException {
		LoanBO loanBO = fillAllLoanInfoRequired(loan);
		if (loan.getBooks().size() > 0) {
			checkLoanFields(loanBO);
			if (!customerHasActiveLoan(loanBO.getCustomer().getId())) {
				LoanPO newLoan = LoanDTO.map(loanBO);
				loanDao.create(newLoan);
				loanBooks(loanBO.getBooks());
			}
		}

		return findActiveLoanByCustomerId(loanBO.getCustomer().getId()).getId();

	}

	private boolean customerHasActiveLoan(int idCustomer) {
		boolean hasActiveLoan = true;
		try {
			findActiveLoanByCustomerId(idCustomer);
		} catch (CustomerLoanException e) {
			hasActiveLoan = false;
		}
		return hasActiveLoan;
	}

	public void returnLoan(ReturnLoan returnLoan) throws LoanException {
		LoanPO loan = loanDao.findById(returnLoan.getIdLoan());
		BookPO bookToReturn = bookService.findById(returnLoan.getIdBook());

		if (bookToReturn != null) {
			boolean listContainsBook = loan.getBooks().stream().anyMatch(book -> book.getId() == bookToReturn.getId());

			if (listContainsBook) {
				returnBookLoaned(bookToReturn);

				if (!isBookMissingToReturn(loan.getBooks())) {
					finishLoan(loan);
				}
			} else {
				throw new LoanException("The loan does not contain any book with id: " + returnLoan.getIdBook());
			}
		} else {
			throw new LoanException("The book with the id: " + returnLoan.getIdBook() + " doesn't exist");
		}

	}

	private void returnBookLoaned(BookPO book) {
		book.setStatus(StatusBook.AVAILABLE);
		bookService.updateBook(book);
	}

	private boolean isBookMissingToReturn(List<BookPO> books) throws CustomerLoanException {
		for (BookPO book : books) {
			BookPO b = bookService.findById(book.getId());
			if (b.getStatus() == StatusBook.LOANED) {
				return true;
			}
		}
		return false;

	}

	private void finishLoan(LoanPO loan) {
		loan.setStatus(StatusLoan.Finished);
		updateLoan(loan);
	}

	private void updateLoan(LoanPO loan) {
		loanDao.update(loan);
	}

	private LoanPO findActiveLoanByCustomerId(int id) throws CustomerLoanException {
		LoanPO loan;
		loan = loanDao.findActiveLoanByCustomerId(id);

		return loan;

	}

	private void checkLoanFields(LoanBO loan) throws LoanException {
		if (loanHasNulls(loan)) {
			throw new LoanEmptyFieldException();
		}
		if (containsUnavailableBooks(loan.getBooks())) {
			throw new BookUnavailableToLoanException("This loan contains one or more books unavailable to loan");
		}

	}

	private boolean loanHasNulls(LoanBO loan) {
		return Stream
				.of(loan.getStaff(), loan.getCustomer(), loan.getDateOfLoan(), loan.getReturnDate(), loan.getBooks())
				.anyMatch(x -> x == null);
	}

	private boolean containsUnavailableBooks(List<BookBO> bookList) {
		return bookList.stream().anyMatch(book -> book.getStatus() != StatusBook.AVAILABLE);
	}

	private void loanBooks(List<BookBO> books) {
		for (BookBO book : books) {
			book.setStatus(StatusBook.LOANED);
			bookService.updateBook(book);
		}
	}

	private LoanBO fillAllLoanInfoRequired(Loan loan) throws LoanException {
		LoanBO loanBO = new LoanBO();
		try {
			loanBO.setCustomer(CustomerDto.map(customerService.findByCurp(loan.getCustomerCurp())));
			loanBO.setStaff(staffService.findByUserName(loan.getStaffUsername()));
			loanBO.setBooks(loan.getBooks().stream().map(book -> BookDto.map(bookService.findById(book)))
					.collect(Collectors.toList()));
		} catch (Exception e) {
			throw new LoanException(e.getMessage());
		}

		loanBO.setStatus(StatusLoan.Active);
		loanBO.setDateOfLoan(LocalDate.now());
		loanBO.setReturnDate(LocalDate.now().plusDays(10));

		return loanBO;

	}

}
