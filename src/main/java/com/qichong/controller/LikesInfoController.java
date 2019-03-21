package com.qichong.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qichong.entity.LikesInfo;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.service.LikesInfoService;
import com.qichong.util.web.ServletUtil;

@Controller
public class LikesInfoController {
	@Autowired
	LikesInfoService likesInfoService;

	/*
	 * @RequestMapping(path = "/insertLikesInfo", method = RequestMethod.GET)
	 * public String insertLikesInfo (int userId,HttpSession session){ Users
	 * currentUser = (Users) session.getAttribute("currentUser"); if(currentUser
	 * != null){ LikesInfo likesInfo = new LikesInfo(new UserInfo(new
	 * Users(currentUser.getId())), new UserInfo(new Users(userId)));
	 * if(likesInfoService.insertLikesInfo(likesInfo)){ return
	 * "redirect:/UserController/user/{userId}"+userId; } }
	 * 
	 * return "redirect:/common/four-zero-four"; }
	 */
	@RequestMapping(path = "/insertLikesInfo", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String insertLikesInfo(Integer userId, HttpSession session) {
		Users currentUser = (Users) session.getAttribute("currentUser");

		JSONResult jr = new JSONResult();
		if (currentUser == null) {
			return jr.setValue(RetEnum.NO_LOGIN, "您还没有登录，请先登录！！");
		} else if (userId == null) {
			return jr.setValue(RetEnum.PARAM_LACK, "缺少参数");
		} else {
			// 查询 A 给 B 点赞 的记录，并判断是否 > 0
			if(currentUser.getId() == userId){
				 return jr.setValue(RetEnum.PARAM_ERROR, "自己点赞功能！暂时未开放，敬请谅解。");
			}else{
				if (likesInfoService.likesInfoUserACount(currentUser.getId(), userId) > 0) {
					return jr.setValue(RetEnum.VALUE_EXIST, "您只可以点赞一次");
				} else {
					// A 给 B 点赞
					LikesInfo likesInfo = new LikesInfo(currentUser, new Users(userId));
					if (likesInfoService.insertLikesInfo(likesInfo)) {
						return jr.setValue(RetEnum.SUCCESS, "已点赞");
					} else {
						return jr.setValue(RetEnum.EXCEPTION, "程序执行错误！");
					}
				}
			}
			
		}
	}
}
