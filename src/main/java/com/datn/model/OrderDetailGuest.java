package com.datn.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "Order_Details_Guest")
public class OrderDetailGuest implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	Double price;
	Integer quantity;
	
	@ManyToOne
	@JoinColumn(name = "Product_id")
	Product product;
	
	@ManyToOne
	@JoinColumn(name = "Orderguest_id")
	OrderGuest orderGuest;
}
