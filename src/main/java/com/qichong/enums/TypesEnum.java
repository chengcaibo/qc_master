package com.qichong.enums;

public enum TypesEnum {

	BANNER_WEAPP_HOME(1, "小程序首页Banner");

	private int id;
	private String name;

	private TypesEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
