package com.datn.controller;


import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.datn.model.Product;
import com.datn.model.ProductBrand;
import com.datn.service.ProductBrandService;
import com.datn.service.ProductService;
import com.datn.service.ProductTypeService;
import com.datn.service.SessionService;


@Controller
public class ShopController {
	@Autowired
	ProductService productService;
	@Autowired
	ProductBrandService productBrandService;
	@Autowired
	ProductTypeService productTypeService;
	@Autowired
	SessionService session;
	
	@RequestMapping("/shop/laptop")
	public String shop_laptop(Model model, @RequestParam("trang") Optional<Integer> trang, @RequestParam("modelName") Optional<String> modelName, @RequestParam("typeName") Optional<String> typeName, @RequestParam("pNotes") Optional<String> pNotes) {
		String typeNameString = typeName.orElse("Laptop");
		String pNoteString = pNotes.orElse("");
		model.addAttribute("typeName", typeNameString);
		model.addAttribute("notes", pNoteString);
		Pageable pageable = PageRequest.of(trang.orElse(0), 9);
		Page<Product> laptop = productService.findPageByProductModelName(modelName.orElse("Laptop"), typeNameString, pNoteString, pageable);
		model.addAttribute("laptop", laptop);
		List<ProductBrand> productBrands = productBrandService.findAllBrandLaptop();
		model.addAttribute("productBrands", productBrands);
		model.addAttribute("modelName", modelName.orElse("Laptop"));
		return "products/shop_laptop";
	}
	
	// CHI TIẾT LAPTOP
	@RequestMapping("/shop/laptop/{id}")
	public String laptop_detail(Model model, @PathVariable("id") Integer id) {
		Product product = productService.findById(id);
		model.addAttribute("product", product);
		return "products/single-product";
	}
	
	@RequestMapping("/shop/linhkien")
	public String shop_linhkien(Model model, @RequestParam("trang") Optional<Integer> trang, @RequestParam("modelName") Optional<String> modelName, @RequestParam("typeName") Optional<String> typeName, @RequestParam("pNotes") Optional<String> pNotes) {
		String typeNameString = typeName.orElse("Linh kiện");
		String pNoteString = pNotes.orElse("");
		model.addAttribute("typeName", typeNameString);
		model.addAttribute("notes", pNoteString);
		Pageable pageable = PageRequest.of(trang.orElse(0), 9);
		Page<Product> linhkien = productService.findPageByProductModelName(modelName.orElse("Linh kiện"), typeNameString, pNoteString, pageable);
		model.addAttribute("linhkien", linhkien);
		List<ProductBrand> productBrands = productBrandService.findAllBrandLinhKien();
		model.addAttribute("productBrands", productBrands);
		model.addAttribute("modelName", modelName.orElse("Linh kiện"));
		return "products/shop_linhkien";
	}
	// CHI TIẾT LINHKIEN
	@RequestMapping("/shop/linhkien/{id}")
	public String linhkien_detail(Model model, @PathVariable("id") Integer id) {
		Product product = productService.findById(id);
		model.addAttribute("product", product);
		return "products/single-product";
	}
	
	@RequestMapping("/shop/manhinh")
	public String shop_manhinh(Model model, @RequestParam("trang") Optional<Integer> trang, @RequestParam("modelName") Optional<String> modelName, @RequestParam("typeName") Optional<String> typeName, @RequestParam("pNotes") Optional<String> pNotes) {
		String typeNameString = typeName.orElse("Màn hình"); // nếu typename trống thì gán = màn hình
		String pNoteString = pNotes.orElse("");
		model.addAttribute("typeName", typeNameString);
		model.addAttribute("notes", pNoteString);
		Pageable pageable = PageRequest.of(trang.orElse(0), 9);
		Page<Product> manhinh = productService.findPageByProductModelName(modelName.orElse("Màn hình"), typeNameString, pNoteString, pageable);
		model.addAttribute("manhinh", manhinh);
		List<ProductBrand> productBrands = productBrandService.findAllBrandManHinh();
		model.addAttribute("productBrands", productBrands);
		model.addAttribute("modelName", modelName.orElse("Màn hình"));
		return "products/shop_manhinh";
	}
	// CHI TIẾT MANHINH
	@RequestMapping("/shop/manhinh/{id}")
	public String manhinh_detail(Model model, @PathVariable("id") Integer id) {
		Product product = productService.findById(id);
		model.addAttribute("product", product);
		return "products/single-product";
	}
	
	@RequestMapping("/shop/search")
	public String search(Model model, @RequestParam("keywords") Optional<String> keyword, @RequestParam("typeName") Optional<String> typeName, @RequestParam("trang") Optional<Integer> trang) {
		String keywords = keyword.orElse(""); // nếu keyword ban đầu chưa có thì gán = rỗng
		String typeNameString = typeName.orElse("");
		model.addAttribute("keywords", keywords);
		model.addAttribute("typeName", typeNameString);
		model.addAttribute("productTypes", productTypeService.findAll()); // TẤT CẢ CÁC TYPENAME
		Pageable pageable = PageRequest.of(trang.orElse(0), 9);
		Page<Product> searchProducts = productService.search(keywords, keywords, keywords, keywords, typeNameString, pageable);
		model.addAttribute("searchProducts", searchProducts);
		return "products/shop";
	}
	
	@RequestMapping("/shop/filter")
	public String filter(Model model, @RequestParam("minPrice") Optional<Double> minPrice, @RequestParam("maxPrice") Optional<Double> maxPrice, @RequestParam("typeName") Optional<String> typeName, @RequestParam("trang") Optional<Integer> trang) {
		Product productTop1Price = productService.findTop1Price(); // sp có giá cao nhất
		Double minPriceDouble = minPrice.orElse(0.0); // giá thấp nhất nếu ko có giá trị gán vào thì sẽ = 0.0;
		Double maxPriceDouble = maxPrice.orElse(productTop1Price.getProduct_price()); //nếu ko có giá trị gán vào thì sẽ lấy giá sp cao nhất
		String typeNameString = typeName.orElse("");
		model.addAttribute("minPrice", minPriceDouble);
		model.addAttribute("maxPrice", maxPriceDouble);
		model.addAttribute("typeName", typeNameString);
		model.addAttribute("productTypes", productTypeService.findAll());
		model.addAttribute("trang", trang.orElse(0));
		Pageable pageable = PageRequest.of(trang.orElse(0), 9);
		Page<Product> filterPrice = productService.filterPrice(minPriceDouble, maxPriceDouble, typeNameString , pageable);
		model.addAttribute("searchProducts", filterPrice);
		return "products/shop";
	}
}
