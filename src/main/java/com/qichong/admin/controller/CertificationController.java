package com.qichong.admin.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.qichong.util.JSONUtil;
import net.sf.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qichong.entity.CertificationEnterprise;
import com.qichong.entity.CertificationInfo;
import com.qichong.entity.State;
import com.qichong.entity.Users;
import com.qichong.enums.PathEnum;
import com.qichong.enums.RetEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.service.CertificationEnterpriseService;
import com.qichong.service.CertificationInfoService;
import com.qichong.service.UsersService;
import com.qichong.token.LoginToken;
import com.qichong.util.OSSUtil;
import com.qichong.util.Utils;
import com.qichong.util.web.ServletUtil;

/**
 * 认证用Controller account
 */
@Controller
public class CertificationController {

	@Autowired
	CertificationInfoService service;
	@Autowired
	CertificationEnterpriseService certificationEnterpriseService;

	@Autowired
	UsersService usersService;

	/** 【实名认证审核】进入实名认证 审核详情页面 */
	@RequestMapping(path = "/admin/audits/realname/{id}")
	public String doAdminCertInfo(@PathVariable("id") Object id, Model model, HttpServletResponse response) {
		// 查询
		try {
			CertificationInfo ci = service.queryOneById(Integer.parseInt(id.toString()));
			if (ci == null)
			{

			} else if (ci.getState().getId() != 4)
			{

			} else {
				model.addAttribute("info", ci);
				return "admin/audits/realname-audit";
				// return "admin/audits/certInfo";
			}
			response.setStatus(404);
			return "admin/404";
		} catch (NumberFormatException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setStatus(404);
		return "admin/404";
	}

	/** 上传pc-端实名认证 */
	@RequestMapping(path = "/audit/realname/upload_pc", method = RequestMethod.POST)
	public String uploadCertification(CertificationInfo ci, Integer fullId, MultipartFile image_aFile,
			MultipartFile image_bFile, MultipartFile image_cFile, HttpSession session, LoginToken token, Model model) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null) {
			model.addAttribute("title", "尚未登录");
			model.addAttribute("content", "抱歉，您尚未登录，还没有权限进行操作");
			model.addAttribute("iconClass", "icon-error");
			return "function/common";// 返回未登录
		}

		// 判断参数完不完整
		if (ServletUtil.isEmpty(ci.getRealName(), ci.getIdentity()) > 0 || image_aFile == null || image_bFile == null
				|| image_cFile == null) {
			model.addAttribute("title", "参数不完整");
			model.addAttribute("content", "参数不完整");
			model.addAttribute("iconClass", "icon-error");
			return "function/common"; // 返回参数不完整
		}

		CertificationInfo queryCi = service.queryOneByUserId(user.getId());
		if (queryCi == null || fullId != null) {
			ci.setUser(user);
			// 上传图片
			ci.setIdentityPictureA(user.getId() + "_identity_a.jpg");
			ci.setIdentityPictureB(user.getId() + "_identity_b.jpg");
			ci.setIdentityPictureC(user.getId() + "_identity_c.jpg");
			this.certUploadToOSS(image_aFile, ci.getIdentityPictureA().getName());
			this.certUploadToOSS(image_bFile, ci.getIdentityPictureB().getName());
			this.certUploadToOSS(image_cFile, ci.getIdentityPictureC().getName());

			if (fullId == null) {
				service.insertOne(ci);
			} else {
				ci.setId(fullId);
				ci.setState(new State(4));
				service.updateOne(ci);
			}
			model.addAttribute("iconClass", "icon-success");
			model.addAttribute("title", "提交审核成功");
			model.addAttribute("content", "提交审核成功,请耐心等待,我们会在72小时内给您回复！");
			return "function/common";// 返回成功
		} else {
			model.addAttribute("iconClass", "icon-error");
			model.addAttribute("title", "提交失败");
			model.addAttribute("content", "您已提交过审核，请勿重复提交！");
			return "function/common";// 返回成功
		}
	}

	/** 【实名认证审核】提交审核结果 */
	@RequestMapping(path = "/admin/cert_action", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doAdminCertAction(Integer id, String reason, String button) {
		try {
			if (id == null || (ServletUtil.isEmpty(button))) {
				return JSONResult.builder(RetEnum.PARAM_LACK).toJSON();
			} else if (button.equalsIgnoreCase("rejected")) { // 驳回
				service.rejectedRealnameAudit(id, reason);
			} else if (button.equalsIgnoreCase("notSure")) { // 待定
				// go to query next
			} else if (button.equalsIgnoreCase("passed")) { // 通过
				service.passedRealnameAudit(id);
			} else {
				return JSONResult.builder(RetEnum.PARAM_ERROR, "不能识别的button参数：" + button).toJSON();
			}
			// query next
			return this.queryNext(id);
		} catch (Exception e) {
			Throwable th = Utils.isQCError(e);
			if (th != null)
				return JSONResult.builder(RetEnum.ERROR, th.getMessage()).toJSON();
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, e.getMessage()).toJSON();
		}
	}

	/** 【实名认证审核】查询下一条待审核的资料id */
	private String queryNext(int notId) {
		CertificationInfo temp = service.queryNextWait(notId);
		if (temp == null) {
			// -1 代表没有下一条了
			return new JSONResult().setValue(RetEnum.SUCCESS, -1);
		} else {
			return new JSONResult().setValue(RetEnum.SUCCESS, temp.getId());
		}
	}

	/** 【实名认证审核】分页查询出所有的资料，wait(待审核)passed(已通过)not-passed(未通过) */
	@RequestMapping(path = "/admin/query_certification_{method}", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
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

	/** 【通用查询审核】查询我的审核资料详情 */
	@RequestMapping(path = "/audit/common/query/self", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doAuditCommonQuerySelf(HttpSession session, LoginToken token) {
		try {
			Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
			if (loginUser==null||loginUser.getId() == null){
				return JSONResult.noLogin().toJSON();
			}
			int userId = loginUser.getId();
			State status = null;
			JSONResult result = JSONResult.builder();
			// 判断是个人还是企业
			Users queryUser = usersService.queryByUserId(userId);
			if (queryUser.getTypeId() == 1) {
				CertificationInfo cinfo = service.queryOneByUserId(userId);
				status = cinfo.getState();
				result.setResult(cinfo);
			} else if (queryUser.getTypeId() == 2) {
				CertificationEnterprise cent = certificationEnterpriseService.queryOneByUserId(userId);
				status = cent.getState();
				result.setResult(cent);
			} else {
				return JSONResult.builder(RetEnum.ERROR, "不能查询管理员的审核信息").toJSON();
			}
			JSONResult jr = stateToRet(status);
			result.setRet(jr.getRet());
			result.setMsg(jr.getMsg());
			return result.toJSON();
		} catch (NullPointerException e) { // 未提交审核
			return JSONResult.builder(RetEnum.AUDIT_NO_SUBMIT, "未提交审核").toJSON();
		} catch (Exception e) {
			return JSONResult.builder(RetEnum.EXCEPTION, "服务器匆忙").toJSON();
		}
	}

	/** 【通用查询审核】查询我的审核资料详情，这个应该是用户查询自己的评审状态,用户在发布自己的需求的时候首先需要实名认证 */
	@RequestMapping(path = "/audit/common/check/self", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doAuditCommonCheckSelf(HttpSession session, LoginToken token) {
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser==null||loginUser.getId() == null){
			return JSONResult.noLogin().toJSON();
		}
		return this.doAuditCommonCheckUserId(loginUser.getId());
	}

	/** 【通用查询审核】查询我的审核资料详情,还没有完成具体的编写，后续可将编写为管理员查看用户的审核状态 */
	@RequestMapping(path = "/audit/common/check/{userId}", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doAuditCommonCheckUserId(@PathVariable("userId") Integer userId) {
		try {
			HashMap<String, Object> map = new HashMap<>();
			State status = null;
			Integer isAgree=null;
			// 判断是个人还是企业
			Users queryUser = usersService.queryByUserId(userId);
			if (queryUser.getTypeId() == 1) {
				CertificationInfo certificationInfo = service.queryOneByUserId(userId);
				status = certificationInfo.getState();
				isAgree = certificationInfo.getIsAgree();
			} else if (queryUser.getTypeId() == 2) {
				CertificationEnterprise certificationEnterprise = certificationEnterpriseService.queryOneByUserId(userId);
				status = certificationEnterprise.getState();
				isAgree=certificationEnterprise.getIsAgree();
			} else {
				return JSONResult.builder(RetEnum.AUDIT_PASSED).toJSON();
			}
			if (status == null) {
				map.put("ret",RetEnum.ERROR.getValue());
				map.put("msg","未知错误");
			} else if (status.getId() == 4) {
				map.put("ret",RetEnum.AUDIT_WAIT.getValue());
				map.put("msg","等待审核中");
			} else if (status.getId() == 5) {
				map.put("ret",RetEnum.AUDIT_PASSED.getValue());
				map.put("msg","审核已通过");
			} else if (status.getId() == 6) {
				map.put("ret",RetEnum.AUDIT_NOT_PASSED.getValue());
				map.put("msg","审核未通过");
			} else {
				map.put("ret",RetEnum.PARAM_ERROR.getValue());
				map.put("msg","未知错误");
			}
			map.put("isAgree",isAgree);
			// 判断实名认证结果
			return JSONResult.builder(map);
		} catch (NullPointerException e) { // 未提交审核
		/*如果service.queryOneByUserId(userId)为null或者service.queryOneByUserId(userId).getState();都会报出空指针异常，说明该条记录未提交评审
		当然一般来说，service.queryOneByUserId(userId)是不会为空的，一般是状态为空，因为查询的一般都是已经存在的用户，已经存在的用户没有提交评审是正常的*/
			return JSONResult.builder(RetEnum.AUDIT_NO_SUBMIT, "未提交审核").toJSON();
		} catch (Exception e) {
			return JSONResult.builder(RetEnum.EXCEPTION, "服务器匆忙").toJSON();
		}
	}

	private JSONResult stateToRet(State state) {
		if (state == null) {
			return JSONResult.builder(RetEnum.ERROR, "未知错误");
		} else if (state.getId() == 4) {
			return JSONResult.builder(RetEnum.AUDIT_WAIT, "等待审核中");
		} else if (state.getId() == 5) {
			return JSONResult.builder(RetEnum.AUDIT_PASSED, "审核已通过");
		} else if (state.getId() == 6) {
			return JSONResult.builder(RetEnum.AUDIT_NOT_PASSED, "审核未通过");
		} else {
			return JSONResult.builder(RetEnum.PARAM_ERROR, "未知错误");
		}
	}

	// /** 【实名认证审核】查询我的审核资料详情 */
	// public String doAuditRealnameCheck(Integer userId) {
	// CertificationInfo ci = service.queryOneByUserId(userId);
	// if (ci == null) {
	// // 未提交审核
	// return new JSONResult().setValue(RetEnum.AUDIT_NO_SUBMIT);
	// } else if (ci.getState().getId() == 4) {
	// return new JSONResult().setValue(RetEnum.AUDIT_WAIT, ci);
	// } else if (ci.getState().getId() == 5) {
	// return new JSONResult().setValue(RetEnum.AUDIT_PASSED, ci);
	// } else if (ci.getState().getId() == 6) {
	// return new JSONResult().setValue(RetEnum.AUDIT_NOT_PASSED, ci);
	// } else {
	// return new JSONResult().setValue(RetEnum.ERROR, "未知错误");
	// }
	// }

	// /** 【企业认证审核】查询我的审核资料详情 */
	// public String doAuditEnterpriseCheck(Integer userId) {
	// CertificationEnterprise ci =
	// certificationEnterpriseService.queryOneByUserId(userId);
	// if (ci == null) {
	// // 未提交审核
	// return new JSONResult().setValue(RetEnum.AUDIT_NO_SUBMIT);
	// } else if (ci.getState().getId() == 4) {
	// return new JSONResult().setValue(RetEnum.AUDIT_WAIT, ci);
	// } else if (ci.getState().getId() == 5) {
	// return new JSONResult().setValue(RetEnum.AUDIT_PASSED, ci);
	// } else if (ci.getState().getId() == 6) {
	// return new JSONResult().setValue(RetEnum.AUDIT_NOT_PASSED, ci);
	// } else {
	// return new JSONResult().setValue(RetEnum.ERROR, "未知错误");
	// }
	// }

	/** 【实名认证审核】上传审核资料-小程序 */

	@RequestMapping(path = "/audit/upload-realname", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doAuditRealnameUpload(CertificationInfo ci, Integer fullId, MultipartFile image_aFile,
			MultipartFile image_bFile, MultipartFile image_cFile, LoginToken token) {
		// 首先判断传入的Token是否有效
		if (!token.isEffective(usersService)) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		}
		// 判断参数完不完整
		if (ServletUtil.isEmpty(ci.getRealName(), ci.getIdentity()) > 0) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK);
		}
		ci.setUser(new Users(token.getUserId()));
		String name = token.getUserId() + "_identity_";
		// 第一次上传
		if (image_aFile != null) {
			name += "a.jpg";
			this.certUploadToOSS(image_aFile, name);
			ci.setIdentityPictureA(name + "?" + Utils.randomNumber(6));
			ci.setIdentityPictureB("wait upload");
			ci.setIdentityPictureC("wait upload");

			// 判断是否已经提交过资料了
			CertificationInfo queryTemp = service.queryOneByUserId(token.getUserId());
			if (queryTemp == null) { // 没有提交过，直接插入
				service.insertOne(ci);
			} else { // 提交过，直接修改
				ci.setId(queryTemp.getId());
				ci.setCreateTime(new Date());
				ci.setState(new State(4));
				service.updateOne(ci);
			}
			return new JSONResult().setValue(RetEnum.SUCCESS, "b", ci.getId());
		} else if (image_bFile != null && fullId != null) { // 第二次上传
			ci.setId(fullId);
			name += "b.jpg";
			this.certUploadToOSS(image_bFile, name);
			ci.setIdentityPictureB(name + "?" + Utils.randomNumber(6));

			service.updateOne(ci);
			return new JSONResult().setValue(RetEnum.SUCCESS, "c", ci.getId());
		} else if (image_cFile != null && fullId != null) {// 第三次上传
			ci.setId(fullId);
			name += "c.jpg";
			this.certUploadToOSS(image_cFile, name);
			ci.setIdentityPictureC(name + "?" + Utils.randomNumber(6));

			service.updateOne(ci);
			return new JSONResult().setValue(RetEnum.SUCCESS, "ok", ci.getId());
		}
		return new JSONResult().setValue(RetEnum.PARAM_LACK);
	}

	/** 【实名认证审核】上传审核资料-手机端 */
	@RequestMapping(path = "/audit/upload-realname-mobi", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String uploadCertificationPhone(CertificationInfo ci, Integer fullId, MultipartFile image_aFile,
			MultipartFile image_bFile, MultipartFile image_cFile, HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		//这个user是从session中获取的，登录的时候将当前用户的相关信息放入session域中
		if (user.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		}


		/*判断参数完不完整，就是实名认证的时候，需要填上完整的信息，否则是不能通过实名认证的*/
		if (ServletUtil.isEmpty(ci.getRealName(), ci.getIdentity()) > 0 || image_aFile == null || image_bFile == null
				|| image_cFile == null) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK);
		}

		CertificationInfo queryCi = service.queryOneByUserId(user.getId());
		if (queryCi == null || fullId != null) {
			ci.setUser(user);
			// 上传图片
			ci.setIdentityPictureA(user.getId() + "_identity_a.jpg");
			ci.setIdentityPictureB(user.getId() + "_identity_b.jpg");
			ci.setIdentityPictureC(user.getId() + "_identity_c.jpg");
			this.certUploadToOSS(image_aFile, ci.getIdentityPictureA().getName());
			this.certUploadToOSS(image_bFile, ci.getIdentityPictureB().getName());
			this.certUploadToOSS(image_cFile, ci.getIdentityPictureC().getName());

			if (fullId == null) {
				service.insertOne(ci);
			} else {
				ci.setId(fullId);
				ci.setState(new State(4));
				service.updateOne(ci);
			}
			return new JSONResult().setValue(RetEnum.SUCCESS);
		} else {
			return new JSONResult().setValue(RetEnum.ERROR);
		}
	}

	/** 实名认证图片上传到阿里云OSS */
	private boolean certUploadToOSS(MultipartFile file, String name) {
		try {
			new OSSUtil(PathEnum.USER_IDENTITY).uploadFile2OSS(file.getInputStream(), name);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 查询个人审核信息(传值stateId)
	 * 
	 * @param method
	 * @param filter
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/query/info", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doAdminQueryCertification(Filters filter, HttpSession session, LoginToken token) {
		JSONResult jr = new JSONResult();
		// 判断是否登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		Object adminToken = session.getAttribute("adminToken");
		if (loginUser.getId() == null && adminToken == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		}
		// 个人审核（根据状态ID获取查询数据，和获取总行数）
		service.queryAll(filter, jr);
		return jr.toJSON();
	}

	/**
	 * 查询个人单个信息(传值id)
	 * 
	 * @param method
	 * @param filter
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/info/detail", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String manageQueryEnterpriseDetail(Filters filter, HttpSession session, LoginToken token) {
		// 判断是否登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		Object adminToken = session.getAttribute("adminToken");
		if (loginUser.getId() == null && adminToken == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		}
		// 企业审核（根据状态ID获取查询数据，和获取总行数）
		CertificationInfo queryOneById = service.queryOneById(filter.getId());
		return new JSONResult().setValue(RetEnum.SUCCESS, queryOneById);
	}

	/** 修改个人状态--ajax */
	@RequestMapping(path = "/manage/info/edit", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String manageEditDemand(CertificationInfo info, HttpSession session, LoginToken token) {
		// 判断是否登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		// 判断用户是不是后台管理员
		Object adminToken = session.getAttribute("adminToken");
		if (loginUser.getId() == null && adminToken == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		}
		// 判断传值是否一样
		if (info.getId() <= 0 || info.getState().getId() <= 1) {
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "参数不完整");
		}
		service.updateOne(info);
		return new JSONResult().setValue(RetEnum.SUCCESS, "成功");
	}

	/** 后台需求查询所有的已经认证的用户（admin） */
	@RequestMapping(path = "/admin/query/user_all", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String queryDemand(Filters filters) {
		JSONResult jr = new JSONResult();
		return new JSONResult().setValue(RetEnum.SUCCESS, service.queryAll(filters, jr));
	}

	/** 后台广告查询所有的已经认证但没有发布过广告的用户（admin） */
	@RequestMapping(path = "/admin/query/adpubic_user_all", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String queryAdpublic(Filters filters) {
		return new JSONResult().setValue(RetEnum.SUCCESS, service.getCertificationInfos(filters));
	}

    /**
     * 当前用来修改认证信息中的是否同意发布需求的相关要求，
     *  0表示未同意，1表示同意
     * @author ing
     * @param userId 当前用户的userid
     * @return msg：ok表示修改成功 msg：fail表示修改失败
     *
     */
	@RequestMapping(path = "/modify/cetification/{userId}", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String modifyIsAgree(@PathVariable("userId") Integer userId) {
        Map<String,String> map=null;
        Users queryUser = usersService.queryByUserId(userId);
        if (queryUser.getTypeId() == 1) {
            map = service.updateIsAgree(userId);
        } else if (queryUser.getTypeId() == 2) {
            map = certificationEnterpriseService.updateIsAgree(userId);
        } else {
            return JSONResult.builder(RetEnum.AUDIT_PASSED).toJSON();
        }
        return JSONResult.builder(map);
	}

}
