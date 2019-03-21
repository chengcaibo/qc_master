package com.qichong.admin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 后台管理路由Controller
 * 
 * @author 孙建雷
 */
@Controller
@RequestMapping(method = RequestMethod.GET)// NOT_API
public class AdminRouteController {

	/** 进入管理后台登录页面 */
	@RequestMapping(path = "/admin/login")// NOT_API
	public String doAdminLogin() {
		return "admin/login";
	}

	/** 进入管理后台 */
	@RequestMapping(path = "/admin")// NOT_API
	public void doAdmin(HttpServletResponse response) throws IOException {
		// return "admin/index";
		response.sendRedirect("/admin/home");
	}

	/* = = = 管理主页 = = = */

	/** 进入管理主页 */
	@RequestMapping(path = "/admin/home")// NOT_API
	public String doAdminHome(HttpServletResponse response) throws IOException {
		return "admin/index";
	}
	/** 进入黑名单管理主页 */
	@RequestMapping(path = "/admin/blacklist")// NOT_API
	public String doAllege(HttpServletResponse response) throws IOException {
		return "admin/blacklist/manage";
	}
	/** 进入某个用户的黑名单详情 */
	@RequestMapping(path = "/admin/blacklist/detail/{userId}")// NOT_API
	public String doBlackListDetail(Model model,HttpServletResponse response, @PathVariable("userId") Integer userId) throws IOException {
		model.addAttribute("userId",userId);
		return "admin/complaint-list/complain-list";
	}
	/** 进入某个用户的申诉列表 */
	@RequestMapping(path = "/admin/Allege/AllegeInfo/{userId}")// NOT_API
	public String doAllegeList(Model model,HttpServletResponse response,@PathVariable("userId")Integer userId) throws IOException {
		model.addAttribute("userId",userId);
		return "admin/allege-list/allege-list";
	}

	/** 进入某个用户的申诉列表 */
	@RequestMapping(path = "/admin/complain/complainlist")// NOT_API
	public String doComplaintList(HttpServletResponse response) throws IOException {
		return "admin/complaint-list/complain-list-users";
	}

	/* = = = 用户管理 = = = */

	/** 进入用户管理 */
	@RequestMapping(path = "/admin/users")// NOT_API
	public void doAdminUsers(HttpServletResponse response) throws IOException {
		response.sendRedirect("/admin/users/personal");
	}

	/** 进入个人用户管理 */
	@RequestMapping(path = "/admin/users/personal")// NOT_API
	public String doAdminUsersPersonal() {
		return "admin/users/personal/manage";
	}

	/** 进入添加个人用户 */
	@RequestMapping(path = "/admin/users/personal/addto")// NOT_API
	public String doAdminUsersPersonalAddto() {
		return "admin/users/personal/addto";
	}

	/** 进入企业用户管理 */
	@RequestMapping(path = "/admin/users/enterprise")// NOT_API
	public String doAdminUsersEnterprise() {
		return "admin/users/enterprise/manage";
	}

	/* = = = = = 审核管理 = = = = */

	/** 进入审核管理 */
	@RequestMapping(path = "/admin/audits")// NOT_API
	public void doAdminAudits(HttpServletResponse response) throws IOException {
		response.sendRedirect("/admin/audits/realname");
	}

	/** 进入实名认证审核 */
	@RequestMapping(path = "/admin/audits/realname")// NOT_API
	public String doAdminAuditsRealname(HttpServletResponse response) {
		return "admin/audits/realname";
	}

	/** 进入企业认证审核 */
	@RequestMapping(path = "/admin/audits/enterprise")// NOT_API
	public String doAdminAuditsEnterprise(HttpServletResponse response) {
		return "admin/audits/enterprise";
	}

}
