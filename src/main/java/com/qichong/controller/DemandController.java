package com.qichong.controller;

import static com.qichong.model.JSONResult.error;
import static com.qichong.model.JSONResult.exception;
import static com.qichong.model.JSONResult.noLogin;
import static com.qichong.model.JSONResult.paramLack;
import static com.qichong.model.JSONResult.success;
import static com.qichong.util.web.ServletUtil.isEmpty;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qichong.entity.DemandInfo;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.service.DemandInfoService;
import com.qichong.service.UsersService;
import com.qichong.token.LoginToken;
import com.qichong.util.Utils;
import com.qichong.util.web.ServletUtil;

@Controller
public class DemandController {

	@Autowired
	DemandInfoService demandInfoService;
	@Autowired
	UsersService usersService;

	/** 需求重新开启 */
	@RequestMapping(path = "/demand/restart", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doDemandRestart(Integer id, String endTime, HttpSession session, LoginToken token) {
		try {
			Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
			if (loginUser.getId() == null)
				return noLogin().toJSON();
			if (id == null || isEmpty(endTime))
				return paramLack().toJSON();
			Date date = Utils.parseDate(endTime); // yyyy-MM-dd HH:mm:ss
			return demandInfoService.demandRestart(id, loginUser.getId(), date).toJSON();
		} catch (ParseException e) {
			return error("endTime日期格式不正确，格式必须为yyyy-MM-dd HH:mm:ss").toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return exception().toJSON();
		}
	}

	/** 需求续期，一次续期7天 */
	@RequestMapping(path = "/demand/renewal", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doDemandRenewal(Integer id, HttpSession session, LoginToken token) {
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null)
			return noLogin().toJSON();
		if (id == null)
			return paramLack().toJSON();
		return demandInfoService.demandRenewal(id, loginUser.getId()).toJSON();
	}

	/** 查询当前登录用户发布过的需求 */
	@RequestMapping(path = "/demand/detail/self", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doDemandMe(Filters filters, HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN);
		filters = filters == null ? new Filters() : filters;
		filters.setUserId(user.getId());
		List<DemandInfo> list = demandInfoService.queryListByUserId(filters);
		return new JSONResult().setValue(RetEnum.SUCCESS, list);
	}

	/** 根据需求Id查询单个需求信息 */
	@RequestMapping(path = "/demand/detail/{demandId}", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doDemandDetail(@PathVariable("demandId") Integer demandId, HttpSession session, LoginToken token) {
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		DemandInfo dinfo = demandInfoService.queryOneByDemandId(demandId, loginUser.getId());
		return success(dinfo).toJSON();
	}

	/** 分页查询所有的需求信息，返回 JSON */
	@RequestMapping(path = "/demand/query", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doDemandQuery(Filters filters) {
		List<DemandInfo> list = demandInfoService.search(filters);
		int total = demandInfoService.searchTotal(filters);
		return success(list).setTotal(total).toJSON();
	}

	/** form表单形式发布需求信息 */
	@RequestMapping(path = "/insertDemandInfo", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	public String insertDemandInfo(MultipartFile demandInfoFile, DemandInfo demandInfo, HttpSession session,
			Model model) {
		Users currentUser = (Users) session.getAttribute("currentUser");
		if (currentUser == null) {
			model.addAttribute("errorTitle", "尚未登录！");
			model.addAttribute("errorBody", "尚未登录,您还没有权限访问此页面！");
			return "error";
		} else {
			demandInfo.setUser(currentUser);
			JSONResult res = demandInfoService.submitDemand(demandInfoFile, demandInfo);
			if (res.getRet() == RetEnum.SUCCESS.getValue()) {
				return "redirect:/demandSuccess";
			} else {
				return "redirect:/four-zero-four";
			}
		}
	}

	/** 发布或者修改需求，传入需求Id则是修改需求，不传则是发布需求 */
	@RequestMapping(path = "/demand/submit-or-update", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String releaseDemandInfo(MultipartFile demandInfoFile, DemandInfo demandInfo, String fromClient,
			HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录");
		} else {
			demandInfo.setUser(user);
			if (demandInfo.getId() == null)
				return demandInfoService.submitDemand(demandInfoFile, demandInfo, fromClient).toJSON();
			else
				return demandInfoService.updateDemandInfo(demandInfoFile, demandInfo, fromClient).toJSON();
		}
	}

	/** form表单形式修改需求信息 */
	@RequestMapping(path = "/updateDemandInfo", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	public String updateDemandInfo(MultipartFile demandInfoFile, DemandInfo demandInfo, HttpSession session,
			Model model) {
		Users currentUser = (Users) session.getAttribute("currentUser");
		if (currentUser == null) {
			model.addAttribute("errorTitle", "尚未登录！");
			model.addAttribute("errorBody", "尚未登录,您还没有权限访问此页面！");
			return "error";
		} else {
			demandInfo.setUser(currentUser);
			JSONResult res = demandInfoService.updateDemandInfo(demandInfoFile, demandInfo);
			if (res.getRet() == RetEnum.SUCCESS.getValue()) {
				return "function/update_success";
			} else {
				return "redirect:/four-zero-four";
			}
		}
	}

	/** 修改需求信息--ajax */
	@RequestMapping(path = "/edit-demandinfo", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String editDemand(MultipartFile demandInfoFile, DemandInfo demandInfo, HttpSession session,
			LoginToken token) {
		// 判断是否登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		}
		JSONResult jr = demandInfoService.updateDemandInfo(demandInfoFile, demandInfo);
		return jr.toJSON();
	}

	/** 跳转到修改需求页面 */
	@RequestMapping(path = "/update/demand", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	public String updateDemand(int id, Model model) {
		DemandInfo demandInfo = demandInfoService.queryOneByDemandId(id);
		model.addAttribute("demandInfo", demandInfo);
		return "demand/update";
	}

	/** 跳转到需求详情页面 */
	@RequestMapping(path = "/one/demand", method = RequestMethod.GET)
	public String oneDemand(Integer demandId, HttpSession session, Model model) {
		Users currentUser = (Users) session.getAttribute("currentUser");
		if (currentUser == null) {
			model.addAttribute("errorTitle", "尚未登录！");
			model.addAttribute("errorBody", "尚未登录,您还没有权限访问此页面！");
			return "error";
		} else {
			DemandInfo demandInfo = demandInfoService.queryOneByDemandId(demandId);
			model.addAttribute("demand", demandInfo);

			return "demand/detail";
		}
	}

	/** 删除需求，必须登录才能删除 */
	@RequestMapping(path = "/demand/delete", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doDemandDelete(Integer id, String pictureName, HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录");
		return demandInfoService.deleteDemand(id, pictureName, user.getId()).toJSON();
	}

	/** 删除需求，必须登录才能删除（返回页面） */
	@RequestMapping(path = "/demand/delete", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	public String doDemandDelete(Integer id, String pictureName, HttpSession session, Model model) {
		Users tempUser = (Users) session.getAttribute("currentUser");
		if (tempUser == null) {
			model.addAttribute("errorTitle", "尚未登录");
			model.addAttribute("errorBody", "尚未登录或登录已过期");
			return "error";
		}
		JSONResult jr = demandInfoService.deleteDemand(id, pictureName, tempUser.getId());
		if (jr.getRet() == RetEnum.SUCCESS.getValue()) {
			return "function/del_success";
		} else {
			model.addAttribute("errorTitle", "删除失败");
			model.addAttribute("errorBody", jr.getMsg());
			return "error";
		}
	}

	/** 修改需求状态--ajax */
	@RequestMapping(path = "/manage/demand/edit", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String manageEditDemand(DemandInfo demandInfo, HttpSession session, LoginToken token) {
		// 判断是否登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		// 判断用户是不是后台管理员
		Object adminToken = session.getAttribute("adminToken");
		if (loginUser.getId() == null && adminToken == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		}
		JSONResult jr = demandInfoService.updateDemandState(demandInfo);
		return jr.toJSON();
	}

	/** form表单形式发布需求信息(admin) */
	@RequestMapping(path = "/admin/insert-demand", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	public String adminInsertDemand(MultipartFile demandInfoFile, DemandInfo demandInfo, HttpSession session,
			Model model) {
		// 判断当前登录的是不是admin用户
		Object token = session.getAttribute("adminToken");
		if (token == null) {
			model.addAttribute("errorTitle", "尚未登录！");
			model.addAttribute("errorBody", "尚未登录,您还没有权限访问此页面！");
			return "error";
		} else {
			// demandInfo.setUser(currentUser);
			JSONResult res = demandInfoService.submitDemand(demandInfoFile, demandInfo);
			if (res.getRet() == RetEnum.SUCCESS.getValue()) {
				return "redirect:/demandSuccess";
			} else {
				return "redirect:/four-zero-four";
			}
		}
	}

}
