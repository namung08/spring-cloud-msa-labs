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
import com.sesac.msa.orderservice.facada.UserServiceFacade;
import com.sesac.msa.orderservice.repository.OrderRepository;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	private final OrderRepository repository;
	private final UserServiceClient userServiceClient;
	private final UserServiceFacade userServiceFacade;
	private final ProductServiceClient productServiceClient;
	private final Tracer tracer;

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
		// span 이름과 같은 것을 설정
		Span span = tracer.nextSpan()
			.name("Create Order")
			.tag("order.userId", order.userId())
			.tag("order.productId", order.productId())
			.tag("order.quantity", order.quantity())
			.start();
		// span 활성화
		try (Tracer.SpanInScope sis = tracer.withSpan(span)) {
			UserResponse user = userServiceFacade.getUserWithFallback(order.userId());
			ProductResponse product = productServiceClient.getProductById(order.productId());
			notFount(user, "User Not Found");
			notFount(product, "Product Not Found");

			if (product.stockQuantity() < order.quantity()) {
				throw new RuntimeException("Out of Stock!");
			}

			Order orderEntity = Order.builder()
				.userId(user.id())
				.totalAmount(product.price().multiply(BigDecimal.valueOf(order.quantity())))
				.status("COMPLETED")
				.build();

			return repository.save(orderEntity);
		} catch (Exception e) {
			span.tag("error", e.getMessage());
			throw e;
		} finally {
			span.end();
		}
	}

	@Override
	public List<Order> getOrdersMyUserId(Long userId) {
		return repository.findByUserIdOrderByCreatedAtDesc(userId);
	}

	private void notFount(Object object, String message) {
		if(object == null) throw new RuntimeException(message);
	}
}
