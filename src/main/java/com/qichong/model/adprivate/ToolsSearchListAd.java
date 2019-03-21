package com.qichong.model.adprivate;

import java.util.Date;

import com.qichong.enums.PathEnum;
import com.qichong.model.Image;

/**
 * 工具搜索位列表位广告Model
 * 
 * @author 孙建雷
 *
 */
public class ToolsSearchListAd {

	private Integer id;
	private Integer userId;
	private String picture1;// 工具封面
	private String toolName;// 工具名称
	private String toolDescription;// 工具描述
	private String address;// 工具地址
	private String addressName;// 地址名称
	private Date createTime;// 创建时间
	private Date updateTime;// 更新时间
	private Double toolRent;// 工具租金

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

	public String getPicture1() {
		return picture1;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getToolDescription() {
		return toolDescription;
	}

	public void setToolDescription(String toolDescription) {
		this.toolDescription = toolDescription;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Double getToolRent() {
		return toolRent;
	}

	public void setToolRent(Double toolRent) {
		this.toolRent = toolRent;
	}

	/* 只能 setName */
	public void setPicture1(String picture1) {
		this.picture1 = new Image(PathEnum.TOOL_PICTURE, picture1).getUrl();
	}

}
