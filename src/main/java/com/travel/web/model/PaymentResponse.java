package com.travel.web.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentResponse {
	private long id;
	private String orderId;
	private String paymentId;
	private String signatureId;

}
