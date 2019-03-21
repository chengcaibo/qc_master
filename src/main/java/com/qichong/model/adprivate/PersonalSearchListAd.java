package com.qichong.model.adprivate;

import java.util.Arrays;

import com.qichong.enums.PathEnum;
import com.qichong.model.Image;
import com.qichong.util.web.ServletUtil;

/**
 * 个人搜索位列表位广告Model
 * 
 * @author 孙建雷
 *
 */
public class PersonalSearchListAd {

	// private Users user;
	//// -- id
	//// -- distance
	//// -- lastUpdateTimeStr

	private Integer userId;
	private String nickName;// 用户昵称
	private String avatar;// 用户头像

	private Double score;// 评分
	private String[] scoreViews; // 评分转换成星星

	private Double hourlyWage;// 时薪

	private String jobCode;// 职业编号
	private String jobName;// 职业名称

	private String regionCode; // 地区编号
	private String regionName;// 地区名称
	private String address; // 详细地址

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatar() {
		return avatar;
	}

	/** 只能 setName */
	public void setAvatar(String avatar) {
		this.avatar = new Image(PathEnum.USER_AVATAR, avatar).getUrl();
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
		// 根据评分计算星级
		this.scoreViews = ServletUtil.calcScoreView(score);
	}

	public String[] getScoreViews() {
		return scoreViews;
	}

	public void setScoreViews(String[] scoreViews) {
		this.scoreViews = scoreViews;
	}

	public Double getHourlyWage() {
		return hourlyWage;
	}

	public void setHourlyWage(Double hourlyWage) {
		this.hourlyWage = hourlyWage;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "PersonalSearchListAd [userId=" + userId + ", nickName=" + nickName + ", avatar=" + avatar + ", score="
				+ score + ", scoreViews=" + Arrays.toString(scoreViews) + ", hourlyWage=" + hourlyWage + ", jobCode="
				+ jobCode + ", jobName=" + jobName + ", regionCode=" + regionCode + ", regionName=" + regionName
				+ ", address=" + address + "]";
	}

}
