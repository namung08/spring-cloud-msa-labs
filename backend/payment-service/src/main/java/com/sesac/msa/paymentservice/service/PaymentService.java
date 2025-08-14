package com.sesac.msa.paymentservice.service;

import com.sesac.msa.paymentservice.entity.Payment;
import com.sesac.msa.paymentservice.event.PaymentRequestEvent;

public interface PaymentService {
	Payment processPayment(PaymentRequestEvent event);
}
