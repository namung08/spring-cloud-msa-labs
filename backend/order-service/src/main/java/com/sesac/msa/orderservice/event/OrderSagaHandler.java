package com.sesac.msa.orderservice.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.sesac.msa.orderservice.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderSagaHandler {
	private final OrderService service;

	@RabbitListener(queues = "${order.event.queue.inventory-failed}")
	public void handleInventoryFailed(InventoryFailedEvent event) {
		log.info("재고 부족으로 인한 실패 이벤트 수신 - orderId: {}, reason: {}", event.getOrderId(), event.getReason());
	}

	@RabbitListener(queues = "${order.event.queue.payment-completed}")
	public void handlePaymentCompleted(PaymentCompletedEvent event) {
		log.info("결제 성공 이벤트 수신 - orderId:{},  amount: {}", event.getOrderId(), event.getAmount());

	}

	@RabbitListener(queues = "${order.event.queue.payment-failed}")
	public void handlePaymentFailed(PaymentFailedEvent event) {
		log.info("결제 실패 이벤트 수신 - orderId: {}, reason: {}", event.getOrderId(), event.getReason());
	}
}
