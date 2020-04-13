package com.hcl.library.model.bo;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerBO extends PersonBO{
	
	private boolean status;
	private AddressBO address;
	private Date birthday;
	private String phone;
	private String email;

	
}
