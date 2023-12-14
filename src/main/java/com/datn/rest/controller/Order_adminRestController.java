package com.datn.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datn.model.Order;
import com.datn.model.OrderDetail;
import com.datn.model.OrderStatus;
import com.datn.service.OrderDetailService;
import com.datn.service.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/ordersAdmin")
public class Order_adminRestController {
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailService orderDetailService;
	
	// ## ADMIN ## //
	@GetMapping
	public List<Order> getAll() {
		return orderService.findAllIdDESC();
	}
	
	@GetMapping("{id}")
	public Order getOne(@PathVariable("id") Long id) {
		return orderService.findById(id);
	}
	
	@GetMapping("detail/{id}")
	public List<OrderDetail> getDetail(@PathVariable("id") Long id) {
		return orderDetailService.findByOrderID(id);
	}
	
	@PostMapping
	public Order post(@RequestBody Order order) {
		orderService.create(order);
		return order;
	}
	
	@PutMapping("{id}")
	public Order put(@PathVariable("id") Long id, @RequestBody Order order) {
		return orderService.update(order);
	}
	
	@PutMapping("status/{id}")
	public Order putstatus(@PathVariable("id") Long id) {
		Order order = orderService.findById(id);
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setId("DXN");
		Order orderNew = new Order();
		orderNew.setId(order.getId());
		orderNew.setCreateDate(order.getCreateDate());
		orderNew.setPhonenumber(order.getPhonenumber());
		orderNew.setAddress(order.getAddress());
		orderNew.setOrderStatus(orderStatus);
		orderNew.setAccount(order.getAccount());
		orderNew.setOrderMethod(order.getOrderMethod());
		return orderService.updateSTT(orderNew);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Long id) {
		orderService.delete(id);
	}
}
