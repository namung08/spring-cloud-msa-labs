package com.sesac.msa.paymentservice.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.sesac.msa.paymentservice.entity.Payment;
import com.sesac.msa.paymentservice.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentSagaHandler {
	private final PaymentService service;
	private final PaymentSagaPublisher sagaPublisher;

	@RabbitListener(queues = "${order.event.queue.payment-request}")
	public void handlePaymentRequest(PaymentRequestEvent event) {
		log.info("Received PaymentRequestEvent: {}", event);
		/*
		 * 결제 시도
		 * 분기
		 * 1. 결제 성공
		 * 결제 성공 이벤트 발행
		 * 2. 결제 실패
		 * 결제 실패 이벤트 발행
		 * */
		Payment payment = null;
		try {
			payment = service.processPayment(event);
			PaymentCompletedEvent completedEvent = PaymentCompletedEvent.builder()
				.orderId(event.getOrderId())
				.userId(event.getUserId())
				.amount(event.getTotalAmount())
				.build();

			sagaPublisher.publishPaymentCompleted(completedEvent);
		} catch (Exception e) {
			PaymentFailedEvent failedEvent = PaymentFailedEvent.builder()
				.orderId(event.getOrderId())
				.userId(event.getUserId())
				.productid(event.getProductId())
				.quantity(event.getQuantity())
				.reason(e.getMessage())
				.build();
			sagaPublisher.publishPaymentFailed(failedEvent);

		}
	}
}
