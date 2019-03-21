package com.qichong.entity;

import java.util.Date;

/** 钱包明细表 */
public class WalletDetail {

	private Integer id;
	private Integer userId;
	private String orderId;
	private Double plus;
	private Double less;
	private String detail;
	private Double balance;
	private Date createTime;
	private String transactionId;

	public WalletDetail(Integer userId, String orderId, String detail, String transactionId) {
		this.userId = userId;
		this.orderId = orderId;
		this.detail = detail;
		this.transactionId = transactionId;
	}

	public WalletDetail() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getPlus() {
		return plus;
	}

	public void setPlus(Double plus) {
		this.plus = plus;
	}

	public Double getLess() {
		return less;
	}

	public void setLess(Double less) {
		this.less = less;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

}
