package com.datn.service;

import java.util.List;

import com.datn.model.OrderStatus;
// trạng thái đơn hàng service
public interface OrderStatusService {
	
	public List<OrderStatus> findAll() ;

	public void create(OrderStatus orderStatus);

	public OrderStatus update(OrderStatus orderStatus);

	public void delete(String id);
}
