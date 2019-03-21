package com.qichong.controller;

import static com.qichong.util.web.ServletUtil.isEmpty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qichong.entity.CertificationEnterprise;
import com.qichong.entity.CertificationInfo;
import com.qichong.entity.GroupInfo;
import com.qichong.entity.GroupPersonnel;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.Filters;
import com.qichong.model.GroupInfoModel;
import com.qichong.model.JSONResult;
import com.qichong.service.CertificationEnterpriseService;
import com.qichong.service.CertificationInfoService;
import com.qichong.service.GroupInfoServcie;
import com.qichong.service.IndustryTypeService;
import com.qichong.service.RegionsService;
import com.qichong.service.TheResponseteamService;
import com.qichong.service.UsersService;
import com.qichong.token.LoginToken;
import com.qichong.util.Utils;
import com.qichong.util.web.ServletUtil;

@Controller
public class GroupController {

	@Autowired
	GroupInfoServcie groupService;
	@Autowired
	UsersService usersService;
	@Autowired
	RegionsService regionsService;
	@Autowired
	IndustryTypeService industryTypeService;
	@Autowired
	CertificationEnterpriseService certificationEnterpriseService;
	@Autowired
	TheResponseteamService theResponseteamService;
	@Autowired
	CertificationInfoService service;

	/* = = = = = = = = = = 以下是新版团队的方法 = = = = = = = = = = = = = = */

	/** 查询我加入的团队 */
	@RequestMapping(path = "/group/joined/self", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doGroupQueryJoinedSelf(HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return JSONResult.noLogin().toJSON();
		List<GroupPersonnel> list = groupService.queryGroupsByUserId(user.getId());
		return JSONResult.builder(RetEnum.SUCCESS).setList(list).toJSON();
	}

	/** 根据groupId查询团队内所有的人员信息 */
	@RequestMapping(path = "/group/personnel", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doGroupPersonnel(Integer groupId) {
		if (groupId == null)
			return JSONResult.paramLack().toJSON();
		List<GroupPersonnel> list = groupService.queryPersonnelByGroupId(groupId);
		return JSONResult.builder(RetEnum.SUCCESS).setList(list).toJSON();
	}

	/** 根据手机号码邀请一位人员加入团队 */
	@RequestMapping(path = "/group/personnel/invite", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doGroupPersonnelInvite(Integer groupId, String phone, HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return JSONResult.noLogin().toJSON();
		if (groupId == null || isEmpty(phone))
			return JSONResult.paramLack().toJSON();
		return groupService.inviteNewPersonnelByPhone(user.getId(), groupId, phone).toJSON();
	}

	/** 查询当前登录用户发布过的团体 */
	@RequestMapping(path = "/group/detail/self", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doGroupListSelf(Filters filters, HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return JSONResult.noLogin().toJSON();
		return this.doGroupList(filters, user.getId());
	}

	/** 根据UserId查询发布团体 */
	@RequestMapping(path = "/group/detail/list", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doGroupList(Filters filters, Integer userId) {
		if (userId == null)
			return JSONResult.paramLack().toJSON();
		if (filters == null)
			filters = new Filters();
		filters.setUserId(userId);
		// 查询团体
		List<GroupInfo> list = groupService.queryListByUserId(filters);
		return JSONResult.builder(RetEnum.SUCCESS).setList(list).toJSON();
	}

	/** 根据groupId查询团体信息 */
	@RequestMapping(path = "/group/detail/{groupId}", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doGroupDetail(@PathVariable("groupId") Integer groupId, HttpSession session, LoginToken token) {
		GroupInfo groupInfo = groupService.queryOneByGroupId(groupId);
		Integer count = theResponseteamService.byCount(groupId); // 查询已被响应的人数
		// 计算剩余人数
		Integer remainingNumber = groupInfo.getQuantity() - (count == null ? 0 : count);
		// 构造Map
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		boolean isMyGroup = groupInfo.getUser().getId().equals(loginUser.getId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupInfo", groupInfo);
		map.put("isMyGroup", isMyGroup);
		map.put("remainingNumber", remainingNumber);
		return new JSONResult().setValue(RetEnum.SUCCESS, map);
	}

	/* = = = = = = = = = = 以上是新版团队的方法 = = = = = = = = = = = = = = */

	/** 发布团体或修改团体信息，传入团体Id则是修改团体，不传则是发布团体 */
	@RequestMapping(path = "/group/submit-or-update", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doGroupSubmitOrUpdate(MultipartFile coverFile, GroupInfo group, HttpSession session,
			LoginToken token) {
		try {
			// 创建 JSONResult对象，用于返回JSON字符串
			Users user = ServletUtil.getThisLoginUser(session, token, usersService);
			if (user.getId() == null)
				return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录！");
			group.setUser(user);

			if (group.getId() == null) {
				return groupService.submit(coverFile, group).toJSON();
			} else {
				return groupService.updateOne(coverFile, group).toJSON();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, e.getMessage()).toJSON();
		}
	}

	/** 删除团体，必须登录才能删除 */
	@RequestMapping(path = "/group/delete", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doGroupDelete(Integer id, String pictureName, HttpSession session, LoginToken token) {
		try {
			Users user = ServletUtil.getThisLoginUser(session, token, usersService);
			if (user.getId() == null)
				return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录");
			return groupService.deleteOne(id, user.getId(), pictureName).toJSON();
		} catch (Exception e) {
			Throwable th = Utils.isQCError(e);
			if (th != null)
				return JSONResult.builder(RetEnum.ERROR, th.getMessage()).toJSON();
			return JSONResult.exception().toJSON();
		}
	}

	/** 根据UserId查询发布团体的总数 */
	@RequestMapping(path = "/query/total/group", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES) // NOT_API
	@ResponseBody
	public String doQueryTotalFollow(Integer userId) {
		Filters fil = new Filters();
		fil.setUserId(userId);
		int total = groupService.queryListByUserId(fil).size();
		return new JSONResult().setValue(RetEnum.SUCCESS, total);
	}

	/** 分页查询所有的团体信息，返回 JSON */
	@RequestMapping(path = "/query/all/group", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES) // NOT_API
	@ResponseBody
	public String doQueryAllGroup(Filters filters) {
		List<GroupInfo> list = groupService.queryAll(filters);
		int total = groupService.groupInfoFilters_total(filters, "");
		return new JSONResult().setValue(RetEnum.SUCCESS, "", list, total);
	}

	/** 返回Group对象，根据Id查询单个的团体信息 */
	@RequestMapping(path = "/groupInfo/{id}", method = RequestMethod.GET) // NOT_API
	public String byIdGroupInfo(@PathVariable("id") int id, Model model) {
		GroupInfo groupInfo = groupService.queryOneByGroupId(id);
		model.addAttribute("groupInfo", groupInfo);
		return "group/updateGroup";
	}

	/** 提交发布团体 */
	@RequestMapping(path = "/submit/group", method = RequestMethod.GET) // NOT_API
	public String queryParent(Model model, HttpSession session) {
		Users currentUser = (Users) session.getAttribute("currentUser");
		if (currentUser == null) {
			return "redirect:/login";
		} else {
			if (currentUser.getTypeId() == 1) {
				CertificationInfo ci = service.queryOneByUserId(currentUser.getId());
				if (ci != null) {
					if (ci.getState().getId() == 4 || ci.getState().getId() == 6) {
						model.addAttribute("title", "未实名认证");
						model.addAttribute("content", "您尚未通过未实名认证，暂时不能发布！");
						model.addAttribute("iconClass", "icon-error");
						return "function/common";
					} else {

						return "group/group-info";
					}
				} else {
					model.addAttribute("title", "未实名认证");
					model.addAttribute("content", "您尚未进行未实名认证，暂时不能发布！");
					model.addAttribute("iconClass", "icon-error");
					return "function/common";
				}

			} else if (currentUser.getTypeId() == 2) {
				CertificationEnterprise certificationEnterprise = certificationEnterpriseService
						.queryOneByUserId(currentUser.getId());
				if (certificationEnterprise != null) {
					if (certificationEnterprise.getState().getId() == 4
							|| certificationEnterprise.getState().getId() == 6) {
						model.addAttribute("title", "未实名认证");
						model.addAttribute("content", "您尚未通过未实名认证，暂时不能发布！");
						model.addAttribute("iconClass", "icon-error");
						return "function/common";
					} else {

						return "group/group-info";
					}
				} else {
					model.addAttribute("title", "未实名认证");
					model.addAttribute("content", "您尚未进行未实名认证，暂时不能发布！");
					model.addAttribute("iconClass", "icon-error");
					return "function/common";
				}

			} else {
				return "index";
			}

		}
	}

	/** 返回Group对象，根据Id查询单个的团体信息upId */
	@RequestMapping(path = "/group/{groupId}", method = RequestMethod.GET) // NOT_API
	public String doGroupInfoPage(@PathVariable("groupId") int groupId, HttpSession session, Model model) {
		Users currentUser = (Users) session.getAttribute("currentUser");
		if (currentUser == null) {
			model.addAttribute("errorTitle", "尚未登陆");
			model.addAttribute("errorBody", "你还没有登陆，没有权限访问此页面！");
			return "error";
		} else {
			GroupInfo groupInfo = groupService.queryOneByGroupId(groupId);
			// 查询推荐团体
			List<GroupInfoModel> likeGroupInfo = groupService.likeGroup(groupInfo.getIndustry().getIndustryCode());
			// 查询已经响应人数并计算剩余人数
			Integer count = theResponseteamService.byCount(groupId);
			int remainingNumber = groupInfo.getQuantity() - (count == null ? 0 : count);
			//
			model.addAttribute("groupInfo", groupInfo);
			model.addAttribute("remainingNumber", remainingNumber);
			model.addAttribute("likeGroupInfo", likeGroupInfo);
			return "group/team-show-casing";
		}
	}

	/** 跳转到团体页面 */
	@RequestMapping("/group") // NOT_API
	public String groupUpdate(HttpSession session, Model model) {
		/* 判断是个人还是企业 */
		Users currentUser = (Users) session.getAttribute("currentUser");
		if (currentUser == null) {
			model.addAttribute("errorTitle", "尚未登陆");
			model.addAttribute("errorBody", "你还没有登陆，没有权限访问此页面！");
			return "error";
		}
		int id = currentUser.getId();
		GroupInfo groupInfo = groupService.queryOneByGroupId(id);
		model.addAttribute("groupInfo", groupInfo);
		return "group/group-info";

	}

	/** 传入团体对象修改团体 */
	@RequestMapping(path = "/updateGroup", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES) // NOT_API
	public String updateGroup(MultipartFile groupFile, GroupInfo group, HttpSession session, Model model) {
		Users tempUser = (Users) session.getAttribute("currentUser");
		if (tempUser == null) {
			model.addAttribute("errorTitle", "尚未登录！");
			model.addAttribute("errorBody", "尚未登录,您还没有权限访问此页面！");
			return "error";
		}
		group.setUser(tempUser);
		JSONResult jr = groupService.updateOne(groupFile, group);
		if (jr.getRet() == RetEnum.SUCCESS.getValue()) {
			return "redirect:/updateSuccess";
		} else {
			model.addAttribute("errorTitle", "修改失败");
			model.addAttribute("errorBody", jr.getMsg());
			return "error";
		}

	}

	/** 团体审核修改状态 */
	@RequestMapping(path = "/manage/group/edit", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES) // NOT_API
	@ResponseBody
	public String manageGroupState(GroupInfo group, HttpSession session, LoginToken token) {
		// 创建 JSONResult对象，用于返回JSON字符串
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		// 判断用户是不是后台管理员
		Object adminToken = session.getAttribute("adminToken");
		if (user.getId() == null && adminToken == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录");
		}
		return groupService.updateOneState(group).toJSON();
	}

	/** 发布团体(admin) */
	@RequestMapping(path = "/admin/group/submit", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES) // NOT_API
	@ResponseBody
	public String doGroupSubmit(MultipartFile coverFile, GroupInfo group, HttpSession session) {
		try {
			Object token = session.getAttribute("adminToken");
			if (token == null)
				return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录！");
			// group.setUser(user);

			return groupService.submit(coverFile, group).toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, e.getMessage()).toJSON();
		}
	}

}
