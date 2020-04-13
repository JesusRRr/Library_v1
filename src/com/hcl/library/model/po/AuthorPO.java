package com.hcl.library.model.po;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "AuthorPO")
@Table(name = "author")
public class AuthorPO extends PersonPO {

	private String nacionality;
	
	@ManyToMany(mappedBy = "authors")
	private List<BookPO> Books = new ArrayList<>();
	
	
}
