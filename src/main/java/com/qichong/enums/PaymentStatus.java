package com.qichong.enums;

public enum PaymentStatus {

	/** 无需付款 */
	NO_NEED_PAYMENT(-1),
	/** 未付款 */
	UNPAID(0),
	/** 已付款 */
	ALREADY_PAID(1);

	private Integer value;

	private PaymentStatus(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

}
