package com.datn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn.model.ProductModel;

public interface ProductModelDAO extends JpaRepository<ProductModel, String>  {
	@Query("SELECT pb.brand_name FROM ProductModel pm INNER JOIN pm.productBrand pb WHERE pm.modelname LIKE %?1%")
	List<ProductModel> findAllBrandLaptop(String modelName);
}
