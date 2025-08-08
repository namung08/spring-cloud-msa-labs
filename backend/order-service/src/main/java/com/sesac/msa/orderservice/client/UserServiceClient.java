package com.sesac.msa.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sesac.msa.orderservice.client.dto.UserResponse;

@FeignClient(name = "user-service")
@RequestMapping("/api/v1/users")
public interface UserServiceClient {
	@GetMapping("/{id}")
	UserResponse getUserById(@PathVariable Long id);
}
