package com.qichong.model;

import com.qichong.entity.GroupInfo;
import com.qichong.entity.IndustryType;
import com.qichong.entity.UserInfo;

public class GroupInfoModel {

	private GroupInfo gi;
	private UserInfo userInfo;
	private IndustryType industryType;// 首选行业

	public GroupInfo getGi() {
		return gi;
	}

	public void setGi(GroupInfo gi) {
		this.gi = gi;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public IndustryType getIndustryType() {
		return industryType;
	}

	public void setIndustryType(IndustryType industryType) {
		this.industryType = industryType;
	}

	@Override
	public String toString() {
		return "GroupInfoModel [gi=" + gi + ", userInfo=" + userInfo + ", industryType=" + industryType + "]";

	}
}