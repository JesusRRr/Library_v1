package com.hcl.library.model.bo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PersonBO {
	private int id;
	private String name;
	private String lastName;
	private String curp;

	public String getFullName() {
		return this.getName()+this.getLastName();
	}
}
