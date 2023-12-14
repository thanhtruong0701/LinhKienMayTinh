package com.datn.service.impl;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datn.dao.OrderDetailGuestDAO;
import com.datn.dao.OrderGuestDAO;
import com.datn.model.OrderDetailGuest;
import com.datn.model.OrderGuest;
import com.datn.service.OrderGuestService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrderGuestServiceImpl implements OrderGuestService {
	@Autowired
	OrderGuestDAO orderGuestDAO;
	@Autowired
	OrderDetailGuestDAO orderDetailGuestDAO;
	
	@Override
	public OrderGuest createOrderGuest(JsonNode orderData) {
		ObjectMapper mapper = new ObjectMapper(); // sử dụng objectmapper để chuyển json thành các đối tượng cần thiết
		
		OrderGuest orderGuest = mapper.convertValue(orderData, OrderGuest.class);  // chuyển orderData(json) thành order
		orderGuestDAO.save(orderGuest);
		
		TypeReference<List<OrderDetailGuest>> type = new TypeReference<List<OrderDetailGuest>>() {};
		List<OrderDetailGuest> details = mapper.convertValue(orderData.get("orderDetailGuests"), type) // lấy orderdetails // dùng convertValue chuyển json thành list orderdetails
				.stream().peek(d -> d.setOrderGuest(orderGuest)).collect(Collectors.toList());
		orderDetailGuestDAO.saveAll(details); // dùng saveAll() để lưu nhiều orderDetails 1 lúc
		
		return orderGuest; // trả về order vừa tạo ra trong CSDL
	}

	@Override
	public OrderGuest findById(Long id) {
		return orderGuestDAO.findById(id).get();
	}

	@Override
	public OrderGuest updateSTT(OrderGuest orderGuest) {
		// TODO Auto-generated method stub
		return orderGuestDAO.save(orderGuest);
	}

	@Override
	public List<OrderGuest> findByPhonenumberAndAddress(String phonenumber, String address) {
		return orderGuestDAO.findByPhonenumberAndAddress(phonenumber, address);
	}

	@Override
	public List<OrderGuest> findAll() {
		return orderGuestDAO.findAll();
	}

	@Override
	public void create(OrderGuest orderGuest) {
		orderGuestDAO.save(orderGuest);
		
	}

	@Override
	public OrderGuest update(OrderGuest orderGuest) {
		return orderGuestDAO.save(orderGuest);
	}

	@Override
	public void delete(Long id) {
		orderGuestDAO.deleteById(id);
	}

	@Override
	public List<OrderGuest> findAllIdDESC() {
		return orderGuestDAO.findAllIdDESC();
	}
	
	
}
