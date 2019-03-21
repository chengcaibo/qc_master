package com.qichong.controller;

import static com.qichong.util.web.ServletUtil.cleanSession;
import static com.qichong.util.web.ServletUtil.isEmpty;
import static com.qichong.util.web.ServletUtil.isLogin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.qichong.entity.AdPublic;
import com.qichong.entity.AppintmentTime;
import com.qichong.entity.CaseInfo;
import com.qichong.entity.CertificationInfo;
import com.qichong.entity.Commentaries;
import com.qichong.entity.DemandInfo;
import com.qichong.entity.EnterpriseInfo;
import com.qichong.entity.GroupInfo;
import com.qichong.entity.IndustryAptitude;
import com.qichong.entity.IndustryNexus;
import com.qichong.entity.JobInfo;
import com.qichong.entity.Regions;
import com.qichong.entity.SkillInfo;
import com.qichong.entity.TheResponse;
import com.qichong.entity.Theresponseteam;
import com.qichong.entity.UserApintmentUser;
import com.qichong.entity.UserInfo;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.model.PersonalTopOneModel;
import com.qichong.service.AdPublicService;
import com.qichong.service.AppintmentTimeService;
import com.qichong.service.CaseInfoService;
import com.qichong.service.CertificationInfoService;
import com.qichong.service.CommentariesService;
import com.qichong.service.DemandInfoService;
import com.qichong.service.EnterpriseInfoService;
import com.qichong.service.FollowNexusService;
import com.qichong.service.GroupInfoServcie;
import com.qichong.service.IndustryAptitudeService;
import com.qichong.service.IndustryTypeService;
import com.qichong.service.JobInfoService;
import com.qichong.service.LikesInfoService;
import com.qichong.service.RegionsService;
import com.qichong.service.SkillInfoService;
import com.qichong.service.TheResponseService;
import com.qichong.service.TheResponseteamService;
import com.qichong.service.UserApintmentUserService;
import com.qichong.service.UserInfoService;
import com.qichong.service.UsersService;
import com.qichong.token.LoginToken;
import com.qichong.util.web.ServletUtil;
import com.qichong.util.wechat.mini.WeChatMiniUtil;

/**
 * 用户信息操作的Controller
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017年11月23日 下午7:10:57
 */
@Controller
public class UserController {

	public static UsersService usersService;

	@Autowired
	public void setUsersService(UsersService usersService) {
		UserController.usersService = usersService;
	}

	@Autowired
	EnterpriseInfoService enterpriseInfoService;

	@Autowired
	EnterpriseInfoService enterpriseService;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	GroupInfoServcie groupService;
	@Autowired
	AdPublicService adPublicService;
	@Autowired
	AppintmentTimeService appintmentService;
	@Autowired
	UserApintmentUserService userApintmentUserService;
	@Autowired
	IndustryAptitudeService AptitudeService;
	@Autowired
	RegionsService regionsService;
	@Autowired
	SkillInfoService skillInfoService;
	@Autowired
	CaseInfoService caseInfoService;
	@Autowired
	IndustryAptitudeService industryAptitudeService;

	@Autowired
	Gson gson;

	@Autowired
	SignController signController;
	@Autowired
	CommentariesService commentariesService;
	@Autowired
	LikesInfoService likesInfoService;
	@Autowired
	FollowNexusService followNexusService;

	@Autowired
	IndustryTypeService industryTypeService;
	@Autowired
	JobInfoService jobInfoService;
	@Autowired
	TheResponseService theResponseService;
	@Autowired
	TheResponseteamService theResponseteamService;
	@Autowired
	DemandInfoService demandInfoService;

	@Autowired
	CertificationInfoService cerfitiationInfoService;

	/** 解除绑定微信账号 */
	@RequestMapping(path = "/user/unbind/wechat", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUserUnbindWechat(HttpSession session, LoginToken token) {
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录");
		} else {
			loginUser.setWxOpenId("");
			loginUser.setWxUnionId("");
			// 存入数据库
			usersService.changeUser(loginUser);
			return new JSONResult().setValue(RetEnum.SUCCESS);
		}
	}

	/** 绑定微信账号 */
	@RequestMapping(path = "/user/bind/wechat", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUserBindWechat(String code, HttpSession session, LoginToken token) throws IOException {
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录");
		} else if (isEmpty(code)) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "缺少参数");
		} else {
			// 将code换成openId
			Map<String, String> map = WeChatMiniUtil.codeToOpenId(code);
			String openId = map.get("openId");
			String unionId = map.get("unionId");
			// 判断openId是否已绑定另一账号
			if (usersService.loginWeChat(openId, unionId) == null) {
				loginUser.setWxOpenId(openId);
				loginUser.setWxUnionId(unionId);
				// 存入数据库
				usersService.changeUser(loginUser);
				return new JSONResult().setValue(RetEnum.SUCCESS);
			} else {
				return new JSONResult().setValue(RetEnum.VALUE_EXIST, "该微信账号已被绑定");
			}
		}
	}

	/** 获取登录用户的用户信息（UserInfo） */
	@RequestMapping(path = "/user/login_user_info", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUserLoginUserInfo(HttpSession session, LoginToken token) {
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN);
		} else {
			// 判断是个人还是企业
			Users queryUser = usersService.queryByUserId(loginUser.getId());
			if (queryUser.getTypeId() == 1) {
				return new JSONResult().setValue(RetEnum.SUCCESS, userInfoService.queryUserInfo(loginUser.getId()));
			} else if (queryUser.getTypeId() == 2) {
				return new JSONResult().setValue(RetEnum.SUCCESS,
						enterpriseInfoService.queryOneByUserId(loginUser.getId()));
			} else {
				return new JSONResult().setValue(RetEnum.SUCCESS);
			}
		}
	}

	/** 获取登录用户的用户信息（UserInfo） */
	@RequestMapping(path = "/user/is-login", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String doUserIsLogin(HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN);
		return new JSONResult().setValue(RetEnum.SUCCESS);
	}

	/** 修改用户密码 */
	@RequestMapping(path = "/user-password", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String changeUserPassword(int userId, String password, String vCode, HttpSession session) {
		JSONResult jr = new JSONResult();
		return jr.setValue(RetEnum.ERROR, "发生错误：该接口已禁用");
	}

	/** 【账户方法】修改用户密码，目前仅支持使用手机验证码修改密码 */
	@RequestMapping(path = "/account/change-password/{type}", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doAccountChangePassword(@PathVariable("type") String type, String phone, String email, String code,
			String password, HttpServletResponse response) {
		if (isEmpty(type)) {
			return JSONResult.paramLack().toJSON();

		} else if (type.equals("password")) { // 新旧密码
			return JSONResult.builder(RetEnum.ERROR, "发生错误：暂未开放").toJSON();

		} else if (type.equals("phone-code")) { // 手机验证码
			return usersService.changePasswordWhitPhoneCode(phone, code, password).toJSON();

		} else if (type.equals("email-code")) { // 邮箱验证码
			return JSONResult.builder(RetEnum.ERROR, "发生错误：暂未开放").toJSON();

		} else if (type.equals("email-long-code")) { // 邮箱长期有效的验证码
			return JSONResult.builder(RetEnum.ERROR, "发生错误：暂未开放").toJSON();
		} else {
			response.setStatus(404);
			return JSONResult.builder(RetEnum.ERROR, "未能识别的type类型").toJSON();
		}
	}

	/** 【账户方法】更改个人用户信息的方法 */
	@RequestMapping(path = "/account/change-userinfo", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doAccountChangeUserInfo(UserInfo info, HttpSession session, LoginToken token) {
		// 获取当前登录的用户
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		// userId若为空则代表没有登录
		if (loginUser.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN);
		// 将当前登录的用户id加入要修改的user中
		Users user = info.getUser() == null ? new Users() : info.getUser();
		user.setId(loginUser.getId()); // 覆盖info.getUser中的id（当前登录只能更改当前登录用户的信息）
		info.setUser(user);
		// 调用service层的方法进行修改用户信息操作
		return userInfoService.changeUserInfo(info).toJSON();
	}

	/** 【账户方法】更改用户账号信息的方法 */
	@RequestMapping(path = "/account/change-user", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doAccountChangeUser(Users user, HttpSession session, LoginToken token) {
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService); // 获取当前登录的用户
		if (loginUser.getId() == null) // userId若为空则代表没有登录
			return new JSONResult().setValue(RetEnum.NO_LOGIN);
		if (user == null)
			return new JSONResult().setValue(RetEnum.PARAM_LACK);
		user.setId(loginUser.getId()); // 覆盖user中的id（当前登录只能更改当前登录用户的信息）
		return usersService.changeUser(user).toJSON(); // 调用service层的方法进行修改用户信息操作
	}

	/** 判断我可以修改用户名吗 */
	@RequestMapping(path = "/user/cani/change-username", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUserCanIChangeUsername(LoginToken token, HttpSession session) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN);
		String username = usersService.queryByUserId(user.getId()).getUsername();
		int length = username.length();
		if (username.substring(0, length > 7 ? 7 : length).equalsIgnoreCase("default"))
			return new JSONResult().setValue(RetEnum.SUCCESS);
		return new JSONResult().setValue(RetEnum.ERROR);
	}

	/** 修改优势 */
	@RequestMapping(path = "/modifyascendancy", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	public String modifyAscendancy(String ascendancy, HttpSession session, Model model) {
		Users currentUser = (Users) session.getAttribute("currentUser");
		if (currentUser == null) {
			model.addAttribute("errorTitle", "尚未登录");
			model.addAttribute("errorBody", "尚未登录或登录已过期");
			return "error";
		}
		if (ServletUtil.isEmpty(ascendancy)) {
			model.addAttribute("errorTitle", "缺少参数");
			model.addAttribute("errorBody", "缺少参数");
			return "error";
		}
		UserInfo ui = new UserInfo(currentUser);
		ui.setAscendancy(ascendancy);
		userInfoService.modifyAscendancy(ui);
		return "redirect:/center#local3";
	}

	/** 修改优势（Ajax方式） */
	@RequestMapping(path = "/center/strong", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String doCenterStrongPOST(String strong, HttpSession session, LoginToken token) {
		Users currentUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (currentUser.getId() == null) {
			return new JSONResult().setValue(RetEnum.ERROR, "尚未登录");
		} else if (ServletUtil.isEmpty(strong)) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "缺少参数");
		} else {
			UserInfo ui = new UserInfo(currentUser);
			ui.setAscendancy(strong);
			userInfoService.modifyAscendancy(ui);
			return new JSONResult().setValue(RetEnum.SUCCESS);
		}
	}

	/** 查询优势 */
	@RequestMapping(path = "/center/strong", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String doCenterStrongGET(HttpSession session, LoginToken token) {
		Users currentUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (currentUser.getId() == null) {
			return new JSONResult().setValue(RetEnum.ERROR, "尚未登录");
		} else {
			return new JSONResult().setValue(RetEnum.SUCCESS, "", userInfoService.queryStrong(currentUser.getId()));
		}
	}

	/** 分页查询所有的个人用户信息，返回 JSON */
	@RequestMapping(path = "/query/all/personal", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String doQueryPersonal(Filters filters, String city) {
		List<UserInfo> list = userInfoService.queryAll(filters, city);
		int total = userInfoService.personalFilters_total(filters, city);
		return new JSONResult().setValue(RetEnum.SUCCESS, "", list, total);
	}

	/** 分页查询所有用户信息，并且可以筛选 */
	@RequestMapping(path = "/query/all/personalinfo", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String doQueryPersonalInfo(Filters filters, String city) {
		List<UserInfo> list = userInfoService.queryAll(filters, city);
		int total = userInfoService.personalFilters_total(filters, city);
		return new JSONResult().setValue(RetEnum.SUCCESS, "", list, total);
	}

	/** 查询total */
	@RequestMapping(path = "/query/total", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String doQueryTotal(String type, Integer userIdA) {
		switch (type) {
		case "personal":// 个人
			return new JSONResult().setValue(RetEnum.SUCCESS, usersService.queryTotal("user_info"));
		case "enterprise":// 企业
			return new JSONResult().setValue(RetEnum.SUCCESS, usersService.queryTotal("enterprise_info"));
		case "group":// 团体
			return new JSONResult().setValue(RetEnum.SUCCESS, usersService.queryTotal("group_info"));
		case "demand_filter":// 需求
			return new JSONResult().setValue(RetEnum.SUCCESS, usersService.queryTotal("demand_info"));
		case "follow":// 我的关注
			return new JSONResult().setValue(RetEnum.SUCCESS, usersService.queryTotal("follow_nexus", userIdA));
		default:
			return new JSONResult().setValue(RetEnum.PARAM_ERROR, "未识别的type");
		}
	}

	/** 查询所有信息（新闻页面用） */
	@RequestMapping(path = "/checkUserInfo", method = RequestMethod.GET)// NOT_API
	public String queryAllInfo(String[] userId, HttpSession session, Model model) {
		List<UserInfo> uiList = userInfoService.queryAllInfo(userId);
		model.addAttribute("uiList", uiList);
		return "search/checkSearch";
	}

	/** 查询手机号是否已存在 */
	@RequestMapping(path = "/checkPhone", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String checkPhone(String telephone) {
		JSONResult jr = new JSONResult();
		List<UserInfo> list = userInfoService.checkPhone(telephone);
		if (list.size() > 0) {
			return jr.setValue(RetEnum.VALUE_EXIST, "错误", list);
		}
		return jr.setValue(RetEnum.VALUE_NOT_EXIST, "list空了", list);
	}

	/** 查询手机号和UserId是否已存在 */
	@RequestMapping(path = "/account/forget/check_phone", produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String doAccountForgetCheckPhone(String telephone) {
		Integer userId = userInfoService.queryUserIdByTelephone(telephone);
		if (userId == null) {
			return new JSONResult().setValue(RetEnum.VALUE_NOT_EXIST);
		}
		return new JSONResult().setValue(RetEnum.SUCCESS, new UserInfo(new Users(userId)));
	}

	/**
	 * 修改密码
	 * 
	 * @param type
	 *            修复密码的方式，password：新旧密码修改；phone：手机号码修改
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @param telephone
	 * @param vCode
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(path = "/user/change-password", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String doUserChangePassword(String type, Integer id, String oldPassword, String newPassword,
			String telephone, String vCode, HttpServletRequest request, HttpServletResponse response) {
		JSONResult jr = new JSONResult();
		System.out.println(type);
		if (isEmpty(type) || id == null) {
			return jr.setValue(RetEnum.PARAM_LACK, "缺少必要的参数！");
		}

		UserInfo ui = usersService.queryUserInfo(id);

		if (type.equals("password")) {
			if (isEmpty(oldPassword) || isEmpty(newPassword)) {
				return jr.setValue(RetEnum.PARAM_LACK, "旧密码或新密码不能为空！");
			}

			if (ui.getUser().getPassword().equalsIgnoreCase(DigestUtils.md5Hex(oldPassword))) {
				if (usersService.modifyPassword(id, newPassword)) {
					// 注销登陆
					signController.doLogoff(request.getSession(), response);
					return jr.setValue(RetEnum.SUCCESS);
				} else {
					return jr.setValue(RetEnum.EXCEPTION, "系统匆忙，请稍后重试！");
				}
			}
			return jr.setValue(RetEnum.ERROR, "旧密码不正确！");
		} else if (type.equals("phone")) {
			if (isEmpty(vCode) || isEmpty(telephone)) {
				return jr.setValue(RetEnum.PARAM_LACK, "验证码或手机号不能为空");
			}
			if (userInfoService.byIdModifyPhone(id, telephone)) {
				return jr.setValue(RetEnum.SUCCESS);
			} else {
				return jr.setValue(RetEnum.EXCEPTION, "系统匆忙，请稍后重试！");
			}
		}
		return jr.setValue(RetEnum.ERROR, "Type不识别！");
	}

	/** 用户更改密码，根据手机号获取UserInfo的Id获取传值密码和验证码 */
	@RequestMapping("/user/update-pwd")// NOT_API
	@ResponseBody
	public String forgetPwd(String pwd, String vCode, Integer id) {
		JSONResult jr = new JSONResult();
		// 判断手机和密码和验证码是否为空
		if (id == null || isEmpty(pwd) || isEmpty(vCode)) {
			return jr.setValue(RetEnum.PARAM_LACK, "缺少必要的参数！");
		}
		// 更改密码(用户的ID,新的密码)
		boolean pwdState = usersService.modifyPassword(id, pwd);
		if (pwdState) {
			return jr.setValue(RetEnum.SUCCESS);
		} else {
			return jr.setValue(RetEnum.EXCEPTION, "系统匆忙，请稍后重试！");
		}
	}

	/**
	 * 根据用户id，来获取用户的个人信息展示在页面上
	 */
	@RequestMapping("/user/{userId}")// NOT_API
	public String byIdUser(@PathVariable("userId") Integer userId, HttpSession session, Model model) {
		Users currentUser = (Users) session.getAttribute("currentUser");
		if (currentUser == null) {
			model.addAttribute("errorTitle", "尚未登录");
			model.addAttribute("errorBody", "尚未登录或登录已过期");
			return "error";
		}
		// 查询可预约时间
		List<AppintmentTime> appintmentTimelist = appintmentService.queryUserTime(userId);
		// 查询用户基本信息
		PersonalTopOneModel userInfo = userInfoService.queryUserInfoProfile(userId);
		// 根据用户的职业，推选别人
		List<UserInfo> pushUesrList = userInfoService.pushUser(userInfo.getJobInfo().getJobCode());
		// 查询用户被预约次数
		int userCount = userApintmentUserService.queryUserCount(userId);
		// 查询用户预约记录
		List<UserApintmentUser> apintmentListB = userApintmentUserService.byUserIdQueryUserBeAppintment(userId);
		// 查询用户的综合技能
		List<SkillInfo> skillInfoList = skillInfoService.queryAllByUserId(userId);
		// 查询用户的成功案例
		List<CaseInfo> caseInfoList = caseInfoService.queryOneByUserId(userId);
		// 查询用户资质证书
		List<IndustryAptitude> industryAptitudeList = industryAptitudeService.queryUserIdAptitude(userId);
		// 查询用户的被评价内容
		List<Commentaries> commentariesList = commentariesService.queryUserCommentaries(userId);
		// 查询用户被点赞次数
		int likeInfoCount = likesInfoService.likesInfoCount(userId);

		// 查询用户可预约时间
		/*
		 * List<AppintmentTime> apintmentTimeList =
		 * appintmentService.queryUserTime(userId);
		 */
		if (followNexusService.followNexusUserACount(currentUser.getId(), userId) > 0) {
			model.addAttribute("messg", "已关注");
		} else {
			model.addAttribute("messg", "+关注");
		}
		model.addAttribute("appintmentTimelist", appintmentTimelist);
		model.addAttribute("ui", userInfo);
		model.addAttribute("appintmentTimelist1", apintmentListB);
		model.addAttribute("userCount", userCount);
		model.addAttribute("skillInfoList", skillInfoList);
		model.addAttribute("caseInfoList", caseInfoList);
		model.addAttribute("industryAptitudeList", industryAptitudeList);
		model.addAttribute("commentariesList", commentariesList);
		model.addAttribute("likesInfoCount", likeInfoCount);

		/* model.addAttribute("apintmentTimeList", apintmentTimeList); */
		model.addAttribute("id", userId);
		model.addAttribute("pushuserList", pushUesrList);
		return "user/user-info";
	}

	/*** 查询跳转，用于center页面 */
	@RequestMapping(path = "/center", method = RequestMethod.GET)// NOT_API
	public String doCenter(HttpServletRequest request, Model model) {
		Users user = isLogin(request, usersService, enterpriseService, true);
		if (user == null) {
			model.addAttribute("errorTitle", "尚未登陆");
			model.addAttribute("errorBody", "您当前尚未登录，请先去<a href='/login'>登录</a>");
			return "error";
		}
		if (user.getTypeId() == 1) {
			model = this.getPersonalDetail(user.getId(), model);
			return "user/user-center";
		} else if (user.getTypeId() == 2) {
			model = this.getEnterpriseDetail(user.getId(), model);
			return "enterprise/enterprise-info";
		}
		return "redirect:common/four-zero-four";
	}

	/**
	 * 根据id获取企业用户的详细信息，并返回Model对象
	 */
	private Model getEnterpriseDetail(int id, Model model) {
		EnterpriseInfo enInfo = enterpriseService.queryOneByUserId(id);
		// List<Regions> regionList =
		// regionsService.queryAllByParentRegionCode("0");
		/*
		 * IndustryNexus industry = industryNexusService.queryFirstIndustry(id);
		 * List<IndustryNexus> industryList =
		 * industryNexusService.queryUserAllIndustry(id);
		 */
		model.addAttribute("enInfo", enInfo);
		// model.addAttribute("regionList", regionList);
		Filters fil = new Filters();
		fil.setUserId(id);
		List<GroupInfo> groupList = groupService.queryListByUserId(fil);
		List<AdPublic> publicList = adPublicService.byUserIdAdPublic(id);
		List<UserApintmentUser> apintmentListA = userApintmentUserService.byUserIdQueryUserAppintmentUser(id);
		List<UserApintmentUser> apintmentListB = userApintmentUserService.byUserIdQueryUserBeAppintment(id);
		model.addAttribute("groupList", groupList);
		model.addAttribute("publicList", publicList);
		model.addAttribute("apintmentListA", apintmentListA);
		model.addAttribute("apintmentListB", apintmentListB);
		/*
		 * model.addAttribute("IndustryList", industryList);
		 * model.addAttribute("industry", industry);
		 */
		return model;
	}

	/** 根据id获取个人用户的详细信息，并返回Model对象 */
	private Model getPersonalDetail(Integer id, Model model) {

		// 查询出基本资料
		UserInfo ui = userInfoService.queryUserInfo(id);
		model.addAttribute("userInfo", ui);

		// 查询出成功案例信息
		List<CaseInfo> caseList = caseInfoService.queryOneByUserId(id);
		model.addAttribute("caseList", caseList);

		// 查询出发布的团体信息
		Filters fil = new Filters();
		fil.setUserId(id);
		List<GroupInfo> groupList = groupService.queryListByUserId(fil);
		model.addAttribute("groupList", groupList);

		// 查询出发布的需求信息

		List<DemandInfo> demandList = demandInfoService.queryListByUserId(fil);
		model.addAttribute("demandList", demandList);
		// 查询审核
		CertificationInfo certificationInfo = cerfitiationInfoService.queryOneByUserId(id);
		model.addAttribute("certificationInfo", certificationInfo);
		// 查询出发布的广告信息
		List<AdPublic> publicList = adPublicService.byUserIdAdPublic(id);
		model.addAttribute("publicList", publicList);
		List<IndustryAptitude> industryAptitudeList = industryAptitudeService.queryUserIdAptitude(id);

		int userCount = userApintmentUserService.queryUserCount(id);

		// 查询出我关注的人（分页）
		// List<FollowNexusModel> followNexusList =
		// followNexusService.queryFollowNexusUserA(id);
		// model.addAttribute("followNexusList", followNexusList);

		// 查询出关注总数（Count）
		model.addAttribute("followsCount", followNexusService.queryFollowTotal(id));
		// 查询出粉丝总数（Count）
		model.addAttribute("fansCount", followNexusService.queryFansTotal(id));

		List<AppintmentTime> appintmentTimeList = appintmentService.queryUserTime(id);
		List<UserApintmentUser> apintmentListAList = userApintmentUserService.byStateAndAId(id, 10);
		List<UserApintmentUser> apintmentListBList = userApintmentUserService.byStateAndBId(id, 10);
		List<UserApintmentUser> completeApintmentListAList = userApintmentUserService.byStateAndAId(id, 11);
		// 待确认的预约记录 userA
		List<UserApintmentUser> toBeConfirmedListAList = userApintmentUserService.byStateAndAId(id, 14);
		// 已完成的预约记录
		List<UserApintmentUser> completeEvaluatedListBList = userApintmentUserService.byStateAndAId(id, 21);
		// 进行中的预约记录
		List<UserApintmentUser> haveInHandListAList = userApintmentUserService.byStateAndAId(id, 13);
		// 已完成的预约记录
		List<UserApintmentUser> completeApintmentListBList = userApintmentUserService.byStateAndBId(id, 11);
		// 待确认的预约记录 userB
		List<UserApintmentUser> toBeConfirmedListBList = userApintmentUserService.byStateAndBId(id, 14);
		// 进行中的预约记录 userB
		List<UserApintmentUser> haveInHandListBList = userApintmentUserService.byStateAndBId(id, 13);
		// 已取消的预约记录 userB
		List<UserApintmentUser> haveInCancelledListBList = userApintmentUserService.byStateAndBId(id, 16);
		// 已完成的预约记录user
		List<UserApintmentUser> completeEvaluatedListAList = userApintmentUserService.byStateAndBId(id, 21);
		// 获取企业与个人已取消的预约记录
		List<UserApintmentUser> apintmentEAlList = userApintmentUserService.byStateAndEId(id, 10);
		// 获取企业与个人已取消的预约记录16
		List<UserApintmentUser> apintmentEACancellList = userApintmentUserService.byStateAndEId(id, 16);
		// 获取企业与个人已取消被预约记录
		List<UserApintmentUser> apintmentEAUlList = userApintmentUserService.byStateAndUIdE(id, 10);
		// 获取企业与个人已完成的预约记录
		List<UserApintmentUser> apintmentEAcancellList = userApintmentUserService.byStateAndEId(id, 11);
		// 获取企业与个人已完成的被预约记录
		List<UserApintmentUser> apintmentEAUcancellList = userApintmentUserService.byStateAndEId(id, 11);

		// model.addAttribute("regionList", regionList);

		// 累计被预约次数
		model.addAttribute("userCount", userCount);

		model.addAttribute("apintmentListAList", apintmentListAList);
		model.addAttribute("apintmentListBList", apintmentListBList);
		model.addAttribute("completeApintmentListAList", completeApintmentListAList);
		model.addAttribute("toBeConfirmedListAList", toBeConfirmedListAList);
		model.addAttribute("apintmentEACancellList", apintmentEACancellList);
		model.addAttribute("haveInHandListAList", haveInHandListAList);
		model.addAttribute("toBeConfirmedListBList", toBeConfirmedListBList);
		model.addAttribute("haveInHandListBList", haveInHandListBList);
		model.addAttribute("haveInCancelledListBList", haveInCancelledListBList);

		model.addAttribute("completeEvaluatedListBList", completeEvaluatedListBList);
		model.addAttribute("completeEvaluatedListAList", completeEvaluatedListAList);
		model.addAttribute("completeApintmentListBList", completeApintmentListBList);
		model.addAttribute("industryAptitudeList", industryAptitudeList);

		model.addAttribute("apintmentEAlList", apintmentEAlList);
		model.addAttribute("apintmentEAUlList", apintmentEAUlList);
		model.addAttribute("apintmentEAcancellList", apintmentEAcancellList);
		model.addAttribute("apintmentEAUcancellList", apintmentEAUcancellList);
		// 查询该用户名下的所有技能
		model.addAttribute("skills", skillInfoService.queryAllByUserId(id));
		model.addAttribute("appintmentTimeList", appintmentTimeList);
		return model;
	}

	@RequestMapping(path = "/user/messg", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	public String userMessg(HttpSession session, Model model) {
		Users currentUser = (Users) session.getAttribute("currentUser");
		if (currentUser != null) {
			// 获取个人与个人待确认的预约记录
			List<UserApintmentUser> apintmentListAList = userApintmentUserService.byStateAndAId(currentUser.getId(),
					14);
			// 获取个人与个人待确认的被预约记录
			List<UserApintmentUser> apintmentListBList = userApintmentUserService.byStateAndBId(currentUser.getId(),
					14);

			// 获取个人与个人的已确认的预约记录
			List<UserApintmentUser> apintmentAProceedList = userApintmentUserService.byStateAndAId(currentUser.getId(),
					13);
			// 获取个人与个人的已确认的被预约记录
			List<UserApintmentUser> apintmentBProceedList = userApintmentUserService.byStateAndBId(currentUser.getId(),
					13);
			// 获取个人与个人的待取消的预约记录
			List<UserApintmentUser> apintmentAcancellList = userApintmentUserService.byStateAndAId(currentUser.getId(),
					17);

			// 获取个人与个人的待取消的被预约的记录
			List<UserApintmentUser> apintmentBcancellList = userApintmentUserService.byStateAndBId(currentUser.getId(),
					16);
			// 获取企业与个人待确认的预约记录
			List<UserApintmentUser> apintmentEAlList = userApintmentUserService.byStateAndEId(currentUser.getId(), 14);
			// 获取企业与个人待确认被预约记录
			List<UserApintmentUser> apintmentEAUlList = userApintmentUserService.byStateAndUIdE(currentUser.getId(),
					14);
			// 获取企业与个人已确认的预约记录
			List<UserApintmentUser> apintmentEAProceedlList = userApintmentUserService
					.byStateAndEId(currentUser.getId(), 13);
			// 获取企业与个人已确认的被预约记录
			List<UserApintmentUser> apintmentEAUProceedlList = userApintmentUserService
					.byStateAndUIdE(currentUser.getId(), 13);
			// 获取企业与个人待取消的预约记录
			List<UserApintmentUser> apintmentEAcancellList = userApintmentUserService.byStateAndEId(currentUser.getId(),
					16);
			// 获取企业与个人待取消的被预约记录
			List<UserApintmentUser> apintmentEAUcancellList = userApintmentUserService
					.byStateAndEId(currentUser.getId(), 17);

			List<TheResponse> theResponseBlist = theResponseService.byUbserBTheResponse(currentUser.getId(), 14);
			model.addAttribute("theResponseBlist", theResponseBlist);
			// 获取A方个人响应方记录;
			List<TheResponse> theResponseAlist = theResponseService.byUserTheResponse(currentUser.getId(), 14);
			model.addAttribute("theResponseAlist", theResponseAlist);
			// A方企业响应记录
			List<TheResponse> theResponseAAlist = theResponseService.byEnterpriseTheResponse(currentUser.getId(), 14);
			model.addAttribute("theResponseAAlist", theResponseAAlist);
			// B方企业被响应记录
			List<TheResponse> theResponseBBlist = theResponseService.byEnterpriseBTheResponse(currentUser.getId(), 14);
			model.addAttribute("theResponseBBlist", theResponseBBlist);
			// 个人响应企业记录 登录的企业是被响应方
			List<TheResponse> theUserAndEnterpriseResponselist = theResponseService
					.byUserBAndEnterpriseTheResponse(currentUser.getId(), 14);
			model.addAttribute("theUserAndEnterpriseResponselist", theUserAndEnterpriseResponselist);

			// A方个人响应企业记录
			List<TheResponse> theResponseABlist = theResponseService.byUserAndEnterpriseTheResponse(currentUser.getId(),
					14);
			model.addAttribute("theResponseABlist", theResponseABlist);
			// 企业响应个人的 个人是被响应方就是登录方
			List<TheResponse> theEnterpriseAndUserBResponselist = theResponseService
					.byEnterpriseAndUserTheResponse(currentUser.getId(), 14);
			model.addAttribute("theEnterpriseAndUserBResponselist", theEnterpriseAndUserBResponselist);
			// B方企业响应个人记录
			List<TheResponse> theResponsesBAlist = theResponseService
					.byUserAndEnterpriseBTheResponse(currentUser.getId(), 14);
			model.addAttribute("theResponsesBAlist", theResponsesBAlist);

			// B方个人已完成记录
			List<TheResponse> theResponseBilist = theResponseService.byUbserBTheResponse(currentUser.getId(), 13);
			model.addAttribute("theResponseBilist", theResponseBilist);
			// 获取A方个人已完成记录
			List<TheResponse> theResponseAilist = theResponseService.byUserTheResponse(currentUser.getId(), 13);
			model.addAttribute("theResponseAilist", theResponseAilist);
			// A方企业被响应已完成记录
			List<TheResponse> theResponseAAilist = theResponseService.byEnterpriseTheResponse(currentUser.getId(), 13);
			model.addAttribute("theResponseAAilist", theResponseAAilist);
			// B方企业被响应已完成记录
			List<TheResponse> theResponseBBilist = theResponseService.byEnterpriseBTheResponse(currentUser.getId(), 13);

			model.addAttribute("theResponseBBilist", theResponseBBilist);
			// A方个人响应企业已完成记录
			List<TheResponse> theResponseABilist = theResponseService
					.byUserAndEnterpriseTheResponse(currentUser.getId(), 13);
			model.addAttribute("theResponseABilist", theResponseABilist);

			List<TheResponse> theuserBResponseABlist = theResponseService
					.byUserAndEnterpriseBTheResponse(currentUser.getId(), 13);
			model.addAttribute("theuserBResponseABlist", theuserBResponseABlist);
			// B方企业响应个人已完成记录
			List<TheResponse> theResponsesBAilist = theResponseService
					.byEnterpriseAndUserTheResponse(currentUser.getId(), 13);
			model.addAttribute("theResponsesBAilist", theResponsesBAilist);

			List<TheResponse> theEnterpriseBAndUserBResponselist = theResponseService
					.byUserBAndEnterpriseTheResponse(currentUser.getId(), 13);
			model.addAttribute("theEnterpriseBAndUserBResponselist", theEnterpriseBAndUserBResponselist);

			/*
			 * //获取个人响应团队记录 List<Theresponseteam> theUserResponseteam =
			 * theResponseteamService.selectUserTheResponseteam(currentUser.
			 * getId(), 14); model.addAttribute("therUseresponseteam",
			 * theUserResponseteam);
			 * 
			 * //获取团队响应团队记录 List<Theresponseteam> theTeamtheResponsesteam =
			 * theResponseteamService.selectTeamTheResponseteam(currentUser.
			 * getId(), 14); model.addAttribute("theTeamtheResponsesteam",
			 * theTeamtheResponsesteam);
			 * 
			 * //获取个人响应团队已完成记录 List<Theresponseteam> thewhatUserResponseteam =
			 * theResponseteamService.selectUserTheResponseteam(currentUser.
			 * getId(), 14); model.addAttribute("thewhatUserResponseteam",
			 * thewhatUserResponseteam); //获取个人响应团队已完成记录 List<Theresponseteam>
			 * thewhatTeamtheResponsesteam =
			 * theResponseteamService.selectUserTheResponseteam(currentUser.
			 * getId(), 14); model.addAttribute("thewhatTeamtheResponsesteam",
			 * thewhatTeamtheResponsesteam);
			 */
			model.addAttribute("theResponseBBilist", theResponseBBilist);

			/**
			 * 团队响应
			 */
			// 个人响应个人发布的团队 响应人是登录者 我响应的
			List<Theresponseteam> theResponseteamUserlist = theResponseteamService
					.selectUserAAndUserTheResponseteam(currentUser.getId(), 14);
			model.addAttribute("theResponseteamUserlist", theResponseteamUserlist);
			// 个人响应个人发布的团队 被响应人是登录者，也就是响应我的
			List<Theresponseteam> theResponseteamBUserlist = theResponseteamService
					.selectUserAndUserBTheResponseteam(currentUser.getId(), 14);
			model.addAttribute("theResponseteamBUserlist", theResponseteamBUserlist);
			// 企业响应个人发布的团队 响应人是登录者，也就是我响应的
			List<Theresponseteam> theEnderResponseteamUserlist = theResponseteamService
					.selectEnterpriseAAndUserBTheResponseteam(currentUser.getId(), 14);
			model.addAttribute("theEnderResponseteamUserlist", theEnderResponseteamUserlist);

			// 企业响应个人发布的团队 被响应者是登录者，也就是响应我的
			List<Theresponseteam> theEnterpriseAderResponseteamUserlist = theResponseteamService
					.selectByUserBAndEnterpriseATheResponseteam(currentUser.getId(), 14);
			model.addAttribute("theEnterpriseAderResponseteamUserlist", theEnterpriseAderResponseteamUserlist);
			// 个人响应企业发布的团队 被响应人是登录者，也就是响应我的
			List<Theresponseteam> theEnderResponseteamBUserlist = theResponseteamService
					.selectUserAAndEnterpriseBTheResponseteam(currentUser.getId(), 14);
			model.addAttribute("theEnderResponseteamBUserlist", theEnderResponseteamBUserlist);
			// 企业响应企业发布的团队 响应人是登录者，也就是我响应的
			List<Theresponseteam> theEnterpriseteamAndUserResponselist = theResponseteamService
					.selectEnterpriseAAndEnterpriseBTheResponseteam(currentUser.getId(), 14);
			model.addAttribute("theEnterpriseteamAndUserResponselist", theEnterpriseteamAndUserResponselist);
			// 企业响应企业发布的团队 被响应人是登录者
			List<Theresponseteam> theEnterpriseteamAndUserBResponselist = theResponseteamService
					.selectEnterpriseAAndEnterpriseTheResponseteam(currentUser.getId(), 14);
			model.addAttribute("theEnterpriseteamAndUserBResponselist", theEnterpriseteamAndUserBResponselist);
			// 个人响应企业发布的团队 响应者是登录者 也就是我响应的
			List<Theresponseteam> theByUserAAndEnterpriseBResponseTeamlist = theResponseteamService
					.selectByUserAAndEnterpriseBTheResponseteam(currentUser.getId(), 14);
			model.addAttribute("theByUserAAndEnterpriseBResponseTeamlist", theByUserAAndEnterpriseBResponseTeamlist);

			// 已确认的记录
			List<Theresponseteam> confrimTheResponseteamUserlist = theResponseteamService
					.selectUserAAndUserTheResponseteam(currentUser.getId(), 13);
			model.addAttribute("confrimTheResponseteamUserlist", confrimTheResponseteamUserlist);
			// 个人响应个人发布的团队 被响应人是登录者，也就是响应我的
			List<Theresponseteam> confrimTheResponseteamBUserlist = theResponseteamService
					.selectUserAndUserBTheResponseteam(currentUser.getId(), 13);
			model.addAttribute("confrimTheResponseteamBUserlist", confrimTheResponseteamBUserlist);
			// 企业响应个人发布的团队 响应人是登录者，也就是我响应的
			List<Theresponseteam> confrimTheEnderResponseteamUserlist = theResponseteamService
					.selectEnterpriseAAndUserBTheResponseteam(currentUser.getId(), 13);
			model.addAttribute("confrimTheEnderResponseteamUserlist", confrimTheEnderResponseteamUserlist);

			// 企业响应个人发布的团队 被响应者是登录者，也就是响应我的
			List<Theresponseteam> confrimTheEnterpriseAderResponseteamUserlist = theResponseteamService
					.selectByUserBAndEnterpriseATheResponseteam(currentUser.getId(), 13);
			model.addAttribute("confrimTheEnterpriseAderResponseteamUserlist",
					confrimTheEnterpriseAderResponseteamUserlist);
			// 个人响应企业发布的团队 被响应人是登录者，也就是响应我的
			List<Theresponseteam> confrimTheEnderResponseteamBUserlist = theResponseteamService
					.selectUserAAndEnterpriseBTheResponseteam(currentUser.getId(), 13);
			model.addAttribute("confrimTheEnderResponseteamBUserlist", confrimTheEnderResponseteamBUserlist);
			// 企业响应企业发布的团队 响应人是登录者，也就是我响应的
			List<Theresponseteam> confrimTheEnterpriseteamAndUserResponselist = theResponseteamService
					.selectEnterpriseAAndEnterpriseBTheResponseteam(currentUser.getId(), 13);
			model.addAttribute("confrimTheEnterpriseteamAndUserResponselist",
					confrimTheEnterpriseteamAndUserResponselist);
			// 企业响应企业发布的团队 被响应人是登录者
			List<Theresponseteam> confrimTheEnterpriseteamAndUserBResponselist = theResponseteamService
					.selectEnterpriseAAndEnterpriseTheResponseteam(currentUser.getId(), 13);
			model.addAttribute("confrimTheEnterpriseteamAndUserBResponselist",
					confrimTheEnterpriseteamAndUserBResponselist);
			// 个人响应企业发布的团队 响应者是登录者 也就是我响应的
			List<Theresponseteam> confrimTheByUserAAndEnterpriseBResponseTeamlist = theResponseteamService
					.selectByUserAAndEnterpriseBTheResponseteam(currentUser.getId(), 13);
			model.addAttribute("confrimTheByUserAAndEnterpriseBResponseTeamlist",
					confrimTheByUserAAndEnterpriseBResponseTeamlist);

			model.addAttribute("apintmentListAList", apintmentListAList);
			model.addAttribute("apintmentListBList", apintmentListBList);
			model.addAttribute("apintmentAProceedList", apintmentAProceedList);
			model.addAttribute("apintmentBProceedList", apintmentBProceedList);
			model.addAttribute("apintmentAcancellList", apintmentAcancellList);
			model.addAttribute("apintmentBcancellList", apintmentBcancellList);
			// 企业这一块
			model.addAttribute("apintmentEAlList", apintmentEAlList);
			model.addAttribute("apintmentEAUlList", apintmentEAUlList);
			model.addAttribute("apintmentEAProceedlList", apintmentEAProceedlList);
			model.addAttribute("apintmentEAUProceedlList", apintmentEAUProceedlList);
			model.addAttribute("apintmentEAcancellList", apintmentEAcancellList);
			model.addAttribute("apintmentEAUcancellList", apintmentEAUcancellList);
			return "/operation-appointment";

		} else {
			model.addAttribute("errorTitle", "尚未登录");
			model.addAttribute("errorBody", "尚未登录或登录已过期");
			return "error";
		}

	}

	@RequestMapping(path = "/user/messg/sum", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String userMessgSum(HttpSession session) {
		Users currentUser = (Users) session.getAttribute("currentUser");
		JSONResult jr = new JSONResult();
		if (currentUser == null) {
			return jr.setValue(RetEnum.PARAM_LACK, 0);
		} else {
			return jr.setValue(RetEnum.PARAM_LACK, userApintmentUserService.messgSum(currentUser.getId()));
		}
	}

	/** 查询用户信息 */
	@RequestMapping(path = "/detail/{type}/{id}", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doDetail(@PathVariable("type") String type, @PathVariable("id") Integer id,
			HttpServletRequest request, Model model) {
		JSONResult jr = new JSONResult();
		if (isEmpty(type) || id == null) {
			return jr.setValue(RetEnum.PARAM_LACK);
		}
		switch (type) {
		case "up": // user personal
			model = this.getPersonalDetail(id, model);
			break;
		case "ue": // user enterprise
			model = this.getEnterpriseDetail(id, model);
			break;
		default:
			return jr.setValue(RetEnum.PARAM_ERROR, "未知的类型");
		}
		return jr.setValue(RetEnum.SUCCESS, "", model);
	}

	/** 查询多个用户信息 */
	@RequestMapping(path = "/detail/personal-more", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doDetailPersonalMore(String ids) {
		List<UserInfo> uinfos;
		if (ids == null || ids.trim().equals("")) {
			uinfos = new ArrayList<UserInfo>();
		} else {
			uinfos = userInfoService.search(Filters.builder().setIds(ids));
		}
		return JSONResult.builder(RetEnum.SUCCESS).setList(uinfos).toJSON();
	}

	/** from方式修改用户信息 */
	@RequestMapping(path = "/center/update", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API

	public String perfectUserInfo(@Param("joiningCity") String joiningCity,
			@Param("joiningProvince") String joiningProvince, UserInfo userInfo, IndustryNexus industryNews,
			HttpSession session, HttpServletRequest request) {
		// 创建 JSONResult对象，用于返回JSON字符串
		JSONResult jr = new JSONResult();

		Users user = (Users) session.getAttribute("currentUser");
		if (user == null) {
			return jr.setValue(RetEnum.NO_LOGIN, "尚未登录！");

		}
		int userId = user.getId();
		industryNews.setUserId(userId);
		// boolean indusreyFlag = industryNews.getIndustryTypeCode() != null ||
		// industryNews.getUserId() != 0;
		// if (indusreyFlag) {
		// industryNexusService.updateOneById(industryNews);
		// }

		boolean flag = userInfo.getNickName() != null || userInfo.getRealName() != null
				|| userInfo.getIdentity() != null || userInfo.getTelephone() != null || userInfo.getGender() != null
				|| userInfo.getBirthday() != null || userInfo.getHourlyWage() != 0 || userInfo.getBalance() != 0
				|| userInfo.getScore() == 0 || userInfo.getEmail() != null || userInfo.getAddress() != null
				|| userInfo.getJobBeginTime() != null;
		if (!flag) {
			return jr.setValue(RetEnum.PARAM_LACK, "缺少参数");
		}

		if (userInfo.getJobInfo() != null) {
			// 查询3级职业
			JobInfo jobInfo = jobInfoService.queryOne(userInfo.getJobInfo().getJobCode());
			if (jobInfo != null) {
				// 查询2级职业
				JobInfo jobInfoOne = jobInfoService.queryOne(jobInfo.getParentCode());
				if (jobInfoOne != null) {
					// 查询1级职业
					JobInfo jobInfoTwo = jobInfoService.queryOne(jobInfoOne.getParentCode());
					if (jobInfoTwo != null) {
						List<String> list = new ArrayList<String>();
						list.add(jobInfoTwo.getJobName());
						list.add(jobInfoOne.getJobName());
						list.add(jobInfo.getJobName());
						userInfo.setIndustryDetails(new Gson().toJson(list));
					}
				}

			}
		}
		if (joiningCity != null && joiningProvince != null) {
			StringBuilder sb = new StringBuilder();
			Regions region = regionsService.selectOne(joiningCity);
			Regions region1 = regionsService.selectOne(joiningProvince);
			if (region != null && region1 != null) {
				userInfo.setAddressAll(
						sb.append(region1.getRegionName() + "/" + region.getRegionName() + "/" + userInfo.getAddress())
								.toString());
			}
		}
		// 判断 地区 是否为空
		if (userInfo.getRegion() == null) {
			userInfo.setRegion(new Regions("0"));
		} else if (userInfo.getRegion().getRegionCode().equals("-1")) {
			userInfo.getRegion().setRegionCode("0");
		}

		// 判断 职业 为空
		if (userInfo.getJobInfo() == null) {
			userInfo.setJobInfo(new JobInfo("0"));
		} else if (userInfo.getJobInfo().getJobCode().equals("-1")) {
			userInfo.getJobInfo().setJobCode("0");
		}

		// 判断 职业 为空
		if (userInfo.getJobInfo().getJobCode().equals("-1")) {
			userInfo.getJobInfo().setJobCode("0");
		}

		userInfo.setUser(user);
		boolean result = userInfoService.editUserInfo(userInfo);
		if (result) {
			// 清除缓存
			cleanSession(session, "currentUserInfo");
			// 重新登陆
			isLogin(request, usersService, enterpriseInfoService, true);
			return "redirect:/updateSuccess";
		} else {
			return "redirect:/updateError";
		}

	}

	/** 更新用户头像 */
	@RequestMapping(path = "/update_avatar", method = RequestMethod.POST)// NOT_API
	public String addUser(MultipartFile avatarFile, String put, HttpSession session, Model model) throws Exception {
		Users currentUser = (Users) session.getAttribute("currentUser");
		if (currentUser == null) {
			model.addAttribute("errorTitle", "尚未登陆");
			model.addAttribute("errorBody", "你还没有登陆，无法上传头像！");
			return "error";
		}

		userInfoService.changeAvatar(currentUser.getId(), avatarFile, put, session);

		return "redirect:/page/update-avatar/reload.html";
	}

	/** 根据用户id来返回用户所发布的团体信息 */
	@RequestMapping(path = "/byUserIdGroup", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String byuserIdGroup(int userId) {
		JSONResult jr = new JSONResult();
		if (userId < 0) {
			return jr.setValue(RetEnum.PARAM_LACK, "id必须大于零");
		}
		Filters fil = new Filters();
		fil.setUserId(userId);
		List<GroupInfo> list = groupService.queryListByUserId(fil);
		if (list == null) {
			return jr.setValue(RetEnum.ERROR, "没有此Id的信息");
		} else {
			return jr.setValue(RetEnum.SUCCESS, "信息存在", list);
		}
	}

	/** 根据用户Id来查询用户所发布的广告 */
	@RequestMapping(path = "/byUserIdAdPublic", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String byUserIdAdPublic(int userId, int startNumber, int sumNumber) {
		JSONResult jr = new JSONResult();
		if (userId < 0) {
			return jr.setValue(RetEnum.PARAM_LACK, "id必须大于零");
		}
		List<AdPublic> list = adPublicService.byUserIdAdPublic(userId);
		if (list == null) {
			return jr.setValue(RetEnum.ERROR, "没有此Id的信息");
		} else {
			return jr.setValue(RetEnum.SUCCESS, "信息存在", list);
		}
	}

	/** 根据个人Id查询被预约记录 */
	@RequestMapping(path = "/byUserIdQuerybeUserAppintmentUser", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String byUserIdBeAppintmentTime(int userId, int startNumber, int sumNumber) {
		JSONResult jr = new JSONResult();
		if (userId < 0) {
			return jr.setValue(RetEnum.PARAM_LACK, "id必须大于零");
		}
		List<UserApintmentUser> list = userApintmentUserService.byUserIdQueryUserBeAppintment(userId);
		if (list == null) {
			return jr.setValue(RetEnum.ERROR, "没有此Id的信息");
		} else {
			return jr.setValue(RetEnum.SUCCESS, "信息存在", list);
		}
	}

	/** 根据个人Id查询已预约记录 */
	@RequestMapping(path = "/byUserIdQueryUserAppintmentUser", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String byUserIdAppintmentTime(int userId) {
		JSONResult jr = new JSONResult();
		if (userId < 0) {
			return jr.setValue(RetEnum.PARAM_LACK, "id必须大于零");
		}
		List<UserApintmentUser> list = userApintmentUserService.byUserIdQueryUserAppintmentUser(userId);
		if (list == null) {
			return jr.setValue(RetEnum.ERROR, "没有此Id的信息");
		} else {
			return jr.setValue(RetEnum.SUCCESS, "信息存在", list);
		}
	}

	/** 查询注册人数 */
	@RequestMapping(path = "/userCount", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String userCount() {
		JSONResult jr = new JSONResult();
		int userCount = usersService.queryCountUser();
		return jr.setValue(RetEnum.SUCCESS, "已有注册人数", userCount);
	}

	/**
	 * 查询total根据name（名称）和状态ID
	 */
	@RequestMapping(path = "/query/total_state", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String doQueryTotalByState(Filters filters) {
		if (filters.getName().isEmpty()) {
			return new JSONResult().setValue(RetEnum.PARAM_ERROR, "未识别的type");
		} else {
			return new JSONResult().setValue(RetEnum.SUCCESS, usersService.queryTotalByState(filters));
		}

	}

}
