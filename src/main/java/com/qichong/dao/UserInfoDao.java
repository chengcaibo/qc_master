package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.UserInfo;
import com.qichong.model.Filters;
import com.qichong.model.PersonalTopOneModel;

public interface UserInfoDao {

	/** 通过筛选查询所有的用户信息 */
	List<UserInfo> selectAllByFilter(Filters filters);

	/** 通过筛选查询所有的用户总数 */
	int selectAllCountByFilter(Filters filters);

	/** 查询优势 */
	String selectStrongByUserId(int userId);

	/** 修改优势 */
	int modifyAscendancy(UserInfo ui);

	/** 查询所有信息（新闻页面用） */
	List<UserInfo> queryAllInfo(@Param("ids") String ids);

	/** 查询最后注册的三个用户 用于广告查询journalism */
	List<PersonalTopOneModel> queryPreferred();

	/** 查询手机号是否存在 */
	List<UserInfo> checkPhone(String telephone);

	/** 根据手机号码查询出userId，只返回user.id和telephone两个字段 */
	UserInfo selectUserIdByTelephone(String telephone);

	/** 推送集合 */
	List<UserInfo> pushUser(String jobCode);

	/**
	 * 根据id修改手机号
	 */
	int byIdModifyPhone(@Param("userId") int userId, @Param("telephone") String telephone);

	/**
	 * 查询所有用户信息
	 */
	List<UserInfo> selectAll(@Param("filters") Filters filters, @Param("city") String city);

	int personalFilters_total(@Param("filters") Filters filters, @Param("city") String city);

	/**
	 * 
	 * @return list 查询评分最高的五位用户
	 */
	List<PersonalTopOneModel> queryTopFive();

	/**
	 * 
	 * @return int 查询所由用户总数
	 */
	int infoCount();

	/**
	 * 
	 * @param randomUser
	 * @return list 查询用户评分大于六的随机抽取
	 */
	List<UserInfo> randomUser(@Param("limit") int limit, @Param("offset") int offset);

	/** 查询单个用户的信息 */
	UserInfo queryUserInfo(int userId);

	/**
	 * 查询单个用户的简介信息
	 * 
	 * @param userId
	 * @return UserInfo
	 */
	PersonalTopOneModel queryUserInfoProfile(int userId);

	/**
	 * 
	 * @param OccupationType
	 * @return list 根据行业进行模糊查询
	 */

	List<PersonalTopOneModel> queryBlurUser(@Param("name") String name, @Param("limit") int limit,
			@Param("offset") int offset, @Param("regionCity") String regionCity);

	/**
	 * 每隔5秒查询十位Id最大的用户
	 * 
	 * @return list集合
	 */
	List<PersonalTopOneModel> oderByMaxIdUserInfo(@Param("city") String city);

	/**
	 * 新增用户详细信息
	 */
	int inserOneUserInfo(UserInfo userInfo);

	/** 修改用户信息，返回受影响的行数 */
	int updateUserInfo(UserInfo userInfo);

	/**
	 * 根据查询某个字段，必须等于传入 的值
	 * 
	 * @param key
	 *            要查询的字段
	 * @param value
	 *            要查询的值
	 */
	List<UserInfo> selectEqualsKey(@Param("key") String key, @Param("value") String value);

	/**
	 * 根据查询某个字段，必须包含传入 的值
	 * 
	 * @param key
	 *            要查询的字段
	 * @param value
	 *            要查询的值
	 */
	List<UserInfo> selectLikeKey(@Param("key") String key, @Param("value") String value);

}
