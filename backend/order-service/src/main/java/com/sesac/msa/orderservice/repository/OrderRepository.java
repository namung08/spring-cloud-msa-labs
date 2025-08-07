package com.sesac.msa.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sesac.msa.orderservice.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
