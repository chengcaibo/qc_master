package com.qichong.token;

/**
 * 令牌接口
 * 
 * @author 孙建雷
 */
public interface Token {

	// 基础支持: 获取access_token接口 /token
	// 请求地址：
	// https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx89ea7d0a3986529b&secret=d5b43ebbbd35bed766ff3ff61d359215

	String getTokenKey();

	/**
	 * 随机生成一个tokenKey
	 * 
	 * @param length
	 *            长度
	 * @return 新的TokenKey
	 */
	String randomTokenKey(int length);

	/**
	 * 创建一个新的Token
	 * 
	 * @param tokenKey
	 *            自定义的TokenKey，若已存在则是否覆盖由开发者控制
	 * 
	 * @param cover
	 *            已存在是否覆盖，true为覆盖
	 * @param values
	 *            要存储的值
	 * @return 返回Token的Key
	 */
	String createToken(String tokenKey, boolean cover, Object... values);

	/**
	 * 重新设置结束失效时间
	 */
	void resetEndTime();

	void setTokenKey(String tokenKey);

	/**
	 * 判断所提供的Token是否有效，有效则返回true
	 */
	boolean isEffective();
}
