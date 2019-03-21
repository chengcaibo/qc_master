package com.qichong.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qichong.entity.DemandInfo;
import com.qichong.entity.State;
import com.qichong.entity.TheResponse;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.service.TheResponseService;
import com.qichong.util.web.ServletUtil;

@Controller
public class TheResponseController {
	@Autowired
	TheResponseService theResponseService;

	/**
	 * 响应需求
	 * 
	 * @param userBId
	 * @param demandId
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/insert/theResponse", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String selUserApintment(int userBId, int demandId, HttpSession session, Model model) {
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
				Integer count = theResponseService.byCount(currentUser.getId(), userBId, demandId);
				if (count == null || count < 1) {
					TheResponse theResponse = new TheResponse();
					theResponse.setUserA(currentUser);
					theResponse.setUserB(new Users(userBId));
					theResponse.setDemand(new DemandInfo(demandId));
					theResponse.setState(new State(14));
					theResponse.setResponseTime(new Date());
					boolean flag = theResponseService.addTheResponse(theResponse);
					if (flag) {
						return jr.setValue(RetEnum.SUCCESS, "已响应");
					} else {
						return jr.setValue(RetEnum.ERROR, "对不起响应失败！");
					}

				} else {
					return jr.setValue(RetEnum.VALUE_EXIST, "您已响应过本次需求，不能重复响应！");
				}
			}

		}
	}

	/**
	 * 
	 * @param
	 * @return 删除拒绝的响应
	 */
	@RequestMapping(path = "/delete/theResponse", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String deleteOne(int id) {
		JSONResult jr = new JSONResult();
		boolean flag = theResponseService.deleteOne(id);
		if (flag) {
			return jr.setValue(RetEnum.SUCCESS, "已拒绝");
		} else {
			return jr.setValue(RetEnum.ERROR, "拒绝失败");
		}
	}

	/**
	 * 
	 * @param
	 * @return 响应需求的确认
	 */
	@RequestMapping(path = "/confrim/theResponse", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String confrimTheResponse(Integer theResponseId, Integer demandId) {
		JSONResult jr = new JSONResult();
		if (theResponseService.byTheResposIsNo(demandId) >= 0) {
			boolean flag = theResponseService.update(theResponseId, 13);
			if (flag) {
				return jr.setValue(RetEnum.SUCCESS, "更新成功！");
			} else {
				return jr.setValue(RetEnum.ERROR, "更新失败");
			}
		} else {
			return jr.setValue(RetEnum.VALUE_EXIST, "更新失败");
		}

	}

	/**
	 * 
	 * @param
	 * @return 响应需求的确认
	 */
	@RequestMapping(path = "/complete/theResponse", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String completeTheResponse(Integer theResponseId, Integer demandId) {
		JSONResult jr = new JSONResult();
		boolean flag = theResponseService.update(theResponseId, 11);
		if (flag) {
			return jr.setValue(RetEnum.SUCCESS, "更新成功！");
		} else {
			return jr.setValue(RetEnum.ERROR, "更新失败");
		}

	}

}
