package com.hcl.library.service;

import java.util.List;

import com.hcl.library.dao.AuthorDao;
import com.hcl.library.dto.AuthorDto;
import com.hcl.library.model.bo.AuthorBO;
import com.hcl.library.model.po.AuthorPO;

public class AuthorService {
	private static AuthorService instance;
	private AuthorDao authorDao;

	private AuthorService() {
		authorDao = new AuthorDao();
	}
	
	public static AuthorService getInstance() {
		if(instance==null) {
			instance=new AuthorService();
		}
		return instance;
	}
	
	public boolean createAuthor(AuthorBO author) {
		AuthorPO persistenceAuthor = getPersistenceAuthor(author);
		AuthorBO authorFound = findByName(persistenceAuthor.getFullName());
		if (authorFound == null) {
			authorDao.create(persistenceAuthor);
			return true;
		} else {
			return false;
		}
	}

	public AuthorBO findById(int id) {
		AuthorPO authorFound = authorDao.findById(id);
		return getBusinessAuthor(authorFound);
	}

	public AuthorBO findByName(String fullName) {
		AuthorPO authorFound = authorDao.find(authorDao.criteriaOfSearching(fullName, "getFullName"));
		return getBusinessAuthor(authorFound);
	}

	public List<AuthorBO> findByNacionality(String nacionality) {
		List<AuthorPO> authorsFound = authorDao.findAll(authorDao.criteriaOfSearching(nacionality, "getNacionality"));
		return getBusinessList(authorsFound);
	}

	protected AuthorPO getPersistenceAuthor(AuthorBO author) {
		if(author!=null) {
			return AuthorDto.map(author);
		}else {
			return null;
		}
	}

	private AuthorBO getBusinessAuthor(AuthorPO author) {
		if(author!=null) {
			return AuthorDto.map(author);
		}else {
			return null;
		}
	}

	private List<AuthorBO> getBusinessList(List<AuthorPO> authors) {
		if(authors!=null) {
			return AuthorDto.mapAuthorListToBO(authors);
		}else {
			return null;
		}
	}

}
