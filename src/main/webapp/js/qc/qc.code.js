const qc = {
	/** Result Enum，返回类型枚举 */
	retEnum : {

		/** 程序执行错误 = -1 */
		EXCEPTION : -1,
		/** 执行成功 = 0 */
		SUCCESS : 0,

		/* 1 开头的值 */

		/** 一般错误 = 100 */
		ERROR : 100,
		/** 缺少参数，参数不完整 = 101 */
		PAR_LACK : 101,
		/** 参数不合法 = 102 */
		PAR_ERROR : 102,

		/** 值已存在 = 110 */
		VALUE_EXIST : 110,
		/** 值不已存在 = 111 */
		VALUE_NOT_EXIST : 111,
		/** 值为空 = 112 */
		VALUE_EMPTY : 112,

		/* 199倒序 */

		/** 审核资料未提交 = 199 */
		AUDIT_NO_SUBMIT : 199,
		/** 审核资料已提交，并等待或正在审核中 = 198 */
		AUDIT_WAIT : 198,
		/** 审核资料已提交，并已通过审核 = 197 */
		AUDIT_PASSED : 197,
		/** 审核资料已提交，并未通过审核 = 196 */
		AUDIT_NOT_PASSED : 196,

		/* 2 开头的值 */

		/** 提供的短信验证码已过期 = 201 */
		VCODE_NOT_EFFECTIVE : 201,
		/** 提供的短信验证码不正确 */
		VCODE_ERROR : 202,

		/* 3 开头的值，用户身份错误 */

		/** 用户未登录 = 300 */
		NO_LOGIN : 300
	}
};


/** 扩展String对象方法：字符串不区分大小写比较 */
String.prototype.equalsIgnoreCase = function(string) {
	return this.toLowerCase() == string.toLowerCase();
}

/** 扩展Date对象方法：格式化日期 */
Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, //月份 
		"d+" : this.getDate(), //日 
		"h+" : this.getHours(), //小时 
		"m+" : this.getMinutes(), //分 
		"s+" : this.getSeconds(), //秒 
		"q+" : Math.floor((this.getMonth() + 3) / 3), //季度 
		"S" : this.getMilliseconds() //毫秒 
	};
	if (!fmt)
		fmt = "yyyy-MM-dd hh:mm:ss";
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for (var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
};

/** 扩展String对象方法：格式化日期，若当前字符串不是日期，则将会返回空字符串 */
String.prototype.formatDate = function(fmt) {
	let temp = new Date(this);
	if (temp == "Invalid Date") {
		return "";
	} else {
		return temp.format(fmt);
	}
}