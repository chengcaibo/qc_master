package com.qichong.service;

import static com.qichong.util.web.ServletUtil.isEmpty;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.token.LoginToken;
import com.qichong.util.sms.SMSUtil;
import com.qichong.util.web.ServletUtil;
import com.qichong.util.wechat.app.WeChatAppUtil;
import com.qichong.util.wechat.mini.WeChatMiniUtil;

@Service
public class SignService {

	@Autowired
	UsersService usersService;

	/**
	 * 绑定微信账号
	 * 
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult bindToWechat(String username, String password, String code, String type) throws Exception {
		if (isEmpty(username, password) > 0)
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数");
		if (type.equals("app") && isEmpty(code))
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数，code不能为空");
		// 先判断用户名和密码是否正确
		Map<String, String> map = this.loginWithNormalReturnMap(username, password, true);

		if (map != null) {
			if (!isEmpty(code)) {
				// 获取到的openId
				String wxOpenId = map.get("openId");
				String wxUnionId = map.get("unionId");
				if (isEmpty(wxOpenId, wxUnionId) != 2) {
					return JSONResult.builder(RetEnum.VALUE_EXIST, "该账号已经绑定了一个微信账号了，不能绑定多个，换一个账号再试试吧！");
				}
				// 将code换成openId
				Map<String, String> resultMap = null;
				if (type.equals("mini")) {
					resultMap = WeChatMiniUtil.codeToOpenId(code);
				} else if (type.equals("app")) {
					resultMap = WeChatAppUtil.codeToOpenId(code);
				}
				if (resultMap == null) {
					return JSONResult.builder(RetEnum.ERROR, "传入的code无效或已过期");
				}
				String openId = resultMap.get("openId");
				String unionId = resultMap.get("unionId");
				// sessionKey = map.get("sessionKey");
				// 存入数据库
				Users updateUser = new Users(Integer.parseInt(map.get("userId")));
				if (type.equals("mini"))
					updateUser.setWxOpenId(openId);
				updateUser.setWxUnionId(unionId);
				usersService.changeUser(updateUser);
			}
			map.remove("openId");
			map.remove("unionId");
			map.remove("userId");
			return JSONResult.builder(RetEnum.SUCCESS).setResult(map);
		}
		return JSONResult.builder(RetEnum.ERROR, "用户名或密码错误");
	}

	/** 微信登录 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult loginWithWeChat(String code, String type) throws Exception {
		// 将传入的code通过微信后台换成用户的openId
		Map<String, String> map = null;
		if (type.equals("mini")) {
			map = WeChatMiniUtil.codeToOpenId(code);
		} else if (type.equals("app")) {
			map = WeChatAppUtil.codeToOpenId(code);
		}
		// 将获取的openId对比登录
		if (map != null) {
			String openId = map.get("openId");
			String unionId = map.get("unionId");
			String sessionKey = map.get("sessionKey");
			Users user = usersService.loginWeChat(openId, unionId);
			if (user != null) {
				if (isEmpty(user.getWxUnionId())) {
					Users updateUser = new Users(user.getId());
					updateUser.setWxUnionId(unionId);
					usersService.changeUser(updateUser);
				}

				Map<String, Object> maps = this.builderToken(user.getId(), user.getPassword(), openId, sessionKey);

				return JSONResult.builder(RetEnum.SUCCESS).setResult(maps);
			}
			return JSONResult.builder(RetEnum.WX_OPENID_NO_BIND, "该openId尚未绑定奇虫账号");
		}
		return JSONResult.builder(RetEnum.ERROR, "code无效或已过期");
	}

	/** 【登录方式】使用手机验证码登录 */
	public JSONResult loginWithPhoneCode(String phone, String code) {
		RetEnum codeRet = SMSUtil.validationVCode(phone, code);
		if (codeRet == RetEnum.SUCCESS) {
			SMSUtil.cleanVCode(phone);
			Users user = usersService.loginWithPhone(phone);
			if (user == null)
				return JSONResult.builder(RetEnum.ERROR, "手机号码不存在");
			Map<String, Object> maps = builderToken(user.getId(), user.getPassword(), user.getWxOpenId(), null);
			return JSONResult.builder(RetEnum.SUCCESS).setResult(maps);
		} else {
			return JSONResult.builder(codeRet, "提供的验证码不正确或已过期");
		}
	}

	/** 【登录方式】普通登录 */
	public JSONResult loginWithNormal(String username, String password) {
		if (isEmpty(username, password) > 0)
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数");
		else {
			Map<String, String> map = this.loginWithNormalReturnMap(username, password, false);
			if (map != null)
				return JSONResult.builder(RetEnum.SUCCESS).setResult(map);
			else
				return JSONResult.builder(RetEnum.ERROR, "用户名或密码错误");
		}
	}

	/** 普通登录返回map */
	private Map<String, String> loginWithNormalReturnMap(String username, String password, boolean wechat) {
		// 先判断用户名和密码是否正确
		Users result = usersService.login(username, password);
		if (result != null) {
			// 生成一个加密字符串，返回给前台存入缓存
			String tokenKey = new LoginToken().newToken(null, false, result.getId(), null, null);
			long timestamp = ServletUtil.getTimeStampDay(7);
			String keepCode = ServletUtil.keepCodeEncode(timestamp, result.getId(), result.getPassword());
			Map<String, String> maps = new HashMap<String, String>();
			maps.put("tokenKey", tokenKey);
			maps.put("keepCode", keepCode);
			if (wechat) {
				maps.put("userId", result.getId().toString());
				maps.put("openId", result.getWxOpenId());
				maps.put("unionId", result.getWxUnionId());
			}
			return maps;
		}
		return null;
	}

	/** 将userId和openId生成一个加密字符串，返回给小程序存入缓存 */
	public Map<String, Object> builderToken(int userId, String password, String openId, String sessionKey) {
		String tokenKey = new LoginToken().newToken(null, false, userId, openId, sessionKey);
		long timestamp = ServletUtil.getTimeStampDay(7);
		String keepCode = ServletUtil.keepCodeEncode(timestamp, userId, password);
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("tokenKey", tokenKey);
		maps.put("keepCode", keepCode);
		return maps;
	}

}
