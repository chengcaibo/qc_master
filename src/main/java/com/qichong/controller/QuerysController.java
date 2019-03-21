package com.qichong.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qichong.entity.AdPublic;
import com.qichong.entity.EnterpriseInfo;
import com.qichong.enums.RetEnum;
import com.qichong.model.EnterpriseTopOneModel;
import com.qichong.model.Filters;
import com.qichong.model.GroupInfoModel;
import com.qichong.model.JSONResult;
import com.qichong.model.PersonalTopOneModel;
import com.qichong.service.AdPublicService;
import com.qichong.service.DemandInfoService;
import com.qichong.service.DeskDisplayService;
import com.qichong.service.EnterpriseInfoService;
import com.qichong.service.GroupInfoServcie;
import com.qichong.service.LatestReleaseService;
import com.qichong.service.UserInfoService;
import com.qichong.util.web.ServletUtil;

/** 所有查询全部信息的控制层 */
@RestController
@RequestMapping(method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES) // NOT_API
public class QuerysController {

	@Autowired
	UserInfoService userInfoService;
	@Autowired
	EnterpriseInfoService enterpriseService;
	@Autowired
	GroupInfoServcie groupService;
	@Autowired
	DeskDisplayService deskdisplayService;
	@Autowired
	AdPublicService adPublicService;
	@Autowired
	DemandInfoService demandInfoService;

	@Autowired
	LatestReleaseService latestReleaseService;

	@RequestMapping(path = "/query/latest-release/10") // NOT_API
	public String doLetestReleaseMax10() {
		return latestReleaseService.queryLatest().toJSON();
	}

	/** 查询前五条最新发布的团体信息 */
	@RequestMapping(path = "/group/query/new_five") // NOT_API
	public String doGroupQueryNewFive() {
		return new JSONResult().setValue(RetEnum.SUCCESS, groupService.queryFiveGroup());
	}

	/** 查询前十三条最新发布的团体信息 */
	@RequestMapping(path = "/querythirteengroup") // NOT_API
	public String querthirteenGroup(String city) {
		// 创建 JSONResult对象，用于返回JSON字符串
		JSONResult jr = new JSONResult();
		List<GroupInfoModel> list = groupService.querthirteenGroup(city);
		if (list == null) {
			return jr.setValue(RetEnum.ERROR, "数据是空的！");
		} else {
			return jr.setValue(RetEnum.SUCCESS, "查询完成", list);
		}
	}

	/** 根据地区查询单个的团体 */
	@RequestMapping(path = "/queryOneGroup") // NOT_API
	public String queryOneGroup(String city) {
		// 创建 JSONResult对象，用于返回JSON字符串
		JSONResult jr = new JSONResult();
		GroupInfoModel group = groupService.queryOneGroup(city);
		if (group == null) {
			return jr.setValue(RetEnum.ERROR, "数据是空的！");
		} else {
			return jr.setValue(RetEnum.SUCCESS, "查询完成", group);
		}
	}

	/** 查询个人五个 */
	@RequestMapping(path = "/personal/query/top_five") // NOT_API
	public String doPersonalQueryTopFive() {
		return new JSONResult().setValue(RetEnum.SUCCESS, "查询完成", userInfoService.queryTopFive());
	}

	/** 查询13条最新发布的企业用户信息 */
	@RequestMapping(path = "/enterPriseThirteen") // NOT_API
	public String enterPriseThirteen(String city) {
		JSONResult jr = new JSONResult();
		List<EnterpriseTopOneModel> list = enterpriseService.enterPriseThirteen(city);
		if (list == null) {
			return jr.setValue(RetEnum.ERROR, "数据是空的！");
		} else {
			return jr.setValue(RetEnum.SUCCESS, "查询完成", list);
		}
	}

	/** 查询个人前十 */
	@RequestMapping(path = "/oderByMaxIdUserInfo") // NOT_API
	public String oderByMaxIdUserInfo(String city) {
		JSONResult jr = new JSONResult();
		List<PersonalTopOneModel> list = userInfoService.oderByMaxIdUserInfo(city);
		if (list == null) {
			return jr.setValue(RetEnum.ERROR, "数据是空的！");
		} else {
			return jr.setValue(RetEnum.SUCCESS, "查询完成", list);
		}
	}

	/** 查询企业置顶 */
	@RequestMapping(path = "/enterprise/query/top") // NOT_API
	public String doEnterpriseQueryTop() {
		EnterpriseTopOneModel enterprise = deskdisplayService.queryEnterpriseTop();
		return new JSONResult().setValue(RetEnum.SUCCESS, "查询完成", enterprise);
	}

	/**
	 * 查询最新注册的企业
	 * 
	 * @param quantity
	 *            查询的数量
	 */
	@RequestMapping(path = "/enterprise/query/new_{quantity}") // NOT_API
	public String doEnterpriseQueryNew(@PathVariable("quantity") String quantity) {
		List<EnterpriseInfo> list = null;
		switch (quantity) {
		case "three":
		case "3": // 查询三个
			list = enterpriseService.queryQuantityOrderByCreateTime(3);
			break;
		case "five":
		case "5":// 查询五个
			list = enterpriseService.queryQuantityOrderByCreateTime(5);
			break;
		case "six":
		case "6":// 查询六个
			list = enterpriseService.queryQuantityOrderByCreateTime(6);
			break;
		case "ten":
		case "10":// 查询十个
			list = enterpriseService.queryQuantityOrderByCreateTime(10);
			break;
		}
		return new JSONResult().setValue(RetEnum.SUCCESS, list);
	}

	/** 查询前五条新发布的需求 */
	@RequestMapping(path = "/demand/query/new") // NOT_API
	public String doDemandQueryNew(Integer offset, String city) {
		Filters filters = new Filters();
		filters.setPageNum(1);
		filters.setPageSize(offset);
		return new JSONResult().setValue(RetEnum.SUCCESS, demandInfoService.search(filters));
	}

	/** 【个人】查询置顶 */
	@RequestMapping(path = "/personal/query/top") // NOT_API
	public String doPersonalQueryTop() {
		PersonalTopOneModel result = deskdisplayService.queryPersonalTop();
		return new JSONResult().setValue(RetEnum.SUCCESS, "执行成功", result);
	}

	/** 【广告】查询个人用户发布的广告 */
	@RequestMapping(path = "/ad/query/personal") // NOT_API
	public String doAdQueryPersonal(Integer limit, Integer offset, String city) {
		return new JSONResult().setValue(RetEnum.SUCCESS, adPublicService.queryAdPersonal(limit, offset, city));
	}

	/** 【广告】查询企业用户发布的广告 */
	@RequestMapping(path = "/ad/query/enterprise") // NOT_API
	public String doAdQueryEnterprise(Integer limit, Integer offset, String city) {
		return new JSONResult().setValue(RetEnum.SUCCESS, adPublicService.queryAdEnterprise(limit, offset, city));
	}

	/** 【广告】查询用户发布的广告（忽略企业、个人） */
	@RequestMapping(path = "/ad/query/all") // NOT_API
	public String doAdQueryAll(Filters filters) {
		List<AdPublic> result = adPublicService.queryAllAndFilter(filters);
		return new JSONResult().setValue(RetEnum.SUCCESS, result);
	}

	/** 查询Id最大的五条广告 */
	@RequestMapping(path = "/queryAdPublicMaxFive") // NOT_API
	public String queryAdPublicMaxFive(Integer limit, Integer offset, String city) {
		JSONResult jr = new JSONResult();
		List<AdPublic> list = adPublicService.queryAdPublicMaxFive(limit, offset, city);
		if (list == null) {
			return jr.setValue(RetEnum.ERROR, "数据是空的！");
		} else {
			return jr.setValue(RetEnum.SUCCESS, "查询完成", list);
		}
	}

}