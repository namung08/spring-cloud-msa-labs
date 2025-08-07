package com.sesac.msa.productservice.service;

import java.util.List;

import com.sesac.msa.productservice.entity.Product;

public interface ProductService {
	Product findById(Long id);

	List<Product> findAll();
}
