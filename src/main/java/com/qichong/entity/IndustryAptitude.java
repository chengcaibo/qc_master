package com.qichong.entity;

import com.qichong.enums.PathEnum;
import com.qichong.model.Image;
import com.qichong.util.web.ServletUtil;

/**
 * 行业资质表实体类，对应每个行业的资质和级别
 * 
 * @创建人 吴志伟
 * @修改人 孙建雷
 * @修改时间 2017-11-9 17:07:34
 */
public class IndustryAptitude {

	private Integer id;// 资质唯一id
	private Users user;
	private UserInfo ui;
	private String text;// 资质介绍
	private Image picture = new Image();// 资质图片，若无可留空

	public IndustryAptitude() {
	}

	public UserInfo getUi() {
		return ui;
	}

	public void setUi(UserInfo ui) {
		this.ui = ui;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = ServletUtil.codeFormat(text);
	}

	public Image getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture.setPath(PathEnum.USER_CERT);
		this.picture.setName(picture);
	}

	@Override
	public String toString() {
		return "IndustryAptitude [id=" + id + ", ui=" + ui + ", text=" + text + "]";
	}

}
