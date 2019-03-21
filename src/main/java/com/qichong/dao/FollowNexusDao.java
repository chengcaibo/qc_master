package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.FollowNexus;
import com.qichong.model.Filters;
import com.qichong.model.FollowesInfoModel;

public interface FollowNexusDao {

	// /** 查询已关注的记录并查出详情 */
	// List<FollowNexusModel> queryFollowNexusUserA(int userAId);

	/** 查询是否已经关注过 */
	int followNexusUserACount(@Param("userIdA") int userIdA, @Param("userIdB") int userIdB);

	/** 新增一条关注记录 */
	boolean insertFollowNexus(FollowNexus followNexus);

	/** 根据UserId查询关注或粉丝（limit） */
	List<FollowesInfoModel> selectFollowNexusByUserId( //
			@Param("userId") int userId, // 待查询者的id
			@Param("loginId") Integer loginId, // 登录者的id，为空代表没有登录
			@Param("joinTable") String join, // join用的表
			@Param("whereTable") String where, // where用的表
			@Param("filters") Filters filters // 筛选
	);

	/** 根据UserId查询关注或粉丝的总数（count） */
	int selectFollowNexusCountByUserId( //
			@Param("userId") int userId, // 待查询者的id
			@Param("whereTable") String where // where用的表
	);

	/** 删除一条记录，返回受影响的行数（不建议使用） */
	int deleteOne(int id);

	/** 删除一条记录，返回受影响的行数 */
	int deleteOneByUserId(@Param("userIdA") int userIdA, @Param("userIdB") int userIdB);
}
