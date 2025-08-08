package com.sesac.msa.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sesac.msa.orderservice.client.dto.ProductResponse;

@FeignClient(name = "product-service")
@RequestMapping("/api/v1/products")
public interface  ProductServiceClient {
	@GetMapping("/{id}")
	ProductResponse getProductById(@PathVariable Long id);
}
