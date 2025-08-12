package com.sesac.msa.userservice.dto;

import lombok.Builder;

@Builder
public record LoginResponse(
	String token,
	String type,
	Long userId,
	String email,
	String name
) {
	public LoginResponse {
		if (type == null || type.isBlank()) {
			type = "Bearer";
		}
	}
}
