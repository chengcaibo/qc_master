package com.qichong.controller;

import static com.qichong.util.web.ServletUtil.isEmpty;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.qichong.entity.SettingPrivacy;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.service.SettingPrivacyService;
import com.qichong.service.UsersService;
import com.qichong.token.LoginToken;
import com.qichong.util.Utils;
import com.qichong.util.web.ServletUtil;

@RestController
@RequestMapping(path = "/setting/privacy", produces = ServletUtil.JSON_PRODUCES)
public class SettingPrivacyController {

	@Autowired
	UsersService usersService;

	@Autowired
	SettingPrivacyService service;

	/** 检查是否可以拨打电话 */
	@RequestMapping(path = "/check/make-phone-call", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUserCheckMakePhoneCall(String phoneNumber, HttpSession session, LoginToken token) {
		try {
			Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
			if (loginUser.getId() == null) {
				return JSONResult.noLogin().toJSON();
			} else if (isEmpty(phoneNumber)) {
				return JSONResult.paramLack("phoneNumber 不能为空").toJSON();
			} else {
				// 首先根据手机号码查询出userId
				Integer userId = usersService.queryUserIdByPhoneNumber(phoneNumber);
				if (userId == null)
					return JSONResult.builder(RetEnum.VALUE_NOT_EXIST, "手机号码不存在").toJSON();
				// 再查询出对方的隐私设置
				SettingPrivacy sp = service.queryOneByUserIdMustHaveValue(userId);
				return JSONResult.returnFlag(sp.getPublicPhone(), "根据对方的隐私设置，您无法执行此操作").toJSON();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.exception().toJSON();
		}
	}

	/** 查询当前登录用户的隐私设置 */
	@RequestMapping(path = "/query/self", method = RequestMethod.GET)
	public String doQuerySelf(HttpSession session, LoginToken token) {
		// 判断是否已登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null)
			return JSONResult.noLogin().toJSON();
		SettingPrivacy privacy = service.queryOneByUserIdMustHaveValue(loginUser.getId());
		return JSONResult.success(privacy).toJSON();
	}

	/** 更新当前登录用户的隐私设置 */
	@RequestMapping(path = "/update/self", method = RequestMethod.POST)
	public String doUpdateSelf(SettingPrivacy privacy, HttpSession session, LoginToken token) {
		// 判断是否已登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null)
			return JSONResult.noLogin().toJSON();
		else if (privacy == null)
			return JSONResult.paramLack().toJSON();
		else {
			privacy.setUserId(loginUser.getId());
			service.updateOne(privacy, false);
			return JSONResult.success().toJSON();
		}
	}

	/** 全局异常处理 */
	@ExceptionHandler(Exception.class)
	public String catchException(Exception ex) {
		Throwable th = null;

		// 判断是否是QCError
		th = Utils.isQCError(ex);
		if (th != null) {
			return JSONResult.error(th.getMessage()).toJSON();
		}

		// 判断是否是方法参数错误异常
		th = Utils.isException(ex, MethodArgumentTypeMismatchException.class);
		if (th != null) {
			return JSONResult.error("bad request - 所给参数类型与期望类型不符合").toJSON();
		}

		// 都不是则抛出
		System.out.println("= = = = = = BannerController = = = = = =");
		ex.printStackTrace();
		return JSONResult.exception().toJSON();
	}

}
