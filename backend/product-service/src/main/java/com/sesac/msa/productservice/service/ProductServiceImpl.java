package com.sesac.msa.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sesac.msa.productservice.entity.Product;
import com.sesac.msa.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
	private final ProductRepository repository;

	@Override
	public Product findById(Long id) {
		return repository.findById(id).orElseThrow(
			() -> new RuntimeException("Product not found!: " + id)
		);
	}

	@Override
	public List<Product> findAll() {
		return repository.findAll();
	}

	@Transactional
	@Override
	public void decreaseStock(Long productId, Integer quantity) {
		Product product = repository.findById(productId)
			.orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다: " + productId));

		if (product.getStockQuantity() < quantity) {
			throw new RuntimeException("재고가 부족합니다");
		}

		product.setStockQuantity(product.getStockQuantity() - quantity);
		repository.save(product);

		log.info("재고 차감 완료 - 상품: {}, 남은 재고: {}", product.getName(), product.getStockQuantity());
	}
}
