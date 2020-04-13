package com.hcl.library.model.bo;

import java.util.ArrayList;
import java.util.List;

import com.hcl.library.model.enums.StatusBook;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BookBO {
	private int id;
	private String name;
	private String isbn;
	private String edition;
	private String editorial;
	private String category;
	private String language;
	private String bookcover;
	private StatusBook status;
	private List<AuthorBO> authors = new ArrayList<>();	
	
}
