package com.sesac.msa.orderservice.event;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentCompletedEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long orderId;
	private Long userId;
	private BigDecimal amount;
}
