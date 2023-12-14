package com.datn.service;

import java.util.List;

import com.datn.model.OrderMethod;

public interface OrderMethodService {
	List<OrderMethod> findAll();
    
    OrderMethod getById(int id);
}
