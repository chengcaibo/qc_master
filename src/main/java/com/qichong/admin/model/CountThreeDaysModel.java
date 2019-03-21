package com.qichong.admin.model;

/**
 * 统计个人注册数量model
 * 
 * @author 孙建雷
 *
 */
public class CountThreeDaysModel {

	private int total; // 总注册量
	private int yesterday; // 昨日
	private int beforeYesterday;// 前日
	private int diff; // 昨日较前日注册量差

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getYesterday() {
		return yesterday;
	}

	public void setYesterday(int yesterday) {
		this.yesterday = yesterday;
	}

	public int getBeforeYesterday() {
		return beforeYesterday;
	}

	public void setBeforeYesterday(int beforeYesterday) {
		this.beforeYesterday = beforeYesterday;
	}

	public int getDiff() {
		return diff;
	}

	public void setDiff(int diff) {
		this.diff = diff;
	}

	@Override
	public String toString() {
		return "CountPersonalSignModel [total=" + total + ", yesterday=" + yesterday + ", beforeYesterday="
				+ beforeYesterday + ", diff=" + diff + "]";
	}

}
