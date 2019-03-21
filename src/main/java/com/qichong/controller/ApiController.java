package com.qichong.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.service.*;
import com.qichong.token.LoginToken;
import com.qichong.util.sms.SMSUtil;
import com.qichong.util.web.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

import static com.qichong.util.web.ServletUtil.isEmpty;

/**
 * API接口相关Controller
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017-11-20 19:37:32
 */
@Controller
@RequestMapping("/api") // NOT_API
public class ApiController {

	@Autowired
	SignService signService;

	@Autowired
	UsersService usersService;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	EnterpriseInfoService enterpriseService;

	@Autowired
	AdPublicService adPublicService;
	@Autowired
	GroupInfoServcie groupService;
	@Autowired
	DemandInfoService demandService;

	/** 更新用户的地理位置 */
	@RequestMapping(path = "/update_location", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES) // ADDED_TO_DOCS
	@ResponseBody
	public String doUpdateLocation(Double longitude, Double latitude, HttpSession session, LoginToken token) {
		Users currentUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (currentUser.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		}
		return usersService.updateLocation(new Users(currentUser.getId(), longitude, latitude)).toJSON();
	}

	/** 小程序修改用户头像 */
	@RequestMapping(path = "/user/upload/avatar", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES) // ADDED_TO_DOCS
	@ResponseBody
	public String doUploadAvatar(MultipartFile avatarFile, String put, LoginToken token, HttpSession session)
			throws Exception {
		// 获取当前登录的用户
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		// userId若为空则代表没有登录
		if (loginUser.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN);
		// 更改用户头像
		return userInfoService.changeAvatar(loginUser.getId(), avatarFile, put, session).toJSON();
	}

	/**
	 * 小程序里的登录，微信绑定openId
	 * 
	 * @param code
	 *            前台获取的code
	 */
	@RequestMapping(path = "/bind/wx_open_id", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES) // ADDED_TO_DOCS
	@ResponseBody
	public String doBindWxOpenId(String username, String password, String jsCode) {
		try {
			return signService.bindToWechat(username, password, jsCode, "mini").toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, "服务器异常").toJSON();
		}
	}

	/**
	 * 小程序微信登录
	 * 
	 * @param code
	 *            前台获取的code
	 */
	@RequestMapping(path = "/wx/login", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES) // ADDED_TO_DOCS
	@ResponseBody
	public String doLoginWx(String code) throws Exception {
		try {
			return signService.loginWithWeChat(code, "mini").toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, "服务器异常").toJSON();
		}
	}

	/**
	 * 【安卓】的绑定微信账号
	 * 
	 * @param code
	 *            前台获取的code
	 */
	@RequestMapping(path = "/app/bind/wechat", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES) // ADDED_TO_DOCS
	@ResponseBody
	public String doWxAppBindWechat(String username, String password, String code) {
		try {
			return signService.bindToWechat(username, password, code, "app").toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, "服务器异常").toJSON();
		}
	}

	/**
	 * 【安卓】的微信登录
	 * 
	 * @param code
	 *            前台获取的code
	 */
	@RequestMapping(path = "/app/login/wechat", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES) // ADDED_TO_DOCS
	@ResponseBody
	public String doWxAppLogin(String code) {
		try {
			return signService.loginWithWeChat(code, "app").toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, "服务器异常").toJSON();
		}
	}

	/** 发送【验证码短信】 */
	@RequestMapping(path = "/sms/send_vcode_sms", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES) // ADDED_TO_DOCS
	@ResponseBody
	public String doSendVCodeSMS(String phone) {
		JSONResult jr = new JSONResult();
		try {
			// 进行发送短信
			SendSmsResponse response = SMSUtil.sendSignupVCodeSMS(phone);
			// 验证是否发送成功
			if (response.getCode().equalsIgnoreCase("ok")) {
				return jr.setValue(RetEnum.SUCCESS);
			} else {
				return jr.setValue(RetEnum.ERROR, response.getMessage());
			}
		} catch (Exception e) {
			return jr.setValue(RetEnum.EXCEPTION, e.getMessage());
		}
	}

	/** 发送【修改密码验证码短信】 */
	@RequestMapping(path = "/sms/send_password_vcode_sms", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES) // ADDED_TO_DOCS
	@ResponseBody
	public String doSendPasswordVCodeSMS(String phone) {
		JSONResult jr = new JSONResult();
		try {
			// 进行发送短信
			SendSmsResponse response = SMSUtil.sendChangePasswordVCodeSMS(phone);
			// 验证是否发送成功
			if (response.getCode().equalsIgnoreCase("ok")) {
				return jr.setValue(RetEnum.SUCCESS);
			} else {
				return jr.setValue(RetEnum.ERROR, response.getMessage());
			}
		} catch (Exception e) {
			return jr.setValue(RetEnum.EXCEPTION, e.getMessage());
		}
	}

	@Autowired
	ValidationService vService;

	/**
	 * 发送验证码
	 * 
	 * @param type
	 *            使用哪种方式发送验证码：email-repwd、phone-login或phone-repwd；
	 * @param email
	 *            当type为email-repwd时必填，电子邮箱地址
	 * @param phone
	 *            当type为phone-login和phone-repwd时必填，手机号码
	 */
	@RequestMapping(path = "/validation/send-code", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES) // ADDED_TO_DOCS
	@ResponseBody
	public String doValidationSendCode(String type, String email, String phone) {
		try {
			if (isEmpty(type)) {
				return JSONResult.paramLack().toJSON();
			} else if (type.equals("phone-login") // 使用手机号登录
					|| type.equals("phone-repwd")// 使用手机号找回密码
					|| type.equals("phone-normal")) { // 手机号通用验证码
				return vService.sendCodeWithPhone(type, phone).toJSON();
			} else if (type.equals("email-repwd")) {// 使用邮箱地址找回密码
				return vService.sendCodeWithEmailRepwd(email).toJSON();
			} else {
				return JSONResult.builder(RetEnum.PARAM_ERROR, "不能识别的type参数").toJSON();
			}
		} catch (Exception e) {
			return JSONResult.exception().toJSON();
		}
	}

	/**
	 * 通用的验证接口
	 * 
	 * @param type
	 *            要验证的类型
	 * @param value
	 *            要验证的值
	 * @return
	 */
	@RequestMapping(path = "/validation", produces = ServletUtil.JSON_PRODUCES) // NOT_API
	@ResponseBody
	public String doValidation(String type, String value, String phone, String luoResponse) {
		JSONResult jr = new JSONResult();
		if (isEmpty(type) || isEmpty(value)) {
			return jr.setValue(RetEnum.PARAM_LACK, "参数为空或不完整");
		}
		switch (type) {
		case "vCode": // 短信验证码
			if (isEmpty(phone)) {
				return jr.setValue(RetEnum.PARAM_LACK, "参数为空或不完整");
			}
			return jr.setValue(SMSUtil.validationVCode(phone, value));
		case "luo-validation":
			return jr.setValue(ServletUtil.luoValidation(value));
		case "personal-phone": // 个人手机号码
			if (vService.personalPhone(value)) {
				return jr.setValue(RetEnum.VALUE_EXIST);
			} else {
				return jr.setValue(RetEnum.VALUE_NOT_EXIST);
			}
		case "personal-email": // 个人邮箱账号
			if (vService.personalEmail(value)) {
				return jr.setValue(RetEnum.VALUE_EXIST);
			} else {
				return jr.setValue(RetEnum.VALUE_NOT_EXIST);
			}
		case "enterprise-name": // 企业 名称
			if (vService.enterpriseName(value)) {
				return jr.setValue(RetEnum.VALUE_EXIST);
			} else {
				return jr.setValue(RetEnum.VALUE_NOT_EXIST);
			}
		case "enterprise-phone": // 企业 手机号码
			return jr.setValue(RetEnum.ERROR, "尚未实现！");
		case "enterprise-email": // 企业 邮箱账号
			return jr.setValue(RetEnum.ERROR, "尚未实现！");
		default:
			return jr.setValue(RetEnum.PARAM_ERROR, "未识别的验证类型");
		}
	}

	/** 上传APP报错日志 */
	@RequestMapping(path = "/upload/applog", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES) // NOT_API
	@ResponseBody
	public String doUploadAppLog(MultipartFile avatarFile, LoginToken token, HttpSession session) {
		// 获取当前登录的用户
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		// userId若为空则代表没有登录
		if (loginUser.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN);
		// 上传日志
		return userInfoService.uploadLog(loginUser.getId(), avatarFile).toJSON();
	}

}
