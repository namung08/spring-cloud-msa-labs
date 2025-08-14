package com.sesac.msa.paymentservice.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentSagaPublisher {
	private final RabbitTemplate rabbitTemplate;

	@Value("${order.event.exchange}")
	private String exchange;

	@Value("${order.event.routing-key.payment-completed}")
	private String paymentCompletedRoutingKey;

	@Value("${order.event.routing-key.payment-failed}")
	private String paymentFailedRoutingKey;

	@Value("${order.event.routing-key.inventory-restore}")
	private String inventoryRestoreRoutingKey;

	public void publishPaymentCompleted(PaymentCompletedEvent completedEvent) {
		rabbitTemplate.convertAndSend(exchange, paymentCompletedRoutingKey, completedEvent);
	}

	public void publishPaymentFailed(PaymentFailedEvent failedEvent) {
		// order service에게 결제 실패 이벤트 발행(주문 취소)
		rabbitTemplate.convertAndSend(exchange, paymentFailedRoutingKey, failedEvent);
		// product service 에게 결제 실패 이벤트 발행(재고 복구)
		rabbitTemplate.convertAndSend(exchange, inventoryRestoreRoutingKey, failedEvent);
	}
}
