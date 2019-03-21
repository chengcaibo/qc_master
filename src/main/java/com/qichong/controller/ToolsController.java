package com.qichong.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qichong.entity.ToolsInfo;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.service.ToolsInfoService;
import com.qichong.service.UsersService;
import com.qichong.token.LoginToken;
import com.qichong.util.web.ServletUtil;

/**
 * 工具Controller
 */
@Controller
public class ToolsController {

	@Autowired
	private ToolsInfoService service;
	@Autowired
	private UsersService usersService;

	/** 返回单个查询结果 */
	@RequestMapping(path = "/tools/detail/{id}", produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doDetailId(@PathVariable("id") Integer id) {
		return new JSONResult().setValue(RetEnum.SUCCESS, service.queryOneById(id));
	}

	/** 查询当前登录用户发布的工具 */
	@RequestMapping(path = "/tools/detail/self", produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doDetailSelf(Filters filters, HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		List<ToolsInfo> list = service.queryListByUserId(user.getId(), filters);
		return new JSONResult().setValue(RetEnum.SUCCESS, list);
	}

	/** 发布或修改工具，传入id即修改，否则则发布 */
	@RequestMapping(path = "/tools/submit-or-update", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSubmitORUpdate(ToolsInfo toolsInfo, HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		toolsInfo.setUser(user);
		return service.submitORUpdate(toolsInfo).toJSON();
	}

	/** 上传工具图片 */
	@RequestMapping(path = "/tools/upload-picture", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUploadPicture(Integer id, MultipartFile picture1File, MultipartFile picture2File,
			MultipartFile picture3File, String pictureName, HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		if (picture1File != null)
			return service.uploadPicture(id, user.getId(), picture1File, "1", pictureName).toJSON();
		else if (picture2File != null)
			return service.uploadPicture(id, user.getId(), picture2File, "2", pictureName).toJSON();
		else if (picture3File != null)
			return service.uploadPicture(id, user.getId(), picture3File, "3", pictureName).toJSON();
		return new JSONResult().setValue(RetEnum.PARAM_LACK);
	}

	/** 上传所有三张工具图片 */
	@RequestMapping(path = "/tools/upload-picture-all", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUploadPictureAll(Integer id, MultipartFile picture1File, MultipartFile picture2File,
			MultipartFile picture3File, String pictureName, HttpSession session, LoginToken token) {
		if (picture1File == null || picture2File == null || picture3File == null) 
			return new JSONResult().setValue(RetEnum.PARAM_LACK);
		Map<String, String> map = new HashMap<String, String>();
		map.put("picture1", this.doUploadPicture(id, picture1File, null, null, pictureName, session, token));
		map.put("picture2", this.doUploadPicture(id, null, picture2File, null, pictureName, session, token));
		map.put("picture3", this.doUploadPicture(id, null, null, picture3File, pictureName, session, token));
		return new JSONResult().setValue(RetEnum.SUCCESS, map);
	}

	@RequestMapping(path = "/tools/update", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doUpdate(ToolsInfo toolsInfo) {
		return service.updateOne(toolsInfo).toJSON();
	}

	/** 删除一个工具，picture1Name 是必填参数 */
	@RequestMapping(path = "/tools/delete", method = RequestMethod.POST)
	@ResponseBody
	public String doDelete(Integer id, String picture1Name, String picture2Name, String picture3Name,
			HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		return service.deleteOne(id, user.getId(), picture1Name, picture2Name, picture3Name).toJSON();
	}
	
	/** 发布工具 （admin）*/
	@RequestMapping(path = "/admin/tools/submit", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String doSubmitTools(ToolsInfo toolsInfo, HttpSession session) {
//		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		Object token = session.getAttribute("adminToken");
		if (token == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
//		toolsInfo.setUser(user);
//		toolsInfo.setAddress("北京");
		return service.submitORUpdate(toolsInfo).toJSON();
	}
	/** 上传工具图片（admin） */
	@RequestMapping(path = "/admin/tools/picture", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String adminUploadPicture(Integer id, MultipartFile picture1File, MultipartFile picture2File,
			MultipartFile picture3File, String pictureName, HttpSession session,Integer userId) {
		Object token = session.getAttribute("adminToken");
		if (token == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		if (picture1File != null)
			return service.uploadPicture(id, userId, picture1File, "1", pictureName).toJSON();
		else if (picture2File != null)
			return service.uploadPicture(id, userId, picture2File, "2", pictureName).toJSON();
		else if (picture3File != null)
			return service.uploadPicture(id, userId, picture3File, "3", pictureName).toJSON();
		return new JSONResult().setValue(RetEnum.PARAM_LACK);
	}

}
