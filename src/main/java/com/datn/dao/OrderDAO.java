package com.datn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn.model.Order;

public interface OrderDAO extends JpaRepository<Order, Long>{
	@Query("SELECT o FROM Order o WHERE o.account.username=?1 ORDER BY o.id DESC")
	List<Order> findByUsername(String username);
	
	Order findFirstByOrderByIdDesc(); // tìm order có id mới nhất
	
	@Query("SELECT o FROM Order o ORDER BY o.id DESC")
	List<Order> findAllIdDESC();
}
