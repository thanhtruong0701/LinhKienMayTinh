package com.datn.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.datn.model.Product;

public interface ProductService {
	Product findById(Integer id);
	List<Product> findAll();
	List<Product> findByProductModelName(String productmodel_name);
	Page<Product> findPageByProductModelName(String productmodel_name, String typeName, String notes, Pageable pageable);
	List<Product> find8Products();
	Page<Product> search(String keywords1, String keywords2, String keywords3, String keywords4, String typeName, Pageable pageable);
	Page<Product> filterPrice(Double minPrice, Double maxPrice, String typeName, Pageable pageable);
	Product findTop1Price();
	Product create(Product product);
	Product update(Product product);
	void delete(Integer id);
}
