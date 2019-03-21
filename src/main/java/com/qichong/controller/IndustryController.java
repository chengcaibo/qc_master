package com.qichong.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qichong.entity.IndustryType;
import com.qichong.enums.RetEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.service.IndustryTypeService;
import com.qichong.util.web.ServletUtil;

import static com.qichong.util.web.ServletUtil.*;

/**
 * 行业操作相关Controller
 * 
 * @创建人 吴志伟
 * @修改人 孙建雷
 * @修改时间 2018年1月3日13:38:23
 */
@Controller
@RequestMapping("/industry")
public class IndustryController {
	@Autowired
	IndustryTypeService industryTypeService;

	/**
	 * 根据传入的名称，模糊查询行业
	 */
	@RequestMapping(path = "/query/like", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doQueryLike(Filters filters) {
		try {
			if (isEmpty(filters.getName()))
				return JSONResult.paramLack("请传入要查询的名称").toJSON();
			return JSONResult.success(industryTypeService.queryLikeIndustry(filters)).toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.exception().toJSON();
		}

	}

	/**
	 * 根据传入的父行业Code，查询所有子行业
	 */
	@RequestMapping(path = "/query/child", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doQueryChild(String parentCode) {
		if (isEmpty(parentCode)) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "请传入父行业Code");
		}
		List<IndustryType> list = industryTypeService.queryChild(parentCode);
		if (!list.isEmpty()) {
			return new JSONResult().setValue(RetEnum.SUCCESS, list);
		} else {
			return new JSONResult().setValue(RetEnum.VALUE_EMPTY, "没有更多子级了");
		}
	}

	/**
	 * 根据传入的【子行业Code】， 反向迭代查询【行业家谱】，直至【顶级】
	 */
	@RequestMapping(path = "/query/parent", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doQueryParent(String childCode) {
		if (isEmpty(childCode)) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "请传入子行业Code");
		}
		return new JSONResult().setValue(RetEnum.SUCCESS, industryTypeService.queryParentIndustry(childCode));
	}

}
