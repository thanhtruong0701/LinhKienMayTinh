package com.datn.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.datn.model.Product;
import com.datn.service.ProductService;

import lombok.AllArgsConstructor;

@Controller
public class HomeController {
	@Autowired
	ProductService productService;
	
	@RequestMapping({"/home"})
	public String abc(Model model, @RequestParam("trang") Optional<Integer> trang, @RequestParam("tranglk") Optional<Integer> tranglk) {
		List<Product> list = productService.find8Products();
		model.addAttribute("items", list);
		Pageable pageable = PageRequest.of(trang.orElse(0), 12);
		Pageable pageablelk = PageRequest.of(tranglk.orElse(0), 12);
		Page<Product> all = productService.findPageByProductModelName("Laptop", "Laptop", "", pageable);
		List<Product> asus = productService.findByProductModelName("Asus");
		List<Product> acer = productService.findByProductModelName("Acer");
		List<Product> msi = productService.findByProductModelName("MSI");
		List<Product> lenovo = productService.findByProductModelName("Lenovo");
		List<Product> dell = productService.findByProductModelName("Dell");
		List<Product> hp = productService.findByProductModelName("HP");
		List<Product> gigabyte = productService.findByProductModelName("Gigabyte");
		List<Product> apple = productService.findByProductModelName("Apple");
		Page<Product> linhkien = productService.findPageByProductModelName("Linh kiện", "Linh kiện", "", pageablelk);
		model.addAttribute("all", all);
		model.addAttribute("asus", asus);
		model.addAttribute("acer", acer);
		model.addAttribute("msi", msi);
		model.addAttribute("lenovo", lenovo);
		model.addAttribute("dell", dell);
		model.addAttribute("hp", hp);
		model.addAttribute("gigabyte", gigabyte);
		model.addAttribute("apple", apple);
		model.addAttribute("linhkien", linhkien);
		return "home/home";
	}
	
	@RequestMapping({"/admin", "/admin/index"})
	public String admin() {
		return "redirect:/admin/index.html";
	}
}
