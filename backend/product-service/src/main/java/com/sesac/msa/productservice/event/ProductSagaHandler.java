package com.sesac.msa.productservice.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.sesac.msa.productservice.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSagaHandler {
	private final ProductService service;
	private final ProductSagaPublisher sagaPublisher;

	@RabbitListener(queues = "${order.event.queue.inventory}")
	public void handleOrderEvent(OrderCreatedEvent event) {
		log.info("주문 생성 이벤트 수신 - orderId: {}", event.getOrderId());
		try {
			service.decreaseStock(event.getProductId(), event.getQuantity());

			PaymentRequestEvent paymentRequestEvent = PaymentRequestEvent.builder()
				.orderId(event.getOrderId())
				.productId(event.getProductId())
				.quantity(event.getQuantity())
				.userId(event.getUserId())
				.totalAmount(event.getTotalAmount())
				.build();

			sagaPublisher.publishPaymentRequest(paymentRequestEvent);

		} catch (Exception e) {
			log.error("재고 차감 실패 - productId: {}", event.getProductId());
			InventoryFailedEvent inventoryFailedEvent = InventoryFailedEvent.builder()
				.orderId(event.getOrderId())
				.productId(event.getProductId())
				.quantity(event.getQuantity())
				.reason("재고 부족")
				.build();
			sagaPublisher.publishInventoryFailed(inventoryFailedEvent);
		}
	}

}
