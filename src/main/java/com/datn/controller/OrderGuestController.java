package com.datn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.datn.model.OrderGuest;
import com.datn.model.OrderStatus;
import com.datn.service.OrderGuestService;

@Controller
public class OrderGuestController {
	@Autowired
	OrderGuestService orderGuestService;
	
	@RequestMapping("/orderGuest/checkout")
	public String checkout() {
		return "orderGuest/checkout";
	}
	
	@RequestMapping("/orderGuest/detail/{id}")
	public String detail(Model model, @PathVariable("id") Long id) {
		model.addAttribute("orderGuest", orderGuestService.findById(id));
		return "orderGuest/detail";
	}
	
	@RequestMapping("/orderGuest/list")
	public String listCheck(Model model) {
		return "orderGuest/list";
	}
	
	@RequestMapping("/orderGuest/listCheck")
	public String list(Model model, @RequestParam("phonenumber") String phonenumber, @RequestParam("address") String address) {
		model.addAttribute("phonenumber", phonenumber);
		model.addAttribute("address", address);
		List<OrderGuest> orderGuests = orderGuestService.findByPhonenumberAndAddress(phonenumber, address);
		model.addAttribute("orderGuest", orderGuests);
		if(orderGuests.size() == 0) {
			model.addAttribute("mess", "Đơn hàng không tồn tại !");
		}
		return "orderGuest/list";
	}
	
	@RequestMapping("/orderGuest/cancel/{id}")
	public String cancel(@PathVariable("id") Long id) {
		OrderGuest orderGuest = orderGuestService.findById(id);
		//
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setId("DH");
		//
		OrderGuest orderGuestCancel = new OrderGuest();
		orderGuestCancel.setId(orderGuest.getId());
		orderGuestCancel.setCreateDate(orderGuest.getCreateDate());
		orderGuestCancel.setPhonenumber(orderGuest.getPhonenumber());
		orderGuestCancel.setAddress(orderGuest.getAddress());
		orderGuestCancel.setOrderStatus(orderStatus);
		orderGuestService.updateSTT(orderGuestCancel);
		System.out.println(orderGuest.getAddress().stripTrailing());
		return "redirect:/orderGuest/listCheck?phonenumber="+orderGuest.getPhonenumber()+"&address="+orderGuest.getAddress();
	}
}
