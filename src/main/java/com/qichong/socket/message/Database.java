package com.qichong.socket.message;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储库
 * 
 * @author 孙建雷
 *
 */
public class Database {

	/** 用来存储线上用户 */
	public static Map<Integer, OnlineUser> onlineUsers = new HashMap<Integer, OnlineUser>();

	public static OnlineUser getOnlineUser(Object key) {
		return onlineUsers.get(key);
	}

}
