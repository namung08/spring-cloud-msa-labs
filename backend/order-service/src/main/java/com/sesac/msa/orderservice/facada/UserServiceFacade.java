package com.sesac.msa.orderservice.facada;

import org.springframework.stereotype.Service;

import com.sesac.msa.orderservice.client.UserServiceClient;
import com.sesac.msa.orderservice.client.dto.UserResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceFacade {

	private final UserServiceClient userServiceClient;

	@CircuitBreaker(name = "user-service", fallbackMethod = "getUserFallback")
	@Retry(name = "user-service")
	public UserResponse getUserWithFallback(Long userId) {
		log.info("User service 호출 시도 - userId = {}", userId);
		return userServiceClient.getUserById(userId);
	}

	// Fallback 메서드: User Service 장애 시 기본 사용자 정보 반환
	public UserResponse getUserFallback(Long userId, Throwable ex) {

		log.warn("User Service 장애 감지! Fallback 실행 - userId: {}, 에러: {}", userId, ex.getMessage());

		return UserResponse.builder()
			.id(userId)
			.name("defaultUser")
			.email("defaultUser@example.com")
			.build();
	}
}
