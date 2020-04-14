package com.hcl.library.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.hcl.library.dao.LoanDao;
import com.hcl.library.dto.BookDto;
import com.hcl.library.dto.CustomerDto;
import com.hcl.library.dto.LoanDTO;
import com.hcl.library.exceptions.CustomerHasActiveLoanException;
import com.hcl.library.model.bo.BookBO;
import com.hcl.library.model.bo.CustomerBO;
import com.hcl.library.model.bo.LoanBO;
import com.hcl.library.model.enums.StatusBook;
import com.hcl.library.model.enums.StatusLoan;
import com.hcl.library.model.po.BookPO;
import com.hcl.library.model.po.CustomerPO;
import com.hcl.library.model.po.LoanPO;
import com.hcl.library.service.rest.request.Loan;

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
	}

	public static LoanService getLoanService() {
		return loanService == null ? loanService = new LoanService() : loanService;
	}

	public int createLoan(Loan loan) throws CustomerHasActiveLoanException {
		CustomerBO customer = CustomerDto.map(customerService.findByCurp(loan.getCustomerCurp()));
		if (findActiveLoanByCustomerId(customer.getId()) != null) {
			LoanBO loanBO = new LoanBO();
			loanBO.setCustomer(customer);
			loanBO.setStaff(staffService.findByUserName(loan.getStaffUsername()));
			loanBO.setStatus(StatusLoan.Active);
			loanBO.setDateOfLoan(LocalDate.now());
			loanBO.setReturnDate(LocalDate.now().plusDays(10));
			loanBO.setBooks(
					loan.getBooks().stream().map(book -> bookService.findById(book)).collect(Collectors.toList()));

			createLoan(loanBO);

			return findActiveLoanByCustomerId(loanBO.getId()).getId();
		}
		else {
			throw new CustomerHasActiveLoanException("This customer already has an active loan");
		}

	}

	private boolean createLoan(LoanBO loan) {
		boolean isCreated = false;
		if (!loanHasNulls(loan)) {
			loan.setBooks(removeBooksNotAvailableToLoan(loan.getBooks()));
			if (loan.getBooks().size() > 0) {
				LoanPO newLoan = getPersistenceObject(loan);
				isCreated = loanDao.create(newLoan);
				loanBooks(loan.getBooks());
			}
		}
		return isCreated;
	}

	public boolean returnLoan(String customerCurp) {
		CustomerPO customer = customerService.findByCurp(customerCurp);
		LoanPO loan = findActiveLoanByCustomerId(customer.getId());

		return false;
	}

	private LoanPO findActiveLoanByCustomerId(int id) {
		return loanDao.findActiveLoanByCustomerId(id);
	}

	private LoanPO getPersistenceObject(LoanBO loan) {
		return LoanDTO.map(loan);
	}

	private List<BookBO> removeBooksNotAvailableToLoan(List<BookBO> bookList) {
		return bookList.stream().map(book -> bookService.findById(book.getId()))
				.filter(book -> bookService.findById(book.getId()).getStatus() == StatusBook.AVAILABLE)
				.collect(Collectors.toList());
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

}
