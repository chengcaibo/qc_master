package com.qichong.enums;

/**
 * Result Enum，返回类型枚举
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017年11月17日 下午1:16:15
 */
public enum RetEnum {

	/** 程序执行错误 = -1 */
	EXCEPTION(-1),
	/** 执行成功 = 0 */
	SUCCESS(0),

	/* = = = 1 开头的值 = = = */

	/** 一般错误 = 100 */
	ERROR(100),
	/** 缺少参数，参数不完整 = 101 */
	PARAM_LACK(101),
	/** 参数不合法 = 102 */
	PARAM_ERROR(102),
	/** 手机号未激活 = 103 */
	PHONE_NOT_ACTIVATION(103),

	/* = = = 11开头的值 = = = */

	/** 值已存在 = 110 */
	VALUE_EXIST(110),
	/** 值不存在 = 111 */
	VALUE_NOT_EXIST(111),
	/** 值为空 = 112 */
	VALUE_EMPTY(112),

	/* = = = 12开头的值 = = = */
	/** 未交付保证金 = 120 */
	MARGIN_NO_PAY(120),
	/** 保证金不足，需要补交 = 121 */
	MARGIN_LESS(121),

	/* = = = 199倒序 = = = */

	/** 审核资料未提交 = 199 */
	AUDIT_NO_SUBMIT(199),
	/** 审核资料已提交，并等待或正在审核中 = 198 */
	AUDIT_WAIT(198),
	/** 审核资料已提交，并已通过审核 = 197 */
	AUDIT_PASSED(197),
	/** 审核资料已提交，并未通过审核 = 196 */
	AUDIT_NOT_PASSED(196),

	/* = = = 2 开头的值 = = = */

	/** 提供的短信验证码已过期 = 201 */
	VCODE_NOT_EFFECTIVE(201),
	/** 提供的短信验证码不正确 = 202 */
	VCODE_ERROR(202),

	/* = = = 3 开头的值，用户身份错误 = = = */

	/** 用户未登录 = 300 */
	NO_LOGIN(300),
	/** 该openId尚未绑定奇虫账号 = 301 */
	WX_OPENID_NO_BIND(301),

	/* = = = 4 开头的值，权限错误 = = = */

	/** 没有权限 = 400 */
	AUTH_ERROR(400);

	private int value;

	private RetEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
