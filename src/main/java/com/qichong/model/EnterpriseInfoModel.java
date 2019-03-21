package com.qichong.model;

import com.qichong.entity.EnterpriseInfo;
import com.qichong.entity.IndustryNexus;

/**
 * 企业信息Model，企业信息扩展
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2018年1月2日14:47:28
 */
public class EnterpriseInfoModel {

	// 基本信息
	private EnterpriseInfo entInfo;
	// 行业信息，首选
	private IndustryNexus indNexus;

	public EnterpriseInfo getEntInfo() {
		return entInfo;
	}

	public void setEntInfo(EnterpriseInfo entInfo) {
		this.entInfo = entInfo;
	}

	public IndustryNexus getIndNexus() {
		return indNexus;
	}

	public void setIndNexus(IndustryNexus indNexus) {
		this.indNexus = indNexus;
	}

}
