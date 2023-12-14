package com.datn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn.model.OrderDetailGuest;

public interface OrderDetailGuestDAO extends JpaRepository<OrderDetailGuest, Long> {
	@Query("SELECT odg FROM OrderDetailGuest odg WHERE odg.orderGuest.id = ?1")
	List<OrderDetailGuest> findByOrderGuestID(Long id);
}
