package com.sesac.msa.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sesac.msa.orderservice.client.dto.ProductResponse;

@FeignClient(name = "product-service")
public interface  ProductServiceClient {
	String BASE_URL = "/api/v1/products";
	@GetMapping(BASE_URL+"/{id}")
	ProductResponse getProductById(@PathVariable Long id);
}
