package com.qichong.entity;

public class OrderUserMargin extends OrderMargin {

	private Integer userId;

	public OrderUserMargin() {
	}

	public OrderUserMargin(Integer userId) {
		this.userId = userId;
	}

	public OrderUserMargin(String orderId, String transactionId) {
		super.setOrderId(orderId);
		super.setTransactionId(transactionId);
	}

	// getter & setter

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
