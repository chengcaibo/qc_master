package com.qichong.admin.util;

import javax.servlet.http.HttpSession;

import com.qichong.util.Utils;

public class AdminUtil {

	/** 登录密码 */
	public final static String LOGIN_KEY = "admin@qc1318.com";

	/** Session令牌 123232*/
	public static String TOKEN_KEY = "";

	static {
		/** 随机生成32位的令牌 */
		TOKEN_KEY = Utils.randomString(32);
	}

	/** 判断是否已登录 */
	public static boolean isLogin(HttpSession session) {
		Object token = session.getAttribute("adminToken");
		if (token == null || !token.toString().equals(TOKEN_KEY)) {
			return false;
		}
		return true;
	}

}
