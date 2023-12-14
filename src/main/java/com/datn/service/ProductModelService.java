package com.datn.service;

import java.util.List;

import com.datn.model.ProductModel;

public interface ProductModelService {
	List<ProductModel> findAllBrandLaptop(String modelName);
	List<ProductModel> findAll();
}
