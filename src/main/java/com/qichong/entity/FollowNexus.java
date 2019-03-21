package com.qichong.entity;

public class FollowNexus {
	private int id;
	private Users userIdA;
	private Users userIdB;

	public FollowNexus() {
	}

	public FollowNexus(int id, Users userIdA, Users userIdB) {
		super();
		this.id = id;
		this.userIdA = userIdA;
		this.userIdB = userIdB;
	}

	public FollowNexus(Users userIdA, Users userIdB) {
		super();
		this.userIdA = userIdA;
		this.userIdB = userIdB;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Users getUserIdA() {
		return userIdA;
	}

	public void setUserIdA(Users userIdA) {
		this.userIdA = userIdA;
	}

	public Users getUserIdB() {
		return userIdB;
	}

	public void setUserIdB(Users userIdB) {
		this.userIdB = userIdB;
	}

	@Override
	public String toString() {
		return "FollowNexus [id=" + id + ", userIdA=" + userIdA + ", userIdB=" + userIdB + "]";
	}

}
