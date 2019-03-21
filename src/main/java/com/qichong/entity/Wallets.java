package com.qichong.entity;

/** 钱包表 */
public class Wallets {

	private Integer id;
	private Integer userId; // 用户id
	private Double balance; // 余额
	private Double margin; // 保证金

	private Double plusBalance; // 增加余额
	private Double lessBalance; // 减少余额

	public Wallets(Integer userId) {
		this.userId = userId;
	}

	public Wallets() {
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

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getMargin() {
		return margin;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}

	public Double getPlusBalance() {
		return plusBalance;
	}

	public void setPlusBalance(Double plusBalance) {
		this.plusBalance = plusBalance;
	}

	public Double getLessBalance() {
		return lessBalance;
	}

	public void setLessBalance(Double lessBalance) {
		this.lessBalance = lessBalance;
	}

	@Override
	public String toString() {
		return "Wallets [id=" + id + ", userId=" + userId + ", balance=" + balance + ", margin=" + margin + "]";
	}

}
