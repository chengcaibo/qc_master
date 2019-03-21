package com.qichong.entity;

import java.util.Date;

public class OrderMargin {

	private Integer id;
	private String orderId;
	private String prepayId;
	private Double margin;
	private Double offerMoney;
	private String offerContent;
	private Date createTime;
	private String transactionId;
	private String refundId;
	private Boolean refunded;

	// 自定义参数
	private boolean isPayment = false; // 是否已支付

	public OrderMargin() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public Double getMargin() {
		return margin;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}

	public Double getOfferMoney() {
		return offerMoney;
	}

	public void setOfferMoney(Double offerMoney) {
		this.offerMoney = offerMoney;
	}

	public String getOfferContent() {
		return offerContent;
	}

	public void setOfferContent(String offerContent) {
		this.offerContent = offerContent;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public Boolean getRefunded() {
		return refunded;
	}

	public void setRefunded(Boolean refunded) {
		this.refunded = refunded;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		if (transactionId != null && !transactionId.equals("")) {
			this.isPayment = true;
		}
	}

	public boolean isPayment() {
		return isPayment;
	}

}
