package com.qichong.service;

import static com.qichong.util.web.ServletUtil.isEmpty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.qichong.dao.EnterpriseInfoDao;
import com.qichong.dao.UserInfoDao;
import com.qichong.entity.UserInfo;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.util.sms.SMSUtil;

/**
 * 用做验证的Service层
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017年11月17日 上午11:59:10
 */
@Service
public class ValidationService {

	@Autowired
	UserInfoDao userInfoDao;

	/** 使用手机号登录 */
	public JSONResult sendCodeWithPhone(String type, String phone) {
		if (isEmpty(phone))
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数");
		if (!type.equals("phone-normal")) {
			List<UserInfo> list = userInfoDao.selectEqualsKey("telephone", phone);
			if (list.size() == 0) {
				return JSONResult.builder(RetEnum.VALUE_NOT_EXIST, "手机号码不存在");
			}
		}
		try {
			SendSmsResponse res = null;
			if (type.equals("phone-login")) {
				res = SMSUtil.sendPhoneLoginVCodeSMS(phone);
			} else if (type.equals("phone-repwd")) {
				res = SMSUtil.sendChangePasswordVCodeSMS(phone);
			} else if (type.equals("phone-normal")) {
				res = SMSUtil.sendNormalSMS(phone);
			}
			if (res.getCode().equalsIgnoreCase("OK"))
				return JSONResult.builder(RetEnum.SUCCESS, res.getCode() + ":" + res.getMessage());
			else if (res.getCode().equalsIgnoreCase("isv.BUSINESS_LIMIT_CONTROL")) {
				return JSONResult.builder(RetEnum.ERROR, "验证码发送过于频繁");
			} else
				return JSONResult.builder(RetEnum.ERROR, "验证码发送失败：" + res.getMessage());
		} catch (ClientException e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.ERROR, "验证码发送失败");
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.exception();
		}
	}

	/** 使用邮箱找回密码 */
	public JSONResult sendCodeWithEmailRepwd(String email) {
		if (isEmpty(email))
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数");
		return JSONResult.builder(RetEnum.ERROR, "暂未开放");
	}

	/**
	 * 验证个人手机号码，已存在返回 true，不存在返回false
	 * 
	 * @param phone
	 *            要验证的手机号
	 */
	public boolean personalPhone(String phone) {
		return userInfoDao.selectEqualsKey("telephone", phone).size() > 0;
	}

	/**
	 * 验证个人手机号码，已存在返回 true，不存在返回false
	 * 
	 * @param phone
	 *            要验证的手机号
	 */
	public boolean personalEmail(String email) {
		return userInfoDao.selectEqualsKey("email", email).size() > 0;
	}

	@Autowired
	EnterpriseInfoDao enterpriseInfoDao;

	/**
	 * 验证企业的名称，已存在返回 true，不存在返回 false
	 * 
	 * @param name
	 *            要验证的企业名称
	 */
	public boolean enterpriseName(String name) {
		return enterpriseInfoDao.selectEqualsKey("enterprise_name", name).size() > 0;
	}

	/**
	 * 验证企业的名称，已存在返回 true，不存在返回 false
	 * 
	 * @param name
	 *            要验证的企业名称
	 */
	// public boolean enterpriseName(String name) {
	// return enterpriseInfoDao.selectEqualsKey("enterprise_name", name).size()
	// > 0;
	// }
}
