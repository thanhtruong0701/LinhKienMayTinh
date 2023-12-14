package com.datn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.datn.model.Product;
import com.datn.service.ProductService;
import com.datn.dao.ProductDAO;
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductDAO productDAO;
	
	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return productDAO.findAll();
	}

	@Override
	public List<Product> findByProductModelName(String productmodel_name) {
		// TODO Auto-generated method stub
		return productDAO.findByProductModelName(productmodel_name);
	}

	@Override
	public List<Product> find8Products() {
		// TODO Auto-generated method stub
		return productDAO.find8Products();
	}

	@Override
	public Page<Product> findPageByProductModelName(String productmodel_name, String typeName, String notes, Pageable pageable) {
		// TODO Auto-generated method stub
		return productDAO.findPageByProductModelName(productmodel_name, typeName, notes, pageable);
	}

	@Override
	public Page<Product> search(String keywords1, String keywords2, String keywords3, String keywords4, String typeName, Pageable pageable) {
		// TODO Auto-generated method stub
		return productDAO.search(keywords1, keywords2, keywords3, keywords4, typeName, pageable);
	}

	@Override
	public Product findById(Integer id) {
		// TODO Auto-generated method stub
		return productDAO.findById(id).get();
	}

	@Override
	public Page<Product> filterPrice(Double minPrice, Double maxPrice, String typeName, Pageable pageable) {
		// TODO Auto-generated method stub
		return productDAO.filterPrice(minPrice, maxPrice, typeName, pageable);
	}

	@Override
	public Product findTop1Price() {
		// TODO Auto-generated method stub
		return productDAO.findTop1Price();
	}
	
	@Override
	public Product create(Product product) {
		// TODO Auto-generated method stub
		return productDAO.save(product);
	}

	@Override
	public Product update(Product product) {
		
		return productDAO.save(product);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		productDAO.deleteById(id);
	}

}
