package com.hcl.library.model.bo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor

public class PersonBO {
	private int id;
	private String name;
	private String lastName;
	private String curp;
	
	
	public String getFullName() {
		return this.getName()+this.getLastName();
	}
	
	@Override
	public String toString() {
		return new StringBuilder(this.getName())
				.append(" ")
				.append(this.getLastName()
		).toString();
	}
}
