package com.hcl.library.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.hcl.library.dao.LoanDao;
import com.hcl.library.exceptions.BookUnavailableToLoanException;
import com.hcl.library.exceptions.CustomerDoesNotExistsException;
import com.hcl.library.exceptions.CustomerLoanException;
import com.hcl.library.exceptions.LoanException;
import com.hcl.library.model.bo.LoanBO;
import com.hcl.library.model.bo.StaffBO;
import com.hcl.library.model.enums.StatusBook;
import com.hcl.library.model.po.AddressPO;
import com.hcl.library.model.po.BookPO;
import com.hcl.library.model.po.CustomerPO;
import com.hcl.library.model.po.LoanPO;
import com.hcl.library.service.rest.request.Loan;

@RunWith(MockitoJUnitRunner.class)
public class LoanServiceTest {

	@Mock
	private BookService bookService;

	@Mock
	private CustomerService customerService;

	@Mock
	private StaffService staffService;

	@Mock
	private LoanDao loanDao;

	@InjectMocks
	private LoanService loanService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(loanDao.create(any(LoanPO.class))).thenReturn(true);
	}

	@Test
	public void testCreateNewLoan() throws Exception {
		Loan loan = new Loan();
		loan.setBooks(Arrays.asList(1, 2, 3));

		when(customerService.findByCurp(any(String.class))).thenReturn(createTestCustomer());
		when(loanDao.findActiveLoanByCustomerId(any(Integer.class))).thenThrow(CustomerLoanException.class)
				.thenReturn(new LoanPO());
		when(staffService.findByUserName(any(String.class))).thenReturn(new StaffBO());
		when(bookService.findById(any(Integer.class))).thenReturn(createBookWithStatus(StatusBook.AVAILABLE));

		assertTrue(loanService.createLoan(loan) >= 0);
		verify(loanDao).create(any(LoanPO.class));

	}

	@Test(expected = LoanException.class)
	public void testCreateLoanNonExistingCustomer() throws Exception {

		when(customerService.findByCurp(any(String.class))).thenThrow(CustomerDoesNotExistsException.class);

		loanService.createLoan(new Loan());

		verifyZeroInteractions(loanDao.create(any(LoanPO.class)));

	}

	@Test(expected = BookUnavailableToLoanException.class)
	public void testCreateWithLoanedBooks() throws Exception {
		Loan loan = new Loan();
		loan.setBooks(Arrays.asList(1, 2, 3));

		when(customerService.findByCurp(any(String.class))).thenReturn(createTestCustomer());
		when(staffService.findByUserName(any(String.class))).thenReturn(new StaffBO());

		when(bookService.findById(any(Integer.class))).thenReturn(createBookWithStatus(StatusBook.LOANED));

		loanService.createLoan(loan);

		verifyZeroInteractions(loanDao.create(any(LoanPO.class)));
	}

	private BookPO createBookWithStatus(StatusBook status) {
		BookPO book = new BookPO();
		book.setId(1);
		book.setIsbn("sdfgh");
		book.setName("sdfg");
		book.setStatus(status);
		return book;
	}

	private CustomerPO createTestCustomer() {
		CustomerPO customer = new CustomerPO();
		customer.setAddress(new AddressPO());

		return customer;
	}

}
