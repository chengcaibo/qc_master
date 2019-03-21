package com.qichong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.qichong.enums.RetEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.service.AdPrivateService;
import com.qichong.util.Utils;
import com.qichong.util.web.ServletUtil;

@RestController
@RequestMapping(path = "/adprivate", produces = ServletUtil.JSON_PRODUCES)
public class AdPrivateController {

	@Autowired
	AdPrivateService adpService;
	@Autowired
	AdPrivateService adPrivateService;


	/** 【工具】查询所有搜索位广告 */
	@RequestMapping(path = "/tools/search-list-ad", method = RequestMethod.GET)
	public String doToolsSearchListAd() {
		return JSONResult.success(adpService.queryToolsSearchListAd()).toJSON();
	}

	/** 【需求】查询所有搜索位广告 */
	@RequestMapping(path = "/demand/search-list-ad", method = RequestMethod.GET)
	public String doDemandSearchListAd() {
		return JSONResult.success(adpService.queryDemandSearchListAd()).toJSON();
	}

	/** 【团体】查询所有搜索位广告 */
	@RequestMapping(path = "/group/search-list-ad", method = RequestMethod.GET)
	public String doGroupSearchListAd() {
		return JSONResult.success(adpService.queryGroupSearchListAd()).toJSON();
	}

	/** 【企业】查询所有企业热门广告 */
	@RequestMapping(path = "/enterprise/hots-ad", method = RequestMethod.GET)
	public String doEnterpriseHotsAd() {
		return JSONResult.success(adpService.queryEnterpriseHotsAd()).toJSON();
	}

	/** 【企业】查询所有搜索位广告 */
	@RequestMapping(path = "/enterprise/search-list-ad", method = RequestMethod.GET)
	public String doEnterpriseSearchListAd() {
		return JSONResult.success(adpService.queryEnterpriseSearchListAd()).toJSON();
	}

	/** 【个人】查询所有搜索位广告 */
	@RequestMapping(path = "/personal/search-list-ad", method = RequestMethod.GET)
	public String doPersonalSearchListAd() {
		return JSONResult.success(adpService.queryPersonalSearchListAd()).toJSON();
	}
	

	/** 【搜索】私有广告 */
	@RequestMapping(path = "/search", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSearchAdPrivate(Filters filters) {
		return new JSONResult().setValue(RetEnum.SUCCESS, adPrivateService.search(filters));
	}


	/** 【异常处理】处理全局异常 */
	@ExceptionHandler(Exception.class)
	public String catchGlobalException(Exception ex) {
		Throwable th = Utils.isQCError(ex);
		if (th != null)
			return JSONResult.error(th.getMessage()).toJSON();

		System.out.println("= = = = 【BEGIN AdPrivateController】 = = = =");
		ex.printStackTrace();
		System.out.println("= = = = 【END AdPrivateController】 = = = =");
		return JSONResult.exception().toJSON();
	}

}
