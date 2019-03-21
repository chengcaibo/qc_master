package com.qichong.entity;

import java.util.Date;
import com.qichong.enums.PathEnum;
import com.qichong.model.Image;
import com.qichong.util.web.ServletUtil;

/**
 * 实体类 小广告 对应表 ad_public
 * 
 * @author Administrator
 */

public class AdPublic {

	private Integer id; // id
	private Users user;// 用户 用户对象
	private UserInfo userInfo;
	private EnterpriseInfo enterpriseInfo;
	private String content;// 内容
	private String phone;
	private Image picture = new Image();// 图片
	private Date time; // 修改时间
	private State state; // 状态表 状态对象
	private SharedTypes sharedType; // 分类

	private String address;
	private String addressName;
	private Double latitude;
	private Double longitude;

	/** 距离数字原形，仅在筛选距离后使用，表示的是该需求距筛选用户的距离（米） */
	private Integer distanceNumber;
	// 将距离转为可识别字符串类型
	private String distance;
	// 状态实体类

	public void setDistanceNumber(Integer distanceNumber) {
		this.distanceNumber = distanceNumber;
		this.distance = ServletUtil.calcDistance(distanceNumber); // 计算距离
	}

	public AdPublic() {
	}

	public AdPublic(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = ServletUtil.codeFormat(address);
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
		this.picture.setPath(PathEnum.AD_PUBLIC);
		this.picture.setName(picture);
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public SharedTypes getSharedType() {
		return sharedType;
	}

	public void setSharedType(SharedTypes sharedType) {
		this.sharedType = sharedType;
	}

	@Override
	public String toString() {
		return "AdPublic [id=" + id + ", user=" + user + ", content=" + content + ", phone=" + phone + ", address="
				+ address + ", picture=" + picture + ", time=" + time + ", state=" + state + ", sharedType="
				+ sharedType + "]";
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

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Integer getDistanceNumber() {
		return distanceNumber;
	}

	public String getDistance() {
		return distance;
	}

}
