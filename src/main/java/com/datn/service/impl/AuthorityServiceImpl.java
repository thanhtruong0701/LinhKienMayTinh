package com.datn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datn.dao.AuthorityDAO;
import com.datn.model.Authority;
import com.datn.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService{
	@Autowired
	AuthorityDAO authorityDAO;
	
	@Override
	public Authority create(Authority authority) {
		return authorityDAO.save(authority);
	}

}
