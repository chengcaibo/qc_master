package com.qichong.model;

import com.qichong.entity.DeskDisplay;
import com.qichong.entity.EnterpriseInfo;
import com.qichong.entity.IndustryType;

public class EnterpriseTopOneModel {

	private EnterpriseInfo ei;
	private DeskDisplay dd;
	private IndustryType industryType;

	public EnterpriseInfo getEi() {
		return ei;
	}

	public void setEi(EnterpriseInfo ei) {
		this.ei = ei;
	}

	public IndustryType getIndustryType() {
		return industryType;
	}

	public void setIndustryType(IndustryType industryType) {
		this.industryType = industryType;
	}

	public DeskDisplay getDd() {
		return dd;
	}

	public void setDd(DeskDisplay dd) {
		this.dd = dd;
	}

	@Override
	public String toString() {
		return "EnterpriseTopOneModel [ei=" + ei + ", dd=" + dd + ", industryType=" + industryType + "]";
	}

}
