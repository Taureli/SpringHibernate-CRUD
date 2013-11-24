package com.example.shdemo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity
@NamedQueries({
		@NamedQuery(name = "pick.all", query = "Select p from Pick p"),
		@NamedQuery(name = "pick.unsold", query = "Select p from Pick p where p.av = false")
})

public class Pick {
	
	private Long id;
	
	private String company;
	private String name;
	private double milimeters;
	private double price;
	private Boolean av = false;
	
	public Pick() {
	}
	
	public Pick(String company, String name, double milimeters, double price) {
		super();
		this.company = company;
		this.name = name;
		this.milimeters = milimeters;
		this.price = price;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getMilimeters() {
		return milimeters;
	}
	
	public void setMilimeters(double milimeters) {
		this.milimeters = milimeters;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Boolean getAv(){
		return av;
	}
	
	public void setAv(Boolean available){
		this.av = available;
	}
	
}
