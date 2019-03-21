package com.qichong.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qichong.entity.GroupInfo;
import com.qichong.entity.State;
import com.qichong.entity.Theresponseteam;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.service.GroupInfoServcie;
import com.qichong.service.TheResponseteamService;
import com.qichong.util.web.ServletUtil;

@Controller
public class TheresponseteamController {
	@Autowired
	TheResponseteamService theResponseteamService;
	@Autowired
	GroupInfoServcie groupInfoService;

	@RequestMapping(path = "/insert/theResponseteam", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String selUserApintment(int userBId, int theResponseteamId, Integer RequiredNumber, HttpSession session,
			Model model) {
		Users currentUser = (Users) session.getAttribute("currentUser");
		JSONResult jr = new JSONResult();
		if (currentUser == null) {
			model.addAttribute("errorTitle", "尚未登录！");
			model.addAttribute("errorBody", "尚未登录,您还没有权限访问此页面！");
			return "error";
		} else {
			if (userBId == currentUser.getId()) {
				return jr.setValue(RetEnum.ERROR, "对不起响应自己暂未开放！");
			} else {
				GroupInfo group = groupInfoService.queryOneByGroupId(theResponseteamId);
				int sumNumber = 0;
				if (theResponseteamService.byCount(theResponseteamId) != null) {
					sumNumber = theResponseteamService.byCount(theResponseteamId);
				}
				if (sumNumber < group.getQuantity() && RequiredNumber < (group.getQuantity() - sumNumber)) {
					Theresponseteam theresponseteam = new Theresponseteam();
					theresponseteam.setResponseTeamTime(new Date());
					theresponseteam.setState(new State(14));
					theresponseteam.setUserA(currentUser);
					theresponseteam.setUserB(new Users(userBId));
					theresponseteam.setGroupInfo(new GroupInfo(theResponseteamId));
					theresponseteam.setRequiredNumber(RequiredNumber);
					boolean flag = theResponseteamService.addTheResponseteam(theresponseteam);
					if (flag) {
						return jr.setValue(RetEnum.SUCCESS, "已响应");
					} else {
						return jr.setValue(RetEnum.ERROR, "对不起响应失败！");
					}

				} else {
					return jr.setValue(RetEnum.VALUE_EXIST, "响应人数已超过团队人数！已没有人可分配，请见谅。");
				}
			}

		}
	}

	@RequestMapping(path = "/delete/theResponseteam", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String deleteTeamResponse(Integer teammId, Model model, HttpSession session) {
		Users currentUser = (Users) session.getAttribute("currentUser");
		JSONResult jr = new JSONResult();
		if (currentUser == null) {
			model.addAttribute("errorTitle", "尚未登录！");
			model.addAttribute("errorBody", "尚未登录,您还没有权限访问此页面！");
			return "error";
		} else {
			boolean flag = theResponseteamService.deleteTheResponseteam(teammId);
			if (flag) {
				return jr.setValue(RetEnum.SUCCESS, "已拒绝！且系统已把记录清空，");
			} else {
				return jr.setValue(RetEnum.ERROR, "拒绝失败！请重新拒绝。");
			}
		}
	}

	@RequestMapping(path = "/update/theResponseteam", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String updateTeamResponse(Integer teammId, Integer groupId, Model model, HttpSession session) {
		Users currentUser = (Users) session.getAttribute("currentUser");
		JSONResult jr = new JSONResult();
		if (currentUser == null) {
			model.addAttribute("errorTitle", "尚未登录！");
			model.addAttribute("errorBody", "尚未登录,您还没有权限访问此页面！");
			return "error";
		} else {
			int countA = theResponseteamService.byResponseIsNo(groupId);
			int countB = groupInfoService.queryOneByGroupId(groupId).getQuantity();
			if (countA < countB) {
				boolean flag = theResponseteamService.uodateTeam(teammId, 13);
				if (flag) {
					return jr.setValue(RetEnum.SUCCESS, "已确认！");
				} else {
					return jr.setValue(RetEnum.ERROR, "确认失败！请重新确认。");
				}
			} else {
				return jr.setValue(RetEnum.VALUE_EXIST, "确认失败！");
			}

		}
	}

	@RequestMapping(path = "/complete/theResponseteam", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String completeTeamResponse(Integer teammId, Model model, HttpSession session) {
		Users currentUser = (Users) session.getAttribute("currentUser");
		JSONResult jr = new JSONResult();
		if (currentUser == null) {
			model.addAttribute("errorTitle", "尚未登录！");
			model.addAttribute("errorBody", "尚未登录,您还没有权限访问此页面！");
			return "error";
		} else {
			boolean flag = theResponseteamService.uodateTeam(teammId, 11);
			if (flag) {
				return jr.setValue(RetEnum.SUCCESS, "已确认！");
			} else {
				return jr.setValue(RetEnum.ERROR, "确认失败！请重新拒绝。");
			}
		}
	}
}
