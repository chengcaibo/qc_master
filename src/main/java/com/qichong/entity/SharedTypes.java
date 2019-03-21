package com.qichong.entity;

/**
 * 共享分类表（shared_types）实体类
 * 
 * @创建人 吴志伟
 * @修改人 孙建雷
 */
public class SharedTypes {

	private String code; // 分类的Code
	private String name; // 分类的名称
	private String parentCode; // 分类的父Code

	public SharedTypes() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	@Override
	public String toString() {
		return "SharedTypes [code=" + code + ", name=" + name + ", parentCode=" + parentCode + "]";
	}

}
