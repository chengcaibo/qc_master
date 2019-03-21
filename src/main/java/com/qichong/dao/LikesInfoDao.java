package com.qichong.dao;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.LikesInfo;

public interface LikesInfoDao {
	/**
	 * 查询被点赞次数
	 * 
	 * @param userId
	 * @return int类型
	 */
	int likesInfoCount(int userId);

	/**
	 * 新增点赞记录
	 * 
	 * @param likesInfo
	 * @return
	 */
	boolean insertLikesInfo(LikesInfo likesInfo);

	/**
	 * 查询点赞人数有没有相同的点赞记录
	 * 
	 * @param userId
	 * @return int
	 */
	int likesInfoUserACount(@Param("userIdA") int userIdA, @Param("userIdB") int userIdB);
}
