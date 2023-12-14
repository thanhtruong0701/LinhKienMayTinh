package com.datn.service;

import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.datn.model.Account;

@Service
public class LoginService implements UserDetailsService {
	@Autowired
	AccountService accountService;
	
	@Autowired
	BCryptPasswordEncoder pe;
	
	/* Cung cấp dữ liệu đăng nhập */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Account account = accountService.findById(username);
			String password = account.getPassword();
			String[] roles = account.getAuthorities().stream()
						.map(t -> t.getRole().getId())
						.collect(Collectors.toList()).toArray(new String[0]);
			return User.withUsername(username)
					.password(pe.encode(password))
					.roles(roles).build();
		} catch (Exception e) {
			throw new UsernameNotFoundException(username + " Not Found !");
		}
	}
	
}
