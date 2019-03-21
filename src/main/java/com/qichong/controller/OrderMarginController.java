package com.qichong.controller;

import static com.qichong.util.web.ServletUtil.isEmpty;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qichong.entity.OrderGroupMargin;
import com.qichong.entity.OrderUserMargin;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.service.OrderMarginService;
import com.qichong.service.UsersService;
import com.qichong.token.LoginToken;
import com.qichong.util.web.ServletUtil;

@RestController
@RequestMapping(path = "/order/margin", produces = ServletUtil.JSON_PRODUCES)
public class OrderMarginController {

	@Autowired
	OrderMarginService marginService;

	@Autowired
	UsersService usersService;

	/**
	 * 【团队】查询已支付成功的保证金总额
	 * 
	 * GET：https://api.qc1318.com/order/margin/group-query-margin-count"
	 * 
	 * @param groupId
	 * 
	 * @param tokenKey
	 * @param keepCode
	 */
	@RequestMapping(path = "/group-query-margin-count", method = RequestMethod.GET)
	public String doGroupQueryMarginCount(Integer groupId, LoginToken token, HttpSession session) {
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null)
			return JSONResult.noLogin().toJSON();
		if (groupId == null)
			return JSONResult.paramLack().toJSON();
		double count = marginService.queryMarginCountByGroupId(groupId);
		return JSONResult.builder(RetEnum.SUCCESS).setResult(count).toJSON();
	}

	/**
	 * 【团队】保证金查询
	 * 
	 * GET：https://api.qc1318.com/order/margin/group-query
	 * 
	 * @param groupId
	 * @param tokenKey
	 * @param keepCode
	 */
	@RequestMapping(path = "/group-query", method = RequestMethod.GET)
	public String doGroupQuery(Integer groupId, LoginToken token, HttpSession session) {
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null)
			return JSONResult.noLogin().toJSON();
		if (groupId == null)
			return JSONResult.paramLack().toJSON();
		List<OrderGroupMargin> ogms = marginService.queryByGroupId(groupId);
		ogms = marginService.removeSensitiveParams(ogms);
		return JSONResult.builder(RetEnum.SUCCESS).setList(ogms).toJSON();
	}

	/**
	 * 【团体】保证金补差价下单
	 * 
	 * POST：https://api.qc1318.com/order/margin/group-makeup-place
	 * 
	 * @param groupId
	 *            要补差价的团队id
	 * @param jsCode
	 *            wx.login获取的jsCode
	 * @param token
	 * @param session
	 */
	@RequestMapping(path = "/group-makeup-place", method = RequestMethod.POST)
	public String doGroupMakeupPlace(Integer groupId, String jsCode, LoginToken token, HttpSession session) {
		try {
			Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
			if (loginUser.getId() == null)
				return JSONResult.noLogin().toJSON();
			if (groupId == null || isEmpty(jsCode))
				return JSONResult.paramLack().toJSON();
			return marginService.makeupTheDiffWithGroup(groupId, loginUser.getId(), jsCode).toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, "系统出现异常").toJSON();
		}
	}

	// /** 【个人】保证金退款 */
	// @RequestMapping(path = "/user-refund", method = RequestMethod.GET)
	// public String doUserRefundUserId(int userId) {
	// System.out.println("Get");
	// return marginService.refundWithUser(userId).toJSON();
	// }

	/** 【个人】保证金退款 */
	@RequestMapping(path = "/user-refund", method = RequestMethod.POST)
	public String doUserRefund(LoginToken token, HttpSession session) {
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null)
			return JSONResult.noLogin().toJSON();
		return marginService.refundWithUser(loginUser.getId()).toJSON();
	}

	/** 【个人】保证金查询 */
	@RequestMapping(path = "/user-query", method = RequestMethod.GET)
	public String doUserQuery(LoginToken token, HttpSession session) {
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null)
			return JSONResult.noLogin().toJSON();
		OrderUserMargin oum = marginService.queryMarginByUserId(loginUser.getId());
		return JSONResult.builder(RetEnum.SUCCESS).setResult(oum).toJSON();
	}

	/**
	 * 【个人】保证金下单
	 * 
	 * POST：https://api.qc1318.com/order/margin/user-place
	 * 
	 * @param jsCode
	 *            wx.login获取的jsCode
	 * @param token
	 * @param session
	 */
	@RequestMapping(path = "/user-place", method = RequestMethod.POST)
	public String doUserPlace(String jsCode, String offerCode, LoginToken token, HttpSession session) {
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null)
			return JSONResult.noLogin().toJSON();
		return marginService.placeWithUser(loginUser.getId(), jsCode, offerCode).toJSON();
	}

}
