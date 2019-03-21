package com.qichong.entity;

import java.util.Date;

/**
 * 行业置顶实体
 * 
 * @创建者：陈文瑾 @创建时间：2017年12月18日15:53:40
 */
public class IndustryTop {

	// 字段
	private int id;// 编号
	private Users user;
	private UserInfo ui;// userInfo 对象(要用图片)
	private EnterpriseInfo ei;
	private int locationId;// 置顶id
	private Date StartTime;// 开始时间
	private Date endTime;// 结束时间
	private String industryCode; // 职位编号
	private String industryName;// 职位名称

	/**
	 * 构造函数
	 */
	public IndustryTop() {
	}

	public IndustryTop(int id, int userId, Date endTime) {
		this.id = id;
		this.user = new Users(userId);
		this.StartTime = new Date();
		this.endTime = endTime;
	}

	public IndustryTop(int id, Users user, UserInfo ui, EnterpriseInfo ei, int locationId, Date startTime, Date endTime,
			String industryCode, String industryName) {
		super();
		this.id = id;
		this.user = user;
		this.ui = ui;
		this.ei = ei;
		this.locationId = locationId;
		StartTime = startTime;
		this.endTime = endTime;
		this.industryCode = industryCode;
		this.industryName = industryName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public UserInfo getUi() {
		return ui;
	}

	public void setUi(UserInfo ui) {
		this.ui = ui;
	}

	public EnterpriseInfo getEi() {
		return ei;
	}

	public void setEi(EnterpriseInfo ei) {
		this.ei = ei;
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

	@Override
	public String toString() {
		return "IndustryTop [id=" + id + ", user=" + user + ", ui=" + ui + ", ei=" + ei + ", locationId=" + locationId
				+ ", StartTime=" + StartTime + ", endTime=" + endTime + ", industryCode=" + industryCode
				+ ", industryName=" + industryName + "]";
	}

}
