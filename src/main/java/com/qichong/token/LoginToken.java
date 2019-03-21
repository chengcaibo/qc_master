package com.qichong.token;

import java.util.HashMap;
import java.util.Map;

import com.qichong.entity.Users;
import com.qichong.service.UsersService;
import com.qichong.socket.message.MessageHeader;
import com.qichong.util.Utils;
import com.qichong.util.web.ServletUtil;

/**
 * 登录令牌，令牌（Token）接口实现类
 * 
 * @author 孙建雷
 */
public class LoginToken implements Token {

	// 私有属性 --- start
	private Integer userId;
	private Users user;
	private String openId;
	private String sessionKey;
	private Long endTime;
	// 私有属性 --- end

	private String keepCode;
	private String tokenKey;

	public LoginToken(MessageHeader header) {
		this.tokenKey = header.getToken();
		this.keepCode = header.getKeepCode();
	}

	public LoginToken() {
	}

	@Override
	public String getTokenKey() {
		return tokenKey;
	}

	private static Map<String, Token> tokens = new HashMap<String, Token>();

	/**
	 * 失效时间，7天(毫秒*秒*分钟*小时*天
	 */
	private static Long failureTimestamp = 1000L * 60 * 60 * 24 * 7;

	/**
	 * 自定义方法，创建Token尽量用这个方法
	 * 
	 * @param tokenKey
	 * @param cover
	 * @param userId
	 * @param openid
	 * @param sessionKey
	 * @return
	 */
	public String newToken(String tokenKey, boolean cover, int userId, String openId, String sessionKey) {
		return this.createToken(tokenKey, cover, userId, openId, sessionKey);
	}

	@Override
	public String createToken(String tokenKey, boolean cover, Object... values) {
		tokenKey = tokenKey == null ? this.randomTokenKey(32) : tokenKey;
		if ((tokens.get(tokenKey) != null && !cover) || (values == null || values.length < 3))
			return null;
		this.userId = (Integer) values[0];
		this.openId = (String) values[1];
		this.sessionKey = (String) values[2];
		this.tokenKey = tokenKey;
		this.resetEndTime();
		tokens.put(tokenKey, this);
		return tokenKey;
	}



	@Override
	public void setTokenKey(String tokenKey) {
		LoginToken token = (LoginToken) tokens.get(tokenKey);
		if (token != null) {

			this.userId = token.getUserId();
			this.openId = token.getOpenId();
			this.sessionKey = token.getSessionKey();
			this.endTime = token.getEndTime();
			this.tokenKey = tokenKey;
		} else {
			this.tokenKey = null;
		}
	}

	@Override
	public boolean isEffective() {
		if (this.tokenKey == null)
			return false;
		LoginToken temp = (LoginToken) tokens.get(this.tokenKey);
		if (temp == null)
			return false;

		Long time = (Long) temp.getEndTime();
		// 判断是否过期
		if (System.currentTimeMillis() >= time) {
			return false;
		}
		return true;
	}

	/** 判断当前Token是否有效，有效则返回true */
	public boolean isEffective(UsersService usersService) {
		if (!this.isEffective()) {
			// 如果无效，则根据keepCode来进行登录
			Users user = ServletUtil.isLogin(this.getKeepCode(), usersService);
			if (user != null) {
				// 重新生成一个TokenKey，返回给小程序存入缓存
				this.newToken(null, false, user.getId(), null, null);
				this.user = user;
				return true;
			} else {
				return false;
			}
		} else {
			// 如果有效，则直接进行查询
			return true;
		}
	}

	private void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	@Override
	public void resetEndTime() {
		this.setEndTime(System.currentTimeMillis() + failureTimestamp);
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	private Long getEndTime() {
		return this.endTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getOpenId() {
		return openId;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public String getKeepCode() {
		return keepCode;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setKeepCode(String keepCode) {
		this.keepCode = keepCode;
	}

	@Override
	public String toString() {
		return "LoginToken [userId=" + userId + ", openId=" + openId + ", sessionKey=" + sessionKey + ", endTime="
				+ endTime + ", tokenKey=" + tokenKey + "]";
	}

	@Override
	public String randomTokenKey(int length) {
		String key = "";
		do {
			key = Utils.randomString(32);
		} while (tokens.get(key) != null);
		return key;
	}

}
