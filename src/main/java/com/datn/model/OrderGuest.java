package com.datn.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "Orders_Guest")
public class OrderGuest implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String address;
	String phonenumber;
	@Temporal(TemporalType.DATE)
	@Column(name = "Createdate")
	Date createDate = new Date();
	
	@ManyToOne
	@JoinColumn(name = "Orderstatus_id")
	OrderStatus orderStatus;
	
	@ManyToOne
	@JoinColumn(name = "Ordermethod_id")
	OrderMethod orderMethod;
	
	@JsonIgnore
	@OneToMany(mappedBy = "orderGuest")
	List<OrderDetailGuest> orderDetailGuests;
}
