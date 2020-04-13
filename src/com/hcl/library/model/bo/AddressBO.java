package com.hcl.library.model.bo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@Builder
@NoArgsConstructor
public class AddressBO {
	private int id;
	private int number;
	private String street;
	private String city;
	private String state;
	private String country;
	private int postalCode;


	
}
