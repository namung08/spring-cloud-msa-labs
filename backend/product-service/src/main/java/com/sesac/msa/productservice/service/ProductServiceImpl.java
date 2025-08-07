package com.sesac.msa.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sesac.msa.productservice.entity.Product;
import com.sesac.msa.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductRepository repository;

	@Override
	public Product findById(Long id) {
		return repository.findById(id).orElseThrow(
			() -> new RuntimeException("Product not found!: " + id)
		);
	}

	@Override
	public List<Product> findAll() {
		return repository.findAll();
	}
}
