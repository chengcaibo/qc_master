package com.qichong.controller;

import static com.qichong.util.web.ServletUtil.isEmpty;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qichong.entity.EnterpriseInfo;
import com.qichong.entity.JobType;
import com.qichong.entity.State;
import com.qichong.entity.UserInfo;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.enums.StateEnum;
import com.qichong.model.JSONResult;
import com.qichong.service.EnterpriseInfoService;
import com.qichong.service.SignService;
import com.qichong.service.UserInfoService;
import com.qichong.service.UsersService;
import com.qichong.token.LoginToken;
import com.qichong.util.sms.SMSUtil;
import com.qichong.util.web.ServletUtil;
import com.qichong.util.wechat.mini.WeChatMiniUtil;

/**
 * 登录、注册相关业务的Controller
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017-11-18 23:46:46
 */
@Controller
public class SignController {

	@Autowired
	UserInfoService userInfoService;

	@Autowired
	EnterpriseInfoService enterSer;
	@Autowired
	UsersService usersService;
	@Autowired
	EnterpriseInfoService enterpriseService;

	@Autowired
	SignService signService;

	/**
	 *
	 * @param nickName 用户注册时填写的昵称
	 * @param phoneNumber 用户注册时的手机号
	 * @param jsCode
	 * @param vCode 验证码
	 * @return
	 */
	@RequestMapping(path = "/sign/resume", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSignResume(String nickName, String phoneNumber, String jsCode, String vCode) {
		try {
			if (isEmpty(nickName, phoneNumber, vCode) > 0){

				return JSONResult.paramLack().toJSON();
			}
			// 通过手机号码查询出用户
			Integer userId = usersService.queryUserIdByPhoneNumber(phoneNumber);
			if (userId != null) {
				// 用户已存在，返回token和用户信息
				RetEnum ret = SMSUtil.validationVCode(phoneNumber, vCode);
				if (ret == RetEnum.SUCCESS) {
					SMSUtil.cleanVCode(phoneNumber);

					UserInfo uinfo = userInfoService.queryUserInfo(userId);
					Users user = uinfo.getUser();
					String password = user.getPassword();
					Map<String, Object> maps = signService.builderToken(userId, password, user.getWxOpenId(), null);
					maps.put("uinfo", uinfo);
					return JSONResult.success(maps).toJSON();
				}
				return JSONResult.builder(ret, "验证码不正确或已过期").toJSON();
			} else {
				// 用户不存在，新注册并返回token
				RetEnum ret = SMSUtil.validationVCode(phoneNumber, vCode);
				if (ret == RetEnum.SUCCESS) {
					SMSUtil.cleanVCode(phoneNumber);
					// 设置默认值
					Users user = new Users();
					user.setPassword("qichong1318");
					user.setState(new State(StateEnum.ACCOUNT_NORMAL));
					Map<String, String> map = WeChatMiniUtil.codeToOpenId(jsCode);
					if (map != null) {
						String openId = map.get("openId"), unionId = map.get("unionId");
						// 判断是否已绑定了奇虫号
						Users wechatUser = usersService.loginWeChat(openId, unionId);
						if (wechatUser == null) {
							user.setWxOpenId(openId);
							user.setWxUnionId(unionId);
						}
					}
					UserInfo uinfo = new UserInfo(user);
					uinfo.setNickName(nickName);
					uinfo.setTelephone(phoneNumber);
					if (uinfo.getJobType() == null){

						uinfo.setJobType(new JobType(2));
					}

					// 注册
					int uid = usersService.signupPersonal(uinfo);
					String password = user.getPassword();
					//向用户发送短信，告知用户名和密码
					SMSUtil.accountInfo(phoneNumber,"qichong1318");
					Map<String, Object> maps = signService.builderToken(uid, password, user.getWxOpenId(), null);
					return JSONResult.success(maps).toJSON();
				}
				return JSONResult.builder(ret, "验证码不正确或已过期").toJSON();
			}
		} catch (org.springframework.dao.DuplicateKeyException e) {
			System.out.println("捕获完整性约束异常：");
			return JSONResult.builder(RetEnum.ERROR, "手机号已被注册").toJSON();
		} catch (Exception e) {
			System.out.println("捕获异常：");
			e.printStackTrace();
			return JSONResult.builder(RetEnum.ERROR, e.getMessage()).toJSON();
		}
	}

	/**
	 * @author ing
	 * @param participatorId 分享者id
	 * @param redPackets 红包
	 * @param nickName 用户昵称
	 * @param phoneNumber 用户手机号
	 * @param vCode 短信验证码
	 * @return
	 * @description 分享注册领红包，分享者和注册信息者都会得到红包
	 */
	@RequestMapping(path = "/sign/signbyshare", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String signByShare(Integer participatorId,Double redPackets,String nickName, String phoneNumber,String vCode) {
		try {
			if (isEmpty(phoneNumber,vCode) > 0){

				return JSONResult.paramLack().toJSON();
			}
			// 通过手机号码查询出用户
			Integer userId = usersService.queryUserIdByPhoneNumber(phoneNumber);
			if (userId != null) {
				// 用户已存在，返回token和用户信息
				RetEnum ret = SMSUtil.validationVCode(phoneNumber, vCode);
				if (ret == RetEnum.SUCCESS) {
					SMSUtil.cleanVCode(phoneNumber);

					UserInfo uinfo = userInfoService.queryUserInfo(userId);
					Users user = uinfo.getUser();
					String password = user.getPassword();
					Map<String, Object> maps = signService.builderToken(userId, password, user.getWxOpenId(), null);
					maps.put("uinfo", uinfo);
					return JSONResult.success(maps).toJSON();
				}
				return JSONResult.builder(ret, "验证码不正确或已过期").toJSON();
			} else {
				// 用户不存在，新注册并返回token
				RetEnum ret = SMSUtil.validationVCode(phoneNumber, vCode);
				if (ret == RetEnum.SUCCESS) {
					SMSUtil.cleanVCode(phoneNumber);
					//创建新的账户并更新分享者的账户金额
					return userInfoService.createUserAndUpdateAccount(participatorId,redPackets,nickName,phoneNumber);
				}
				return JSONResult.builder(ret, "验证码不正确或已过期").toJSON();
			}
		} catch (org.springframework.dao.DuplicateKeyException e) {
			System.out.println("捕获完整性约束异常：");
			return JSONResult.builder(RetEnum.ERROR, "手机号已被注册").toJSON();
		} catch (Exception e) {
			System.out.println("捕获异常：");
			e.printStackTrace();
			return JSONResult.builder(RetEnum.ERROR, e.getMessage()).toJSON();
		}
	}

	/** 使用手机验证码登录 */
	@RequestMapping(path = "/login/with-phone-code", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doLoginWithPhoneCode(String phone, String code) {
		if (isEmpty(phone, code) > 0) {
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数").toJSON();
		}
		return signService.loginWithPhoneCode(phone, code).toJSON();
	}

	/**
	 * 个人注册验证
	 * 
	 * @param phone
	 *            要验证的手机号
	 * @return 0 - 手机号可用；103 - 手机号未激活；100 - 手机号已存在
	 */
	@RequestMapping(path = "/signup/personal/validation", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSignupPersonalValidation(String phone) {
		if (isEmpty(phone)) {
			return JSONResult.paramLack().toJSON();
		}
		return usersService.personalValidationPhone(phone).toJSON();
	}

	/** 个人账号激活方法 */
	@RequestMapping(path = "/signup/personal/activation", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSignupPersonalActivation(UserInfo userInfo, String vCode) {
		if (userInfo == null || userInfo.getUser() == null) {
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数").toJSON();
		}
		// 激活账号
		String password = userInfo.getUser().getPassword();
		return usersService.signupPersonalActivation(userInfo, password, vCode).toJSON();
	}

	/** 小程序的个人注册方法 */
	@RequestMapping(path = "/api/signup/personal", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doApiSignupPersonal(MultipartFile avatarFile, String put, UserInfo userInfo, String code,
			String vCode, HttpSession session) {
		if (userInfo == null || userInfo.getUser() == null
				|| (isEmpty(userInfo.getTelephone(), userInfo.getUser().getPassword()) > 0)) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "缺少参数");
		}
		try {

			// 判断验证码是否正确
			RetEnum ret = SMSUtil.validationVCode(userInfo.getTelephone(), vCode);
			if (ret != RetEnum.SUCCESS) {
				return new JSONResult().setValue(ret, "验证码错误或已过期");
			}

			// 将code换成openId
			// 将传入的code通过微信后台换成用户的openId
			Users temp = userInfo.getUser();
			if (!isEmpty(code)) {
				Map<String, String> map = WeChatMiniUtil.codeToOpenId(code);
				String openId = map.get("openId");
				String unionId = map.get("unionId");
				// 判断OpenId是否已经被绑定
				Users queryUser = usersService.loginWeChat(openId, unionId);
				if (queryUser != null) {
					return new JSONResult().setValue(RetEnum.VALUE_EXIST, "该微信账号已经绑定了一个账号了，不能再绑定了，请换一个微信账号进行绑定！");
				}
				temp.setWxOpenId(openId);
				temp.setWxUnionId(unionId);
			}
			userInfo.setUser(temp);
			// 执行注册方法
			int userId = usersService.signupPersonal(userInfo);

			// 更改用户头像
			if (avatarFile != null) {
				userInfoService.changeAvatar(userId, avatarFile, put, session);
			}

			// 进行微信登录
			String tokenKey = new LoginToken().newToken(null, false, userId, null, null);

			long timestamp = ServletUtil.getTimeStampDay(7);

			String keepCode = ServletUtil.keepCodeEncode(timestamp, userId, userInfo.getUser().getPassword());

			Map<String, String> maps = new HashMap<String, String>();
			maps.put("tokenKey", tokenKey);
			maps.put("keepCode", keepCode);

			SMSUtil.cleanVCode(userInfo.getTelephone());
			return new JSONResult().setValue(RetEnum.SUCCESS, maps);
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
			return new JSONResult().setValue(RetEnum.PARAM_ERROR, "手机号" + userInfo.getTelephone() + "已被注册！");
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONResult().setValue(RetEnum.EXCEPTION, e.getMessage());
		}
	}

	/** 判断我是否还在登录 */
	@RequestMapping(path = "/user/is_login", produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUserIsLogin(HttpServletRequest request, HttpServletResponse response) {
		JSONResult jr = new JSONResult();
		jr.setValue(RetEnum.SUCCESS);
		try {
			int ms = 1000, count = 1, maxCount = 60 * 3;
			// 牵制请求3分钟
			do {
				Users user;
				if (count == 1) {
					user = ServletUtil.isLogin(request, usersService, enterpriseService, false);
				} else {
					user = (Users) request.getSession().getAttribute("currentUser");
				}
				if (user == null) {
					jr.setValue(RetEnum.NO_LOGIN);
					// 销毁Session
					request.getSession().invalidate();
					// 清除Cookie
					ServletUtil.delCookie(response, "keepCode");
					break;
				} else {
					ServletContext sc = request.getSession().getServletContext();
					HttpSession temp = (HttpSession) sc.getAttribute(user.getId().toString());
					if (temp == null) {
						// 未存SessionId，可能是服务器重启导致Session丢失，重新登录
						ServletUtil.isLogin(request, usersService, enterpriseService, true);
					} else if (temp.getId().equals(request.getSession().getId())) {
						// 请求的SessionId等于保存的SessionId，说明当前是一个人
					} else {
						jr.setValue(RetEnum.NO_LOGIN);
						// 销毁Session
						request.getSession().invalidate();
						// 清除Cookie
						ServletUtil.delCookie(response, "keepCode");
						break;
					}
				}
				// 每次牵制1秒钟
				Thread.sleep(ms);
			} while (maxCount > ++count);
		} catch (java.lang.IllegalStateException ise) {
			jr.setValue(RetEnum.NO_LOGIN);
			// 清除Cookie
			ServletUtil.delCookie(response, "keepCode");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jr.toJSON();
	}

	/** 用户的登录方法 */
	@RequestMapping(path = "/login", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String OnSignIn(String username, String password, String jsCode, HttpSession session,
			HttpServletResponse response) {
		// 创建 JSONResult对象，用于返回JSON字符串
		JSONResult jr = new JSONResult();

		// 判断用户名或密码参数是否为空
		if (isEmpty(username) || isEmpty(password)) {
			return jr.setValue(RetEnum.PARAM_LACK, "用户名或密码不能为空！");
		}
		// 登录前清理登录的缓存
		ServletUtil.cleanSessionInLogoff(session);

		// 调用 Service 层方法进行登录操作
		Users result = usersService.login(username, password);
		// 判断是否登录成功
		if (result != null) {
			// 将登录成功后的用户存入Session中，默认超时时间为20分钟
			session.setAttribute("currentUser", result);
			// 将登录信息存入Cookie中，用于保持登录状态
			long timestamp = ServletUtil.getTimeStampDay(7);
			String keepCode = ServletUtil.keepCodeEncode(timestamp, result.getId(), result.getPassword());
			Cookie cookie = new Cookie("keepCode", keepCode);
			cookie.setMaxAge(60 * 60 * 24 * 7); // 7 天内自动登录
			response.addCookie(cookie);

			// 将Session存到Application里，来做限制只能在一台电脑上登录
			ServletUtil.applicationLogin(result.getId().toString(), session);

			return jr.setValue(RetEnum.SUCCESS, "登录成功！");
		} else {
			return jr.setValue(RetEnum.ERROR, "用户名或密码错误！");
		}
	}

	/**
	 * 注销登录
	 */
	@RequestMapping(path = "/logoff", method = RequestMethod.GET)
	public String doLogoff(HttpSession session, HttpServletResponse response) {
		// 销毁Session
		session.invalidate();
		// 清除Cookie
		ServletUtil.delCookie(response, "keepCode");
		return "redirect:/";
	}

	/**
	 * 注销登录--ajax方法
	 */
	@RequestMapping(path = "/logoff/login", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doLogoffLogin(HttpSession session, HttpServletResponse response) {
		JSONResult jr = new JSONResult();
		// 销毁Session
		session.invalidate();
		// 清除Cookie
		ServletUtil.delCookie(response, "keepCode");
		return jr.setValue(RetEnum.SUCCESS);
	}
	// @RequestMapping(path = "/admin/logoff", method = RequestMethod.POST)
	// public String doAdminLogoff(HttpSession session) {
	// session.setAttribute("adminToken", null);
	// return new JSONResult().setValue(RetEnum.SUCCESS);
	// }

	/**
	 * 个人用户注册
	 */
	@RequestMapping(path = "/sign/personal", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doPersonalSignup(UserInfo userInfo) {
		JSONResult jr = new JSONResult();
		// 判断必须参数是否为空
		if (isEmpty(userInfo.getTelephone()) || isEmpty(userInfo.getUser().getPassword())
				|| isEmpty(userInfo.getEmail())) {
			return jr.setValue(RetEnum.PARAM_LACK, "缺少必要的参数");
		}
		try {
			usersService.signupPersonal(userInfo);
			// 清除验证码
			SMSUtil.cleanVCode(userInfo.getTelephone());

			return jr.setValue(RetEnum.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return jr.setValue(RetEnum.EXCEPTION, e.getMessage());
		}
	}

	/** 企业账户注册 */
	@RequestMapping(path = "/sign/enterprise", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doEnterpriseSignup(EnterpriseInfo enterpriseInfo, MultipartFile logoFile, MultipartFile licenseFile,
			HttpSession session) {
		JSONResult jr = new JSONResult();
		// 判断必须参数是否为空
		if (isEmpty(enterpriseInfo.getUser().getPassword()) || isEmpty(enterpriseInfo.getEnterpriseName())
				|| isEmpty(enterpriseInfo.getPersonName()) || isEmpty(enterpriseInfo.getTelephone()) || logoFile == null
				|| licenseFile == null) {
			return jr.setValue(RetEnum.PARAM_LACK, "缺少必要的参数");
		}
		try {
			// 正式注册
			usersService.signupEnterprise(logoFile, licenseFile, enterpriseInfo);
			// 清除验证码
			SMSUtil.cleanVCode(enterpriseInfo.getTelephone());
			return jr.setValue(RetEnum.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return jr.setValue(RetEnum.EXCEPTION, e.getMessage());
		}
	}

	/**
	 * 小程序企业账户注册
	 */
	@RequestMapping(path = "/api/sign/enterprise", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doApiEnterpriseSignup(EnterpriseInfo enterpriseInfo, String vCode) {
		// 正式注册
		return usersService.signupEnterpriseInfo(enterpriseInfo, vCode).toJSON();
	}

	/** 后台企业账户注册 */
	@RequestMapping(path = "/admin/upload/enterprise", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doEnterpriseUpload(EnterpriseInfo enterpriseInfo, MultipartFile logoFile, MultipartFile licenseFile,
			HttpSession session) {
		JSONResult jr = new JSONResult();

		// 判断必须参数是否为空
		/*
		 * if (isEmpty(enterpriseInfo.getUser().getPassword()) ||
		 * isEmpty(enterpriseInfo.getEnterpriseName()) ||
		 * isEmpty(enterpriseInfo.getPersonName()) ||
		 * isEmpty(enterpriseInfo.getTelephone()) || logoFile == null ||
		 * licenseFile == null) { return jr.setValue(RetEnum.PARAM_LACK,
		 * "缺少必要的参数"); }
		 */
		try {
			// 正式注册
			usersService.signupEnterprise(logoFile, licenseFile, enterpriseInfo);
			// 清除验证码
			/* SMSUtil.cleanVCode(enterpriseInfo.getTelephone()); */
			return jr.setValue(RetEnum.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return jr.setValue(RetEnum.EXCEPTION, e.getMessage());
		}
	}
}
