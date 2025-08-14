package com.sesac.msa.paymentservice.event;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentFailedEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long orderId;
	private Long userId;
	private Long productid;
	private Integer quantity;
	private String reason;
}
