package com.datn.service;


import java.util.List;

import com.datn.model.OrderDetail;

public interface OrderDetailService {
	public List<OrderDetail> findByOrderID(Long id);
}
