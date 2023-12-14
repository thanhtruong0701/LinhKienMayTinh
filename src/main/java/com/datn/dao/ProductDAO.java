package com.datn.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn.model.Product;

public interface ProductDAO extends JpaRepository<Product, Integer> {
	@Query("SELECT p FROM Product p WHERE p.productModel.modelname LIKE %?1%")
	List<Product> findByProductModelName(String modelName);
	
	@Query(value="SELECT TOP 8 * FROM PRODUCTS ORDER BY Product_price DESC", nativeQuery=true)
	List<Product> find8Products();
	
	@Query("SELECT p FROM Product p WHERE p.productModel.modelname LIKE %?1% AND p.productModel.productType.type_name LIKE %?2% AND p.product_notes LIKE %?3%")
	Page<Product> findPageByProductModelName(String modelName, String typeName, String notes, Pageable pageable);
	
	@Query("SELECT p FROM Product p WHERE (p.product_name LIKE %?1% OR p.product_info LIKE %?2% OR p.product_desc LIKE %?3% OR p.product_notes LIKE %?4%) AND p.productModel.productType.type_name LIKE %?5%")
	Page<Product> search(String keywords1, String keywords2, String keywords3, String keywords4, String typeName, Pageable pageable);
	

	@Query("SELECT p FROM Product p WHERE p.product_price BETWEEN ?1 AND ?2 AND p.productModel.productType.type_name LIKE %?3%")
	Page<Product> filterPrice(Double minPrice, Double maxPrice, String typeName, Pageable pageable);
	
	@Query(value="SELECT TOP 1 * FROM PRODUCTS ORDER BY Product_price DESC", nativeQuery=true)
	Product findTop1Price();
}
