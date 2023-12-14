package com.datn.service;


import java.util.List;

import com.datn.model.OrderDetailGuest;


public interface OrderDetailGuestService {
	public List<OrderDetailGuest> findByOrderGuestID(Long id);
}
