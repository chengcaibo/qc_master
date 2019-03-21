package com.qichong.entity;

/**
 * 
 * @author Administrator 实体类 位置类 对应表 desk_location
 */
public class DeskLocation {

	private int id;// id
	private String name;// 位置

	public DeskLocation() {}

	public DeskLocation(int id) {
		this.id = id;
	}

	public DeskLocation(String name) {
		this.name = name;
	}

	public DeskLocation(int id, String name) {
		super();
		this.id = id;
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
		return "Desk_location [id=" + id + ", name=" + name + "]";
	}

}
