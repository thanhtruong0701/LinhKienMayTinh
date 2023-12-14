package com.datn.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn.model.OrderGuest;

public interface OrderGuestDAO extends JpaRepository<OrderGuest, Long> {
	@Query("SELECT og FROM OrderGuest og WHERE og.phonenumber = ?1 AND og.address = ?2 ORDER BY og.id DESC")
	List<OrderGuest> findByPhonenumberAndAddress(String phonenumber, String address);
	
	@Query("SELECT og FROM OrderGuest og ORDER BY og.id DESC")
	List<OrderGuest> findAllIdDESC();
}
