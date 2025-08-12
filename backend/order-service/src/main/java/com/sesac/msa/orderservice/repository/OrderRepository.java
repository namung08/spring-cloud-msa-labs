package com.sesac.msa.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sesac.msa.orderservice.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByUserId(Long userId);

	List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
}
