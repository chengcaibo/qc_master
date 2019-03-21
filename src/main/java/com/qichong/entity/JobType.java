package com.qichong.entity;

/**
 * JobType表
 * 
 * @author 孙建雷
 *
 */
public class JobType {

	private Integer id;
	private String type;

	public JobType() {
	}

	public JobType(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "JobType [id=" + id + ", type=" + type + "]";
	}

}
