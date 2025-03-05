package com.travel.web.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class PaymentResponse {
	private long id;
	private String orderId;
	private String paymentId;
	private String signatureId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getSignatureId() {
		return signatureId;
	}
	public void setSignatureId(String signatureId) {
		this.signatureId = signatureId;
	}

	
}
