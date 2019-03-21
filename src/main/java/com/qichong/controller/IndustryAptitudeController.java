package com.qichong.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qichong.entity.IndustryAptitude;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.service.IndustryAptitudeService;
import com.qichong.service.UsersService;
import com.qichong.token.LoginToken;
import com.qichong.util.web.ServletUtil;

/**
 * 资质
 * 
 * @创建人 陈文瑾
 * @修改人 孙建雷
 * @修改时间 2018年4月27日
 */
@Controller
@RequestMapping("/center/aptitude")
public class IndustryAptitudeController {

	@Autowired
	UsersService usersService;

	@Autowired
	IndustryAptitudeService ias;

	/** 查询某个用户的资质 */
	@RequestMapping(path = "/{userId}", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doCenterAptitude(@PathVariable("userId") Integer userId) {
		return new JSONResult().setValue(RetEnum.SUCCESS, ias.queryUserIdAptitude(userId));
	}

	/** 查询我的资质 */
	@RequestMapping(path = "/self", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doCenterAptitudeMe(HttpSession session, LoginToken token) {
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录");
		} else {
			return this.doCenterAptitude(loginUser.getId());
		}
	}

	/** 修改资质 */
	@RequestMapping(path = "/update", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUpdate(MultipartFile aptitudeFile, IndustryAptitude ia, String pictureName, HttpSession session,
			LoginToken token) {
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录");
		ia.setUser(loginUser);
		return ias.updateOne(aptitudeFile, ia, pictureName).toJSON();
	}

	/** 删除资质 */
	@RequestMapping(path = "/delete", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doDelete(int id, String pictureName, HttpSession session, LoginToken token) {
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录");
		return ias.deleteOne(id, loginUser.getId(), pictureName).toJSON();
	}

	/** 新增资质 */
	@RequestMapping(path = "/insert", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String insertAptitude(MultipartFile aptitudeFile, IndustryAptitude ia, HttpSession session,
			LoginToken token) {
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录");
		ia.setUser(loginUser);
		return ias.insertOne(aptitudeFile, ia).toJSON();
	}

}
