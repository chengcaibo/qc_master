package com.qichong.model;

/**
 * 子对父Model
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2018年1月8日16:15:22
 */
public class ChildParentModel {

	private String code;
	private String child;
	private String parent;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "ChildParentModel [code=" + code + ", child=" + child + ", parent=" + parent + "]";
	}

}
