package com.example.shdemo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


@Entity
@NamedQueries({ 
	@NamedQuery(name = "zamowienie.all", query = "Select z from Zamowienie z"),
	@NamedQuery(name = "zamowienie.byCustomerID", query = "Select z from Zamowienie z where z.customerID = :customerID")
})

public class Zamowienie {
	
	private Long id;
	
	private String customer = "brak";
	private String customerID = "";
	//private int idPick;
	//private int amount;
	
	private List<Pick> picks = new ArrayList<Pick>();
	
	public Zamowienie() {
	}
	
//	public Zamowienie(String customer, int amount) {
//		super();
//		this.customer = customer;
//		this.amount = amount;
//	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCustomer() {
		return customer;
	}
	
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	@Column(unique = true, nullable = false)
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	
//	public int getIdPick() {
//		return idPick;
//	}
//	
//	public void setIdPick(int idPick) {
//		this.idPick = idPick;
//	}
//	
//	public int getAmount() {
//		return amount;
//	}
//	
//	public void setAmount(int amount) {
//		this.amount = amount;
//	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Pick> getPicks() {
		return picks;
	}
	public void setPicks(List<Pick> picks) {
		this.picks = picks;
	}
	
}
