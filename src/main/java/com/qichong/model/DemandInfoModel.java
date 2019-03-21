package com.qichong.model;

import com.qichong.entity.DemandInfo;
import com.qichong.entity.UserInfo;

public class DemandInfoModel {
	private UserInfo ui;
	private DemandInfo di;
	public UserInfo getUi() {
		return ui;
	}
	public void setUi(UserInfo ui) {
		this.ui = ui;
	}
	
	public DemandInfo getDi() {
		return di;
	}
	public void setDi(DemandInfo di) {
		this.di = di;
	}
	@Override
	public String toString() {
		return "DemandInfoModel [ui=" + ui + ", gi=" + di + "]";
	}
	
	
}
