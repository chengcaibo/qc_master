package com.qichong.enums;

/**
 * 对应数据库中的状态表（{@link com.qichong.entity.State State}）
 * 
 * @author 孙建雷
 */
public enum StateEnum {

	/** 正常 = 1 */
	NORMAL(1, "正常"),
	/** 未约 = 2 */
	NOT_RESERVED(2, "未约"),
	/** 已约 = 3 */
	ALREADY_RESERVED(3, "已约"),

	/* 审核相关 */

	/** 待审核 = 4 */
	AUDIT_PENDING(4, "待审核"),
	/** 审核通过 = 5 */
	AUDIT_PASS(5, "审核通过"),
	/** 审核未通过 = 6 */
	AUDIT_REJECTED(6, "审核未通过"),

	/* 通用 */

	/** 已封禁 = 7 */
	BANNED(7, "已封禁"),
	/** 已弃用 = 8 */
	DEPRECATED(8, "已弃用"),
	/** 已过期 = 9 */
	EXPIRED(9, "已过期"),

	/* 账号相关 */

	/**账号正常 = 10,就是已经被激活的状态 */
	ACCOUNT_NORMAL(10, "账号正常"),
	/** 账号未激活 = 11 */
	ACCOUNT_NOT_ACTIVATION(11, "账号未激活"),
	/** 账号已冻结 = 12 */
	ACCOUNT_FROZEN(12, "账号已冻结"),
	/** 账号已禁用 = 13 */
	ACCOUNT_DISABLED(13, "账号已禁用"),

	/* 订单相关 */

	/** 待付款 = 101 */
	ORDER_PENDING_PAYMENT(101, "待付款"),
	/** 等待中 = 102 */
	ORDER_WAITING(102, "等待中"),
	/** 进行中 = 103 */
	ORDER_PROCESSING(103, "进行中"),
	/** 待评价 = 104 */
	ORDER_TO_BE_EVALUATED(104, "待评价"),
	/** 已完成 = 105 */
	ORDER_COMPLETED(105, "已完成"),
	/** 已关闭 = 106 */
	ORDER_CLOSED(106, "已关闭"),

	/* 需求相关 */

	/** 需求_正常 = 110 */
	DEMAND_NORMAL(110, "正常"),
	/** 需求_已接受 = 111 */
	DEMAND_RECEIVED(111, "已接受"),
	/** 需求_进行中 = 112 */
	DEMAND_PROCESSING(112, "进行中"),
	/** 需求_已完成 = 113 */
	DEMAND_COMPLETED(113, "已完成"),
	/** 需求_已到期 = 114 */
	DEMAND_EXPIRED(114, "已到期"),
	/** 需求_已关闭 = 115 */
	DEMAND_CLOSED(115, "已关闭");

	private int id;
	private String state;

	private StateEnum(int id, String state) {
		this.id = id;
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return state;
	}

}
