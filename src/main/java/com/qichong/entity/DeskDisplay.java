package com.qichong.entity;

import java.util.Date;

/**
 * 
 * @author Administrator 实体类 前台显示类 对应表 desk_display
 */

public class DeskDisplay {

	private int id;// id
	private Users user;// 用户
	private DeskLocation location;// 位置对象
	private Date startTime;// 开始时间
	private Date endTime;// 结束时间

	public DeskDisplay() {}

	public DeskDisplay(int id) {
		super();
		this.id = id;
	}

	public DeskDisplay(Users user, DeskLocation location, Date satrtTime, Date endTime) {
		super();
		this.user = user;
		this.location = location;
		this.startTime = satrtTime;
		this.endTime = endTime;
	}

	public DeskDisplay(int id, Users user, DeskLocation location, Date satrtTime, Date endTime) {
		this.id = id;
		this.user = user;
		this.location = location;
		this.startTime = satrtTime;
		this.endTime = endTime;
	}

	/**
	 * 更新一条数据时的 构造函数
	 * 
	 * @param id 要更新的 id
	 * @param userId 要更新的用户Id
	 * @param endTime 结束时间
	 */
	public DeskDisplay(int id, int userId, Date endTime) {
		this.id = id;
		this.user = new Users(userId);
		this.startTime = new Date();
		this.endTime = endTime;
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

	public DeskLocation getLocation() {
		return location;
	}

	public void setLocation(DeskLocation location) {
		this.location = location;
	}

	public Date getSatrtTime() {
		return startTime;
	}

	public void setSatrtTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "DeskDisplay [id=" + id + ", user=" + user + ", location=" + location + ", satrtTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}

}
