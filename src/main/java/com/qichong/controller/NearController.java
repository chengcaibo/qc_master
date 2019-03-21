package com.qichong.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qichong.entity.UserInfo;
import com.qichong.enums.RetEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.service.UserInfoService;
import com.qichong.service.UsersService;
import com.qichong.token.LoginToken;
import com.qichong.util.web.ServletUtil;

/**
 * 附近相关功能
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017-11-20 19:37:32
 */
@Controller
public class NearController {

	@Autowired
	UsersService usersService;
	@Autowired
	UserInfoService userInfoService;

	/** 查询附近的个人 */
	@RequestMapping(path = "/query/nearby/persoal", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES) // NOT_API
	@ResponseBody
	public String doQuerySpanPersoal(Filters filters, HttpSession session, LoginToken token) {
		filters.setUserId(ServletUtil.getThisLoginUser(session, token, usersService).getId());
		List<UserInfo> ui = userInfoService.queryNearbyPersoanl(filters);
		return new JSONResult().setValue(RetEnum.SUCCESS, ui);
	}

}
