package com.datn.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datn.dao.OrderDetailGuestDAO;
import com.datn.model.OrderDetailGuest;
import com.datn.service.OrderDetailGuestService;
@Service
public class OrderDetailGuestServiceImpl implements OrderDetailGuestService{
	@Autowired
	OrderDetailGuestDAO orderDetailGuestDAO;

	@Override
	public List<OrderDetailGuest> findByOrderGuestID(Long id) {
		return orderDetailGuestDAO.findByOrderGuestID(id);
	}


}
