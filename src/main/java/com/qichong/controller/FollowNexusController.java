package com.qichong.controller;

import static com.qichong.util.web.ServletUtil.getThisLoginUser;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.service.FollowNexusService;
import com.qichong.service.UsersService;
import com.qichong.token.LoginToken;
import com.qichong.util.web.ServletUtil;

@Controller
public class FollowNexusController {

	@Autowired
	FollowNexusService followNexusService;

	/** 查询是否关注了这个人 */
	@RequestMapping(path = "/user/is_follow", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUserIsFollow(int userId, LoginToken token,HttpSession session) {
		Users user =getThisLoginUser(session, token, usersService);
		// 首先判断传入的Token是否有效
		if (user.getId()== null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN);
		}
		boolean flag = followNexusService.followNexusUserACount(user.getId(), userId) > 0;
		return new JSONResult().setValue(RetEnum.SUCCESS, flag);
	}

	/**
	 * 分页查询所有的关注信息count，返回 JSON
	 */
	@RequestMapping(path = "/query/total/follows", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doQueryTotalFollow(Integer userId) {
		// 查询
		int total = followNexusService.queryFollowTotal(userId);
		return new JSONResult().setValue(RetEnum.SUCCESS, total);
	}

	/** 根据UserId查询关注或粉丝 */
	@RequestMapping(path = "/query/follow_{method}/{userId}", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUserFollows(@PathVariable("method") String method, @PathVariable("userId") Integer userId,
			Filters filters, HttpSession session, LoginToken token) {
		/* Step.1 查询出当前登录的用户 */
		Users loginUser = getThisLoginUser(session, token, usersService);// 当前登录的用户
		/* Step.2 判断是否登录 */
		Users queryUser = new Users(userId); // 待查询的用户
		if (userId == null) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "缺少参数或参数不合法");
		} else if (userId == 0) {// 传入的userId == 0 代表要查询当前登录用户的关注列表
			if (loginUser.getId() == null) {
				return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录");
			} else {
				queryUser.setId(loginUser.getId());
			}
		}
		/* Step.3 查询并返回 */
		int ida = queryUser.getId();
		Integer idb = loginUser.getId(); // 用来查询通过queryUser查询出来的人是否关注了loginUser或被loginUser关注
		if (method.equals("follows")) {
			return new JSONResult().setValue(RetEnum.SUCCESS, followNexusService.queryFollows(ida, idb, filters));
		} else if (method.equals("fans")) {
			return new JSONResult().setValue(RetEnum.SUCCESS, followNexusService.queryFans(ida, idb, filters));
		} else {
			return new JSONResult().setValue(RetEnum.PARAM_ERROR);
		}
	}

	/** 查询出我的粉丝（关注我的人） */
	// @RequestMapping(path = "/query/user_fans/{userId}", method =
	// RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	// @ResponseBody
	// public String doUserFollower(@PathVariable("userId") Integer userId,
	// Filters filters, HttpSession session,
	// LoginToken token) {
	// // 当前登录的用户
	// Users currentUser = getThisLoginUser(session, token, usersService);
	// // 待查询的用户
	// Users queryUser = new Users(userId);
	// if (userId == null) {
	// return new JSONResult().setValue(RetEnum.PAR_LACK, "缺少参数或参数不合法");
	// } else if (userId == 0) {// 传入的userId == 0 代表要查询当前登录用户的关注列表
	// if (currentUser.getId() == null) {
	// return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录");
	// } else {
	// queryUser.setId(currentUser.getId());
	// }
	// }
	// List<UserInfo> list = followNexusService.queryFans(currentUser.getId());
	// return new JSONResult().setValue(RetEnum.SUCCESS, list);
	// }

	@Autowired
	UsersService usersService;

	/**
	 * 添加关注
	 * 
	 * @param userId
	 *            被关注人的UserId
	 */
	@RequestMapping(path = "/add_follow", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String insertLikesInfo(Integer userId, HttpSession session, LoginToken token) {
		// 获取当前登录的用户
		Users currentUser = getThisLoginUser(session, token, usersService);
		// 首先判断传入的Token是否有效
		if (currentUser.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN);
		} else {
			return followNexusService.addFollow(currentUser.getId(), userId).toJSON();
		}
	}

	/**
	 * 取消关注
	 * 
	 * @param userId
	 *            被取消关注人的userId
	 */
	@RequestMapping(path = "/cancel_follow", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doCancelFollow(Integer userId, HttpSession session, LoginToken token) {
		// 当前登录的用户
		Users loginUser = getThisLoginUser(session, token, usersService);

		if (loginUser.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN);
		}
		followNexusService.cancelFollow(loginUser.getId(), userId);
		return new JSONResult().setValue(RetEnum.SUCCESS);
	}
}
