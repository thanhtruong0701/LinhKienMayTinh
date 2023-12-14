package com.datn.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datn.dao.OrderStatusDAO;
import com.datn.model.OrderStatus;
import com.datn.service.OrderStatusService;

@Service
public class OrderStatusServiceImpl implements OrderStatusService{
	@Autowired
	OrderStatusDAO orderStatusDAO;
	
	@Override
	public List<OrderStatus> findAll() {
		return orderStatusDAO.findAll();
	}

	@Override
	public void create(OrderStatus orderStatus) {
		orderStatusDAO.save(orderStatus);
	}

	@Override
	public OrderStatus update(OrderStatus orderStatus) {
		return orderStatusDAO.save(orderStatus);
	}

	@Override
	public void delete(String id) {
		orderStatusDAO.deleteById(id);
	}
}
