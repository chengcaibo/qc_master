package com.qichong.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.qichong.entity.Users;
import com.qichong.service.EnterpriseInfoService;
import com.qichong.service.UsersService;
import com.qichong.util.web.ServletUtil;

/**
 *
 * 页面路由 Controller
 *
 * @author 孙建雷
 * 
 *         2017-10-12 13:58:21
 *
 */
@Controller
public class RouteController {

	/** 访问帮助中心 */
	@RequestMapping("/help-center")// NOT_API
	public String doHelpCenter() {
		return "footer-info/help-center";
	}

	/** 访问新手上路 */
	@RequestMapping("/novice-road")// NOT_API
	public String doNoviceRoad() {
		return "footer-info/novice-road";
	}

	/**
	 * 访问关于我们
	 */
	@RequestMapping("/about-us")// NOT_API
	public String doAbout() {
		return "about-us";
	}

	/**
	 * 访问主页
	 */
	@RequestMapping("/")// NOT_API
	public String doIndex() {
		return "index";
	}

	/** 访问引导页 */

	/*
	 * @RequestMapping("/welcome") public String doBootPage(String ie) { // y =
	 * yes if (!ServletUtil.isEmpty(ie) && ie.equals("y")) { return
	 * "boot/boot-page-ie"; } return "boot/boot-page"; }
	 */

	@RequestMapping("/test")// NOT_API
	public String doTest() {
		return "test";
	}

	// 访问【个人注册】页面
	@RequestMapping(path = "/signup-personal", method = RequestMethod.GET)// NOT_API
	public String doSignupPersonal() {
		return "sign/signup-personal";
	}

	// 访问【企业注册】页面
	@RequestMapping(path = "/signup-enterprise", method = RequestMethod.GET)// NOT_API
	public String doSignupEnterpr() {
		return "sign/signup-enterprise";
	}

	@RequestMapping(path = "/login", method = RequestMethod.GET)// NOT_API
	public String doLogin() {
		return "sign/login";
	}

	@RequestMapping("/region")// NOT_API
	public String doRegion() {
		return "region";
	}

	@Autowired
	UsersService usersService;

	@Autowired
	EnterpriseInfoService enterpriseService;

	/**
	 * 进入头部导航，并判断是否登录
	 */
	@RequestMapping(path = "/header", method = RequestMethod.GET)// NOT_API
	public String doHeaderIndex(HttpServletRequest request) {
		// 判断是否登录
		ServletUtil.isLogin(request, usersService, enterpriseService, true);
		return "common/header";
	}

	@RequestMapping("/four-zero-four")// NOT_API
	public String doFourZeroFour() {
		return "common/four-zero-four";
	}

	@RequestMapping("/aboutUs")// NOT_API
	public String doaboutUs() {
		return "/about-us";
	}

	@RequestMapping("/enterpriseInfo")// NOT_API
	public String doEnterpriseInfo() {
		return "enterprise/enterprise-info";
	}

	@RequestMapping("/posted")// NOT_API
	public String doPosted() {
		return "function/posted";
	}

	@RequestMapping("/updateSuccess")// NOT_API
	public String doUpdateSuccess() {
		return "function/update_success";
	}

	@RequestMapping("/seckillError")// NOT_API
	public String doSeckillError() {
		return "function/seckillError";
	}

	@RequestMapping("/seckillSuccess")// NOT_API
	public String doSeckillSuccess() {
		return "function/success-includes";
	}

	@RequestMapping("/updateError")// NOT_API
	public String doUpdateError() {
		return "function/update_error";
	}

	@RequestMapping("/demandSuccess")// NOT_API
	public String doDemandSuccess() {
		return "function/demand-success";
	}

	@RequestMapping("/addTime")// NOT_API
	public String doAddTime() {
		return "/addTime";
	}

	@RequestMapping("/rob-position")// NOT_API
	public String doRopPosition() {
		return "function/rob-position";
	}

	/*
	 * @RequestMapping("/journalism") public String doJournalism() { return
	 * "search/journalism"; }
	 */

	@RequestMapping("/requirements")// NOT_API
	public String doRequirements(HttpSession session, Model model) {
		Users currentUser = (Users) session.getAttribute("currentUser");
		if (currentUser == null) {
			model.addAttribute("errorTitle", "尚未登录！");
			model.addAttribute("errorBody", "尚未登录,您还没有权限访问此页面！");
			return "error";
		}
		return "demand/release";

	}

}
