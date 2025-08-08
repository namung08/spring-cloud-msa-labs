package com.sesac.msa.orderservice.dto.request;

public record OrderRequest(
	Long productId,
	Long userId,
	Integer quantity
) {
}
