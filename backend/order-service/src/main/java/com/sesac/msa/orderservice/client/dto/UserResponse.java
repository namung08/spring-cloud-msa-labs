package com.sesac.msa.orderservice.client.dto;

import lombok.Builder;

@Builder
public record UserResponse(
	Long id,
	String email,
	String name) {
}
