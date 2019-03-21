package com.qichong.entity;

import com.qichong.enums.StateEnum;

/**
 * 实体类 State 状态表 对应表state
 * 
 * @author 孙建雷
 */
public class State {

	private Integer id; // id
	private String state;// 状态名称

	public State() {
	}

	public State(StateEnum stateEnum) {
		this.id = stateEnum.getId();
	}

	public State(Integer id) {
		this.id = id;
	}

	public State(Integer id, String state) {
		this.id = id;
		this.state = state;
	}

	public State(String state) {
		super();
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "State [id=" + id + ", state=" + state + "]";
	}

}
