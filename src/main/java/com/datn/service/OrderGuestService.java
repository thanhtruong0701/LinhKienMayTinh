package com.datn.service;

import java.util.List;

import com.datn.model.OrderGuest;
import com.fasterxml.jackson.databind.JsonNode;

public interface OrderGuestService {
	public OrderGuest createOrderGuest(JsonNode orderData) ;
	
	public OrderGuest findById(Long id) ;
	
	public OrderGuest updateSTT(OrderGuest orderGuest);
	
	public List<OrderGuest> findByPhonenumberAndAddress(String phonenumber, String address);

	public List<OrderGuest> findAll();
	
	public List<OrderGuest> findAllIdDESC();

	public void create(OrderGuest orderGuest);

	public OrderGuest update(OrderGuest orderGuest);

	public void delete(Long id);
}
