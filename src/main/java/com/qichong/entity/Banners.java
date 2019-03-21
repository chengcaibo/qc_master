package com.qichong.entity;

import java.util.Date;

import com.qichong.enums.PathEnum;
import com.qichong.model.Image;

/**
 * Banners
 * 
 * @author 孙建雷
 *
 */
public class Banners {

	private Integer id;
	private String name;
	private String content;
	private String url;
	private String path;
	private Image picture = new Image(PathEnum.BANNER_PICTURE);
	private Date beginTime;
	private Date endTime;
	private Boolean alwaysOnline;
	private transient Date createTime;
	private transient Integer typeId;
	private transient Integer statusId;
	private transient Integer displayOrder;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Image getPicture() {
		return picture;
	}

	public void setPicture(String name) {
		this.picture.setName(name);
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Boolean getAlwaysOnline() {
		return alwaysOnline;
	}

	public void setAlwaysOnline(Boolean alwaysOnline) {
		this.alwaysOnline = alwaysOnline;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

}
