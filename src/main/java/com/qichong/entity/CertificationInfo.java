package com.qichong.entity;

import java.util.Date;

import com.qichong.enums.PathEnum;
import com.qichong.model.Image;

/**
 * 
 * 个人认证表，存储用户的认证信息，如果该表中没有 某用户 的记录，那么他将无法发送任何信息（certification_info）
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2018-2-7 14:27:04
 */
public class CertificationInfo {

	private Integer id;
	private Users user;
	private String realName;
	private String identity; // 身份证号
	private Image identityPictureA = new Image();; // 身份证正面照片
	private Image identityPictureB = new Image();; // 身份证反面照片
	private Image identityPictureC = new Image();; // 身份证手持照片
	private Date createTime;
	private Date editTime;
	private String reason; // 驳回理由

	private String formId; // 用于给微信用户推送的formId
	private State state; // 状态Id
	//是否已经同意发布需求注意事项,0代表
	private Integer isAgree;

	public Integer getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(Integer isAgree) {
		this.isAgree = isAgree;
	}

	public CertificationInfo() {
	}

	public CertificationInfo(Integer id) {
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Image getIdentityPictureA() {
		return identityPictureA;
	}

	public Image getIdentityPictureB() {
		return identityPictureB;
	}

	public Image getIdentityPictureC() {
		return identityPictureC;
	}

	/* setter */
	public void setIdentityPictureA(String identityPictureA) {
		this.identityPictureA.setPath(PathEnum.USER_IDENTITY);
		this.identityPictureA.setName(identityPictureA);
	}

	public void setIdentityPictureB(String identityPictureB) {
		this.identityPictureB.setPath(PathEnum.USER_IDENTITY);
		this.identityPictureB.setName(identityPictureB);
	}

	public void setIdentityPictureC(String identityPictureC) {
		this.identityPictureC.setPath(PathEnum.USER_IDENTITY);
		this.identityPictureC.setName(identityPictureC);
	}
	/* setter */

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	@Override
	public String toString() {
		return "CertificationInfo [id=" + id + ", user=" + user + ", realName=" + realName + ", identity=" + identity
				+ ", identityPictureA=" + identityPictureA.getName() + ", identityPictureB="
				+ identityPictureB.getName() + ", identityPictureC=" + identityPictureC.getName() + ", createTime="
				+ createTime + ", editTime=" + editTime + ", reason=" + reason + ", formId=" + formId + ", state="
				+ state + "]";
	}

}
