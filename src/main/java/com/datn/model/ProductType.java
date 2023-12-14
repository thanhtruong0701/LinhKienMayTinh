package com.datn.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "Product_Types")
public class ProductType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String type_name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "productType", fetch = FetchType.EAGER)
	List<ProductModel> productModels;
}
