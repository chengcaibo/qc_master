package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.AdPublic;
import com.qichong.model.AdpublicInfoModel;
import com.qichong.model.Filters;

/**
 * 广告Dao接口
 * 
 * @author 陈文瑾
 * @时间 2017年12月5日09:55:40
 */

public interface AdPublicDao {
	
	/** 查询所有【广告】信息并且筛选 */
	List<AdPublic> selectAllByFilter(Filters filters);

	/** 查询全部个人发布广告信息 用于首页更多 */
	List<AdPublic> queryUserInfoAdpublicMore();

	/** 查询广告详情 */
	List<AdpublicInfoModel> selAdpublicAll(@Param("filters") Filters filters);

	/** 查询全部企业发布广告信息 用于首页更多 */
	List<AdPublic> queryEnterpriseAdpublicMore();

	/** 查询全部并筛选 */
	List<AdPublic> selectAllAndFilter(Filters filters);

	/** 根据UserId查询广告信息 */
	AdPublic selectByUserId(int userId);

	List<AdPublic> selAdpublic(int userId);

	/** 新增广告返回受影响的行数 */
	int insertOne(AdPublic adPublic);

	/** 修改广告 返回受影响行数 */
	int updateOne(AdPublic adPublic);
	
	/** 根据id删除广告 */
	int deleteOne(int id);

	/** 查询五條ID最大的广告记录 */
	List<AdPublic> queryAdPublicMaxFive(@Param("limit") int limit, @Param("offset") int offset,
			@Param("city") String city);

	/** 查询个人发布的广告记录 */
	List<AdPublic> queryAdPublicUserMaxFive(@Param("limit") int limit, @Param("offset") int offset,
			@Param("city") String city);

	/** 查询企业发布发布的广告记录 */
	List<AdPublic> queryAdPublicEnterpriseMaxFive(@Param("limit") int limit, @Param("offset") int offset,
			@Param("city") String city);

	/** 根据Id查询用户所发布过的广告 */
	List<AdPublic> byUserIdAdPublic(int userId);

	/** 查询所有的广告 */
	List<AdPublic> queryAdPublic();

	/** 查询单个的广告 */
	AdPublic queryOneAdpublic(int adPublicId);

	/** 详情广告 */
	AdPublic queryAdpublicInfo(Integer id);

	/** 查询你可能喜欢 */
	List<AdPublic> queryLike(@Param("content") String content, @Param("ruleOutId") int ruleOutId);

}
