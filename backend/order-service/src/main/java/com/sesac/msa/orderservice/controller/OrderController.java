package com.sesac.msa.orderservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sesac.msa.orderservice.dto.request.OrderRequest;
import com.sesac.msa.orderservice.entity.Order;
import com.sesac.msa.orderservice.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
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

	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
		try {
			Order order = service.createOrder(orderRequest);

			return ResponseEntity.status(HttpStatus.CREATED).body(order);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/my")
	@Operation(summary = "내 주문 목록", description = "로그인한 사용자의 주문 목록을 조회")
	public ResponseEntity<List<Order>> getMyOrders(HttpServletRequest request) {
		// api gateway 에서 전달한 x-user-id 헤더에서 사용자 id 추출
		String header = request.getHeader("X-User-Id");
		if (header == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Long userId = Long.valueOf(header);

		List<Order> orders = service.getOrdersMyUserId(userId);
		return ResponseEntity.ok(orders);
	}
}
