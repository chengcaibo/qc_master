package com.qichong.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qichong.entity.MessageOffline;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.service.MessageOfflineService;
import com.qichong.token.LoginToken;
import com.qichong.util.web.ServletUtil;

/**
 * 消息Controller
 * 
 * @author 孙建雷
 *
 */
@Controller
public class MessageController {

	@Autowired
	MessageOfflineService messageOfflineService;

	/** 查询【当前登录用户】所有的未读消息 */
	@RequestMapping(path = "/message/query-unread-self", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doMessageQueryUnreadSelf(HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, UserController.usersService);
		if (user.getId() == null)
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
		Map<String, List<?>> map = new HashMap<String, List<?>>();
		// 查询【当前登录用户】所有的离线消息
		List<MessageOffline> msgoffs = messageOfflineService.queryListOfflineMessageByUserIdB(user.getId());
		map.put("msgoffs", msgoffs);
		// 查询点赞消息，回复消息，评论消息等
		return JSONResult.builder(RetEnum.SUCCESS).set(map).toJSON();
	}

	/** 查询【当前登录用户】所有的离线消息 */
	@RequestMapping(path = "/message/query-offline-self", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doMessageQueryOffline(HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, UserController.usersService);
		if (user.getId() == null)
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
		// 查询【当前登录用户】所有的离线消息
		List<MessageOffline> msgoffs = messageOfflineService.queryListOfflineMessageByUserIdB(user.getId());
		return JSONResult.builder(RetEnum.SUCCESS).setList(msgoffs).toJSON();
	}

	/** 删除【当前登录用户】所有的离线消息 */
	@RequestMapping(path = "/message/delete-offline-self", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doMessageDeleteOffline(HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, UserController.usersService);
		if (user.getId() == null)
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
		// 查询【当前登录用户】所有的离线消息
		Boolean flag = messageOfflineService.deleteByUserId(user.getId());
		return JSONResult.builder(flag ? RetEnum.SUCCESS : RetEnum.ERROR).setMsg(flag.toString()).toJSON();
	}

}
