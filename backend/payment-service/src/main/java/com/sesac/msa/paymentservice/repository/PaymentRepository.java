package com.sesac.msa.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sesac.msa.paymentservice.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
