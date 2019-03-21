package com.qichong.model;

import java.util.Date;

public class LatestModel {

	private Integer id;
	private String content; // content 最多 22个字
	private Date time;
	private String type; // 最新消息的类型：personal、enterprise、demand、group

	public LatestModel(Integer id, String content, Date time, String type) {
		this.id = id;
		this.content = content;
		this.time = time;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "LatestModel [id=" + id + ", content=" + content + ", time=" + time + ", type=" + type + "]";
	}

}
