package com.qichong.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.qichong.entity.CertificationEnterprise;
import com.qichong.entity.EnterpriseInfo;
import com.qichong.entity.IndustryType;
import com.qichong.entity.Regions;
import com.qichong.entity.State;
import com.qichong.entity.Users;
import com.qichong.enums.PathEnum;
import com.qichong.enums.RetEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.service.AdPublicService;
import com.qichong.service.AppintmentTimeService;
import com.qichong.service.CertificationEnterpriseService;
import com.qichong.service.EnterpriseInfoService;
import com.qichong.service.GroupInfoServcie;
import com.qichong.service.IndustryAptitudeService;
import com.qichong.service.IndustryTypeService;
import com.qichong.service.RegionsService;
import com.qichong.service.UserApintmentUserService;
import com.qichong.service.UserInfoService;
import com.qichong.service.UsersService;
import com.qichong.token.LoginToken;
import com.qichong.util.OSSUtil;
import com.qichong.util.web.ServletUtil;

/**
 * 企业信息controller
 * 
 * @author 吴志伟
 *
 */
@Controller
public class EnterpriseController {

	@Autowired
	EnterpriseInfoService enterpriseService;
	@Autowired
	RegionsService regionsService;
	@Autowired
	UserInfoService userInfoService;

	@Autowired
	UsersService usersService;

	@Autowired
	GroupInfoServcie groupService;
	@Autowired
	AdPublicService adPublicService;
	@Autowired
	AppintmentTimeService appintmentService;
	@Autowired
	UserApintmentUserService userApintmentUserService;
	@Autowired
	IndustryAptitudeService AptitudeService;
	@Autowired
	IndustryTypeService industryTypeService;
	@Autowired
	CertificationEnterpriseService certifictionEnterpriseService;

	/** 根据行业查询企业信息 */
	@RequestMapping(path = "/industryByEnterprise", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String doIndustryByEnterprise(String industryDetails) {
		return new JSONResult().setValue(RetEnum.SUCCESS, "", enterpriseService.industryByEnterprise(industryDetails));
	}

	/** 分页查询所有的企业信息，返回 JSON */
	@RequestMapping(path = "/queryEnterpriseInfo", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String doQueryEnterpriseInfo(String searchKeyword, Filters filters) {
		return this.doQueryAllEnterprise(searchKeyword, filters);
	}

	/** 分页查询所有的企业用户信息，返回 JSON */
	@RequestMapping(path = "/query/all/enterprise", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String doQueryAllEnterprise(String searchKeyword, Filters filters) {
		// 查询
		List<EnterpriseInfo> result = enterpriseService.queryAll(filters);
		return new JSONResult().setValue(RetEnum.SUCCESS, "", result, enterpriseService.queryAll_total(filters));
	}

	/** 完善企业信息 */
	@RequestMapping(path = "/insertEnterprise", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String perfectEnterpriseInfo(@Param("joiningCity") String joiningCity,
			@Param("joiningProvince") String joiningProvince, EnterpriseInfo enterprise) {

		// 创建 JSONResult对象，用于返回JSON字符串
		JSONResult jr = new JSONResult();
		if (enterprise == null) {
			return jr.setValue(RetEnum.PARAM_ERROR, "参数为空");
		}
		if (joiningCity != null && joiningProvince != null) {
			StringBuilder sb = new StringBuilder();
			Regions region = regionsService.selectOne(joiningCity);
			Regions region1 = regionsService.selectOne(joiningProvince);
			if (region != null && region1 != null) {
				sb.append(region1.getRegionName() + region.getRegionName() + enterprise.getAddress());
				enterprise.setAddress(sb.toString());
			}
		}
		if (enterprise.getIndustry() != null) {
			IndustryType industryType = industryTypeService.selectOne(enterprise.getIndustry().getIndustryCode());
			if (industryType != null) {
				IndustryType industryTypeOne = industryTypeService.selectOne(industryType.getParentCode());
				if (industryTypeOne != null) {
					IndustryType industryTypeTwo = industryTypeService.selectOne(industryTypeOne.getParentCode());
					if (industryTypeTwo != null) {
						List<String> list = new ArrayList<String>();
						list.add(industryTypeTwo.getIndustryName());
						list.add(industryTypeOne.getIndustryName());
						list.add(industryType.getIndustryName());
						enterprise.setIndustryDetails(new Gson().toJson(list));
					}
				}
			}
		}
		if (enterprise.getRegion() == null) {
			enterprise.setRegion(new Regions("0"));
		} else if (enterprise.getRegion().getRegionCode().equals("-1")) {
			enterprise.getRegion().setRegionCode("0");
		}
		if (enterprise.getRegion().getRegionCode().equals("-1")) {
			enterprise.getRegion().setRegionCode("0");
		}

		if (enterprise.getIndustry() == null) {
			enterprise.setIndustry(new IndustryType("0"));
		} else if (enterprise.getIndustry().getIndustryCode().equals("-1")) {
			enterprise.getIndustry().setIndustryCode("0");
		}

		if (enterprise.getIndustry().getIndustryCode().equals("-1")) {
			enterprise.getIndustry().setIndustryCode("0");
		}
		boolean flag = enterpriseService.add(enterprise);
		if (flag) {
			return jr.setValue(RetEnum.SUCCESS, "信息保存成功");
		} else {
			return jr.setValue(RetEnum.PARAM_LACK, "信息保存失败");
		}
	}

	/** 更新企业logo */
	@RequestMapping(path = "/update_logo", method = RequestMethod.POST)// NOT_API
	public String addEnterprise(MultipartFile avatarFile, String put, HttpSession session, Model model)
			throws Exception {
		Users currentUser = (Users) session.getAttribute("currentUser");
		if (currentUser == null) {
			model.addAttribute("errorTitle", "尚未登陆");
			model.addAttribute("errorBody", "你还没有登陆，无法上传头像！");
			return "error";
		}

		// 发布到阿里云OSS
		String name = currentUser.getId() + "_logo.jpg";
		new OSSUtil(PathEnum.ENT_LOGO).uploadFile2OSS(avatarFile.getInputStream(), name);

		// 存入数据库
		EnterpriseInfo enInfo = new EnterpriseInfo(currentUser);
		enInfo.setLogo(name);
		enterpriseService.updateOne(enInfo);
		return "redirect:/update-avatar/reload.html";
	}

	/** 更新企业logo */
	@RequestMapping(path = "/ent/update-logo", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	public String doUpdateLogo(MultipartFile logoFile, HttpSession session, LoginToken token) throws Exception {
		Users currentUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (currentUser == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN);
		}

		// 发布到阿里云OSS
		String name = currentUser.getId() + "_logo.jpg";
		new OSSUtil(PathEnum.ENT_LOGO).uploadFile2OSS(logoFile.getInputStream(), name);

		// 存入数据库
		EnterpriseInfo enInfo = new EnterpriseInfo(currentUser);
		enInfo.setLogo(name);
		enterpriseService.updateOne(enInfo);
		return new JSONResult().setValue(RetEnum.SUCCESS);
	}

	@RequestMapping(path = "/modify-logo", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String addEnterpriseLogo(MultipartFile avatarFile, EnterpriseInfo enInfo, HttpSession session,
			LoginToken token) throws Exception {
		// 获取当前登录的用户
		Users currentUser = (Users) session.getAttribute("currentUser");
		// userId若为空则代表没有登录
		if (currentUser.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN);
		}
		// 发布到阿里云OSS
		String name = currentUser.getId() + "_logo.jpg";
		new OSSUtil(PathEnum.ENT_LOGO).uploadFile2OSS(avatarFile.getInputStream(), name);
		// 存入数据库
		enInfo.setLogo(name);
		enterpriseService.updateOne(enInfo);
		return new JSONResult().setValue(RetEnum.SUCCESS);
	}

	// /** 设置 用户信息的方法，请求类型为POST */
	// @RequestMapping(path = "/enterprise/info", method = RequestMethod.POST,
	// produces = ServletUtil.JSON_PRODUCES)
	// @ResponseBody
	// public String doUserInfo(EnterpriseInfo info, HttpSession session,
	// LoginToken token) {
	// // 获取当前登录的用户
	// Users loginUser = ServletUtil.getThisLoginUser(session, token,
	// usersService);
	// // userId若为空则代表 没有登录
	// if (loginUser.getId() == null)
	// return new JSONResult().setValue(RetEnum.NO_LOGIN);
	// // 将当前登录的用户id加入要修改的user中
	// Users user = info.getUser() == null ? new Users() : info.getUser();
	// user.setId(loginUser.getId());
	// info.setUser(user);
	// // 调用service层的方法进行修改用户信息操作
	// return enterpriseService.updateOne(info).toJSON();
	// }

	/** 修改用户信息&&上传logo&&上传营业执照 */
	@RequestMapping(path = "/enterprise", method = RequestMethod.POST)// NOT_API
	public String perfectEnInfo(String joiningCity, String joiningProvince, MultipartFile logoFile,
			MultipartFile licenseFile, EnterpriseInfo enInfo, MultipartFile banner1File, MultipartFile banner2File,
			MultipartFile banner3File, MultipartFile picture0File, HttpSession session) throws IOException {
		Users user = (Users) session.getAttribute("currentUser");
		if (user == null) {
			return "redirect:/updateError";
		}

		boolean flag = enInfo.getEnterpriseName() != null || enInfo.getPersonName() != null
				|| enInfo.getIntroduce() != null || enInfo.getTelephone() != null || enInfo.getEmail() != null
				|| enInfo.getWebsite() != null || enInfo.getRegion() != null || enInfo.getIndustry() != null
				|| enInfo.getAddress() != null;
		if (!flag) {
			return "redirect:/updateError";
		}

		if (logoFile != null && !logoFile.isEmpty()) {
			// 发布到阿里云OSS
			String name = user.getId() + "_logo.jpg";
			new OSSUtil(PathEnum.ENT_LOGO).uploadFile2OSS(logoFile.getInputStream(), name);

			// 存入数据库
			enInfo.setLogo(name);
		}
		if (licenseFile != null && !licenseFile.isEmpty()) {
			// 发布到阿里云OSS
			String name = user.getId() + "_license.jpg";
			new OSSUtil(PathEnum.ENT_LICENSE).uploadFile2OSS(licenseFile.getInputStream(), name);
			// 存入数据库
			enInfo.setBusinessLicense(name);
			CertificationEnterprise ci = new CertificationEnterprise();
			ci.setUser(user);
			ci.setState(new State(4));
			certifictionEnterpriseService.byUserIdUpdateOne(ci);
		}

		if (banner1File != null && !banner1File.isEmpty()) {
			// 发布到阿里云OSS
			String name = user.getId() + "_banner1.jpg";
			new OSSUtil(PathEnum.ENT_BANNER).uploadFile2OSS(banner1File.getInputStream(), name);

			// 存入数据库
			enInfo.setBanner1(name);
		}
		if (banner2File != null && !banner2File.isEmpty()) {
			// 发布到阿里云OSS
			String name = user.getId() + "_banner2.jpg";
			new OSSUtil(PathEnum.ENT_BANNER).uploadFile2OSS(banner2File.getInputStream(), name);

			// 存入数据库
			enInfo.setBanner2(name);
		}
		if (banner3File != null && !banner3File.isEmpty()) {
			// 发布到阿里云OSS
			String name = user.getId() + "_banner3.jpg";
			new OSSUtil(PathEnum.ENT_BANNER).uploadFile2OSS(banner3File.getInputStream(), name);

			// 存入数据库
			enInfo.setBanner3(name);
		}
		if (picture0File != null && !picture0File.isEmpty()) {
			// 发布到阿里云OSS
			String name = user.getId() + "_picture0.jpg";
			new OSSUtil(PathEnum.ENT_PICTURE).uploadFile2OSS(picture0File.getInputStream(), name);

			// 存入数据库
			enInfo.setPicture0(name);
		}

		enInfo.setUser(new Users(user.getId()));
		if (joiningCity != null && joiningProvince != null) {
			StringBuilder sb = new StringBuilder();
			Regions region = regionsService.selectOne(joiningCity);
			Regions region1 = regionsService.selectOne(joiningProvince);
			if (region != null && region1 != null) {
				enInfo.setAddressAll(
						sb.append(region1.getRegionName() + region.getRegionName() + enInfo.getAddress()).toString());
			}
		}
		if (enInfo.getIndustry() != null) {
			IndustryType industryType = industryTypeService.selectOne(enInfo.getIndustry().getIndustryCode());
			if (industryType != null) {
				IndustryType industryTypeOne = industryTypeService.selectOne(industryType.getParentCode());
				if (industryTypeOne != null) {
					IndustryType industryTypeTwo = industryTypeService.selectOne(industryTypeOne.getParentCode());
					if (industryTypeTwo != null) {
						List<String> list = new ArrayList<String>();
						list.add(industryTypeTwo.getIndustryName());
						list.add(industryTypeOne.getIndustryName());
						list.add(industryType.getIndustryName());
						enInfo.setIndustryDetails(new Gson().toJson(list));
					}
				}
			}
		}
		if (enInfo.getRegion() == null) {
			enInfo.setRegion(new Regions("0"));
		} else if (enInfo.getRegion().getRegionCode().equals("-1")) {
			enInfo.getRegion().setRegionCode("0");
		}
		if (enInfo.getRegion().getRegionCode().equals("-1")) {
			enInfo.getRegion().setRegionCode("0");
		}

		if (enInfo.getIndustry() == null) {
			enInfo.setIndustry(new IndustryType("0"));
		} else if (enInfo.getIndustry().getIndustryCode().equals("-1")) {
			enInfo.getIndustry().setIndustryCode("0");
		}

		if (enInfo.getIndustry().getIndustryCode().equals("-1")) {
			enInfo.getIndustry().setIndustryCode("0");
		}
		JSONResult jr = enterpriseService.updateOne(enInfo);
		if (jr.getRet() == RetEnum.SUCCESS.getValue()) {
			return "redirect:/updateSuccess";
		} else {
			System.out.println("整错了");
			return "redirect:/updateError";
		}

	}

	@RequestMapping(path = "/by/enterprise", method = RequestMethod.GET)// NOT_API
	public String byIdEnterprise(Integer id, HttpSession session, Model model) {
		Users currentUser = (Users) session.getAttribute("currentUser");
		if (currentUser == null) {
			model.addAttribute("errorTitle", "尚未登陆");
			model.addAttribute("errorBody", "你还没有登陆，无法上传头像！");
			return "error";
		}
		model.addAttribute("enterprise", enterpriseService.byIdEnterprise(id));
		return "enterprise/enterprise-show";
	}

	/** 修改企业信息 */
	@RequestMapping(path = "/enterprise/update", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUpdate(EnterpriseInfo ei, HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录...");
		ei.setUser(user);
		return enterpriseService.updateOne(ei).toJSON();
	}

	/** 企业上传二维码图片 */
	@RequestMapping(path = "/enterprise/upload-qrcode", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUploadBanner(MultipartFile file, String qrCodeName, String picName, HttpSession session,
			LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录...");
		return enterpriseService.updateQrCode(user.getId(), file, qrCodeName, picName).toJSON();
	}

	/**
	 *
	 * @param file 企业理念图片
	 * @param picName 图片的名字，主要用来存储
	 * @param session
	 * @param token
	 * @return
	 */
	@RequestMapping(path = "/enterprise/upload-philosophy", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUploadPhilosophy(MultipartFile file, String idea, String picName, HttpSession session,
								 LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null){

			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录...");
		}
		return enterpriseService.updatePhilosophy(user.getId(), file,  picName,idea).toJSON();
	}

	/**
	 *
	 * @param file
	 * @param picName
	 * @param session
	 * @param token
	 * @return
	 */
	@RequestMapping(path = "/enterprise/upload-profile", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUploadProfile(MultipartFile file, String introduce, String picName, HttpSession session,
									 LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null){

			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录...");
		}
		return enterpriseService.updateProfile(user.getId(), file,  picName,introduce).toJSON();
	}

	/**
	 *
	 * @param file
	 * @param picName
	 * @param session
	 * @param token
	 * @return
	 */
	@RequestMapping(path = "/enterprise/upload-advantage", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUploadAdvantage(MultipartFile file, String advantageContent ,String picName, HttpSession session,
								  LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null){

			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录...");
		}
		return enterpriseService.updateAdvantage(user.getId(), file,  picName,advantageContent).toJSON();
	}

	/**
	 *
	 * @param file
	 * @param picName
	 * @param session
	 * @param token
	 * @return
	 */
	@RequestMapping(path = "/enterprise/upload-honor", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUploadHonor(MultipartFile file, String honorContent, String picName, HttpSession session,
									LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null){

			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录...");
		}
		return enterpriseService.updateHonor(user.getId(), file,  picName,honorContent).toJSON();
	}


	/** 企业上传Banner图片 */
	@RequestMapping(path = "/enterprise/upload-banner-{num}", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUploadBanner(@PathVariable("num") String num, String picName, MultipartFile file,
			HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录...");
		if (file == null || ServletUtil.isEmpty(num))
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "缺少参数");
		if (num.equals("1") || num.equals("2") || num.equals("3")) {
			return enterpriseService.updateBanner(user.getId(), num, picName, file).toJSON();
		} else {
			return new JSONResult().setValue(RetEnum.PARAM_ERROR, "参数错误");
		}
	}

	/** 企业上传logo或营业执照 */
	@RequestMapping(path = "/enterprise/upload-logo-or-license", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUploadLogoOrLicense(Integer userId, String pictureName, MultipartFile logoFile,
			MultipartFile licenseFile, HttpSession session, LoginToken token) {
		// Users user = ServletUtil.getThisLoginUser(session, token,
		// usersService);
		// if (user.getId() == null)
		// return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录...");
		return enterpriseService.uploadPicture(userId, pictureName, logoFile, licenseFile).toJSON();
	}

	/** 当前登录企业上传logo或营业执照 */
	@RequestMapping(path = "/enterprise/upload-logo-or-license-self", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUploadLogoOrLicenseSelf(String pictureName, MultipartFile logoFile, MultipartFile licenseFile,
			HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录...");
		return this.doUploadLogoOrLicense(user.getId(), pictureName, logoFile, licenseFile, session, token);
	}

	/** 删除企业自上传图片 */
	@RequestMapping(path = "/enterprise/delete/picture/self", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doDeletePicture(String num, String name, LoginToken token, HttpSession session) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录...");
		if (ServletUtil.isEmpty(num, name) > 0)
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "缺少参数");
		return enterpriseService.deletePicture(user.getId(), num, name).toJSON();
	}

	/** 企业自上传图片 */
	@RequestMapping(path = "/enterprise/upload/picture/{userId}", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUploadPicture(@PathVariable("userId") Integer userId, String num, String name,
			MultipartFile pictureFile) {
		if (pictureFile == null || ServletUtil.isEmpty(num))
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "缺少参数");
		return enterpriseService.updatePicture(userId, num, name, pictureFile).toJSON();
	}

	/**
	 * 企业自上传图片（当前登录企业）
	 * 
	 * @param num
	 *            当前上传的第几张图片（0-9）
	 * @param name
	 *            图片的名字，第一次上传无需传值，修改图片的时候需要传值，传从数据库中获取到的图片名称
	 * @param pictureFile
	 *            图片文件
	 */
	@RequestMapping(path = "/enterprise/upload/picture/self", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUploadPictureSelf(String num, String name, MultipartFile pictureFile, LoginToken token,
			HttpSession session) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录...");
		return this.doUploadPicture(user.getId(), num, name, pictureFile);
	}

	/** 查询企业用户自上传图片 */
	@RequestMapping(path = "/enterprise/query/picture/{userId}", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doQueryPicture(@PathVariable("userId") Integer userId) {
		EnterpriseInfo temp = enterpriseService.queryOneByUserId(userId);
		if (temp != null && temp.getUser().getTypeId() == 2)
			return new JSONResult().setValue(RetEnum.SUCCESS, temp);
		return new JSONResult().setValue(RetEnum.PARAM_ERROR, "不是企业用户...");
	}

	/** 查询当前登录企业用户自上传图片 */
	@RequestMapping(path = "/enterprise/query/picture/self", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doQueryPictureSelf(LoginToken token, HttpSession session) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录...");
		return this.doQueryPicture(user.getId());
	}

}
