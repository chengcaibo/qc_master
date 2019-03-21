package com.qichong.entity;

import java.util.Date;

import com.qichong.util.web.ServletUtil;

/**
 * 实体类 User_apintment_user 个人预约个人类 对应表user_apintment_user
 */

public class UserApintmentUser {

	private int id; // id
	private Users userIdA;
	private Users userIdB;
	private String remark;// 预约备注
	private Date startTime;// 预约开始时间
	private Date endTime;// 预约结束时间
	private State state; // 状态 状态对象
	private UserInfo uiA;
	private UserInfo uiB;
	private EnterpriseInfo enterpriseInfo;

	public UserApintmentUser() {}

	public UserApintmentUser(int id) {
		this.id = id;
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
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = ServletUtil.codeFormat(remark);
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public UserInfo getUiA() {
		return uiA;
	}

	public void setUiA(UserInfo uiA) {
		this.uiA = uiA;
	}

	public UserInfo getUiB() {
		return uiB;
	}

	public void setUiB(UserInfo uiB) {
		this.uiB = uiB;
	}
	
	
	public EnterpriseInfo getEnterpriseInfo() {
		return enterpriseInfo;
	}

	public void setEnterpriseInfo(EnterpriseInfo enterpriseInfo) {
		this.enterpriseInfo = enterpriseInfo;
	}

	@Override
	public String toString() {
		return "UserApintmentUser [id=" + id + ", userIdA=" + userIdA + ", userIdB=" + userIdB + ", remark=" + remark
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", state=" + state + ", uiA=" + uiA + ", uiB="
				+ uiB + ", enterpriseInfo=" + enterpriseInfo + "]";
	}

}
