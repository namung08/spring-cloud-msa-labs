package com.sesac.msa.orderservice.dto.request;

public record OrderRequest(
	Long id,
	Long userId,
	Integer quantity
) {
}
