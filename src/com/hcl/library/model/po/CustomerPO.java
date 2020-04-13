package com.hcl.library.model.po;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.hcl.library.model.enums.StatusBook;
import com.hcl.library.model.enums.StatusCustomer;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "CustomerPO")
@Table(name = "customer")
public class CustomerPO extends PersonPO{
	
	
	@Column
	@Enumerated(EnumType.STRING)
	private StatusCustomer status;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_address")
	private AddressPO address;
	
	@Column
	private Date birthday;
	
	
	@Column
	private String email;

	@Column
	private String phone;
	
	public CustomerPO() {
		
	}
	public AddressPO getAddress() {
		return address;
	}

	public void setAddress(AddressPO address) {
		this.address = address;
	}

	public Date getBirthday() {
		return birthday;
	}
	public void setStatus(StatusCustomer status) {
		this.status = status;
	}

	public StatusCustomer getStatus() {
		return status;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
