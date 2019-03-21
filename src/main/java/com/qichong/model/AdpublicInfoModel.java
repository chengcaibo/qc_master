package com.qichong.model;

import com.qichong.entity.AdPublic;
import com.qichong.entity.UserInfo;

public class AdpublicInfoModel {
	private UserInfo ui;
	private AdPublic ai;

	public UserInfo getUi() {
		return ui;
	}

	public void setUi(UserInfo ui) {
		this.ui = ui;
	}

	public AdPublic getAi() {
		return ai;
	}

	public void setAi(AdPublic ai) {
		this.ai = ai;
	}

	@Override
	public String toString() {
		return "AdpublicInfoModel [ui=" + ui + ", ai=" + ai + "]";
	}

}
