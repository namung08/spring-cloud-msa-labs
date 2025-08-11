package com.sesac.msa.orderservice.client.dto;

public record UserResponse(
	Long id,
	String email,
	String name) {
}
