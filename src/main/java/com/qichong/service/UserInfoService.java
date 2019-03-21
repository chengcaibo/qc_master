package com.qichong.service;

import static com.qichong.util.web.ServletUtil.isEmpty;
import static com.qichong.util.web.ServletUtil.queryAllChildCodes;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.qichong.entity.*;
import com.qichong.enums.StateEnum;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.qichong.dao.UserInfoDao;
import com.qichong.enums.RetEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.model.PersonalTopOneModel;
import com.qichong.util.Utils;

@Service
public class UserInfoService {

	@Autowired
	UserInfoDao userInfoDao;

	@Autowired
	UsersService userService;
	@Autowired
	RegionsService regionService;
	@Autowired
	JobInfoService jobService;
	@Autowired
	SignService signService;
	@Autowired
	WalletService walletService;

	/** 搜索、筛选、个人 */
	public List<UserInfo> search(Filters filters) {
		// 将搜索某项的所有子级
		filters.setJobCodes(queryAllChildCodes(filters.getJobCode(), jobService));
		filters.setRegionCodes(queryAllChildCodes(filters.getRegionCode(), regionService));
		return userInfoDao.selectAllByFilter(filters);
	}

	/** 搜索、筛选、个人总数 */
	public int searchTotal(Filters filters) {
		// 将搜索某项的所有子级
		filters.setJobCodes(queryAllChildCodes(filters.getJobCode(), jobService));
		filters.setRegionCodes(queryAllChildCodes(filters.getRegionCode(), regionService));
		return userInfoDao.selectAllCountByFilter(filters);
	}

	public List<UserInfo> queryNearbyPersoanl(Filters filters) {
		return userInfoDao.selectAllByFilter(filters);
	}

	/** 修改优势 */
	public boolean modifyAscendancy(UserInfo ui) {
		return userInfoDao.modifyAscendancy(ui) > 0;
	}

	/** 查询优势 */
	public String queryStrong(int userId) {
		return userInfoDao.selectStrongByUserId(userId);
	}

	/**
	 * 查询所有信息（新闻页面用）
	 */
	public List<UserInfo> queryAllInfo(String[] ids) {
		String build = "";
		for (int i = 0; i < ids.length; i++) {
			build += (i != 0) ? ", " : "";
			build += ids[i];
		}
		return userInfoDao.queryAllInfo(build);
	}

	public List<PersonalTopOneModel> queryPreferred() {
		return userInfoDao.queryPreferred();
	}

	/**
	 * 查询手机号是否已存在
	 */
	public List<UserInfo> checkPhone(String telephone) {
		return userInfoDao.checkPhone(telephone);
	}

	/** 根据手机号查询用户id */
	public Integer queryUserIdByTelephone(String telephone) {
		UserInfo uinfo = userInfoDao.selectUserIdByTelephone(telephone);
		if (uinfo != null) {
			return uinfo.getUser().getId();
		}
		return null;
	}

	/**
	 * 根据验证码修改手机号
	 */
	public boolean byIdModifyPhone(int userId, String telephone) {
		return userInfoDao.byIdModifyPhone(userId, telephone) > 0;
	}

	/**
	 * 分页查询所有用户信息，并且可以筛选
	 */
	public List<UserInfo> queryAll(Filters filters, String city) {
		// 计算筛选条件
		return userInfoDao.selectAll(filters, city);
	}

	/** 查询出上面那个方法的Total */
	public int personalFilters_total(Filters filters, String city) {
		return userInfoDao.personalFilters_total(filters, city);
	}

	/**
	 * 查询评分最高的五位用户
	 */
	public List<PersonalTopOneModel> queryTopFive() {
		return userInfoDao.queryTopFive();
	}

	/**
	 * 查询所由用户总数
	 */
	public int infoCount() {
		return userInfoDao.infoCount();
	}

	/**
	 * 查询用户评分大于六的随机抽取
	 */
	public List<UserInfo> randomUser(int page, int pageSize) {
		// 根据页码和页大小计算偏移量
		int limit = (page - 1) * pageSize;
		return userInfoDao.randomUser(limit, pageSize);
	}

	/**
	 * 查询单个用户的信息
	 */
	public UserInfo queryUserInfo(int userId) {
		return userInfoDao.queryUserInfo(userId);
	}

	/**
	 * 查询单个用户的简介信息
	 */
	public PersonalTopOneModel queryUserInfoProfile(int userId) {
		return userInfoDao.queryUserInfoProfile(userId);
	}

	/**
	 * 根据行业进行模糊查询
	 * 
	 * @param page
	 *            页码
	 * @param pageSize
	 *            页大小，表示一页显示几条数据
	 * @return
	 */

	public List<PersonalTopOneModel> queryBlurUser(String OccupationTypeName, int page, int pageSize,
			String regionCity) {
		// 根据页码和页大小计算偏移量
		int limit = (page - 1) * pageSize;
		return userInfoDao.queryBlurUser(OccupationTypeName, limit, pageSize, regionCity);
	}

	/** 根据所查看的用户职业，推送5个用户 */
	public List<UserInfo> pushUser(String jobCode) {
		return userInfoDao.pushUser(jobCode);

	}

	/**
	 * 每隔5秒查询十位Id最大的用户
	 * 
	 * @return list集合
	 */
	public List<PersonalTopOneModel> oderByMaxIdUserInfo(String city) {
		return userInfoDao.oderByMaxIdUserInfo(city);
	}

	/**
	 * 更改用户头像
	 * 
	 * @param userId
	 *            要更改的用户id
	 * @param avatarFile
	 *            要更改的头像
	 * @param crop
	 *            裁剪信息
	 * @throws Exception
	 */
	public JSONResult changeAvatar(int userId, MultipartFile avatarFile, String put, HttpSession session) {
		// 获取服务器的上传路径
		String uploadPath = Utils.getUploadPath(session);
		// 重新调整头像大小并上传至阿里云
		String avatarName = Utils.resizeAvatar(uploadPath, avatarFile, put, userId);
		if (avatarName == null) {
			return JSONResult.builder(RetEnum.ERROR, "头像上传至OSS失败");
		}
		// 存入数据库
		UserInfo userInfo = new UserInfo(new Users(userId));
		// 在末尾添加随机数，可以用来防止缓存
		userInfo.setAvatar(avatarName + "?" + Utils.randomNumber(6));
		return changeUserInfo(userInfo);
	}

	/**
	 * 上传APP报错日志
	 */
	public JSONResult uploadLog(int userId, MultipartFile avatarFile) {
		try {
			String uploadPath = "E:\\data\\upload\\userLog";
			String fileName = userId + "_log_" + Utils.formatDate(new Date(), "yyyyMMddHHmmss") + ".txt";
			File uploadFile = new File(uploadPath, fileName);
			FileUtils.copyInputStreamToFile(avatarFile.getInputStream(), uploadFile);
			return JSONResult.builder(RetEnum.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, e.getMessage());
		}
	}

	/** 修改用户信息 */
	public boolean editUserInfo(UserInfo userInfo) {
		return userInfoDao.updateUserInfo(userInfo) > 0;
	}

	/** 修改UserInfo表信息和user表信息 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult changeUserInfo(UserInfo userInfo) {
		try {
			Users u = userInfo.getUser();
			// 判断是否修改了users表
			if (isEmpty(u.getUsername(), u.getPassword(), u.getTypeId() == null ? null : String.valueOf(u.getTypeId()),
					u.getWxOpenId(), u.getWxUnionId(),
					u.getState() == null ? null : String.valueOf(u.getState().getId())) < 6) {
				userService.changeUser(u);
			}
			// 更新user_info表
			userInfoDao.updateUserInfo(userInfo);
			return JSONResult.builder(RetEnum.SUCCESS);
		} catch (DuplicateKeyException dke) {
			return JSONResult.builder(RetEnum.VALUE_EXIST, "触发唯一键约束");
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, e.getMessage());
		}
	}

	/**
	 * @author ing
	 * @param participatorId
	 * @param redPackets
	 * @param nickName
	 * @param phoneNumber
	 * @return
	 * @descrition 用户分享注册，创建一个用户并给出随机红包，分享者也得到同样数额的红包，开启事务
	 */
	@Transactional(rollbackFor = Exception.class)
	public String createUserAndUpdateAccount(Integer participatorId, Double redPackets, String nickName, String phoneNumber) {
		//1，创建一个用户，设置为默认密码
		Users user = new Users();
		user.setPassword("9sfd8sdfgui9ewri");
		user.setState(new State(StateEnum.ACCOUNT_NOT_ACTIVATION));
		//2,，分享者的账户增加金额
		Wallets wallets = walletService.queryOneMustHaveValue(participatorId);
		wallets.setPlusBalance(redPackets);
		Boolean ret = walletService.updateAccount(wallets);
		if(ret){
			System.out.println("账户余额更新成功");
		}else{
			System.out.println("账户余额更新失败");
		}
		//3，被分享者根据手机号创建新的账号，并同样增加与分享者相同的红包数量
		UserInfo uinfo= new UserInfo(user);
		uinfo.setNickName(nickName);
		uinfo.setTelephone(phoneNumber);
		if (uinfo.getJobType() == null){

			uinfo.setJobType(new JobType(2));
		}
		// 注册
		int uid = 0;
		try {
			uid = userService.signupPersonal(uinfo);
			//根据电话号码查询出新创建的账户的userid
			Integer userid = userService.queryUserIdByPhoneNumber(uinfo.getTelephone());
			//	查出该用户userid对应的wallets
			Wallets account = walletService.queryOneMustHaveValue(userid);
			account.setPlusBalance(redPackets);
			Boolean Ret = walletService.updateAccount(account);
			if(Ret){
				System.out.println("账户余额更新成功");
			}else{
				System.out.println("账户余额更新失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONResult.success().toJSON();
	}


}
