package com.qichong.util.wechat.app;

import java.util.HashMap;
import java.util.Map;

import com.qichong.util.Utils;
import com.qichong.util.web.NetworkUtil;

/** 微信小程序辅助类 */
public class WeChatAppUtil extends WeChatApp {

	/* --- AccessToken Begin --- */

	// private static String accessToken; // access_token
	// private static Date accessTokenTime; // access_token 的失效时间

	/* --- AccessToken End --- */

	/**
	 * 根据Code获取微信用户的OpenId，如果失败则返回 null
	 * 
	 * @param code
	 *            安卓端传入的Code
	 * @throws Exception
	 */
	public static Map<String, String> codeToOpenId(String code) throws Exception {
		// 发起HTTP GET请求
		String data = String.format(UrlParam.GET_ACCESS_TOKEN, APP_ID, APP_SECRET, code);
		String result = NetworkUtil.sendPost(Url.GET_ACCESS_TOKEN, data);
		// 将返回结果反序列化成一个Map
		Map<String, Object> res = Utils.jsonToMap(result);

		// 判断返回结果是否有错
		if (res.get("errcode") != null) {
			System.out.println("WeChatAppUtil.codeToOpenId获取失败：" + res.toString());
			return null;
		}

		// 将OpenID封装成一个Map并返回
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("accessToken", res.get("access_token").toString());
		returnMap.put("expiresIn", res.get("expires_in").toString());
		returnMap.put("refreshToken", res.get("refresh_token").toString());
		returnMap.put("openId", res.get("openid").toString());
		returnMap.put("scope", res.get("scope").toString());
		returnMap.put("unionId", res.get("unionid").toString());
		return returnMap;
	}

}
