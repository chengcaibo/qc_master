package com.qichong.controller;

import static com.qichong.util.web.ServletUtil.isEmpty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qichong.entity.JobInfo;
import com.qichong.enums.RetEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.service.JobInfoService;
import com.qichong.util.web.ServletUtil;

/**
 * 职业操作相关Controller
 * 
 * @创建人 孙建雷
 * @修改时间 2018年4月13日16:24:37
 */
@Controller
@RequestMapping("/job_info")
public class JobInfoController {

	@Autowired
	JobInfoService jobInfoService;

	/** 根据传入的名称，模糊查询行业 */
	@RequestMapping(path = "/query/like", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doQueryLike(Filters filters) {
		try {
			if (isEmpty(filters.getName()))
				return JSONResult.paramLack("请传入要查询的名称").toJSON();
			return JSONResult.success(jobInfoService.queryLikeAllJob(filters)).toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.exception().toJSON();
		}
	}

	/** 根据传入的父职业Code，查询所有子职业 */
	@RequestMapping(path = "/query/child", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doQueryChild(String parentCode) {
		if (isEmpty(parentCode)) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "请传入父职业Code");
		}
		List<JobInfo> list = jobInfoService.queryChild(parentCode);
		if (!list.isEmpty()) {
			return new JSONResult().setValue(RetEnum.SUCCESS, list);
		} else {
			return new JSONResult().setValue(RetEnum.VALUE_EMPTY, "没有更多子级了");
		}
	}

	/** 根据传入的【子职业Code】， 反向迭代查询【职业家谱】，直至【顶级】 */

	@RequestMapping(path = "/query/parent", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doQueryParent(String childCode) {
		if (isEmpty(childCode)) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "请传入子职业Code");
		}
		return new JSONResult().setValue(RetEnum.SUCCESS, jobInfoService.queryParentIndustry(childCode));
	}

}
