package com.qichong.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qichong.entity.Regions;
import com.qichong.enums.RetEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.service.RegionsService;
import com.qichong.util.web.ServletUtil;
import static com.qichong.util.web.ServletUtil.*;

/**
 * 地区操作相关Controller
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2018年1月4日11:48:22
 */
@Controller
@RequestMapping("/region")
public class RegionController {

	@Autowired
	RegionsService regionsService;

	/**
	 * 根据传入的名称，模糊查询地区
	 */
	@RequestMapping(path = "/query/like", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doQueryLikeRegion(Filters filters) {
		try {
			if (isEmpty(filters.getName()))
				return JSONResult.paramLack("请传入要查询的名称").toJSON();
			return JSONResult.success(regionsService.queryLikeRegion(filters)).toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.exception().toJSON();
		}
	}

	/**
	 * 根据传入的【父地区Code】，查询所有的【子地区】信息
	 */
	@RequestMapping(path = "/query/child", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doQueryChild(String parentCode) {
		if (isEmpty(parentCode)) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "请传入父地区Code");
		}
		List<Regions> list = regionsService.queryChild(parentCode);
		if (!list.isEmpty()) {
			return new JSONResult().setValue(RetEnum.SUCCESS, list);
		} else {
			return new JSONResult().setValue(RetEnum.VALUE_EMPTY, "没有更多子级了");
		}
	}

	/**
	 * 根据传入的【子地区Code】， 反向迭代查询【地区家谱】，直至【顶级】
	 */
	@RequestMapping(path = "/query/parent", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doQueryParent(String childCode) {
		if (isEmpty(childCode)) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "请传入子地区Code");
		}
		return new JSONResult().setValue(RetEnum.SUCCESS, regionsService.queryParent(childCode));
	}
}
