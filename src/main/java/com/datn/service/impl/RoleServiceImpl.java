package com.datn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datn.dao.RoleDAO;
import com.datn.model.Role;
import com.datn.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	RoleDAO roleDAO;
	
	@Override
	public Role findById(String id) {
		return roleDAO.findById(id).get();
	}
	
}
