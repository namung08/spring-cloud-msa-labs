package com.sesac.msa.productservice.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSagaPublisher {
	private final RabbitTemplate rabbitTemplate;

	@Value("${order.event.exchange}")
	private String exchange;

	@Value("${order.event.routing-key.payment-request}")
	private String paymentRequestRoutingKey;

	@Value("${order.event.routing-key.inventory}")
	private String inventoryRoutingKey;

	// 재고 차감 성공 시 -> 결제 요청 이벤트
	public void publishPaymentRequest(PaymentRequestEvent paymentRequestEvent) {
		rabbitTemplate.convertAndSend(exchange, paymentRequestRoutingKey, paymentRequestEvent);
	}

	// 재고 부족 -> 차감 실패 이벤트
	public void publishInventoryFailed(InventoryFailedEvent inventoryFailedEvent) {
		rabbitTemplate.convertAndSend(exchange, inventoryRoutingKey, inventoryFailedEvent);
	}
}
