package com.qichong.entity;

/**
 * 用户与所属行业之间的关系表实体类
 * 
 * @创建人 吴志伟
 * @修改人 孙建雷
 * @修改时间 2017-11-9 16:37:00
 */
public class IndustryNexus {

	private int id; // 唯一标识 id
	private int userId; // 用户id
	private String industryTypeCode; // 行业类型id，industry_type表外键

	private IndustryType industryType;

	private boolean isFirst;// 是否是首选行业

	public IndustryNexus() {
	}

	public IndustryNexus(int id, int userId, String industryTypeCode) {
		this.id = id;
		this.userId = userId;
		this.industryTypeCode = industryTypeCode;
	}

	public IndustryNexus(int userId, String industryTypeCode) {
		this.userId = userId;
		this.industryTypeCode = industryTypeCode;
	}

	public IndustryNexus(Users user, String industryTypeCode, boolean isFirst) {
		this.userId = user.getId();
		this.industryTypeCode = industryTypeCode;
		this.isFirst = isFirst;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public IndustryType getIndustryType() {
		return industryType;
	}

	public void setIndustryType(IndustryType industryType) {
		this.industryType = industryType;
	}

	public String getIndustryTypeCode() {
		return industryTypeCode;
	}

	public void setIndustryTypeCode(String industryTypeCode) {
		this.industryTypeCode = industryTypeCode;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {

		this.isFirst = isFirst;

	}

	@Override
	public String toString() {
		return "IndustryNexus [id=" + id + ", userId=" + userId + ", industryTypeCode=" + industryTypeCode
				+ ", isFirst=" + isFirst + "]";
	}

}
