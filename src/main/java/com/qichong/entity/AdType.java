package com.qichong.entity;

/** AD类型表 */
public class AdType {

	public AdType() {
	}

	public AdType(Integer id) {
		this.id = id;
	}

	private Integer id;
	private String name;

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

}
