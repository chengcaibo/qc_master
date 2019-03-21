package com.qichong.model;

import java.util.Date;
import com.qichong.enums.PathEnum;
import com.qichong.util.web.ServletUtil;

public class IndustryTopModel {

	// userInfo&enterpriseInfo
	private String unName;
	private String introduce;
	private String telephone;
	private Image logo = new Image(PathEnum.ENT_LOGO, "no");
	// industryTop
	private int locationId;// 置顶id
	private Date StartTime;// 开始时间
	private Date endTime;// 结束时间
	private String industryCode; // 职位编号
	private String industryName;// 职位名称

	/**
	 * 构造函数
	 */
	public IndustryTopModel() {
		super();
	}

	/**
	 * getter&setter
	 * 
	 * @return
	 */
	
	

	public String getIntroduce() {
		return introduce;
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

	public String getUnName() {
		return unName;
	}

	public void setUnName(String unName) {
		this.unName = unName;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public Date getStartTime() {
		return StartTime;
	}

	public void setStartTime(Date startTime) {
		StartTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Image getLogo() {
		if (ServletUtil.isEmpty(this.logo.getName())) {
			this.logo.setName("no");
		}
		return logo;
	}

	public void setLogo(String logo) {
		this.logo.setPath(PathEnum.ENT_LOGO);
		this.logo.setName(logo);
	}


}
