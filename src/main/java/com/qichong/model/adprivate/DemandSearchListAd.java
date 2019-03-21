package com.qichong.model.adprivate;

import java.util.Date;

import com.qichong.enums.PathEnum;
import com.qichong.model.Image;

/**
 * 需求搜索位列表位广告Model
 * 
 * @author 孙建雷
 *
 */
public class DemandSearchListAd {

	private Integer id;
	private Integer userId;
	private String content;// 需求介绍
	private String picture;// 需求封面
	private String contact;// 联系人
	private String address; // 地址
	private String addressName; // 地址名称
	private Date releaseTime; // 发布时间
	private Double reward;// 酬劳

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPicture() {
		return picture;
	}

	/* 只能 setName */
	public void setPicture(String picture) {
		this.picture = new Image(PathEnum.DEMANDINFO_PICTURE, picture).getUrl();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Double getReward() {
		return reward;
	}

	public void setReward(Double reward) {
		this.reward = reward;
	}

}
