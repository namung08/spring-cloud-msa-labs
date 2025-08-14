package com.sesac.msa.paymentservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sesac.msa.paymentservice.entity.Payment;
import com.sesac.msa.paymentservice.entity.PaymentStatus;
import com.sesac.msa.paymentservice.event.PaymentRequestEvent;
import com.sesac.msa.paymentservice.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
	private final PaymentRepository repository;

	@Transactional
	@Override
	public Payment processPayment(PaymentRequestEvent event) {
		Payment payment = Payment.builder()
			.orderId(event.getOrderId())
			.userId(event.getUserId())
			.amount(event.getTotalAmount())
			.status(PaymentStatus.PENDING)
			.paymentMethod("CARD")
			.build();

		Payment saved = repository.save(payment);
		try {
			Thread.sleep(2000);
			if (Math.random() < 0.3) {
				throw new RuntimeException("잔액 부족");
			}
			saved.setStatus(PaymentStatus.COMPLETED);

		} catch (Exception e) {
			saved.setStatus(PaymentStatus.FAILED);
			saved.setFailureReason(e.getMessage());
		}
		return saved;
	}
}
