package com.qichong.entity;

import java.util.Date;

/**
 * 响应记录表
 * 
 * @创建人 吴志伟
 *
 */
public class TheResponse {
	private int id;
	private Users userA;
	private Users userB;
	private DemandInfo demand;
	private Date responseTime;
	private UserInfo userInfo;
	private UserInfo userInfoB;
	private EnterpriseInfo enterpriseInfo;
	private EnterpriseInfo enterpriseInfoB;
	private State state;

	// 构造
	public TheResponse() {
	}



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

	public DemandInfo getDemand() {
		return demand;
	}

	public void setDemand(DemandInfo demand) {
		this.demand = demand;
	}



	public Date getResponseTime() {
		return responseTime;
	}



	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}



	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "TheResponse [id=" + id + ", userA=" + userA + ", userB=" + userB + ", demand=" + demand
				+ ", responseTime=" + responseTime + ", userInfo=" + userInfo + ", userInfoB=" + userInfoB
				+ ", enterpriseInfo=" + enterpriseInfo + ", enterpriseInfoB=" + enterpriseInfoB + ", state=" + state
				+ "]";
	}

}
