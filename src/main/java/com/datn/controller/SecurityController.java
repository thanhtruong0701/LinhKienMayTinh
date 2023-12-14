package com.datn.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.datn.model.Account;
import com.datn.model.Authority;
import com.datn.model.Role;
import com.datn.service.AccountService;
import com.datn.service.AuthorityService;
import com.datn.service.MailerService;
import com.datn.service.RoleService;

@Controller
public class SecurityController {
	public static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
	public Path srcPath = Paths.get("src");
	public Path mainPath = Paths.get("main");
	public Path resourcePath = Paths.get("resources");
	public Path staticPath = Paths.get("static");
	public Path imagePath = Paths.get("images");
	public Path avtPath = Paths.get("avt");
	@Autowired
	AccountService accountService;
	@Autowired
	AuthorityService authorityService;
	@Autowired
	RoleService roleService;
	@Autowired
	ServletContext context;
	@Autowired
	HttpServletRequest request;
	@Autowired
	MailerService mailer;
	
	@RequestMapping("/login")
	public String loginForm(Model model) {
		model.addAttribute("message", "Vui lòng đăng nhập!");
		return "security/login";
	}
	
	@RequestMapping("/login/success")
	public String loginSuccess(Model model) {
		model.addAttribute("message", "Đăng nhập thành công!");
		return "security/login";
	}
	
	@RequestMapping("/login/error")
	public String loginError(Model model) {
		model.addAttribute("message", "Sai thông tin đăng nhập!");
		return "security/login";
	}
	
	@RequestMapping("/security/unauthoried")
	public String unauthoried(Model model) {
		model.addAttribute("message", "Không có quyền truy xuất!");
		return "security/login";
	}
	
	@RequestMapping("/logoff/success")
	public String logoffSuccess(Model model) {
		model.addAttribute("message", "Bạn đã đăng xuất!");
		return "security/login";
	}
	
	@CrossOrigin("*")
	@ResponseBody
	@RequestMapping("/rest/security/authentication")
	public Object getAuthentication(HttpSession session) {
		return session.getAttribute("authentication");
	}
	
	// REGISTER
	@GetMapping("/register")
	public String registerForm(Model model) {
		return "security/register";
	}
	
	@PostMapping("/register")
	public String register(Model model,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("fullname") String fullname,
			@RequestParam("email") String email,
			@RequestParam("photo") MultipartFile photo) {
		try {
			Account account = new Account();
			account.setUsername(username);
			account.setPassword(password);
			account.setFullname(fullname);
			account.setEmail(email);
			account.setPhoto(photo.getOriginalFilename());
			account.setActivated(true);
			Account accountR = accountService.findByUsername(username);
			Role role = roleService.findById("CUST"); // lấy role có id CUST
			Authority authority = new Authority();
			authority.setAccount(account);
			authority.setRole(role);
			if(accountR != null) {
				model.addAttribute("messR", "Tạo tài khoản thất bại, tên đăng nhập tồn tại !");
				return "security/register";
			}else {
				accountService.create(account); // tạo account mới
				authorityService.create(authority); // tạo authority mới dựa trên account và role đã có
				File dir = new File(CURRENT_FOLDER.resolve(srcPath).resolve(mainPath).resolve(resourcePath).resolve(staticPath).resolve(imagePath).resolve(avtPath).toString());
				if(!dir.exists()) {
					dir.mkdirs();
				}
				File savedFile = new File(dir, photo.getOriginalFilename());
				photo.transferTo(savedFile); // tạo ảnh trong folder
				mailer.sendEmail(account, "welcome"); // gửi mail
				model.addAttribute("messR", "Tạo tài khoản thành công ! Chúng tôi đã gửi tên đăng nhập & mật khẩu vào email của bạn");
				
			}
		} catch (Exception e) {
			model.addAttribute("messR", "Tạo tài khoản thất bại ! \n" +e.getMessage());
		}
		return "security/register";
	}
	
	
	// CHANGE PASSWORD
	@GetMapping("/changePassword")
	public String changePasswordForm() {
		return "security/changePassowrd";
	}
	
	@PostMapping("/changePassword")
	public String changePassword(Model model, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("newpassword") String newpassword, @RequestParam("confirmpassword") String confirmpassword) {
		try {
			Account currentAccount = accountService.findById(username); // lấy account hiện tại - (tìm account theo username)
			if(!currentAccount.getPassword().equals(password)) { // nếu mật khẩu của account hiện tại != mật khẩu nhập
				model.addAttribute("messC", "Sai mật khẩu hiện tại!"); // báo lỗi
			}else {
				if(!newpassword.equals(confirmpassword)) { // nếu field password mới != field nhập lại password mới
					model.addAttribute("messC", "Mật khẩu xác nhận không khớp !"); // báo lỗi
				}else { // ngược lại nếu nhập đúng
					currentAccount.setPassword(newpassword); // setpassword mới
					accountService.create(currentAccount); // lưu
					model.addAttribute("messC", "Đổi mật khẩu thành công");
				}
			}
		} catch (Exception e) {
			model.addAttribute("messC", "Tài khoản không tồn tại! "+e.getMessage()); // nếu username không tồn tại thì thông báo...
		}
		return "security/changePassowrd";
	}
	
	// PROFILE
	@GetMapping("/profile")
	public String profileform(Model model) {
		model.addAttribute("accountLogin", accountService.findByUsername(request.getRemoteUser()));
		return "security/profile";
	}
	
	@PostMapping("/editProfile")
	public String profile(Model model, @RequestParam("photo") MultipartFile photo,
		@RequestParam("fullname") String fullname, @RequestParam("email") String email, @RequestParam("username") String username) {
		try {
			Account account = accountService.findByUsername(username);
			account.setFullname(fullname);
			account.setEmail(email);
			if(photo.getOriginalFilename().equals("")) {
				account.setPhoto(account.getPhoto());
			}else {
				account.setPhoto(photo.getOriginalFilename());
			}
			accountService.create(account);
			File dir = new File(CURRENT_FOLDER.resolve(srcPath).resolve(mainPath).resolve(resourcePath).resolve(staticPath).resolve(imagePath).resolve(avtPath).toString());
			if(!dir.exists()) {
				dir.mkdirs();
			}
			File savedFile = new File(dir, photo.getOriginalFilename());
			photo.transferTo(savedFile); // tạo ảnh trong folder
			model.addAttribute("accountLogin", accountService.findByUsername(username));
		} catch (Exception e) {
			model.addAttribute("messP", e.getMessage());
		}
		return "redirect:/profile";
	}
	
	
	// FORGOT PASSWORD - SEND EMAIL
	@GetMapping("/forgotPassword")
	public String forgotForm(Model model) {
		return "security/forgotPassword";
	}
	
	@PostMapping("/forgotPassword")
	public String forgot(Model model, @RequestParam("username") String username, @RequestParam("email") String email) throws MessagingException {
		Account account = accountService.resetPassword(username, email); // đã xử lí randompass
		if(account != null) { // nếu tài khoản có username & email đó có tồn tại
			mailer.sendEmail(account, "forgot_Pass"); // forgot_pass (tên đặt bên mailerserviceimpl)
			model.addAttribute("messF", "Hãy Kiểm Tra Email, Chúng Tôi Đã Gửi Mật Khẩu Mới !");
		}else {
			model.addAttribute("messF", "Username & Email Không Hợp Lệ !");
		}
		return "security/forgotPassword";
	}
}
