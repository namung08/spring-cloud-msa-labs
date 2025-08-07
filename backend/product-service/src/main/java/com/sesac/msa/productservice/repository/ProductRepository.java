package com.sesac.msa.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sesac.msa.productservice.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
