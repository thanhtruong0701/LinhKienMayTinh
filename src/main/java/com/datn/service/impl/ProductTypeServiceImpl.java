package com.datn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datn.dao.ProductTypeDAO;
import com.datn.model.ProductType;
import com.datn.service.ProductTypeService;
@Service
public class ProductTypeServiceImpl implements ProductTypeService {
	@Autowired
	ProductTypeDAO productTypeDAO;

	@Override
	public List<ProductType> findAll() {
		// TODO Auto-generated method stub
		return productTypeDAO.findAll();
	}
}
