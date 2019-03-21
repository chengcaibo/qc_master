package com.qichong.entity;

/**
 * 级别表实体类，用来存储资质的级别
 * 
 * @创建人 吴志伟
 * @修改人 孙建雷
 * @修改时间 2017-11-9 16:34:28
 */
public class IndustryAptitudeLevel {

	private int id; // id
	private String name;// 级别名称

	public IndustryAptitudeLevel() {}

	public IndustryAptitudeLevel(String name) {
		this.name = name;
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
		return "level [id=" + id + ", name=" + name + "]";
	}

}
