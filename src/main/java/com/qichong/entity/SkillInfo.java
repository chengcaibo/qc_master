package com.qichong.entity;

import com.qichong.util.web.ServletUtil;

public class SkillInfo {
	private int id;
	private Users user;
	private String skillName;

	public SkillInfo() {
	}

	public SkillInfo(Users user, String skillName) {
		this.user = user;
		this.skillName = skillName;
	}

	public SkillInfo(int id, Users user, String skillName) {
		this.id = id;
		this.user = user;
		this.skillName = skillName;
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

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = ServletUtil.codeFormat(skillName);
	}

	@Override
	public String toString() {
		return "SkillInfo [id=" + id + ", user=" + user + ", skillName=" + skillName + "]";
	}
}
