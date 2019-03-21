package com.qichong.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 实体类 appintment_time 对应时间表
 * 
 * @author wuzhiwei
 *
 */
public class AppintmentTime {

	private Integer id;// id
	private Users user;// 用户
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date startTime;// 开始时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date endTime;// 结束时间
	private String selectedTime;// 已选择的时间
	private Date dateIssued;// 发布时间
	private State state; // 状态 对象

	public AppintmentTime() {
	}

	public AppintmentTime(Users user, Date startTime, Date endTime, Date dateIssued, State state) {
		this.user = user;
		this.startTime = startTime;
		this.endTime = endTime;
		this.dateIssued = dateIssued;
		this.state = state;
	}

	// Getter & Setter
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

	public Date getDateIssued() {
		return dateIssued;
	}

	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getSelectedTime() {
		return selectedTime;
	}

	public void setSelectedTime(String selectedTime) {
		this.selectedTime = selectedTime;
	}

	@Override
	public String toString() {
		return "AppintmentTime [id=" + id + ", user=" + user + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", selectedTime=" + selectedTime + ", dateIssued=" + dateIssued + ", state=" + state + "]";
	}

}
