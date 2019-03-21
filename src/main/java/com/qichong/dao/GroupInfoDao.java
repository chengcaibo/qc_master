package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.GroupInfo;
import com.qichong.model.Filters;
import com.qichong.model.GroupInfoModel;

/**
 * 团体实体类接口
 */
public interface GroupInfoDao {

	/** 查询所有【团体】信息并且筛选 */
	List<GroupInfo> selectAllByFilter(Filters filters);

	/** 查询所有的团体信息，并筛选 */
	List<GroupInfo> selectAllAndFilter(@Param("filters") Filters filters, @Param("city") String city);

	int groupInfoFilters_total(@Param("filters") Filters filters, @Param("city") String city);

	/** 查询最新注册的3个 */
	List<GroupInfoModel> groupPreferred();

	/** 查询最新注册的13个 */
	List<GroupInfoModel> querthirteenGroup(@Param("city") String city);

	/** 查询最新注册的1个 */
	GroupInfoModel queryOneGroup(@Param("city") String city);

	/** 查询5团体信息 */
	List<GroupInfoModel> querFiveGroup();

	/** 插入一条团体信息 */
	int insertOne(GroupInfo group);

	/** 根据id修改团体信息 */
	int updateOne(GroupInfo group);

	/** 删除团体信息 */
	int deleteOne(@Param("id") int id, @Param("userId") Integer loginUserId);

	/** 根据行业模糊查询团体 */
	List<GroupInfoModel> byNameGroupInfo(@Param("name") String name, @Param("city") String regionCity);

	/** 为用户推介相同行业的团体 */
	List<GroupInfoModel> likeGroup(@Param("industryCode") String industryCode);
}
