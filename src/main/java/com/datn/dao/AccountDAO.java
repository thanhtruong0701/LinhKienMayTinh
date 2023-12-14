package com.datn.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn.model.Account;

public interface AccountDAO extends JpaRepository<Account, String>{
	@Query("SELECT DISTINCT ar.account  FROM Authority ar WHERE ar.role.id IN ('DIRE', 'STAF')")
	List<Account> getAdministrators();
	
	Account findByUsername(String username); // giành cho chức năng đăng nhập
	
	Account findByUsernameAndEmail(String username, String email); // giành cho chức năng resetpassword
}
