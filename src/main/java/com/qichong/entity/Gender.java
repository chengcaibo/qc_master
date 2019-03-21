package com.qichong.entity;

/**
 * 实体类 性别类 对应表 gender
 */
public class Gender {

	private Integer id;// id
	private String name;// 性别名称

	public Gender() {
	}

	public Gender(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
		return "Gender [id=" + id + ", name=" + name + "]";
	}

}
