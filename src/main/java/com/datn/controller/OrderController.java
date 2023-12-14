package com.datn.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.datn.model.Account;
import com.datn.model.Order;
import com.datn.model.OrderStatus;
import com.datn.service.AccountService;
import com.datn.service.OrderService;

@Controller
public class OrderController {
	@Autowired
	OrderService orderService;
	@Autowired
	AccountService accountService;
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("/order/checkout")
	public String checkout() {
		return "order/checkout";
	}

	@RequestMapping("/order/detail/{id}")
	public String detail(Model model, @PathVariable("id") Long id) {
		model.addAttribute("order", orderService.findById(id));
		return "order/detail";
	}
	
	@RequestMapping("/order/list")
	public String detail(Model model) {
		String username = request.getRemoteUser();
		model.addAttribute("orders", orderService.findByUsername(username));
		return "order/list";
	}
	
	@RequestMapping("/order/cancel/{id}")
	public String cancel(@PathVariable("id") Long id) {
		Order order = orderService.findById(id);
		//
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setId("DH");
		//
		Order orderCancel = new Order();
		orderCancel.setId(order.getId());
		orderCancel.setCreateDate(order.getCreateDate());
		orderCancel.setPhonenumber(order.getPhonenumber());
		orderCancel.setAddress(order.getAddress());
		orderCancel.setOrderStatus(orderStatus);
		orderCancel.setAccount(accountService.findById(request.getRemoteUser()));
		orderService.updateSTT(orderCancel);
		return "redirect:/order/list";
	}
}
