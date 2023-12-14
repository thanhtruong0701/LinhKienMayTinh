package com.datn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn.model.ProductBrand;

public interface ProductBrandDAO extends JpaRepository<ProductBrand, Integer> {
	@Query("SELECT pb FROM ProductBrand pb INNER JOIN pb.productModels pm INNER JOIN pm.productType pt WHERE pt.id = 1")
	List<ProductBrand> findAllBrandLaptop();
	
	@Query("SELECT pb FROM ProductBrand pb INNER JOIN pb.productModels pm INNER JOIN pm.productType pt WHERE pt.id = 2")
	List<ProductBrand> findAllBrandLinhKien();
	
	@Query("SELECT pb FROM ProductBrand pb INNER JOIN pb.productModels pm INNER JOIN pm.productType pt WHERE pt.id = 3")
	List<ProductBrand> findAllBrandManHinh();
}
