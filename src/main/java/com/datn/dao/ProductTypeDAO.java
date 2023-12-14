package com.datn.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datn.model.ProductType;

public interface ProductTypeDAO extends JpaRepository<ProductType, Integer>  {

}
