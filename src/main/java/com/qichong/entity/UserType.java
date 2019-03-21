package com.qichong.entity;

/**
 * 实体类 User_Type 用户类别类 对应表 user_type
 */
public class UserType {

	private int id;// id
	private String name;// 用户类型

	public UserType() {}

	public UserType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserType [id=" + id + ", name=" + name + "]";
	}

}
