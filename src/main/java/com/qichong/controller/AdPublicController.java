package com.qichong.controller;

import static com.qichong.util.web.ServletUtil.isEmpty;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qichong.entity.AdPublic;
import com.qichong.entity.CertificationEnterprise;
import com.qichong.entity.CertificationInfo;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.AdpublicInfoModel;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.service.AdPublicService;
import com.qichong.service.CertificationEnterpriseService;
import com.qichong.service.CertificationInfoService;
import com.qichong.service.GroupInfoServcie;
import com.qichong.service.RegionsService;
import com.qichong.service.UserInfoService;
import com.qichong.service.UsersService;
import com.qichong.token.LoginToken;
import com.qichong.util.web.ServletUtil;

@Controller
public class AdPublicController {

	@Autowired
	AdPublicService adPublicService;
	@Autowired
	UsersService usersService;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	GroupInfoServcie groupService;
	@Autowired
	RegionsService regionsService;
	@Autowired
	CertificationEnterpriseService certificationEnterpriseService;
	@Autowired
	CertificationInfoService service;

	/** 查询全部信息 --并筛选 */
	@RequestMapping(path = "/query/all/adpublicinfo", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES) // NOT_API
	@ResponseBody
	public String doQueryAllGroup(Filters filters, String city) {
		List<AdPublic> list = adPublicService.queryAllAndFilter(filters);
		return new JSONResult().setValue(RetEnum.SUCCESS, "", list);
	}

	/**
	 * 查询个人所有的广告信息，返回 JSON
	 */
	@RequestMapping(path = "/query/all/adpublic", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES) // NOT_API
	@ResponseBody
	public String doQueryAllAdpublic(Integer userId) {
		// 查询
		List<AdPublic> list = adPublicService.queryAdpublic(userId);
		return new JSONResult().setValue(RetEnum.SUCCESS, list);
	}

	/** 查询全部个人发布广告信息 用于首页更多 */
	@RequestMapping(path = "/moreAdpublic") // NOT_API
	public String queryAllAdpublicMore(AdPublic adpublic, Model model, HttpSession session) {
		List<AdPublic> userInfoAdpublicList = adPublicService.queryUserInfoAdpublicMore();
		model.addAttribute("userInfoAdpublicList", userInfoAdpublicList);
		return "adPublic/adpublic-info";
	}

	/** 查询全部个人发布广告信息 用于首页更多 ---手机端 */
	@RequestMapping(path = "/check/adpublicinfo", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES) // NOT_API
	@ResponseBody
	public String queryAllAdpublicInfoMore(AdPublic adpublic, Model model, HttpSession session) {
		List<AdPublic> list = adPublicService.queryUserInfoAdpublicMore();
		return new JSONResult().setValue(RetEnum.SUCCESS, list);
	}

	/** 查询全部个人发布广告信息 --手机端 */
	@RequestMapping(path = "/sel/adpublicinfo", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES) // NOT_API
	@ResponseBody
	public String queryAdpublicInfo(Filters filter) {
		List<AdpublicInfoModel> list = adPublicService.selAdpublicAll(filter);
		return new JSONResult().setValue(RetEnum.SUCCESS, list);
	}

	/** 查询全部企业发布广告信息 用于首页更多 */
	@RequestMapping(path = "/moreAdpublic—enterprise") // NOT_API
	public String queryenterpriseAdpublicMore(AdPublic adpublic, Model model, HttpSession session) {
		List<AdPublic> enterpriseAdpublicList = adPublicService.queryEnterpriseAdpublicMore();
		model.addAttribute("enterpriseAdpublicList", enterpriseAdpublicList);
		return "adPublic/adpublic-info";
	}

	/** 跳转页面 */
	@RequestMapping(path = "/jumpAdpublic", method = RequestMethod.GET) // NOT
																		// API
	public String queryParent(Model model, HttpSession session) {
		Users currentUser = (Users) session.getAttribute("currentUser");
		if (currentUser == null) {
			return "redirect:/login";
		} else {
			if (currentUser.getTypeId() == 1) {
				CertificationInfo ci = service.queryOneByUserId(currentUser.getId());
				if (ci != null) {
					if (ci.getState().getId() == 4 || ci.getState().getId() == 6) {
						model.addAttribute("title", "未实名认证");
						model.addAttribute("content", "您尚未通过未实名认证，暂时不能发布！");
						model.addAttribute("iconClass", "icon-error");
						return "function/common";
					} else {
						return "adPublic/free-advertising";
					}

				} else {
					model.addAttribute("title", "未实名认证");
					model.addAttribute("content", "您尚未进行未实名认证，暂时不能发布！");
					model.addAttribute("iconClass", "icon-error");
					return "function/common";
				}

			} else if (currentUser.getTypeId() == 2) {
				CertificationEnterprise certificationEnterprise = certificationEnterpriseService
						.queryOneByUserId(currentUser.getId());
				if (certificationEnterprise != null) {
					if (certificationEnterprise.getState().getId() == 4
							|| certificationEnterprise.getState().getId() == 6) {
						model.addAttribute("title", "未实名认证");
						model.addAttribute("content", "您尚未通过未实名认证，暂时不能发布！");
						model.addAttribute("iconClass", "icon-error");
						return "function/common";
					} else {
						return "adPublic/free-advertising";
					}
				} else {
					model.addAttribute("title", "未实名认证");
					model.addAttribute("content", "您尚未进行未实名认证，暂时不能发布！");
					model.addAttribute("iconClass", "icon-error");
					return "function/common";
				}

			} else {
				return "index";
			}

		}

	}

	/** 广告的新增或者修改，aorc = add or change */
	@RequestMapping(path = "/ad/aorc", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES) // NOT_API
	@ResponseBody
	public String doAddOrChange(MultipartFile abPublicFile, AdPublic adPublic, HttpSession session) throws IOException {
		// 判断是否登录
		Users user = (Users) session.getAttribute("currentUser");
		if (user == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登陆！");
		}
		adPublic.setUser(user);
		return adPublicService.addOrChange(adPublic, abPublicFile).toJSON();
	}

	/** 广告发布或更新 */
	@RequestMapping(path = "/ad/submit-or-update", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSubmitOrUpdate(AdPublic adpublic, HttpSession session, LoginToken token) throws IOException {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN);
		adpublic.setUser(user);
		return adPublicService.submitOrUpdate(adpublic).toJSON();
	}

	/** 更新广告封面图片 */
	@RequestMapping(path = "/ad/upload-picture", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUploadPicture(Integer id, MultipartFile pictureFile, String pictureName, HttpSession session,
			LoginToken token) throws IOException {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN);
		return adPublicService.uploadPicture(id, user.getId(), pictureFile, pictureName).toJSON();
	}

	/** 更多广告页面，more ad */
	@RequestMapping(path = "/more/ad") // NOT_API
	public String doMoreAd(Model model) {
		List<AdPublic> list = adPublicService.queryAdPublic();
		if (list == null) {
			return "common/four-zero-four";
		} else {
			model.addAttribute("list", list);
			return "adPublic/adpublic-info";
		}
	}

	/** 删除广告 */
	@RequestMapping(path = "/ad/delete", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String delAdpublic(int id) {

		JSONResult jr = new JSONResult();

		if (id < 0) {
			return jr.setValue(RetEnum.PARAM_LACK, "id必须大于零");
		}
		if (adPublicService.delAdpublic(id)) {
			return jr.setValue(RetEnum.SUCCESS, "删除成功");
		} else {
			return jr.setValue(RetEnum.EXCEPTION, "删除失败");
		}
	}

	/** 发布广告信息 */
	@RequestMapping(path = "/insertAdPublic", method = RequestMethod.POST) // NOT_API
	public String perfectAdpublic(MultipartFile abPublicFile, AdPublic adPublic, HttpSession session, Model model)
			throws IOException {
		Users user = (Users) session.getAttribute("currentUser");
		adPublic.setUser(user);

		if (isEmpty(adPublic.getContent(), adPublic.getPhone(), adPublic.getAddress()) > 0 || abPublicFile.isEmpty()) {
			model.addAttribute("title", "发布失败");
			model.addAttribute("content", "请您将信息填写完整再发布，谢谢。");
			return "function/common";
		}

		if (adPublicService.addOrChange(adPublic, abPublicFile).getRet() == RetEnum.SUCCESS.getValue()) {
			return "function/publishSuccessfully";
		} else {
			model.addAttribute("title", "发布失败");
			model.addAttribute("content", "服务器匆忙，请稍后再试。");
			return "function/common";
		}
	}

	/** 查询一个小广告，并推荐可能喜欢的，返回页面 */
	@RequestMapping(path = "/query/one/adpublic", method = RequestMethod.GET) // NOT_API
	public String doQueryOnePublic(int adPublicId, Model model) {
		model = this.queryOneAndLike(adPublicId, model);
		return "adPublic/advertising-for-details";
	}

	/** 查询当前登录者发布的广告信息 */
	@RequestMapping(path = "/ad/detail/self", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doAdDetailSelf(HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		}
		AdPublic ad = adPublicService.queryOneByuserId(user.getId());
		return new JSONResult().setValue(RetEnum.SUCCESS, ad);

	}

	/** 根据userId查询发布的广告信息 */
	@RequestMapping(path = "/ad/detail/{id}", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doAdDetail(@PathVariable("id") int adId, Model model, HttpSession session, LoginToken token) {
		AdPublic adPublic = adPublicService.queryOneByAdId(adId);
		model.addAttribute("adpublic", adPublic);
		// 查询可能喜欢的
		List<AdPublic> likeList = adPublicService.queryLike(adPublic.getContent(), adId);
		model.addAttribute("likeList", likeList);
		return new JSONResult().setValue(RetEnum.SUCCESS, model);
	}

	/** 查询一个小广告，并推荐可能喜欢的，返回Model */
	public Model queryOneAndLike(int adPublicId, Model model) {
		AdPublic adPublic = adPublicService.queryOneAdpublic(adPublicId);
		model.addAttribute("adpublic", adPublic);
		// 查询可能喜欢的
		List<AdPublic> likeList = adPublicService.queryLike(adPublic.getContent(), adPublicId);
		model.addAttribute("likeList", likeList);
		return model;
	}

	/** 广告详情 */
	@RequestMapping(path = "/adpublic/detail/{id}", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES) // NOT_API
	@ResponseBody
	public String queryAdpublicDetails(@PathVariable("id") Integer id) {
		// 查询
		AdPublic ad = adPublicService.queryOneByuserId(id);
		return new JSONResult().setValue(RetEnum.SUCCESS, ad);

	}

	@RequestMapping(path = "/query/one/ad", method = RequestMethod.GET) // NOT_API
	public String queryOne(int adPublicId, Model model) {
		AdPublic adPublic = adPublicService.queryOneAdpublic(adPublicId);
		model.addAttribute("adpublic", adPublic);
		return "adPublic/update-adpublic";
	}

	/** 【后台管理】修改广告的信息 */
	@RequestMapping(path = "/manage/adpublic/edit", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES) // NOT_API
	@ResponseBody
	public String EditAdPublicState(AdPublic adpublic, HttpSession session, LoginToken token) {
		// 判断是否登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		Object adminToken = session.getAttribute("adminToken");
		if (loginUser.getId() == null && adminToken == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		}
		JSONResult jr = adPublicService.updateOne(adpublic);
		return jr.toJSON();
	}

	/**
	 * 广告的新增(admin)
	 */
	@RequestMapping(path = "/admin/ad/aorc", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES) // NOT_API
	@ResponseBody
	public String doAddAdpubic(MultipartFile abPublicFile, AdPublic adPublic, HttpSession session) throws IOException {
		// 判断当前登录的是不是admin用户
		Object token = session.getAttribute("adminToken");
		if (token == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登陆！");
		}
		// adPublic.setUser(user);
		return adPublicService.addOrChange(adPublic, abPublicFile).toJSON();
	}

}
