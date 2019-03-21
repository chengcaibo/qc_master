package com.qichong.entity;

/**
 * 行业类别表，用来存储所有类别
 * 
 * @创建人 吴志伟
 * @修改人 孙建雷
 * @修改人 陈文瑾
 * @修改时间 2017-12-1 14:13:02
 */
public class IndustryType {

	/* 行业实体类 */
	private String industryCode;// 行业号
	private String industryName;// 行业名称
	private String parentCode;// 子父级id

	/* 构造函数 */
	public IndustryType() {
	}

	public IndustryType(String industryCode) {
		this.industryCode = industryCode;
	}

	public IndustryType(String industryCode, String industryName, String parentCode) {
		this.industryCode = industryCode;
		this.industryName = industryName;
		this.parentCode = parentCode;
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

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	@Override
	public String toString() {
		return "IndustryType [industryCode=" + industryCode + ", industryName=" + industryName + ", parentCode="
				+ parentCode + "]";
	}

}
