package com.hcl.library.model.po;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.hcl.library.model.enums.StatusBook;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity(name="BookPO")
@Table(name = "book")
public class BookPO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String name;
	private String isbn;
	private String edition;
	private String editorial;
	private String category;
	private String language;
	private String bookcover;
	
	@Enumerated(EnumType.STRING)
	private StatusBook status;
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "book_author", 
				joinColumns = @JoinColumn(name = "id_book"), 
				inverseJoinColumns = @JoinColumn(name = "id_author"))
	private List<AuthorPO> authors = new ArrayList<>();
	
	
}
