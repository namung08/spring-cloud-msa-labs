package com.sesac.msa.orderservice.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sesac.msa.orderservice.client.ProductServiceClient;
import com.sesac.msa.orderservice.client.UserServiceClient;
import com.sesac.msa.orderservice.client.dto.ProductResponse;
import com.sesac.msa.orderservice.client.dto.UserResponse;
import com.sesac.msa.orderservice.dto.request.OrderRequest;
import com.sesac.msa.orderservice.entity.Order;
import com.sesac.msa.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	private final OrderRepository repository;
	private final UserServiceClient userServiceClient;
	private final ProductServiceClient productServiceClient;

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

	@Override
	public Order createOrder(OrderRequest order) {
		UserResponse user = userServiceClient.getUserById(order.userId());
		ProductResponse product = productServiceClient.getProductById(order.productId());
		notFount(user, "User Not Found");
		notFount(product, "Product Not Found");

		if(product.stockQuantity() < order.quantity()) {
			throw new RuntimeException("Out of Stock!");
		}

		Order orderEntity = Order.builder()
			.userId(user.id())
			.totalAmount(product.price().multiply(BigDecimal.valueOf(order.quantity())))
			.status("COMPLETED")
			.build();

		return repository.save(orderEntity);
	}

	private void notFount(Object object, String message) {
		if(object == null) throw new RuntimeException(message);
	}
}
