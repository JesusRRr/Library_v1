package com.hcl.library.generics;

import java.util.List;
import java.util.function.Predicate;

public interface IGenericCrud<T> {

	boolean create(T entity);

	boolean deleteById(int id);

	T update(T newData);

	T findById(int id);
	
	List<T> findAll();
	
	T find(Predicate<T> predicate);


}
