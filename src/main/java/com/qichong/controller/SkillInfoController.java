package com.qichong.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.service.SkillInfoService;
import com.qichong.service.UsersService;
import com.qichong.token.LoginToken;
import com.qichong.util.web.ServletUtil;

/**
 * 技能controller
 * 
 * @创建人：陈文瑾 @创建时间：2017-12-28 16:45:12
 *
 */

@Controller
public class SkillInfoController {
	@Autowired
	SkillInfoService skillInfoService;

	@Autowired
	UsersService usersService;

	/** 查询我的技能 */
	@RequestMapping(path = "/center/skill/self", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSkillSelf(HttpSession session, LoginToken token) {
		Users currentUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (currentUser.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		return this.doSkillUserId(currentUser.getId());
	}

	/** 根据UserId查询技能 */
	@RequestMapping(path = "/center/skill/user/{userId}", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSkillUserId(@PathVariable("userId") Integer userId) {
		return new JSONResult().setValue(RetEnum.SUCCESS, skillInfoService.queryAllByUserId(userId));
	}

	/** 根据Id查询技能 */
	@RequestMapping(path = "/center/skill/{id}", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSkillId(@PathVariable("id") Integer id) {
		return new JSONResult().setValue(RetEnum.SUCCESS, skillInfoService.queryAllById(id));
	}

	/** 新增技能 */
	@RequestMapping(path = "/center/skill/add", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSkillAdd(String[] skills, HttpSession session, LoginToken token) {
		Users currentUser = ServletUtil.getThisLoginUser(session, token, usersService);

		JSONResult jr = new JSONResult();
		if (currentUser.getId() == null) {
			return jr.setValue(RetEnum.NO_LOGIN, "尚未登陆");
		}
		int count = skillInfoService.skillCount(currentUser.getId());
		if (skills.length + count > 8) {
			// return jr.setValue(RetEnum.ERROR, "数据不能超过八条");
			return jr.setValue(RetEnum.ERROR, "你已有" + count + "个技能，无法再添加" + skills.length + "个技能，最多只能添加八个，请甄选后重试！");
		}
		try {
			skillInfoService.addTimes(skills, currentUser);
			return jr.setValue(RetEnum.SUCCESS, "添加成功");
		} catch (Exception e) {
			return jr.setValue(RetEnum.ERROR, "服务器匆忙，请稍后重试...");
		}
	}

	/** 修改技能 */
	@RequestMapping(path = "/center/skill/edit/{id}", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSkillEdit(@PathVariable("id") Integer id, String skill, HttpSession session, LoginToken token) {
		Users currentUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (currentUser.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登陆");
		} else if (skillInfoService.editOne(id, skill)) {
			return new JSONResult().setValue(RetEnum.SUCCESS);
		} else {
			return new JSONResult().setValue(RetEnum.ERROR, "服务器匆忙，请稍后重试...");
		}
	}

	/** 删除技能 */
	@RequestMapping(path = "/center/skill/del/{id}", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSkillDelete(@PathVariable("id") Integer id, HttpSession session, LoginToken token) {
		Users currentUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (currentUser.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登陆");
		} else if (skillInfoService.removeOneById(id)) {
			return new JSONResult().setValue(RetEnum.SUCCESS);
		} else {
			return new JSONResult().setValue(RetEnum.ERROR, "服务器匆忙，请稍后重试...");
		}
	}

}
