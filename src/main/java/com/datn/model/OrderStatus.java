package com.datn.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "Order_Status")
public class OrderStatus implements Serializable{
	@Id
	String id;
	String status_name;
	Integer stt;
	
	@JsonIgnore
	@OneToMany(mappedBy = "orderStatus")
	List<Order> orders;
	
	@JsonIgnore
	@OneToMany(mappedBy = "orderStatus")
	List<OrderGuest> orderGuests;
}
