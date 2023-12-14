package com.datn.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datn.dao.OrderMethodDAO;
import com.datn.model.OrderMethod;
import com.datn.service.OrderMethodService;

@Service
public class OrderMethodServiceImpl implements OrderMethodService{

    @Autowired
    OrderMethodDAO orderMethodDAO;

	@Override
	public List<OrderMethod> findAll() {
		return orderMethodDAO.findAll();
	}

	@Override
	public OrderMethod getById(int id) {
		return orderMethodDAO.getById(id);
	}
}
