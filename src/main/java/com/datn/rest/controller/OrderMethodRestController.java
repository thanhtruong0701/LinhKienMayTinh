package com.datn.rest.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datn.model.OrderMethod;
import com.datn.service.OrderMethodService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/orderMethods")
public class OrderMethodRestController {
	@Autowired
	OrderMethodService orderMethodService;
	
	@GetMapping
	public List<OrderMethod> findAll(){
		return orderMethodService.findAll();
	}
}
