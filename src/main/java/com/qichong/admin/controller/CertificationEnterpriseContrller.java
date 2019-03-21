package com.qichong.admin.controller;

import static com.qichong.util.web.ServletUtil.isEmpty;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qichong.entity.CertificationEnterprise;
import com.qichong.entity.EnterpriseInfo;
import com.qichong.entity.State;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.service.CertificationEnterpriseService;
import com.qichong.service.EnterpriseInfoService;
import com.qichong.service.UsersService;
import com.qichong.token.LoginToken;
import com.qichong.util.web.ServletUtil;

@Controller
public class CertificationEnterpriseContrller {

	@Autowired
	CertificationEnterpriseService service;
	@Autowired
	UsersService usersService;
	@Autowired
	EnterpriseInfoService enterpriseService;

	/** 【实名认证审核】进入实名认证 审核详情页面 */
	@RequestMapping(path = "/admin/audits/enterprise/{id}")// NOT_API
	public String doAdminCertInfo(@PathVariable("id") Object id, Model model) {
		// 查询
		try {
			CertificationEnterprise ci = service.queryOneById(Integer.parseInt(id.toString()));
			if (ci == null) {
			} else if (ci.getState().getId() != 4) {
			} else {
				model.addAttribute("info", ci);
				return "admin/audits/enterprise-audit";
			}
			return "admin/404";
		} catch (NumberFormatException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/404";
	}

	/** 【实名认证审核】提交审核结果 */
	@RequestMapping(path = "/admin/enterprise/cert_action", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String doAdminCertAction(Integer id, String reason, String button) {
		if (id == null || (ServletUtil.isEmpty(button))) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK);
		}
		CertificationEnterprise ci = new CertificationEnterprise();
		if (button.equalsIgnoreCase("rejected")) { // 驳回
			if (ServletUtil.isEmpty(reason)) {
				return new JSONResult().setValue(RetEnum.PARAM_LACK);
			}
			ci.setId(id);
			ci.setReason(reason);
			ci.setState(new State(6));
			service.updateOne(ci);
			return this.queryNext(id);
		} else if (button.equalsIgnoreCase("notSure")) { // 待定
			return this.queryNext(id);
		} else if (button.equalsIgnoreCase("passed")) { // 通过
			ci.setId(id);
			ci.setState(new State(5));
			service.updateOne(ci);
			// 同步到enterpriseInfo表中
			CertificationEnterprise cinfo = service.queryOneById(Integer.parseInt(id.toString()));
			EnterpriseInfo einfo = new EnterpriseInfo(cinfo.getUser());
			einfo.setEnterpriseName(cinfo.getEnterpriseName());
			einfo.setPersonName(cinfo.getPersonName());
			einfo.setBusinessLicense(cinfo.getBusinessLicense().getName());
			enterpriseService.updateOne(einfo);
			return this.queryNext(id);
		} else {
			return new JSONResult().setValue(RetEnum.PARAM_ERROR);
		}
	}

	/** 【实名认证审核】查询下一条待审核的资料id */
	private String queryNext(int notId) {
		CertificationEnterprise temp = service.queryNextWait(notId);
		if (temp == null) {
			return new JSONResult().setValue(RetEnum.SUCCESS, -1);
		} else {
			return new JSONResult().setValue(RetEnum.SUCCESS, temp.getId());
		}
	}

	/** 【实名认证审核】分页查询出所有的资料，wait(待审核)passed(已通过)not-passed(未通过) */
	@RequestMapping(path = "/admin/enterprise/query_certification_{method}", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String doAdminQueryCertification(@PathVariable("method") String method, Filters filter) {
		JSONResult jr = new JSONResult();
		if (method.equalsIgnoreCase("wait")) {
			service.queryAllWait(filter, jr);// 查询待审核
		} else if (method.equalsIgnoreCase("passed")) {
			service.queryAllPassed(filter, jr); // 查询已通过
		} else {
			service.queryAllNotPassed(filter, jr); // 查询未通过
		}
		return jr.toJSON();
	}

	/** 【实名认证审核】查询我的审核资料详情 */
	@RequestMapping(path = "/auidt/enterprise/check/self", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String doAdminCertMe(HttpSession session, LoginToken token) {
		// 获取当前登录的用户
		Users currentUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (currentUser.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		}

		CertificationEnterprise ci = service.queryOneByUserId(currentUser.getId());
		if (ci == null) {
			// 未提交审核
			return new JSONResult().setValue(RetEnum.AUDIT_NO_SUBMIT);
		} else if (ci.getState().getId() == 4) {
			return new JSONResult().setValue(RetEnum.AUDIT_WAIT, ci);
		} else if (ci.getState().getId() == 5) {
			return new JSONResult().setValue(RetEnum.AUDIT_PASSED, ci);
		} else if (ci.getState().getId() == 6) {
			return new JSONResult().setValue(RetEnum.AUDIT_NOT_PASSED, ci);
		} else {
			return new JSONResult().setValue(RetEnum.ERROR, "未知错误");
		}
	}

	/** 上传认证信息 */
	@RequestMapping(path = "/auidt/enterprise/upload", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String doAuditEnterpriseUpload(CertificationEnterprise ce, MultipartFile licenseFile, HttpSession session,
			LoginToken token) {
		try {
			Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
			Integer userId = loginUser.getId();
			if (userId == null)
				return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
			else if (isEmpty(ce.getEnterpriseName(), ce.getPersonName()) > 0)
				return JSONResult.paramLack().toJSON();
			else if (licenseFile == null || licenseFile.isEmpty())
				return JSONResult.paramLack().toJSON();
			else
				return service.auditEnterpriseUpload(ce, userId, licenseFile).toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, "服务器异常").toJSON();
		}
	}

	/**
	 * 查询企业审核信息(传值stateId)
	 * 
	 * @param method
	 * @param filter
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/query/enterprise", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String doAdminQueryCertification(Filters filter, HttpSession session, LoginToken token) {
		// 判断是否登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		// 判断当前登录的是不是admin用户
		Object adminToken = session.getAttribute("adminToken");
		if (loginUser.getId() == null && adminToken == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		}
		JSONResult jr = new JSONResult();
		// 企业审核（根据状态ID获取查询数据，和获取总行数）
		service.queryAll(filter, jr);
		return jr.toJSON();
	}

	/**
	 * 查询企业单个信息(传值id)
	 * 
	 * @param method
	 * @param filter
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/enterprise/detail", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String manageQueryEnterpriseDetail(Filters filter, HttpSession session, LoginToken token) {
		// 判断是否登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		// 判断当前登录的是不是admin用户
		Object adminToken = session.getAttribute("adminToken");
		if (loginUser.getId() == null && adminToken == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		}
		// 企业审核（根据状态ID获取查询数据，和获取总行数）
		CertificationEnterprise queryOneById = service.queryOneById(filter.getId());
		return new JSONResult().setValue(RetEnum.SUCCESS, queryOneById);
	}

	/** 修改企业状态--ajax */
	@RequestMapping(path = "/manage/enterprise/edit", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String manageEditDemand(CertificationEnterprise enterprise, HttpSession session, LoginToken token) {

		// 判断是否登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		Object adminToken = session.getAttribute("adminToken");
		if (loginUser.getId() == null && adminToken == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		}
		// 判断传值是否一样
		if (enterprise.getId() <= 0 || enterprise.getState().getId() <= 1) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "参数不完整");
		}
		service.updateOne(enterprise);
		return new JSONResult().setValue(RetEnum.SUCCESS, "成功");
	}
}
