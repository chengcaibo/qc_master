package com.qichong.enums;

public enum OrderType {

	USER_ORDER(0), TOOLS_ORDER(1), DEMAND_ORDER(2);

	private Byte value;

	private OrderType(Byte value) {
		this.value = value;
	}

	private OrderType(Integer value) {
		this.value = value.byteValue();
	}

	public Byte getValue() {
		return value;
	}

	/** 获取枚举 */
	public static OrderType get(Byte value) {
		for (OrderType e : values()) {
			if (e.getValue().equals(value)) {
				return e;
			}
		}
		return null;
	}

}
