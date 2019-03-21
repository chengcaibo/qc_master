package com.qichong.controller;

import static com.qichong.util.web.ServletUtil.isEmpty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qichong.entity.SharedTypes;
import com.qichong.enums.RetEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.service.SharedTypeService;
import com.qichong.util.web.ServletUtil;

@Controller
@RequestMapping("/shared_type")
public class SharedTypesController {

	@Autowired
	SharedTypeService service;

	/** 根据传入的名称，模糊查询所有项，可进行分页 */
	@RequestMapping(path = "/query/like", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doQueryLike(Filters filters) {
		try {
			if (isEmpty(filters.getName()))
				return JSONResult.paramLack("请传入要查询的名称").toJSON();
			return JSONResult.success(service.queryLikeAll(filters)).toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.exception().toJSON();
		}

	}

	/** 根据传入的父级code，查询所有子级 */
	@RequestMapping(path = "/query/child", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doQueryChild(String parentCode) {
		if (isEmpty(parentCode)) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "请传入父地区Code");
		}
		List<SharedTypes> list = service.queryChild(parentCode);
		if (!list.isEmpty()) {
			return new JSONResult().setValue(RetEnum.SUCCESS, list);
		} else {
			return new JSONResult().setValue(RetEnum.VALUE_EMPTY, "没有更多子级了");
		}
	}

	/** 根据传入的【子级code】， 反向迭代向上查询，直至【顶级】 */
	@RequestMapping(path = "/query/parent", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doQueryParent(String childCode) {
		if (isEmpty(childCode)) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "请传入子行业Code");
		}
		return new JSONResult().setValue(RetEnum.SUCCESS, service.queryParentToTopLevel(childCode));
	}

}
