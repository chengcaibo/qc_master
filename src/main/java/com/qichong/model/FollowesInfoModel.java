package com.qichong.model;

import com.qichong.entity.UserInfo;

/**
 * 关注信息Model
 */
public class FollowesInfoModel {

	private Integer id;// Follow表中的id

	private UserInfo ui; // 用户的基本信息

	// 若已下两个都为true，则说明当前条目与当前登录者是“互相关注”
	private boolean isLoginFollow = false; // 是否关注了TA
	private boolean isFollowLogin = false; // TA是否关注了我

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserInfo getUi() {
		return ui;
	}

	public void setUi(UserInfo ui) {
		this.ui = ui;
	}

	public void setFollowLogin(Integer followLogin) {
		this.isFollowLogin = (followLogin != null);
	}

	public void setLoginFollow(Integer loginFollow) {
		this.isLoginFollow = (loginFollow != null);
	}

	public boolean getIsLoginFollow() {
		return isLoginFollow;
	}

	public boolean getIsFollowLogin() {
		return isFollowLogin;
	}

}
