package com.sesac.msa.productservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sesac.msa.productservice.entity.Product;
import com.sesac.msa.productservice.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService service;

	@GetMapping("/{id}")
	@Operation(
		summary = "상품 단일 조회",
		description = "ID로 상품 정보를 조회"
	)
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(service.findById(id));
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("")
	public ResponseEntity<List<Product>> getProducts() {
		return ResponseEntity.ok(service.findAll());
	}
}
