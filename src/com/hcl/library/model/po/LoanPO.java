package com.hcl.library.model.po;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.hcl.library.model.enums.StatusLoan;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity(name = "LoanPO")
@Table(name = "loan")
public class LoanPO {
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@Id
	@GeneratedValue
	@Column(name="loan_id")
	private int id;
	
	@ManyToOne(optional= false, fetch = FetchType.EAGER)
	@JoinColumn(name="id_customer")
	private CustomerPO customer;

	@ManyToOne(optional= false, fetch = FetchType.EAGER)
	@JoinColumn(name="id_staff")
	private StaffPO staff;
	
	private LocalDate dateOfLoan;
	
	private LocalDate returnDate;
	
	@Enumerated(EnumType.STRING)
	private StatusLoan status;
	
	@OneToMany
	@JoinTable(
		name = "loan_book",
		joinColumns = @JoinColumn(name="id_loan")
	)
	private List<BookPO> books = new ArrayList<>();
	
	
	public LoanPO(){
		
	}

	
	
	
	
}
