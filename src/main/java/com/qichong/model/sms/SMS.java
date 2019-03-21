package com.qichong.model.sms;

import java.util.Date;

/**
 * 短信实体类，这个类不能直接实例化对象，只能通过子类调用
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017-11-16 14:07:55
 */
public class SMS {

	// 手机号码
	private String phone;

	// 创建时间
	private Date createTime;

	// 有效时间，单位为秒
	private int effectiveTime;

	// 失效时间
	private Date endTime;

	/**
	 * @param phone
	 * @param createTime 创建时间
	 * @param effectiveTime 有效时间，单位为秒
	 */
	protected SMS(String phone, int effectiveTime) {
		this.phone = phone;
		this.effectiveTime = effectiveTime;
		// 计算失效时间
		long current = System.currentTimeMillis();
		this.createTime = new Date(current);
		this.resetEffectiveTime(current);
	}

	/**
	 * 重置失效时间
	 */
	public void resetEffectiveTime() {
		this.endTime = new Date(System.currentTimeMillis() + (effectiveTime * 1000));
	}

	/**
	 * 私有，重置失效时间，保证准时性
	 */
	private void resetEffectiveTime(long current) {
		this.endTime = new Date(current + (effectiveTime * 1000));
	}

	public String getPhone() {
		return phone;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public int getEffectiveTime() {
		return effectiveTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	@Override
	public String toString() {
		return "SMS [phone=" + phone + ", createTime=" + createTime + ", effectiveTime=" + effectiveTime + ", endTime="
				+ endTime + "]";
	}

}
