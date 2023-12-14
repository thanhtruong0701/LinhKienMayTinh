package com.datn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datn.dao.OrderDetailDAO;
import com.datn.model.OrderDetail;
import com.datn.service.OrderDetailService;
@Service
public class OrderDetailServiceImpl implements OrderDetailService{
	@Autowired
	OrderDetailDAO orderDetailDAO;
	
	@Override
	public List<OrderDetail> findByOrderID(Long id) {
		return orderDetailDAO.findByOrderID(id);
	}

}
