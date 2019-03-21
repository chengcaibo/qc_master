package com.qichong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.FollowNexusDao;
import com.qichong.entity.FollowNexus;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.Filters;
import com.qichong.model.FollowesInfoModel;
import com.qichong.model.JSONResult;

@Service
public class FollowNexusService {

	@Autowired
	FollowNexusDao followNexusDao;

	/** 取消关注 */
	public boolean cancelFollow(int userIdA, int userIdB) {
		return followNexusDao.deleteOneByUserId(userIdA, userIdB) > 0;
	}

	/** 根据UserId查询出关注列表（limit） */
	public List<FollowesInfoModel> queryFollows(int userId, Integer loginId, Filters filters) {
		return followNexusDao.selectFollowNexusByUserId(userId, loginId, "b", "a", filters);
	}

	/** 根据UserId查询出关注总数（count） */
	public Integer queryFollowTotal(int userId) {
		return followNexusDao.selectFollowNexusCountByUserId(userId, "a");
	}

	/** 根据UserId查询出粉丝列表（limit） */
	public List<FollowesInfoModel> queryFans(int userId, Integer loginId, Filters filters) {
		return followNexusDao.selectFollowNexusByUserId(userId, loginId, "a", "b", filters);
	}

	/** 根据UserId查询出粉丝总数（count） */
	public Integer queryFansTotal(int userId) {
		return followNexusDao.selectFollowNexusCountByUserId(userId, "b");
	}

	// /** 查询已关注的记录详情 */
	// public List<FollowNexusModel> queryFollowNexusUserA(int userId) {
	// return followNexusDao.queryFollowNexusUserA(userId);
	// }

	/** 查询是否关注过 */
	public int followNexusUserACount(int userIdA, int userIdB) {
		return followNexusDao.followNexusUserACount(userIdA, userIdB);
	}

	/**
	 * 添加关注
	 * 
	 * @param followerId
	 *            关注者的id
	 * @param followId
	 *            被关注人的id
	 */
	public JSONResult addFollow(Integer followerId, Integer followId) {
		JSONResult jr = new JSONResult();
		if (followerId == null || followId == null) {
			jr.setValue(RetEnum.PARAM_LACK, "缺少参数");
		} else if (followerId.equals(followId)) {
			jr.setValue(RetEnum.ERROR, "自己不能关注自己");
		} else if (this.followNexusUserACount(followerId, followId) > 0) {
			// 查询 A 给 B 点赞 的记录，并判断是否 > 0
			jr.setValue(RetEnum.VALUE_EXIST, "不能重复关注");
		} else {
			// A 关注 B
			FollowNexus followNexus = new FollowNexus(new Users(followerId), new Users(followId));
			if (followNexusDao.insertFollowNexus(followNexus)) {
				jr.setValue(RetEnum.SUCCESS);
			} else {
				jr.setValue(RetEnum.EXCEPTION, "服务器匆忙");
			}
		}
		return jr;
	}

	/** 取消关注，根据Follow表中的id */
	@Deprecated
	public boolean cancelFollow(int id) {
		return followNexusDao.deleteOne(id) > 0;
	}

	/** 新增关注记录 */
	@Deprecated
	public boolean insertFollowNexus(FollowNexus followNexus) {
		return followNexusDao.insertFollowNexus(followNexus);
	}

}
