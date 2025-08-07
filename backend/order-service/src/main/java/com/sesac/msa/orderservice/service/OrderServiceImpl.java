package com.sesac.msa.orderservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sesac.msa.orderservice.entity.Order;
import com.sesac.msa.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	private final OrderRepository repository;

	@Override
	public Order findById(Long id) {
		return repository.findById(id).orElseThrow(
			() -> new RuntimeException("Product not found!: " + id)
		);
	}

	@Override
	public List<Order> findAll() {
		return repository.findAll();
	}
}
