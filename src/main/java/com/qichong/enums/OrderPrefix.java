package com.qichong.enums;

public enum OrderPrefix {
	/** 空 */
	EMPTY(""),
	/** 余额订单前缀 - ba */
	BALANCE("ba"),
	/** 团队保证金前缀 - gm */
	GROUP_MARGIN("gm"),
	/** 用户保证金前缀 - um */
	USER_MARGIN("um"),
	/** 退款前缀 - re */
	REFUND("re");

	private String prefix;

	private OrderPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

	public static String getOrderPrefix(String orderId) {
		return orderId.substring(0, 2);
	}

}
