package com.qichong.admin.controller;

import static com.qichong.util.web.ServletUtil.isEmpty;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.qichong.entity.JobType;
import com.qichong.entity.State;
import com.qichong.entity.UserInfo;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.enums.StateEnum;
import com.qichong.model.JSONResult;
import com.qichong.service.UserInfoService;
import com.qichong.service.UsersService;
import com.qichong.util.web.ServletUtil;

/**
 * 个人信息管理后台
 * 
 * @author 孙建雷
 * @version 1.0.0
 */
@RestController
@RequestMapping(path = "/admin/personal", produces = ServletUtil.JSON_PRODUCES)// NOT_API
public class AdminPersonalController {

	@Autowired
	UserInfoService uinfoServer;
	@Autowired
	UsersService usersService;

	/**
	 * 添加用户信息
	 * 
	 * @param uinfo
	 * @param defaultAvatar
	 * @param avatarFile
	 * @return
	 */
	@RequestMapping(path = "/addto", method = RequestMethod.POST)// NOT_API
	public String doAddTo(UserInfo uinfo, MultipartFile avatarFile, String put, HttpSession session) {
		try {
			// 设置默认值
			if (uinfo.getUser() == null)
			{
				uinfo.setUser(new Users());
			}
			//这里是系统管理员添加的默认密码
			uinfo.getUser().setPassword("9sfd8sdfgui9ewri");
			//由系统管理员添加的用户初始时未激活状态
			uinfo.getUser().setState(new State(StateEnum.ACCOUNT_NOT_ACTIVATION));

			if (uinfo.getJobType() == null){

				uinfo.setJobType(new JobType(2));
			}

			if (isEmpty(uinfo.getEmail())) {
				uinfo.setEmail(null);
			}
			// System.out.println(uinfo);

			// 注册
			int uid = usersService.signupPersonal(uinfo);
			// 判断需不需要上传头像
			if (avatarFile != null && avatarFile.getSize() > 0) {
				// 更改头像
				JSONResult jr = uinfoServer.changeAvatar(uid, avatarFile, put, session);
				if (jr.getRet() != RetEnum.SUCCESS.getValue()) {
					return JSONResult.builder(RetEnum.SUCCESS, "添加成功，但是头像设置失败！").setResult(uid).toJSON();
				}
			}
			return JSONResult.builder(RetEnum.SUCCESS, "添加成功！").setResult(uid).toJSON();
		} catch (org.springframework.dao.DuplicateKeyException e) {
			System.out.println("捕获完整性约束异常：");
			return JSONResult.builder(RetEnum.ERROR, "哎呀，手机号 \"" + uinfo.getTelephone() + "\" 已经添加过了，换一个再试试吧！").toJSON();
		} catch (Exception e) {
			System.out.println("捕获异常：");
			e.printStackTrace();
			return JSONResult.builder(RetEnum.ERROR, e.getMessage()).toJSON();
		}

	}

}
