package com.datn.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.datn.model.Order;
import com.datn.model.OrderGuest;
import com.datn.service.OrderGuestService;
import com.datn.service.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/order")
public class OrderRestController {
	// ### PHÍA NGƯỜI DÙNG ###//
	@Autowired
	OrderService orderService;
	@Autowired
	OrderGuestService orderGuestService;
	// TẠO ĐƠN HÀNG
	@PostMapping()
	public Order create(@RequestBody JsonNode order) {
		return orderService.create(order);
	}
	// TẠO ĐƠN HÀNG GUEST
	@PostMapping("/guest")
	public OrderGuest createOrderGuest(@RequestBody JsonNode orderGuest) {
		return orderGuestService.createOrderGuest(orderGuest);
	}
}
