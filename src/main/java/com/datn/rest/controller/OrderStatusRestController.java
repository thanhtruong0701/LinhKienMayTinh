package com.datn.rest.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datn.model.OrderStatus;
import com.datn.service.OrderStatusService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/orderStatuss")
public class OrderStatusRestController {
	@Autowired
	OrderStatusService orderStatusService;
	
	@GetMapping
	public List<OrderStatus> findAll(){
		return orderStatusService.findAll();
	}
}
