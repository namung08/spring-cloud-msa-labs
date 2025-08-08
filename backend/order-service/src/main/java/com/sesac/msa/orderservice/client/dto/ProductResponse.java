package com.sesac.msa.orderservice.client.dto;

import java.math.BigDecimal;

public record ProductResponse(
	Long id,
	String name,
	BigDecimal price,
	Integer stockQuantity) {
}
