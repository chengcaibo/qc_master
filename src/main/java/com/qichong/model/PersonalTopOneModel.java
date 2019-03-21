package com.qichong.model;

import java.text.ParseException;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.qichong.entity.Gender;
import com.qichong.entity.JobInfo;
import com.qichong.entity.Users;
import com.qichong.enums.PathEnum;
import com.qichong.util.Utils;
import com.qichong.util.web.ServletUtil;

public class PersonalTopOneModel {

	private int userId;
	private String introduce;
	private Image avatar = new Image(); // 头像
	private String nickName;
	private String realName; // 真实姓名
	private String regionCode; // 地区代码
	private String regionName; // 地区名

	private JobInfo jobInfo;

	private Double score; // 评分
	private String[] scoreViews;

	private double hourlyWage;// 时薪
	private String industryName;// 首选行业
	private String levelName; // 资质
	private Date startTime;
	private Date endTime;
	private int id; // 唯一ID
	private Users user; // 用户表中的id
	private String identity;// 身份证号
	private String telephone;// 手机
	private Gender gender; // 性别id
	private String genderName;// 性别名称

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;// 出生日期

	private double balance;// 余额
	private String email;// 邮箱地址
	private String address; // 详细地址
	private int age; // 年龄

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date jobBeginTime; // 工作开始时间，用于计算工作年限
	private String jobYears; // 工作年限

	private String ascendancy;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public Image getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar.setPath(PathEnum.USER_AVATAR);
		this.avatar.setName(avatar);
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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

	public JobInfo getJobInfo() {
		return jobInfo;
	}

	public void setJobInfo(JobInfo jobInfo) {
		this.jobInfo = jobInfo;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public double getScore() {
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

	public double getHourlyWage() {
		return hourlyWage;
	}

	public void setHourlyWage(double hourlyWage) {
		this.hourlyWage = hourlyWage;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
		// 顺便计算出年龄
		this.age = Utils.calcAgeByBirthday(birthday);
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public String getAscendancy() {
		return ascendancy;
	}

	public void setAscendancy(String ascendancy) {
		this.ascendancy = ascendancy;
	}

}
