package com.datn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String product_name;
	Double product_price;
	String product_gifts;
	String product_benefits;
	String product_info;
	String product_img1;
	String product_img2;
	String product_img3;
	String product_img4;
	String product_desc;
	Integer product_quantity;
	String product_notes;
	Boolean product_active;
	@ManyToOne
	@JoinColumn(name = "Productmodel_name")
	ProductModel productModel;
}
