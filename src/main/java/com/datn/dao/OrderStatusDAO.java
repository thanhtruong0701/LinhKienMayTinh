package com.datn.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datn.model.OrderStatus;

public interface OrderStatusDAO extends JpaRepository<OrderStatus, String>{
	
}
