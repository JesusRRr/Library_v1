package com.hcl.library.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.hcl.library.model.bo.AuthorBO;
import com.hcl.library.model.po.AuthorPO;

public class AuthorDto {
	
	public static final AuthorBO map(AuthorPO persistenceAuthor) {
		AuthorBO author = new AuthorBO();
		author.setId(persistenceAuthor.getId());
		author.setName(persistenceAuthor.getName());
		author.setLastName(persistenceAuthor.getLastName());
		author.setCurp(persistenceAuthor.getCurp());
		//author.setBooks(BookDto.mapBookListToBO(persistenceAuthor.getBooks()));
	
		return author;
	}
	
	public static final AuthorPO map(AuthorBO businessAuthor) {
		AuthorPO author = new AuthorPO();
		author.setId(businessAuthor.getId());
		author.setName(businessAuthor.getName());
		author.setLastName(businessAuthor.getLastName());
		author.setCurp(businessAuthor.getCurp());
		//author.setBooks(BookDto.mapBookListToPO(businessAuthor.getBooks()));
		
		return author;
	}
	
	public static List<AuthorBO> mapAuthorListToBO(List<AuthorPO> authors){
		return authors.stream().map(author -> {
			AuthorBO newAuthor = new AuthorBO();
			newAuthor.setName(author.getName());
			newAuthor.setLastName(author.getLastName());
			newAuthor.setCurp(author.getCurp());
			newAuthor.setNacionality(author.getNacionality());			
			return newAuthor;
			
		}).collect(Collectors.toList());
	}
	
	public static List<AuthorPO> mapAuthorListToPO(List<AuthorBO> authors){
		return authors.stream().map(author -> {
			AuthorPO newAuthor = new AuthorPO();
			newAuthor.setName(author.getName());
			newAuthor.setLastName(author.getLastName());
			newAuthor.setCurp(author.getCurp());
			newAuthor.setNacionality(author.getNacionality());			
			return newAuthor;
			
		}).collect(Collectors.toList());
	}
}
