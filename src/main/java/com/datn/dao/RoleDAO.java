package com.datn.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import com.datn.model.Role;

public interface RoleDAO extends JpaRepository<Role, String> { }
