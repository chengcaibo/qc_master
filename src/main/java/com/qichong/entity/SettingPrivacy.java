package com.qichong.entity;

/** 隐私设置实体类 */
public class SettingPrivacy {

	private Integer id;
	private Integer userId;
	private Boolean publicPhone;
	private Boolean publicEmail;

	public SettingPrivacy() {
	}

	public SettingPrivacy(Integer userId) {
		this.userId = userId;
	}

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

	public Boolean getPublicPhone() {
		return publicPhone;
	}

	public void setPublicPhone(Boolean publicPhone) {
		this.publicPhone = publicPhone;
	}

	public Boolean getPublicEmail() {
		return publicEmail;
	}

	public void setPublicEmail(Boolean publicEmail) {
		this.publicEmail = publicEmail;
	}

	@Override
	public String toString() {
		return "SettingPrivacy [id=" + id + ", userId=" + userId + ", publicPhone=" + publicPhone + ", publicEmail="
				+ publicEmail + "]";
	}

}
