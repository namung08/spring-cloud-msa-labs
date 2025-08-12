package com.sesac.msa.userservice.dto;

public record LoginRequest(
	String email,
	String password
) {
}
