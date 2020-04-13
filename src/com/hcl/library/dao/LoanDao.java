package com.hcl.library.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.hcl.library.generics.GenericCrudImpl;
import com.hcl.library.model.enums.StatusLoan;
import com.hcl.library.model.po.LoanPO;

public class LoanDao extends GenericCrudImpl<LoanPO> {

	public LoanDao() {
		super(LoanPO.class);
	}

	public LoanPO findActiveLoanByCustomerId(int customerId) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<LoanPO> query = builder.createQuery(LoanPO.class);
		Root<LoanPO> root = query.from(LoanPO.class);
		query.select(root).where(builder.equal(root.get("customer"), customerId), builder.equal(root.get("status"), StatusLoan.Active));
		
		LoanPO loan = em.createQuery(query).getResultList().get(0);
		
		return loan;
	}

}
