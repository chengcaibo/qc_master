package com.qichong.model;

import com.qichong.entity.UserInfo;

public class FollowNexusModel {
	
	private int id;
	private UserInfo userA;
	private UserInfo userB;
	public FollowNexusModel(){}
	public FollowNexusModel(int id, UserInfo userA, UserInfo userB) {
		super();
		this.id = id;
		this.userA = userA;
		this.userB = userB;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UserInfo getUserA() {
		return userA;
	}
	public void setUserA(UserInfo userA) {
		this.userA = userA;
	}
	public UserInfo getUserB() {
		return userB;
	}
	public void setUserB(UserInfo userB) {
		this.userB = userB;
	}
	@Override
	public String toString() {
		return "FollowNexusModel [id=" + id + ", userA=" + userA + ", userB=" + userB + "]";
	}
	
	
}
