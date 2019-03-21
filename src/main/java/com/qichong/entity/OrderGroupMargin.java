package com.qichong.entity;

public class OrderGroupMargin extends OrderMargin {

	private Integer groupId;

	public OrderGroupMargin() {
	}

	public OrderGroupMargin(Integer groupId) {
		this.groupId = groupId;
	}

	public OrderGroupMargin(String orderId, String transactionId) {
		super.setOrderId(orderId);
		super.setTransactionId(transactionId);
	}

	// getter & setter

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		return "OrderGroupMargin [orderId=" + getOrderId() + ", margin=" + getMargin() + ", offerMoney="
				+ getOfferMoney() + ", offerContent=" + getOfferContent() + "]";
	}

}
