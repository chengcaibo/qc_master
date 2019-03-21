package com.qichong.service;

import static com.qichong.util.web.ServletUtil.isEmpty;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.qichong.dao.EnterpriseInfoDao;
import com.qichong.dao.UserInfoDao;
import com.qichong.dao.UsersDao;
import com.qichong.entity.CertificationEnterprise;
import com.qichong.entity.EnterpriseInfo;
import com.qichong.entity.Gender;
import com.qichong.entity.State;
import com.qichong.entity.UserInfo;
import com.qichong.entity.Users;
import com.qichong.enums.PathEnum;
import com.qichong.enums.RetEnum;
import com.qichong.enums.StateEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.util.OSSUtil;
import com.qichong.util.Utils;
import com.qichong.util.sms.SMSUtil;

@Service
public class UsersService {

	@Autowired
	UsersDao usersDao;

	@Autowired
	UserInfoDao userInfoDao;

	@Autowired
	UserInfoService userInfoService;

	@Autowired
	EnterpriseInfoDao enterInfoDao;
	@Autowired
	CertificationEnterpriseService certificationEnterpriseService;

	/** 根据手机号码查询出用户的userId */
	public Integer queryUserIdByPhoneNumber(String phoneNumber) {
		UserInfo uinfo = userInfoDao.selectUserIdByTelephone(phoneNumber);
		if (uinfo == null)
			return null;
		return uinfo.getUser().getId();
	}

	/**
	 * 使用手机验证码更改密码
	 * 
	 * @param phone
	 *            要被更改密码的手机号
	 * @param code
	 *            手机号的验证码
	 * @param password
	 *            新密码
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult changePasswordWhitPhoneCode(String phone, String code, String password) {
		if (isEmpty(phone, code) > 0) {
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数");
		}
		RetEnum codeRet = SMSUtil.validationVCode(phone, code);
		if (codeRet == RetEnum.SUCCESS) {
			SMSUtil.cleanVCode(phone);
			Integer uid = userInfoService.queryUserIdByTelephone(phone);
			if (uid == null) {
				return JSONResult.builder(RetEnum.ERROR, "手机号码不存在");
			}
			Users user = new Users(uid);
			user.setPassword(password);
			return this.changeUser(user);
		} else {
			return JSONResult.builder(codeRet, "提供的验证码不正确或已过期");
		}
	}

	/**
	 * 查询Uesr根据UserId
	 */
	// public Users queryByWxOpenId(String openId, String unionId) {
	// return usersDao.loginWeChat(openId, unionId);
	// }

	/**
	 * 查询Uesr根据UserId
	 */
	public List<Users> queryByUserIds(int... userIds) {
		if (userIds.length == 0)
			return null;
		return usersDao.selectUsersById(userIds);
	}

	/**
	 * 查询Uesr根据UserId
	 */
	public Users queryByUserId(int userId) {
		return usersDao.selectUserById(userId);
	}

	/** 更改用户信息 */
	public JSONResult changeUser(Users user) {
		JSONResult jr = new JSONResult(RetEnum.SUCCESS);
		try {
			// username 关键字预留
			if (user.getUsername() != null) {
				String username = user.getUsername();
				if ((username != null) && (username.indexOf("default") != -1 || username.indexOf("admin") != -1
						|| username.indexOf("admini") != -1 || username.indexOf("administrator") != -1
						|| username.indexOf("root") != -1)) {
					jr.setValue(RetEnum.PARAM_ERROR, "不能使用预留关键字作为账号");
					return jr;
				}
			}
			// 密码进行md5加密
			if (user.getPassword() != null)
				user.setPassword(DigestUtils.md5Hex(user.getPassword()));

			if (usersDao.updateUser(user) > 0) { // 更新users表
				jr.setValue(RetEnum.SUCCESS);
			} else {
				jr.setValue(RetEnum.ERROR, "未知错误");
			}
		} catch (DuplicateKeyException dke) {
			jr.setValue(RetEnum.VALUE_EXIST, "触发唯一键约束");
		} catch (Exception e) {
			e.printStackTrace();
			jr.setValue(RetEnum.EXCEPTION, e.getMessage());
		}
		return jr;
	}

	/**
	 * 更改用户的经纬度信息
	 */
	public JSONResult updateLocation(Users user) {
		// 纬度在前（范围-90~90），经度在后（范围-180~180）
		JSONResult jr = new JSONResult();
		if (user.getLongitude() == null || user.getLatitude() == null) {
			jr.setValue(RetEnum.PARAM_LACK, "缺少参数");
		} else if ((user.getLatitude() > 90 || user.getLatitude() < -90)
				|| (user.getLongitude() > 180 || user.getLongitude() < -180)) {
			jr.setValue(RetEnum.PARAM_ERROR, "意外的经纬度");
		} else {

			if (this.changeUser(user).getRet() == RetEnum.SUCCESS.getValue()) {
				jr.setValue(RetEnum.SUCCESS, "成功更新了用户的位置");
			} else {
				jr.setValue(RetEnum.ERROR, "未知的错误");
			}
		}
		return jr;
	}

	/**
	 * 查询Total，根据表名
	 * 
	 * @param tableName
	 *            表名
	 * @return 返回该表的总数
	 */
	public int queryTotal(String tableName) {
		return usersDao.selectCountByTableName(tableName);
	}

	public int queryTotal(String tableName, int userIdA) {
		return usersDao.selectCountByTableName(tableName);
	}

	/**
	 * 根据用户id查询详细信息
	 * 
	 * @param uid
	 *            用户ID
	 */
	public UserInfo queryUserInfo(int uid) {
		return userInfoDao.queryUserInfo(uid);
	}

	public boolean modifyPassword(int id, String password) {
		return usersDao.modifyPassword(id, DigestUtils.md5Hex(password)) > 0;
	}

	/** 使用微信登录 */
	public Users loginWeChat(String openId, String unionId) {
		return usersDao.loginWeChat(openId, unionId);
	}

	/** 使用微信登录 */
	public Users loginWithPhone(String telephone) {
		return usersDao.loginWithTelephone(telephone);
	}

	/** 用户等陆 返回对象保存session */
	public Users login(String username, String password) {
		// 密码进行MD5加密
		String newPassword = String.valueOf(DigestUtils.md5Hex(password));
		// 调用dao层方法进行登录
		Users temp = usersDao.loginPersonal(new Users(username, newPassword));
		if (temp == null) {
			temp = usersDao.loginEnterprise(new Users(username, newPassword));
		}
		return temp;
	}

	/** Cookie 登录 */
	public Users cookieLogin(int uid, String password) {
		return usersDao.cookieLogin(new Users(uid, password));
	}

	/** 注册验证手机号码是否可用 */
	public JSONResult personalValidationPhone(String phone) {
		// 根据手机号查询此人
		Filters filters = Filters.builder().setPhone(phone);
		List<UserInfo> list = userInfoDao.selectAllByFilter(filters);
		// 判断是否存在
		if (list.size() > 0) {
			State state = list.get(0).getUser().getState();
			// 判断状态是否为未激活
			if (state.getId() == StateEnum.ACCOUNT_NOT_ACTIVATION.getId()) {
				return JSONResult.builder(RetEnum.PHONE_NOT_ACTIVATION, "手机号未激活");
			}
			return JSONResult.builder(RetEnum.ERROR, "手机号已存在");
		}
		return JSONResult.builder(RetEnum.SUCCESS);
	}

	/**
	 * 个人注册和企业注册公用的方法
	 * 
	 * @throws Exception
	 */
	private void insertUsers(Users user, String telephone, int typeId) throws Exception {
		// 生成默认的用户名
		user.setUsername("default-" + telephone + "-" + Utils.randomNumber(6));
		// 密码进行MD5加密
		user.setPassword(String.valueOf(DigestUtils.md5Hex(user.getPassword())));
		// 生成用户注册时间
		user.setCreateTime(new Date());
		// 设置用户类别
		user.setTypeId(typeId);
		if (usersDao.insertOneAndGetId(user) < 1) {
			throw new Exception("向“users”表中插入数据时失败");
		}
	}

	/**
	 * 个人用户注册，向Users中插入一条纪录，同时向UserInfo表中插入一条数据，通过事务控制
	 * 
	 * @param uinfo
	 *            表注册的用户信息
	 * @return 成功返回新用户的用户id，失败报错
	 * @throws Exception
	 *             如果失败了就报错
	 */
	@Transactional(rollbackFor = Exception.class)
	public int signupPersonal(UserInfo uinfo) throws Exception {
		// 1、先向 users 表中插入一条数据
		insertUsers(uinfo.getUser(), uinfo.getTelephone(), 1);
		// 2、再向UserInfo表中插入一条数据
		// 生成默认资料
		if (uinfo.getNickName() == null)
			uinfo.setNickName("p" + Utils.formatDate(new Date(), "HHmmssMMdd"));
		uinfo.setNickName(uinfo.getNickName().trim());
		if (uinfo.getAvatar().getName() == null) {// 生成默认头像
			// uinfo.setAvatar("default-avatar.jpg?0");
			String before = "normal/default-avatar-";
			String avatarNumber = Utils.randomNumber(1, 100, 3);
			String aftar = ".jpg";
			uinfo.setAvatar(before + avatarNumber + aftar + "?" + Utils.randomNumber(6));
		}
		if (uinfo.getGender() == null)
			uinfo.setGender(new Gender(1));
		if (userInfoDao.inserOneUserInfo(uinfo) < 1) {
			throw new Exception("向“user_info”表中插入数据时失败");
		}
		return uinfo.getUser().getId();
	}

	/**
	 * 企业用户注册，跟 个人用户注册步骤一样
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean signupEnterprise(MultipartFile logoFile, MultipartFile licenseFile, EnterpriseInfo newEnter)
			throws Exception {
		// 1、先向 users 表中插入一条数据
		insertUsers(newEnter.getUser(), newEnter.getTelephone(), 2);

		// 2、上传logo和营业执照到阿里云的OSS
		String logoName = newEnter.getUser().getId() + "_logo.jpg";
		new OSSUtil(PathEnum.ENT_LOGO).uploadFile2OSS(logoFile.getInputStream(), logoName);
		newEnter.setLogo(logoName);

		String licenseName = newEnter.getUser().getId() + "_license.jpg";
		new OSSUtil(PathEnum.ENT_LICENSE).uploadFile2OSS(licenseFile.getInputStream(), licenseName);
		newEnter.setBusinessLicense(licenseName);

		// 3、再向EnterpriseInfo表中插入一条数据
		if (enterInfoDao.inserOnSignup(newEnter) <= 0) {
			throw new Exception("向“enterprise_info”表中插入数据时失败");
		}
		CertificationEnterprise ci = new CertificationEnterprise();
		ci.setUser(newEnter.getUser());
		ci.setBusinessLicense(licenseName);
		ci.setEnterpriseName(newEnter.getEnterpriseName());
		ci.setPersonName(newEnter.getPersonName());
		ci.setCreateTime(new Date());
		ci.setState(new State(4));
		certificationEnterpriseService.insertOne(ci);
		return true;
	}

	/** 小程序企业用户注册，不传图 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult signupEnterpriseInfo(EnterpriseInfo entInfo, String vCode) {
		JSONResult jr = new JSONResult();
		// 判断必须参数是否为空
		if (isEmpty(entInfo.getUser().getPassword(), entInfo.getEnterpriseName(), entInfo.getPersonName()) > 0
				|| entInfo.getTelephone() == null) {
			jr.setValue(RetEnum.PARAM_LACK, "缺少必要的参数");
		} else {
			try {
				// 判断验证码是否正确
				if (SMSUtil.validationVCode(entInfo.getTelephone(), vCode) == RetEnum.SUCCESS) {
					// 清除验证码
					SMSUtil.cleanVCode(entInfo.getTelephone());
					// 1、先向 users 表中插入一条数据
					insertUsers(entInfo.getUser(), entInfo.getTelephone(), 2);
					// 2、设置默认值
					entInfo.setLogo("uploading.jpg");
					entInfo.setBusinessLicense("uploading.jpg");
					// 2、再向EnterpriseInfo表中插入一条数据
					if (enterInfoDao.inserOnSignup(entInfo) < 1) {
						throw new Exception("向“enterprise_info”表中插入数据时失败");
					}
					// 3、向企业审核中插入一条数据
					CertificationEnterprise ci = new CertificationEnterprise();
					ci.setUser(entInfo.getUser());
					ci.setEnterpriseName(entInfo.getEnterpriseName());
					ci.setBusinessLicense("uploading.jpg");
					ci.setPersonName(entInfo.getPersonName());
					ci.setCreateTime(new Date());
					ci.setState(new State(4));
					if (!certificationEnterpriseService.insertOne(ci)) {
						throw new Exception("向“certification_enterprise”表中插入数据时失败");
					}
					jr.setValue(RetEnum.SUCCESS, entInfo.getUser().getId());
				} else {
					jr.setValue(RetEnum.VCODE_ERROR, "验证码错误");
				}
			} catch (Exception e) {
				e.printStackTrace();
				jr.setValue(RetEnum.EXCEPTION, e.getMessage());
			}
		}
		return jr;
	}

	/**
	 * 个人激活账号
	 * 
	 * @param telephone
	 *            手机号码
	 * @param password
	 *            密码
	 * @param vCode
	 *            手机号验证码
	 * @return 101 - 缺少参数；201 - 验证码已过期；202 - 验证码不正确；
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult signupPersonalActivation(UserInfo userInfo, String password, String vCode) {
		String nickName = userInfo.getNickName();
		String telephone = userInfo.getTelephone();
		if (isEmpty(nickName, telephone, password, vCode) > 0) {
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数");
		}
		// 判断验证码是否正确
		RetEnum ret = SMSUtil.validationVCode(telephone, vCode);
		if (ret != RetEnum.SUCCESS) {
			return JSONResult.builder(ret, "验证码错误或已过期");
		}

		List<UserInfo> uinfos = userInfoService.search(Filters.builder().setPhone(telephone));
		if (uinfos.size() > 0) {
			int userId = uinfos.get(0).getUser().getId();
			Users user = new Users(userId);
			user.setPassword(DigestUtils.md5Hex(password)); // 密码进行MD5加密
			user.setState(StateEnum.ACCOUNT_NORMAL);// 将账号状态置为正常
			// usersDao.updateUser(user);
			UserInfo uinfo = new UserInfo(user);
			uinfo.setNickName(nickName);
			userInfoService.changeUserInfo(uinfo);// 更新数据库
			return JSONResult.builder(RetEnum.SUCCESS, "激活成功");
		}
		return JSONResult.builder(RetEnum.ERROR, "激活失败，手机号码不存在。");
	}

	public Users queryTypeId(int userId) {
		return usersDao.queryTypeId(userId);
	}

	/**
	 * 查询用户已注册数量
	 * 
	 * @return
	 */
	public int queryCountUser() {
		return usersDao.queryCountUser();
	}

	/**
	 * 查询Total，根据表名状态查询
	 * 
	 * @param tableName
	 *            表名
	 * @return 返回该表的总数
	 */
	public int queryTotalByState(Filters filters) {
		return usersDao.selectCountByTableNameAndState(filters);
	}
}
