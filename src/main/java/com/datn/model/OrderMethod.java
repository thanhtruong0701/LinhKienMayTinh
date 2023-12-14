package com.datn.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "Order_Method")
public class OrderMethod implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String method_name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "orderMethod")
	List<Order> orders;
	
	@JsonIgnore
	@OneToMany(mappedBy = "orderMethod")
	List<OrderGuest> orderGuests;
}
