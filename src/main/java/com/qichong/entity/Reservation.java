package com.qichong.entity;

import java.util.Date;

public class Reservation {
	private int id;
	private EnterpriseInfo enterpriseInfo;
	private Users user;
	private DeskLocation location;
	private Date creationTime;
	private Date endTime;

	public Reservation() {
	}
	
	
	public Reservation(EnterpriseInfo enterpriseInfo, Users user, DeskLocation location, Date creationTime,
			Date endTime) {
		super();
		this.enterpriseInfo = enterpriseInfo;
		this.user = user;
		this.location = location;
		this.creationTime = creationTime;
		this.endTime = endTime;
	}


	public Reservation(int id, EnterpriseInfo enterpriseInfo, Users user, DeskLocation location, Date creationTime,
			Date endTime) {
		super();
		this.id = id;
		this.enterpriseInfo = enterpriseInfo;
		this.user = user;
		this.location = location;
		this.creationTime = creationTime;
		this.endTime = endTime;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EnterpriseInfo getEnterpriseInfo() {
		return enterpriseInfo;
	}

	public void setEnterpriseInfo(EnterpriseInfo enterpriseInfo) {
		this.enterpriseInfo = enterpriseInfo;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public DeskLocation getLocation() {
		return location;
	}

	public void setLocation(DeskLocation location) {
		this.location = location;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", enterpriseInfo=" + enterpriseInfo + ", user=" + user + ", location="
				+ location + ", creationTime=" + creationTime + ", endTime=" + endTime + "]";
	}
	
}
