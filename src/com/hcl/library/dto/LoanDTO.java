package com.hcl.library.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.hcl.library.model.bo.LoanBO;
import com.hcl.library.model.po.LoanPO;

public final class LoanDTO {

	public static final LoanBO map(LoanPO persistenceLoan) {
		return null;
	}

	public static final LoanPO map(LoanBO businessLoan) {
		LoanPO loan = new LoanPO();
		loan.setCustomer(CustomerDto.map(businessLoan.getCustomer()));
		loan.setStaff(StaffDto.map(businessLoan.getStaff()));
		loan.setDateOfLoan(businessLoan.getDateOfLoan());
		loan.setReturnDate(businessLoan.getReturnDate());
		loan.setStatus(businessLoan.getStatus());
		loan.setBooks(BookDto.mapBookListToPO(businessLoan.getBooks()));

		return loan;
	}

	public static final List<LoanPO> mapLoanListToPO(List<LoanBO> loans) {
		return loans.stream().map(loan -> {
			LoanPO newLoan = new LoanPO();
			newLoan.setCustomer(CustomerDto.map(loan.getCustomer()));
			newLoan.setStaff(StaffDto.map(loan.getStaff()));
			newLoan.setBooks(BookDto.mapBookListToPO(loan.getBooks()));
			newLoan.setDateOfLoan(loan.getDateOfLoan());
			newLoan.setReturnDate(loan.getReturnDate());
			newLoan.setStatus(loan.getStatus());

			return newLoan;

		}).collect(Collectors.toList());
	}

	public static final List<LoanBO> mapLoanListToBO(List<LoanPO> loans) {
		return loans.stream().map(loan -> {
			LoanBO newLoan = new LoanBO();
			newLoan.setStaff(StaffDto.map(loan.getStaff()));
			newLoan.setCustomer(CustomerDto.map(loan.getCustomer()));
			newLoan.setBooks(BookDto.mapBookListToBO(loan.getBooks()));
			newLoan.setDateOfLoan(loan.getDateOfLoan());
			newLoan.setReturnDate(loan.getReturnDate());
			newLoan.setStatus(loan.getStatus());

			return newLoan;

		}).collect(Collectors.toList());
	}

}
