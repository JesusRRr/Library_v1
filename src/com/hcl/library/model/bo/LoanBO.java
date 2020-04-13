package com.hcl.library.model.bo;

import java.time.LocalDate;
import java.util.List;

import com.hcl.library.model.enums.StatusLoan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class LoanBO {
	private int id;
	private CustomerBO customer;
	private StaffBO staff;
	private LocalDate dateOfLoan;
	private LocalDate returnDate;
	private StatusLoan status;
	private List<BookBO> books;
	
	
}
