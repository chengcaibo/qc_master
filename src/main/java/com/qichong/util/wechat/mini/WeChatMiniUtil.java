package com.qichong.util.wechat.mini;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.qichong.util.Utils;
import com.qichong.util.web.NetworkUtil;

/** 微信小程序辅助类 */
public class WeChatMiniUtil extends WeChatMini {

	/** 获取微信小程序码 */
	public static InputStream getWXACode(String path) throws Exception {
		String accessToken = getAccessToken();
		String url = Url.GET_WXA_CODE + "?access_token=" + accessToken;
		JsonObject json = new JsonObject();
		json.addProperty("path", path);
		json.addProperty("width", 280);
		json.addProperty("is_hyaline", false);
		String params = Utils.gson.toJson(json);
		return NetworkUtil.sendPostReturnInputStream(url, params);
	}

	/* --- AccessToken Begin --- */

	private static String accessToken; // access_token
	private static Date accessTokenTime; // access_token 的失效时间

	/* --- AccessToken End --- */

	/**
	 * 设置AccessToken
	 * 
	 * @param accessToken
	 *            AccessToken
	 * @param expiresIn
	 *            AccessToken的失效时间，单位：秒
	 */
	public static void setAccessToken(String accessToken, int expiresIn) {
		WeChatMiniUtil.accessToken = accessToken;
		WeChatMiniUtil.accessTokenTime = new Date(System.currentTimeMillis() + ((expiresIn - 60) * 1000));
	}

	/** 获取AccessToken */
	public static String getAccessToken() {
		try {
			long thisTime = System.currentTimeMillis();
			if (WeChatMiniUtil.accessTokenTime == null || thisTime >= accessTokenTime.getTime()) { // 如果不存在AccessToken或者AccessToken已过期就重新获取
				// 发起HTTP GET请求
				String data = String.format(UrlParam.GET_ACCESS_TOKEN, APP_ID, APP_SECRET);
				String result = NetworkUtil.sendGet(Url.GET_ACCESS_TOKEN, data); // 发送Get请求
				// 将返回结果反序列化成一个Map
				Map<String, Object> map = Utils.jsonToMap(result);
				// 判断返回结果是否有错
				if (map.get("errcode") != null)
					throw new RuntimeException(
							"获取AccessToken时失败，错误码：" + map.get("errcode") + "，错误信息：" + map.get("errmsg"));
				String accessToken = map.get("access_token").toString();
				Double expiresIn = Double.parseDouble(map.get("expires_in").toString());
				// set进内存
				setAccessToken(accessToken, expiresIn.intValue());
			}
			return WeChatMiniUtil.accessToken;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取AccessToken
	 * 
	 * @throws IOException
	 */
	public static void getAndSetAccessToken() {

	}

	/**
	 * 根据Code获取微信用户的OpenId，如果失败则返回 null
	 * 
	 * @param code
	 *            小程序wx.login获取的jsCode
	 */
	public static Map<String, String> codeToOpenId(String code) throws IOException {
		// 发起HTTP GET请求
		String data = String.format("appid=%s&secret=%s&js_code=%s&grant_type=authorization_code", APP_ID, APP_SECRET,
				code);
		String result = NetworkUtil.sendGet("https://api.weixin.qq.com/sns/jscode2session", data);
		// 将返回结果反序列化成一个Map
		Map<String, Object> resultMap = Utils.jsonToMap(result);

		// 判断返回结果是否有错
		if (resultMap.get("errcode") != null)
			return null;
		// 将OpenID封装成一个Map并返回
		Map<String, String> returnMap = new HashMap<String, String>();

		put(resultMap, "openid", returnMap, "openId");
		put(resultMap, "unionid", returnMap, "unionId");
		put(resultMap, "session_key", returnMap, "sessionKey");

		return returnMap;
	}

	/**
	 * 从map1中取到的值put到map2中，如果取到的值为空，则不进行put操作
	 */
	private static boolean put(Map<?, ?> map1, String key1, Map<String, String> map2, String key2) {
		Object value = map1.get(key1);
		if (value != null) {
			map2.put(key2, value.toString());
			return true;
		}
		return false;
	}

}
