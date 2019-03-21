package com.qichong.model.adprivate;

import com.qichong.enums.PathEnum;
import com.qichong.model.Image;

/**
 * 企业搜索位列表位广告Model
 * 
 * @author 孙建雷
 *
 */
public class EnterpriseSearchListAd {

	private Integer userId;
	private String enterpriseName;// 企业名称
	private String logo;// 企业logo
	private String introduce;// 企业介绍

	private String industryCode;// 行业编号
	private String industryName;// 行业名称

	private String regionCode; // 地区编号
	private String regionName;// 地区名称

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getLogo() {
		return logo;
	}

	/* 只能 setName */
	public void setLogo(String logo) {
		this.logo = new Image(PathEnum.ENT_LOGO, logo).getUrl();
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

}
