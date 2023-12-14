package com.datn.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.datn.dao.AccountDAO;
import com.datn.model.Account;
import com.datn.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	AccountDAO accountDAO;

	@Override
	public Account findById(String username) {
		return accountDAO.findById(username).get();
	}

	@Override
	public List<Account> findAll() {
		return accountDAO.findAll();
	}

	@Override
	public Account create(Account account) {
		return accountDAO.save(account);
	}

	@Override
	public Account findByUsername(String username) {
		return accountDAO.findByUsername(username);
	}

	@Override
	public Account resetPassword(String username, String email) {
		Account accountExist = accountDAO.findByUsernameAndEmail(username, email);
		if(accountExist != null) {
			String chuoi = "qwertyu!@#$%^&*_iopasdfghjk!@#$%^&*_l1234567890zxcvbnmQW!@#$%^&*_ERTYUIOP1234567890ASDFGHJKLZXCVBNM!@#$%^&*_";
			int len = 6;
			char[] password = new char[len];
			Random random = new Random();
			for(int i=0; i<len; i++) {
				password[i] = chuoi.charAt(random.nextInt(chuoi.length()));
			}
			accountExist.setPassword(new String(password)); // gán password mới
			return accountDAO.save(accountExist); // cập nhật lại thông tin
		}
		return null;
	}
}
