package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.GroupPersonnel;

/**
 * 团队人员实体类接口
 *
 * @创建人 孙建雷
 * @创建时间 2018年9月29日14:33:53
 */
public interface GroupPersonnelDao {

	/** 根据groupId查询所有队员 */
	List<GroupPersonnel> selectPersonnelByGroupId(int groupId);

	/** 根据userId查询所在团队 */
	List<GroupPersonnel> selectGroupsByUserId(int userId);

	/** 根据groupId和userId查询团队 */
	GroupPersonnel selectOneByGroupIdAndUserId(@Param("groupId") int groupId, @Param("userId") int userId);

	/** 添加一条新队员 */
	int insertOne(GroupPersonnel gp);

	/** 删除groupId下的所有人员 */
	int deleteByGroupId(int groupId);

	/** 删除某人在某团体下 */
	int deleteByGroupIdAndUserId(@Param("groupId") int groupId, @Param("userId") int userId);

}
