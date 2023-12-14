package com.datn.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datn.model.ProductBrand;
import com.datn.model.ProductModel;
import com.datn.model.ProductType;
import com.datn.service.ProductBrandService;
import com.datn.service.ProductModelService;
import com.datn.service.ProductTypeService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest")
public class CategoryRestController {
	@Autowired
	ProductTypeService productTypeService;
	@Autowired
	ProductModelService productModelService;
	@Autowired
	ProductBrandService productBrandService;
	
	@GetMapping("categories")
	public List<ProductType> getAllCategory(){
		return productTypeService.findAll();
	}
	
	@GetMapping("models")
	public List<ProductModel> getAllModel(){
		return productModelService.findAll();
	}
	
	@GetMapping("brands")
	public List<ProductBrand> getAllBrand(){
		return productBrandService.findAll();
	}
	
}
