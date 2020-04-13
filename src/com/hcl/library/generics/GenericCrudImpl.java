package com.hcl.library.generics;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class GenericCrudImpl<T> implements IGenericCrud<T> {
	private EntityManagerFactory emf= Persistence.createEntityManagerFactory("Library");
	protected EntityManager em;
	private final Class<T> name;
	
	public GenericCrudImpl(Class<T> name){
		this.em = emf.createEntityManager();
		this.name = name;
	}

	private void manageCreateException(Exception e) {
		System.out.println(e);
		this.em.getTransaction().rollback();
	}
	
	
	@Override
	public boolean create(T entity) {
		this.em=emf.createEntityManager();
		return persist(entity);
	}
	
	private boolean persist(T entity) {
		em.getTransaction().begin();
		try {
			this.em.persist( entity );
		}catch(Exception e) {
			manageCreateException(e);
			return false;
		}
		em.getTransaction().commit();
		return true;
	}

	@Override
	public boolean deleteById(int id) {
		System.out.println("deleting");
		return false;
	}

	@Override
	public T update( T entity) {
		em.getTransaction().begin();
		em.merge(entity);
		em.getTransaction().commit();
		em.close();
	
		return entity;		
	}
	
	@Override
	public T findById(int id) {
		
		this.em = emf.createEntityManager();
		T entity = this.em.find(name, id);		
		
		return entity;
	}
	
	@Override
	public List<T> findAll() {
		
		StringBuilder out = new StringBuilder("SELECT t from ").append(name.getSimpleName()).append(" t");
		return em.createQuery(out.toString()).getResultList();
	}
	
	
	public T find(Predicate<T> predicate) {
		T entity = null;
		Optional<T> optionalEntity = findAll().stream().filter(predicate).findAny();
		
		if (optionalEntity.isPresent()) {
			return optionalEntity.get();
		}
		return entity;
	}
	
	
	public List<T> findAll(Predicate<T> predicate) {
		 List<T> entities = findAll().stream().filter(predicate).collect(Collectors.toList());	
		return entities;
	}
	
	public Predicate<T> criteriaOfSearching(String tarjet, String method){
		String entityTarjetFormatted = tarjet.replace(" ", "");		
		Predicate<T> sameCondition = entity -> {
			try {
				return entity.getClass().getMethod(method).invoke(entity).toString().replace(" ", "")
						.equalsIgnoreCase(entityTarjetFormatted);
			} catch (Exception e) {
				return false;				
			} 
		};
		
		return sameCondition;
	}
	
	
}