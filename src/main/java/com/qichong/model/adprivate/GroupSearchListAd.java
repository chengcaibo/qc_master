package com.qichong.model.adprivate;

import com.qichong.enums.PathEnum;
import com.qichong.model.Image;

/**
 * 团体搜索位列表位广告Model
 * 
 * @author 孙建雷
 *
 */
public class GroupSearchListAd {

	private Integer id;
	private Integer userId;
	private String groupName;// 团体名称
	private String picture;// 团体封面
	private String introduce;// 团体介绍
	private String personnelCost; // 人均酬劳

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getPersonnelCost() {
		return personnelCost;
	}

	public void setPersonnelCost(String personnelCost) {
		this.personnelCost = personnelCost;
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

	public String getPicture() {
		return picture;
	}

	/* 只能 setName */
	public void setPicture(String picture) {
		this.picture = new Image(PathEnum.GROUP_COVER, picture).getUrl();
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
