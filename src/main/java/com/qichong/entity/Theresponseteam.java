package com.qichong.entity;

import java.util.Date;


public class Theresponseteam {
	private int id;
	private Users userA;
	private Users userB;
	private Date  responseTeamTime;
	private State state;
	private GroupInfo groupInfo;
	private UserInfo userInfoA;
	private UserInfo userInfoB;
	private EnterpriseInfo enterpriseInfo;
	private EnterpriseInfo enterpriseInfoB;
	private int requiredNumber;//所需人数
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Users getUserA() {
		return userA;
	}

	public void setUserA(Users userA) {
		this.userA = userA;
	}

	public Users getUserB() {
		return userB;
	}

	public void setUserB(Users userB) {
		this.userB = userB;
	}

	public Date getResponseTeamTime() {
		return responseTeamTime;
	}

	public void setResponseTeamTime(Date responseTeamTime) {
		this.responseTeamTime = responseTeamTime;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}


	public GroupInfo getGroupInfo() {
		return groupInfo;
	}

	public void setGroupInfo(GroupInfo groupInfo) {
		this.groupInfo = groupInfo;
	}

	public UserInfo getUserInfoA() {
		return userInfoA;
	}

	public void setUserInfoA(UserInfo userInfoA) {
		this.userInfoA = userInfoA;
	}

	public UserInfo getUserInfoB() {
		return userInfoB;
	}

	public void setUserInfoB(UserInfo userInfoB) {
		this.userInfoB = userInfoB;
	}

	public EnterpriseInfo getEnterpriseInfo() {
		return enterpriseInfo;
	}

	public void setEnterpriseInfo(EnterpriseInfo enterpriseInfo) {
		this.enterpriseInfo = enterpriseInfo;
	}

	public EnterpriseInfo getEnterpriseInfoB() {
		return enterpriseInfoB;
	}

	public void setEnterpriseInfoB(EnterpriseInfo enterpriseInfoB) {
		this.enterpriseInfoB = enterpriseInfoB;
	}

	public int getRequiredNumber() {
		return requiredNumber;
	}

	public void setRequiredNumber(int requiredNumber) {
		this.requiredNumber = requiredNumber;
	}

	@Override
	public String toString() {
		return "Theresponseteam [id=" + id + ", userA=" + userA + ", userB=" + userB + ", responseTeamTime="
				+ responseTeamTime + ", state=" + state + ", groupInfo=" + groupInfo + ", userInfoA=" + userInfoA
				+ ", userInfoB=" + userInfoB + ", enterpriseInfo=" + enterpriseInfo + ", enterpriseInfoB="
				+ enterpriseInfoB + ", requiredNumber=" + requiredNumber + "]";
	}

}
