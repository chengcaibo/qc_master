package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.EnterpriseInfo;
import com.qichong.model.EnterpriseInfoModel;
import com.qichong.model.EnterpriseTopOneModel;
import com.qichong.model.Filters;

/**
 * 企业信息表【dao】接口
 * 
 * @创建人 吴志伟
 * @修改人 孙建雷
 * @修改时间 2017-11-9 13:52:02
 */
public interface EnterpriseInfoDao {

	/** 根据行业查询企业信息 */
	List<EnterpriseInfo> industryByEnterprise(@Param("industryDetails") String industryDetails);

	/** 查询所有的企业信息，并筛选 */
	List<EnterpriseInfo> selectAllByFilter(Filters filters);

	/** 查询最新注册的三个公司 */
	List<EnterpriseTopOneModel> enterprisePreferred();

	/** 查询全部企业，注册时间降序，可筛选 */
	List<EnterpriseInfo> selectAll(Filters filter);

	/** 查询全部企业，注册时间降序，可筛选 */
	int selectAllTotal(Filters filter);

	/** 查询所有的企业，并筛选 */
	List<EnterpriseInfoModel> selectAllAndFilter(@Param("filters") Filters filter, @Param("city") String city);

	int enterpriseInfoFilters_total(@Param("filters") Filters filter, @Param("city") String city);

	/** 分页查询出所有企业信息 */
	List<EnterpriseInfo> queryAll(@Param("limit") int limit, @Param("offset") int offset,
			@Param("enterpriseName") String enterpriseName);

	/** 新增企业信息 */
	int insertOne(EnterpriseInfo ei);

	/**
	 * 新增企业信息
	 */
	int inserOnSignup(EnterpriseInfo ei);

	/**
	 * 根据行业类别进行模糊查询
	 */
	List<EnterpriseTopOneModel> queryLikeByTypeName(@Param("industryName") String industryName,
			@Param("regionCity") String regionCity);

	/** 修改企业信息，返回受影响的行数 */
	int updateOne(EnterpriseInfo enterpriseInfo);

	/** 修改企业Banner图片 */
	int updateBanner(@Param("userId") Integer userId, @Param("num") String num, @Param("url") String url);

	/** 修改企业资质图片 */
	int updatePicture(@Param("userId") Integer userId, @Param("num") Integer num, @Param("url") String url);

	/** 查询id最大的五条用来做轮播 */
	List<EnterpriseTopOneModel> oderByEnterpriseMaxFive();

	/** 查询id最大的13条用来做轮播 */
	List<EnterpriseTopOneModel> enterPriseThirteen(@Param("city") String city);

	/** 删除企业信息 */
	boolean deleteOne(int id);

	/**
	 * 根据查询某个字段，必须等于传入 的值
	 * 
	 * @param key
	 *            要查询的字段
	 * @param value
	 *            要查询的值
	 */
	List<EnterpriseInfo> selectEqualsKey(@Param("key") String key, @Param("value") String value);

	/**
	 * 根据查询某个字段，必须包含传入 的值
	 * 
	 * @param key
	 *            要查询的字段
	 * @param value
	 *            要查询的值
	 */
	List<EnterpriseInfo> selectLikeKey(@Param("key") String key, @Param("value") String value);

	/**
	 * 查询单个企业
	 * 
	 * @param id
	 * @return
	 */
	EnterpriseInfo byIdEnterprise(Integer id);



	Integer updateEnterpriseInfo(@Param("userId")Integer userId,@Param("philosophy") String philosophy,@Param("profile")String profile,@Param("advantage")String advantage,@Param("honor")String honor,@Param("idea")String idea,@Param("honorContent")String honorContent,@Param("advantageContent")String advantageContent,@Param("introduce")String introduce );
}
