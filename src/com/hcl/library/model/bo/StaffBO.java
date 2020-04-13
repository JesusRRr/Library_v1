package com.hcl.library.model.bo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StaffBO extends PersonBO{
	private String userName;
	private String password;
	private LoanBO loan;

}
