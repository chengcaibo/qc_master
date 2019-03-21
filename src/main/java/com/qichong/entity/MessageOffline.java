package com.qichong.entity;

import java.util.Date;

/**
 * 离线消息表实体类
 * 
 * @author 孙建雷
 */
public class MessageOffline {

	private Integer id; // 自增主键，本条数据的唯一标识
	private Users userA;// 发送消息人的id，users表外键
	private Users userB;// 发送消息人的id，users表外键
	private String message;// 消息主体，消息主体应该限制为最长为300个字符
	private Date createTime;// 本条数据的创建时间
	private State state;// 状态id，state表外键，默认为1（正常）

	public MessageOffline() {
	}

	public MessageOffline(Integer userIdA, Integer userIdB, String message) {
		this.userA = new Users(userIdA);
		this.userB = new Users(userIdB);
		this.message = message;
		// this.createTime = new Date();
		// this.state = new State(1);
	}

	public MessageOffline(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Users getUserIdA() {
		return userA;
	}

	public void setUserA(Users userA) {
		this.userA = userA;
	}

	public Users getUserB() {
		return userB;
	}

	public void setUserIdB(Users userB) {
		this.userB = userB;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
