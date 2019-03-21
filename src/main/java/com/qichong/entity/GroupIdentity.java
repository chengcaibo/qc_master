package com.qichong.entity;

public class GroupIdentity {
	private Integer id;
	private String identity;
	private String remark;

	public GroupIdentity() {
	}

	public GroupIdentity(Integer id) {
		this.id = id;
	}

	public GroupIdentity(Integer id, String identity, String remark) {
		this.id = id;
		this.identity = identity;
		this.remark = remark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "GroupIdentity [id=" + id + ", identity=" + identity + ", remark=" + remark + "]";
	}

}
