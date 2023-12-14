package com.datn.service;

import java.util.List;

import com.datn.model.ProductBrand;

public interface ProductBrandService {
	List<ProductBrand> findAllBrandLaptop();
	List<ProductBrand> findAllBrandLinhKien();
	List<ProductBrand> findAllBrandManHinh();
	List<ProductBrand> findAll();
}
