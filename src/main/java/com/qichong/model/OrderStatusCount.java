package com.qichong.model;

/**
 * 订单状态统计
 * 
 * @author 13564
 *
 */
public class OrderStatusCount {

	private Integer count;
	private Integer s101;
	private Integer s102;
	private Integer s103;
	private Integer s104;
	private Integer s105;
	private Integer s106;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getS101() {
		return s101;
	}

	public void setS101(Integer s101) {
		this.s101 = s101;
	}

	public Integer getS102() {
		return s102;
	}

	public void setS102(Integer s102) {
		this.s102 = s102;
	}

	public Integer getS103() {
		return s103;
	}

	public void setS103(Integer s103) {
		this.s103 = s103;
	}

	public Integer getS104() {
		return s104;
	}

	public void setS104(Integer s104) {
		this.s104 = s104;
	}

	public Integer getS105() {
		return s105;
	}

	public void setS105(Integer s105) {
		this.s105 = s105;
	}

	public Integer getS106() {
		return s106;
	}

	public void setS106(Integer s106) {
		this.s106 = s106;
	}

	@Override
	public String toString() {
		return "OrderStatusCount [count=" + count + ", s101=" + s101 + ", s102=" + s102 + ", s103=" + s103 + ", s104="
				+ s104 + ", s105=" + s105 + ", s106=" + s106 + "]";
	}

}
