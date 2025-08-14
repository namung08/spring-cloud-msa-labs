package com.sesac.msa.paymentservice.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentSagaHandler {
	@RabbitListener(queues = "${order.event.queue.payment-request}")
	public void handlePaymentRequest(PaymentRequestEvent event) {
		log.info("Received PaymentRequestEvent: {}", event);
	}
}
