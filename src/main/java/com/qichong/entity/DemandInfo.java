package com.qichong.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.qichong.enums.PathEnum;
import com.qichong.model.Image;
import com.qichong.util.web.ServletUtil;

public class DemandInfo {

	private Integer id;
	private Users user;
	private UserInfo userInfo;
	private EnterpriseInfo enterpriseInfo;
	private String content;// 需求内容
	private Image picture = new Image();
	private Double reward;// 可提供的最小酬劳
	private Double endReward; // 可提供的最大酬劳
	private String contact;// 联系人
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date releaseTime;// 发布时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime; // 需求结束时间，当前时间到结束时间为需求的有效时间
	private String phone;// 联系电话
	private String note;// 备注信息
	private SharedTypes sharedType; // 分类

	private String userType; // 用户标识分类

	private String address;// 详细地址
	private String addressName; // 地址名称
	private Double longitude;// 需求所在的经度
	private Double latitude; // 需求所在的纬度

	/** 距离数字原形，仅在筛选距离后使用，表示的是该需求距筛选用户的距离（米） */
	private Integer distanceNumber;
	// 将距离转为可识别字符串类型
	private String distance;
	// 状态实体类
	private State state;

	public void setDistanceNumber(Integer distanceNumber) {
		this.distanceNumber = distanceNumber;
		this.distance = ServletUtil.calcDistance(distanceNumber); // 计算距离
	}

	private Boolean isMySubmit = null;

	public DemandInfo() {
	}

	public DemandInfo(Integer id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = ServletUtil.codeFormat(content);
	}

	public Image getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture.setPath(PathEnum.DEMANDINFO_PICTURE);
		this.picture.setName(picture);
	}

	public Double getReward() {
		return reward;
	}

	public void setReward(Double reward) {
		this.reward = reward;
	}

	public Double getEndReward() {
		return endReward;
	}

	public void setEndReward(Double endReward) {
		this.endReward = endReward;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = ServletUtil.codeFormat(address);
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public Integer getDistanceNumber() {
		return distanceNumber;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = ServletUtil.codeFormat(contact);
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = ServletUtil.codeFormat(note);
	}

	public SharedTypes getSharedType() {
		return sharedType;
	}

	public void setSharedType(SharedTypes sharedType) {
		this.sharedType = sharedType;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public EnterpriseInfo getEnterpriseInfo() {
		return enterpriseInfo;
	}

	public void setEnterpriseInfo(EnterpriseInfo enterpriseInfo) {
		this.enterpriseInfo = enterpriseInfo;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Boolean getIsMySubmit() {
		return isMySubmit;
	}

	public void setIsMySubmit(Boolean isMySubmit) {
		this.isMySubmit = isMySubmit;
	}

}
