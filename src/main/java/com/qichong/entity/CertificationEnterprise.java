package com.qichong.entity;

import java.util.Date;

import com.qichong.enums.PathEnum;
import com.qichong.model.Image;

public class CertificationEnterprise {
	
	private Integer id;
	private Users user;
	private String enterpriseName;
	private String personName;
	private Image businessLicense = new Image(PathEnum.ENT_LICENSE); // 企业营业执照
	private Date createTime;
	private Date editTime;
	private String reason; // 驳回理由

	private String formId; // 用于给微信用户推送的formId
	private State state; // 状态Id
	//是否已经同意发布需求注意事项
	private Integer isAgree;

	public Integer getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(Integer isAgree) {
		this.isAgree = isAgree;
	}


	public CertificationEnterprise() {
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

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Image getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense.setName(businessLicense);
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Certification_Enterprise [id=" + id + ", user=" + user + ", enterpriseName=" + enterpriseName
				+ ", personName=" + personName + ", businessLicense=" + businessLicense + ", createTime=" + createTime
				+ ", editTime=" + editTime + ", reason=" + reason + ", formId=" + formId + ", state=" + state + "]";
	}

}
