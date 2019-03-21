package com.qichong.model.sms;

/**
 * 验证码短信实体类
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017-11-16 14:07:42
 */
public class VCodeSMS extends SMS {

	/**
	 * 
	 * @param phone
	 *            手机号码
	 * @param vCode
	 *            验证码
	 * @param effectiveTime
	 *            有效时间，以秒为单位
	 */
	public VCodeSMS(String phone, String vCode, int effectiveTime) {
		super(phone, effectiveTime);
		this.vCode = vCode;
	}

	// vCode = Verification Code = 验证码
	private String vCode;

	public String getvCode() {
		return vCode;
	}

	public void setvCode(String vCode) {
		this.vCode = vCode;
	}

}
