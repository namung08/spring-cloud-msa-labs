package com.sesac.msa.orderservice.service;

import java.util.List;

import com.sesac.msa.orderservice.dto.request.OrderRequest;
import com.sesac.msa.orderservice.entity.Order;

public interface OrderService {
	Order findById(Long id);

	List<Order> findAll();

	Order createOrder(OrderRequest order);
}
