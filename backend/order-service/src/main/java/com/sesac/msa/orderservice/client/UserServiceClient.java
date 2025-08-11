package com.sesac.msa.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sesac.msa.orderservice.client.dto.UserResponse;

@FeignClient(name = "user-service")
public interface UserServiceClient {
	String BASE_URL = "/api/v1/users";
	@GetMapping(BASE_URL+"/{id}")
	UserResponse getUserById(@PathVariable Long id);
}
