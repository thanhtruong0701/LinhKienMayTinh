package com.datn;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.datn.service.LoginService;

@Configuration
@EnableWebSecurity // cần thiết
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter { // file cấu hình (phải có mã framework security trong pom.xml)
	@Autowired
	LoginService loginService;
	

    /* Cơ Chế Mã Hóa Mật Khẩu */
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/* Quản Lý Dữ Liệu Người Sử Dụng */
    /* Cung cấp dữ liệu đăng nhập */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { // AuthenticationManagerBuilder: auth là nơi chứa dữ liệu người dùng
		auth.userDetailsService(loginService);
	}
	
	/* Phân Quyền Sử Dụng Và Hình Thức Đăng Nhập */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// CSRF, CORS DISABLE
		httpSecurity.csrf().disable().cors().disable(); // vô hiệu hóa không cho truy cập từ bên ngoài
		
		// Phân quyền sử dụng
		httpSecurity.authorizeRequests()
			.antMatchers("/order/**", "/changePassword", "/profile").authenticated() // trang /order/**, changePassword phải đăng nhập
			.antMatchers("/admin/**").hasAnyRole("STAF","DIRE") // trang /admin/** thì phải STAF Hoặc DIRE mới vào được
			.antMatchers("/rest/authorities").hasRole("DIRE") // trang phân quyền thì chỉ có DIRE mới được vào
			.anyRequest().permitAll(); // các trang còn lại thì cho phép vào không cần đăng nhập 
		
		// Giao diện đăng nhập tự tạo
		httpSecurity.formLogin()
			.loginPage("/login") // địa chỉ request dẫn đến form đăng nhập
			.loginProcessingUrl("/login/check") // địa chỉ POST mà form login submit đến (action)
			.defaultSuccessUrl("/home", false) // khi đăng nhập thành công sẽ chuyển đến địa chỉ này hoặc false: không
			.failureUrl("/login/error") // nếu lỗi thì chuyển sang error
			.usernameParameter("username") // tên của thẻ input bên html
			.passwordParameter("password"); // tên của thẻ input passowrd bên html
		
		httpSecurity.rememberMe()
			.tokenValiditySeconds(86400) // lưu 86400 giây
			.rememberMeParameter("remember"); // tên của thẻ input checkbox bên html (mặc định nếu ko đặt là remember-me)
		
		httpSecurity.exceptionHandling() // nếu đăng nhập rồi nhưng cố tình truy xuất đến các địa chỉ chưa được cấp quyền
			.accessDeniedPage("/security/unauthoried"); // chuyển đến địa chỉ unauthoried
		
		httpSecurity.logout()
			.logoutUrl("/logoff")
			.logoutSuccessUrl("/logoff/success");
	}
	
	/* Cho phép truy xuất REST API từ bên ngoài (domain khác) */
	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}
}
