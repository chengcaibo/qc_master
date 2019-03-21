package com.qichong.socket.message;

import org.springframework.web.socket.WebSocketSession;

import com.qichong.entity.Users;

/**
 * 线上的用户
 * 
 * @author 孙建雷
 */
public class OnlineUser {

	private Users user; // 对应的用户
	private WebSocketSession session;// 连接的session
	private String token;

	public OnlineUser() {
	}

	public OnlineUser(Users user, WebSocketSession session) {
		this.user = user;
		this.session = session;
	}

	public OnlineUser(Users user, WebSocketSession session, String token) {
		this.user = user;
		this.session = session;
		this.token = token;
	}

	public Users getUser() {
		return user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public WebSocketSession getSession() {
		return session;
	}

	public void setSession(WebSocketSession session) {
		this.session = session;
	}

}
