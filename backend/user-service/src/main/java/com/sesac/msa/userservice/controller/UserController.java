package com.sesac.msa.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sesac.msa.userservice.dto.LoginRequest;
import com.sesac.msa.userservice.dto.LoginResponse;
import com.sesac.msa.userservice.entity.User;
import com.sesac.msa.userservice.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService service;

	@GetMapping("/{id}")
	@Operation(
		summary = "사용자 단일 조회",
		description = "ID로 사용자 정보를 조회"
	)
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(service.findById(id));
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/login")
	@Operation(summary = "로그인", description = "이메일과 패스워드로 로그인하고 JWT 토큰을 발급받습니다")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		try {
			return ResponseEntity.ok(service.login(loginRequest));
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
