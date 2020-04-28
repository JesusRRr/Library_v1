package com.hcl.library.dao;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.hcl.library.exceptions.CustomerLoanException;
import com.hcl.library.generics.GenericCrudImpl;
import com.hcl.library.model.enums.StatusLoan;
import com.hcl.library.model.po.LoanPO;

public class LoanDao extends GenericCrudImpl<LoanPO> {

	public LoanDao() {
		super(LoanPO.class);
	}

	public LoanPO findActiveLoanByCustomerId(int customerId)throws CustomerLoanException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<LoanPO> query = builder.createQuery(LoanPO.class);
		Root<LoanPO> root = query.from(LoanPO.class);
		query.select(root).where(builder.equal(root.get("customer"), customerId), builder.equal(root.get("status"), StatusLoan.Active));
		
		LoanPO loan;

		try {
			loan = em.createQuery(query).getSingleResult();
		}catch(NoResultException e) {
			throw new CustomerLoanException("The customer with id: " + customerId +" does not have an active loan");
		}
		
		
		return loan;
	}

}
