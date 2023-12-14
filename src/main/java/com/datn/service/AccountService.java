package com.datn.service;

import java.util.List;

import com.datn.model.Account;

public interface AccountService {
	Account findById(String username);
	
	Account findByUsername(String username); // giành cho chức năng đăng nhập

	List<Account> findAll();
	
	Account create(Account account);

	Account resetPassword(String username, String email);
}