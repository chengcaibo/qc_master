package com.qichong.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.qichong.enums.PathEnum;
import com.qichong.model.Image;
import com.qichong.util.web.ServletUtil;

/**
 * 吴志伟 成功案例实体类
 */
public class CaseInfo {

	private int id;
	private Users user;
	private String caseName;
	private String caseContent;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;

	private String clientName;
	private String clientTelephone;
	private Image picture = new Image();
	private String clientAddress;

	public CaseInfo() {
	}

	public CaseInfo(int id, Users user, String caseName, String caseContent, Date startTime, Date endTime,
			String clientName, String clientTelephone, Image picture, String clientAddress) {
		this.id = id;
		this.user = user;
		this.caseName = caseName;
		this.caseContent = caseContent;
		this.startTime = startTime;
		this.endTime = endTime;
		this.clientName = clientName;
		this.clientTelephone = clientTelephone;
		this.picture = picture;
		this.clientAddress = clientAddress;
	}

	public CaseInfo(Users user, String caseName, String caseContent, Date startTime, Date endTime, String clientName,
			String clientTelephone, Image picture, String clientAddress) {
		this.user = user;
		this.caseName = caseName;
		this.caseContent = caseContent;
		this.startTime = startTime;
		this.endTime = endTime;
		this.clientName = clientName;
		this.clientTelephone = clientTelephone;
		this.picture = picture;
		this.clientAddress = clientAddress;
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

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = ServletUtil.codeFormat(caseName);
	}

	public String getCaseContent() {
		return caseContent;
	}

	public void setCaseContent(String caseContent) {
		this.caseContent = ServletUtil.codeFormat(caseContent);
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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = ServletUtil.codeFormat(clientName);
	}

	public String getClientTelephone() {
		return clientTelephone;
	}

	public void setClientTelephone(String clientTelephone) {
		this.clientTelephone = clientTelephone;
	}

	public Image getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture.setPath(PathEnum.SUCCESS_CASE);
		this.picture.setName(picture);
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = ServletUtil.codeFormat(clientAddress);
	}

	@Override
	public String toString() {
		return "CaseInfo [id=" + id + ", user=" + user + ", caseName=" + caseName + ", caseContent=" + caseContent
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", clientName=" + clientName
				+ ", clientTelephone=" + clientTelephone + ", clientAddress=" + clientAddress + "]";
	}

}
