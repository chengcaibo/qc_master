package com.qichong.entity;

import java.util.Date;

/**
 * 余额订单表
 * 
 * @author 孙建雷
 *
 */
public class OrderBalance {

	private Integer id;
	private Integer userId;
	private String orderId;
	private String prepayId;
	private Double money;
	private Date createTime;

	public OrderBalance() {
	}

	public OrderBalance(Integer userId, String orderId, String prepayId, Double money) {
		this.userId = userId;
		this.orderId = orderId;
		this.prepayId = prepayId;
		this.money = money;
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

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
