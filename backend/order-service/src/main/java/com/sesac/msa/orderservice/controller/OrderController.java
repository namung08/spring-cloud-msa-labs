package com.sesac.msa.orderservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sesac.msa.orderservice.entity.Order;
import com.sesac.msa.orderservice.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService service;

	@GetMapping("/{id}")
	@Operation(
		summary = "주문 단일 조회",
		description = "ID로 주문 정보를 조회"
	)
	public ResponseEntity<Order> getProductById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(service.findById(id));
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("")
	public ResponseEntity<List<Order>> getProducts() {
		return ResponseEntity.ok(service.findAll());
	}
}
