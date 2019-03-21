package com.qichong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.LikesInfoDao;
import com.qichong.entity.LikesInfo;

@Service
public class LikesInfoService {
	@Autowired
	LikesInfoDao likesInfoDao;

	/**
	 * 查询点赞次数
	 * 
	 * @param userId
	 * @return int
	 */
	public int likesInfoCount(int userId) {
		return likesInfoDao.likesInfoCount(userId);
	}

	/**
	 * 新增点赞记录
	 * 
	 * @param likesInfo
	 * @return boolean
	 */
	public boolean insertLikesInfo(LikesInfo likesInfo) {
		return likesInfoDao.insertLikesInfo(likesInfo);
	}

	/**
	 * 查询是否点赞过
	 * 
	 * @param userId
	 * @return int
	 */
	public int likesInfoUserACount(int userIdA, int userIdB) {
		return likesInfoDao.likesInfoUserACount(userIdA, userIdB);
	}

}
