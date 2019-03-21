package com.qichong.entity;

import java.util.Date;

import com.qichong.util.web.ServletUtil;

/**
 * 
 * @author wuzhiwei 实体类 commentaries 评论 对应表commentaries
 */
public class Commentaries {
	private int id;// id
	private Users user; // 预约人
	private Users BUsers; // 被预约人
	private UserInfo reviewers;// 评价人
	private UserInfo Breviewers;// 被评价人
	private String evluationContent;// 评价内容
	private Date commmentTime;// 评价时间
	private Double score;
	private Date startTime;
	private Date endTime;
	

	public Commentaries() {
	}

	public Commentaries(UserInfo reviewers, UserInfo breviewers, String evluationContent, Date commmentTime,
			Double score) {
		super();
		this.reviewers = reviewers;
		Breviewers = breviewers;
		this.evluationContent = evluationContent;
		this.commmentTime = commmentTime;
		this.score = score;
	}

	public Commentaries(int id, UserInfo reviewers, UserInfo breviewers, String evluationContent, Date commmentTime,
			Double score) {
		super();
		this.id = id;
		this.reviewers = reviewers;
		Breviewers = breviewers;
		this.evluationContent = evluationContent;
		this.commmentTime = commmentTime;
		this.score = score;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserInfo getReviewers() {
		return reviewers;
	}

	public void setReviewers(UserInfo reviewers) {
		this.reviewers = reviewers;
	}

	public UserInfo getBreviewers() {
		return Breviewers;
	}

	public void setBreviewers(UserInfo breviewers) {
		Breviewers = breviewers;
	}

	public String getEvluationContent() {
		return evluationContent;
	}

	public void setEvluationContent(String evluationContent) {
		this.evluationContent = ServletUtil.codeFormat(evluationContent);
	}

	public Date getCommmentTime() {
		return commmentTime;
	}

	public void setCommmentTime(Date commmentTime) {
		this.commmentTime = commmentTime;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}
	

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Users getBUsers() {
		return BUsers;
	}

	public void setBUsers(Users bUsers) {
		BUsers = bUsers;
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

	@Override
	public String toString() {
		return "Commentaries [id=" + id + ", user=" + user + ", BUsers=" + BUsers + ", reviewers=" + reviewers
				+ ", Breviewers=" + Breviewers + ", evluationContent=" + evluationContent + ", commmentTime="
				+ commmentTime + ", score=" + score + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
