package com.qichong.entity;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.qichong.enums.PathEnum;
import com.qichong.model.Image;
import com.qichong.util.Utils;
import com.qichong.util.web.ServletUtil;

/**
 * user_info表实体类，个人 用户详细信息
 * 
 * @创建人 吴志伟
 * @修改人 孙建雷
 * @修改时间 2017-11-27 16:21:57
 */
public class UserInfo {

	private Integer id; // 唯一ID
	private Users user; // 用户表中的id
	private Integer userId;
	private String nickName;// 用户昵称
	private String realName;// 用户真实姓名
	private String introduce;// 个人介绍
	private String ascendancy;// 优势
	// 这里必须实例化
	private Image avatar = new Image();// 用户头像
	// private String avatar;// 用户头像
	private String identity;// 身份证号
	private String telephone;// 手机
	private Gender gender; // 性别

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;// 生日
	private Integer age; // 年龄

	private Double hourlyWage;// 时薪

	private Double score;// 评分
	private String[] scoreViews; // 评分转换成星星

	private Double balance;// 余额
	private String email;// 邮箱地址
	private Regions region; // 地区
	private String address; // 详细地址

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date jobBeginTime; // 工作开始时间，用于计算工作年限
	private String jobYears; // 工作年限
	private JobInfo jobInfo; // 职业、职位
	private JobType jobType; // 工作类型

	private String label; // 个人标签
	private String skillName;
	private String industryDetails;
	private String addressAll;

	private String appintmentCount;// 用于累计预约次数

	// 一对多查询用户技能
	private List<SkillInfo> skills;
	//用户的被举报次数
	private Integer reportedTimes;
	//是否在黑名单里面
	private Integer blackList;
	//加入黑名单的原因，0表示为加入黑名单，1表示恶意举报，2表示发布虚假消息
	private String  reason;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getReportedTimes() {
		return reportedTimes;
	}

	public void setReportedTimes(Integer reportedTimes) {
		this.reportedTimes = reportedTimes;
	}

	public Integer getBlackList() {
		return blackList;
	}

	public void setBlackList(Integer blackList) {
		this.blackList = blackList;
	}

	/** 构造 */
	public UserInfo() {
	}

	/**
	 * getter&setter
	 * 
	 * @param user
	 */
	public UserInfo(Users user) {
		this.user = user;
	}

	public UserInfo(int id) {
		this.id = id;
	}

	public Integer getId() {
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = ServletUtil.codeFormat(nickName);
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = ServletUtil.codeFormat(realName);
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = ServletUtil.codeFormat(introduce);
	}

	public String getAscendancy() {
		return ascendancy;
	}

	public void setAscendancy(String ascendancy) {
		this.ascendancy = ServletUtil.codeFormat(ascendancy);
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * 返回 Image<sup>(1)</sup> 对象
	 * 
	 * @see com.qichong.model.Image (1) 点击查看Image
	 */
	public Image getAvatar() {
		return avatar;
	}

	/**
	 * 只能 setName
	 */
	public void setAvatar(String name) {
		this.avatar.setPath(PathEnum.USER_AVATAR);
		this.avatar.setName(name);
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
		// 顺便计算出年龄
		this.age = Utils.calcAgeByBirthday(birthday);
	}

	public Integer getAge() {
		return age;
	}

	public JobType getJobType() {
		return jobType;
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

	public JobInfo getJobInfo() {
		return jobInfo;
	}

	public void setJobInfo(JobInfo jobInfo) {
		this.jobInfo = jobInfo;
	}

	public Double getHourlyWage() {
		return hourlyWage;
	}

	public void setHourlyWage(Double hourlyWage) {
		this.hourlyWage = hourlyWage;
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

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Regions getRegion() {
		return region;
	}

	public void setRegion(Regions region) {
		this.region = region;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = ServletUtil.codeFormat(address);
	}

	public Date getJobBeginTime() {
		return jobBeginTime;
	}

	public void setJobBeginTime(Date jobBeginTime) throws ParseException {
		this.jobBeginTime = jobBeginTime;
		// 顺便计算出工作年限
		this.jobYears = Utils.getDateYearDiffer(jobBeginTime) + "年";
	}

	public String getJobYears() {
		return jobYears;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getIndustryDetails() {
		return industryDetails;
	}

	public void setIndustryDetails(String industryDetails) {
		this.industryDetails = industryDetails;
	}

	public String getAddressAll() {
		return addressAll;
	}

	public void setAddressAll(String addressAll) {
		this.addressAll = addressAll;
	}

	public String getAppintmentCount() {
		return appintmentCount;
	}

	public void setAppintmentCount(String appintmentCount) {
		this.appintmentCount = appintmentCount;
	}

	public List<SkillInfo> getSkills() {
		return skills;
	}

	public void setSkills(List<SkillInfo> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", user=" + user + ", nickName=" + nickName + ", realName=" + realName
				+ ", introduce=" + introduce + ", ascendancy=" + ascendancy + ", avatar=" + avatar + ", identity="
				+ identity + ", telephone=" + telephone + ", gender=" + gender + ", birthday=" + birthday + ", age="
				+ age + ", hourlyWage=" + hourlyWage + ", score=" + score + ", scoreViews="
				+ Arrays.toString(scoreViews) + ", balance=" + balance + ", email=" + email + ", region=" + region
				+ ", address=" + address + ", jobBeginTime=" + jobBeginTime + ", jobYears=" + jobYears + ", jobInfo="
				+ jobInfo + ", jobType=" + jobType + ", label=" + label + ", skillName=" + skillName
				+ ", industryDetails=" + industryDetails + ", addressAll=" + addressAll + ", appintmentCount="
				+ appintmentCount + ", skills=" + skills + "]";
	}

}
