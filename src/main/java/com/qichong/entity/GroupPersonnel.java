package com.qichong.entity;

import com.qichong.enums.GroupIdentityEnum;

public class GroupPersonnel {

	private Integer groupId;
	private Integer userId;
	private Integer identityId;

	private GroupInfo ginfo;
	private UserInfo uinfo;
	private GroupIdentity identity;

	public GroupPersonnel() {
	}

	public GroupPersonnel(Integer groupId, Integer userId, Integer identityId) {
		this.groupId = groupId;
		this.userId = userId;
		this.identityId = identityId;
	}

	public GroupPersonnel(Integer groupId, Integer userId, GroupIdentityEnum identity) {
		this.groupId = groupId;
		this.userId = userId;
		this.identityId = identity.getId();
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getIdentityId() {
		return identityId;
	}

	public void setIdentityId(GroupIdentityEnum identity) {
		this.identityId = identity.getId();
	}

	public void setIdentityId(Integer identityId) {
		this.identityId = identityId;
	}

	public GroupIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(GroupIdentity identity) {
		this.identity = identity;
	}

	public GroupInfo getGinfo() {
		return ginfo;
	}

	public void setGinfo(GroupInfo ginfo) {
		this.ginfo = ginfo;
	}

	public UserInfo getUinfo() {
		return uinfo;
	}

	public void setUinfo(UserInfo uinfo) {
		this.uinfo = uinfo;
	}

	@Override
	public String toString() {
		return "GroupPersonnel [groupId=" + groupId + ", userId=" + userId + ", identityId=" + identityId + "]";
	}

}
