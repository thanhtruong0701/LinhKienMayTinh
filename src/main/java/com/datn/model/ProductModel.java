package com.datn.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product_Models")
public class ProductModel {
	@Id
	String modelname;
	
	@JsonIgnore
	@OneToMany(mappedBy = "productModel", fetch = FetchType.EAGER)
	List<Product> products;
	
	@ManyToOne
	@JoinColumn(name = "Producttype_id")
	ProductType productType;
	
	@ManyToOne
	@JoinColumn(name = "Productbrand_id")
	ProductBrand productBrand;
	
}
