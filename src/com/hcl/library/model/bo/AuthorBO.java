package com.hcl.library.model.bo;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorBO extends PersonBO{
	private String nacionality;
	private List<BookBO> Books = new ArrayList<>();
	@Override
	public String toString() {
		return "AuthorBO [nacionality=" +", toString()=" + super.toString() + "]";
	}
	
	
	
}
