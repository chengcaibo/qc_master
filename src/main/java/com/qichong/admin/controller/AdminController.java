package com.qichong.admin.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qichong.admin.util.AdminUtil;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.util.web.ServletUtil;

/**
 * 管理后台相关接口
 * 
 * @author 孙建雷
 */
@RestController
@RequestMapping(path = "/admin", produces = ServletUtil.JSON_PRODUCES)// NOT_API
public class AdminController {

	/**
	 * 管理员登录
	 * 
	 * @param loginKey
	 *            登录密码
	 */
	@RequestMapping(path = "/login", method = RequestMethod.POST)// NOT_API
	public String doAdminLogin(String loginKey, HttpSession session) {
		loginKey = String.valueOf(loginKey);
		if (loginKey.equals(AdminUtil.LOGIN_KEY)) {
			session.setAttribute("adminToken", AdminUtil.TOKEN_KEY);
			return new JSONResult().setValue(RetEnum.SUCCESS);
		}
		session.setAttribute("adminToken", null);
		return new JSONResult().setValue(RetEnum.ERROR, "密码错误");
	}

	/** 管理员注销登录 */
	@RequestMapping(path = "/logoff", method = RequestMethod.POST)// NOT_API
	public String doAdminLogoff(HttpSession session) {
		session.setAttribute("adminToken", null);
		return new JSONResult().setValue(RetEnum.SUCCESS);
	}

}
