package com.qichong.controller;

import static com.qichong.util.web.ServletUtil.isEmpty;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qichong.entity.CaseInfo;
import com.qichong.entity.Users;
import com.qichong.enums.PathEnum;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.service.CaseInfoService;
import com.qichong.service.UsersService;
import com.qichong.token.LoginToken;
import com.qichong.util.OSSUtil;
import com.qichong.util.Utils;
import com.qichong.util.web.ServletUtil;

/**
 * 成功案例
 * 
 * @创建人 陈文瑾
 * @创建时间 2017-12-29 11:48:21
 */
@Controller
public class CaseInfoController {

	@Autowired
	CaseInfoService caseInfoService;

	@Autowired
	UsersService usersService;

	@RequestMapping(path = "/caseinfo/{id}", method = RequestMethod.GET)
	public String caseInfoById(@PathVariable("id") int id, Model model) {
		model.addAttribute("ci", caseInfoService.queryOneByCaseId(id));
		return "user/user-center";
	}

	/** 根据id查询成功案例 */
	@RequestMapping(path = "/center/case/{id}", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doCenterCaseId(@PathVariable("id") Integer id) {
		return new JSONResult().setValue(RetEnum.SUCCESS, caseInfoService.queryOneByCaseId(id));
	}

	/** 根据UserId查询成功案例 */
	@RequestMapping(path = "/center/case/user/{userId}", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doCenterCaseUserId(@PathVariable("userId") Integer userId) {
		return new JSONResult().setValue(RetEnum.SUCCESS, caseInfoService.queryOneByUserId(userId));
	}

	/** 查询当前登录用户的成功案例 */
	@RequestMapping(path = "/center/case/self", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doCenterCaseSelf(HttpSession session, LoginToken token) {
		// 获取登录用户
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		}
		return this.doCenterCaseUserId(loginUser.getId());
	}

	/** 新增成功案例 */
	@RequestMapping(path = "/center/case/insert", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doCenterCaseInsert(MultipartFile caseFile, CaseInfo caseInfo, HttpSession session, LoginToken token)
			throws IOException {
		// 判断是否登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		}
		// 判断参数是否完整
		boolean isNotFull = (caseInfo.getEndTime() == null || caseInfo.getStartTime() == null
				|| (caseFile == null || caseFile.isEmpty()))
				|| isEmpty(caseInfo.getCaseContent(), caseInfo.getCaseName(), caseInfo.getClientAddress(),
						caseInfo.getClientName(), caseInfo.getClientTelephone()) > 0;
		if (isNotFull) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "缺少参数");
		}
		String name = loginUser.getId() + Utils.randomString(16) + "_case.jpg";
		new OSSUtil(PathEnum.SUCCESS_CASE).uploadFile2OSS(caseFile.getInputStream(), name);
		caseInfo.setPicture(name);
		caseInfo.setUser(loginUser);
		caseInfoService.insertOne(caseInfo);
		return new JSONResult().setValue(RetEnum.SUCCESS);
	}

	/** 修改成功案例信息 */
	@RequestMapping(path = "/center/case/change", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doChangeCase(MultipartFile caseFile, CaseInfo ci, HttpSession session, LoginToken token)
			throws IOException {
		// 判断是否登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		}
		// 判断参数是否完整
		boolean isNotFull = (ci.getEndTime() == null || ci.getStartTime() == null) || isEmpty(ci.getCaseContent(),
				ci.getCaseName(), ci.getClientAddress(), ci.getClientName(), ci.getClientTelephone()) > 0;
		if (isNotFull) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "缺少参数");
		}
		// 上传到阿里云OSS
		if (caseFile != null && !caseFile.isEmpty()) {
			String name = ci.getId() + "_case.jpg";
			new OSSUtil(PathEnum.SUCCESS_CASE).uploadFile2OSS(caseFile.getInputStream(), name);
			ci.setPicture(name);
		}
		caseInfoService.changeCase(ci);
		return new JSONResult().setValue(RetEnum.SUCCESS, "保存成功！");
	}

	/** 删除成功案例 */
	@RequestMapping(path = "/center/case/delete", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doCenterCaseDelete(Integer id, HttpSession session, LoginToken token) {
		// 判断是否登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		} else if (id == null) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "缺少参数");
		} else {
			caseInfoService.deleteOne(id);
			return new JSONResult().setValue(RetEnum.SUCCESS);
		}
	}

}
