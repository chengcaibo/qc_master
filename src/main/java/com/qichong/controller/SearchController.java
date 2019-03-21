package com.qichong.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qichong.entity.AdPrivate;
import com.qichong.entity.Bananer;
import com.qichong.entity.DemandInfo;
import com.qichong.entity.JournalismInfo;
import com.qichong.entity.NewsInformation;
import com.qichong.entity.UserInfo;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.exception.QCErrorRuntimeException;
import com.qichong.model.DemandInfoModel;
import com.qichong.model.EnterpriseTopOneModel;
import com.qichong.model.Filters;
import com.qichong.model.GroupInfoModel;
import com.qichong.model.IndustryTopModel;
import com.qichong.model.JSONResult;
import com.qichong.model.PersonalTopOneModel;
import com.qichong.service.AdPrivateService;
import com.qichong.service.AdPublicService;
import com.qichong.service.BananerService;
import com.qichong.service.DemandInfoService;
import com.qichong.service.EnterpriseInfoService;
import com.qichong.service.GroupInfoServcie;
import com.qichong.service.IndustryTopService;
import com.qichong.service.JournalismService;
import com.qichong.service.NewsInformationService;
import com.qichong.service.ToolsInfoService;
import com.qichong.service.UserInfoService;
import com.qichong.service.UsersService;
import com.qichong.token.LoginToken;
import com.qichong.util.Utils;
import com.qichong.util.web.ServletUtil;

@Controller
public class SearchController {

	@Autowired
	UsersService usersService;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	EnterpriseInfoService enterpriseService;
	@Autowired
	GroupInfoServcie groupInfoService;
	@Autowired
	DemandInfoService demandInfoService;
	@Autowired
	JournalismService journalismService;
	@Autowired
	BananerService bananerService;
	@Autowired
	NewsInformationService newsInformationService;
	@Autowired
	IndustryTopService industryTopService;
	@Autowired
	AdPublicService adService;
	@Autowired
	ToolsInfoService toolsService;

	/* - - - TODO: begin 兼容小程序线上版本5.6.5.R，未发布新版本，不可删除 - - - */
	@Autowired
	AdPrivateService adPrivateService;

	/** 【搜索】私有广告 */
	@RequestMapping(path = "/search/ad-private", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSearchAdPrivate(Filters filters) {
		List<AdPrivate> list = adPrivateService.search(filters);
		// 由于旧版不支持多个openType，所以采用一个兼容写法，只保留第一个openType
		for (AdPrivate ad : list) {
			String openType = ad.getOpenType();
			if (openType != null) {
				int index = openType.indexOf(",");
				index = index == -1 ? openType.indexOf(", ") : index;
				if (index != -1) {
					ad.setOpenType(openType.substring(0, index));
				}
			}
		}
		return new JSONResult().setValue(RetEnum.SUCCESS, list);
	}
	/* - - - end 兼容小程序线上版本5.6.5.R，未发布新版本，不可删除 - - - */

	/** 【搜索】个人 */
	@RequestMapping(path = "/search/personal", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSearchPersonal(Filters filters, HttpSession session, LoginToken token) {
		try {
			if (filters.getIgnoreLoginUser()) {// 是否忽略了当前登录的用户
				Users user = ServletUtil.getThisLoginUser(session, token, usersService);
				filters.setNotId(user.getId());
			}
			// 根据filters查询个人
			List<UserInfo> list = userInfoService.search(filters);
			// 判断是否需要total参数
			Integer total = null;
			if (filters.isTotal() == true) {
				total = userInfoService.searchTotal(filters);
			}
			return JSONResult.builder(RetEnum.SUCCESS).setList(list).setTotal(total).toJSON();
		} catch (Exception e) {
			Throwable th = Utils.isQCError(e);
			if (th != null) {
				return JSONResult.builder(RetEnum.ERROR, th.getMessage()).toJSON();
			}
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, "服务器异常").toJSON();
		}
	}

	/** 【搜索】企业 */
	@RequestMapping(path = "/search/enterprise", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSearchEnterprise(Filters filters, HttpSession session, LoginToken token) {
		try {
			if (filters.getIgnoreLoginUser()) { // 是否忽略了当前登录的用户
				Users user = ServletUtil.getThisLoginUser(session, token, usersService);
				filters.setNotId(user.getId());
			}
			return new JSONResult().setValue(RetEnum.SUCCESS, enterpriseService.search(filters));
		} catch (QCErrorRuntimeException e) {
			return JSONResult.builder(RetEnum.ERROR, e.getMessage()).toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, "服务器异常").toJSON();
		}
	}

	/** 【搜索】团体 */
	@RequestMapping(path = "/search/group", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSearchGroup(Filters filters) {
		try {
			return new JSONResult().setValue(RetEnum.SUCCESS, groupInfoService.search(filters));
		} catch (QCErrorRuntimeException e) {
			return JSONResult.builder(RetEnum.ERROR, e.getMessage()).toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, "服务器异常").toJSON();
		}
	}

	/** 【搜索】需求 */
	@RequestMapping(path = "/search/demand", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSearchDemand(Filters filters) {
		try {
			int total = demandInfoService.searchTotal(filters);
			return new JSONResult().setValue(RetEnum.SUCCESS, "", demandInfoService.search(filters), total);
		} catch (QCErrorRuntimeException e) {
			return JSONResult.builder(RetEnum.ERROR, e.getMessage()).toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, "服务器异常").toJSON();
		}
	}

	/** 【搜索】广告 */
	@RequestMapping(path = "/search/ad", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSearchAd(Filters filters) {
		try {
			return new JSONResult().setValue(RetEnum.SUCCESS, adService.search(filters));
		} catch (QCErrorRuntimeException e) {
			return JSONResult.builder(RetEnum.ERROR, e.getMessage()).toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, "服务器异常").toJSON();
		}
	}

	/** 【搜索】工具 */
	@RequestMapping(path = "/search/tools", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSearchTools(Filters filters) {
		try {
			return new JSONResult().setValue(RetEnum.SUCCESS, toolsService.search(filters));
		} catch (QCErrorRuntimeException e) {
			return JSONResult.builder(RetEnum.ERROR, e.getMessage()).toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, "服务器异常").toJSON();
		}
	}

	/** 根据用户的行业来进行模糊查询 */
	@RequestMapping("/journalism")
	public String queryBlurUser(String regionCity, String s, Model model) {
		List<IndustryTopModel> industryTopList = new ArrayList<IndustryTopModel>();
		if (s.equals("") || s.isEmpty() || s == null) {
			industryTopList = industryTopService.queryAllLoction();
		} else {
			industryTopList = industryTopService.queryEnterprise(s);
			if (industryTopList == null) {
				industryTopList = industryTopService.queryAllLoction();
			}
		}
		model.addAttribute("s", s);
		List<Bananer> bananerList = null;
		List<NewsInformation> newsList = newsInformationService.queryNewsInformation();
		bananerList = bananerService.queryCarousel(s);
		if (bananerList.size() == 0) {
			bananerList = bananerService.queryCarousel(null);
		}
		List<JournalismInfo> JournalismList = journalismService.queryJournalism();
		List<PersonalTopOneModel> preferredList = userInfoService.queryPreferred();
		List<PersonalTopOneModel> userList = userInfoService.queryBlurUser(s, 1, 20, regionCity);
		List<EnterpriseTopOneModel> enterpriseList = enterpriseService.queryLikeByTypeName(s, regionCity);
		List<EnterpriseTopOneModel> enterprisePreferredList = enterpriseService.enterprisePreferred();
		List<GroupInfoModel> groupInfoList = groupInfoService.byNameGroup(s, regionCity);
		List<GroupInfoModel> groupPreferredList = groupInfoService.groupPreferred();
		Filters filters = new Filters();
		filters.setKeyword(s);
		filters.setRegionCity(regionCity);
		List<DemandInfo> demandInfoList = demandInfoService.search(filters);
		List<DemandInfoModel> demandPreferredList = demandInfoService.demandPreferred();
		model.addAttribute("preferredList", preferredList);
		model.addAttribute("groupInfoList", groupInfoList);
		model.addAttribute("groupPreferredList", groupPreferredList);
		model.addAttribute("enterpriseList", enterpriseList);
		model.addAttribute("enterprisePreferredList", enterprisePreferredList);
		model.addAttribute("userList", userList);
		model.addAttribute("demandInfoList", demandInfoList);
		model.addAttribute("demandPreferredList", demandPreferredList);
		model.addAttribute("JournalismList", JournalismList);
		model.addAttribute("bananerList", bananerList);
		model.addAttribute("newsList", newsList);
		model.addAttribute("industryTopList", industryTopList);
		model.addAttribute("regionCity", regionCity);
		return "search/journalism";
	}
}
