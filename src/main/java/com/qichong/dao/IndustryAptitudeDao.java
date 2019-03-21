package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.IndustryAptitude;

/** 资质实体类接口 */
public interface IndustryAptitudeDao {
	/** 新增用户资质信息 */
	int insertOne(IndustryAptitude aptitude);

	/** 删除资质 */
	int deleteOne(@Param("id") int id, @Param("userId") Integer userId);

	/** 修改资质 */
	int updateOne(IndustryAptitude aptitude);

	/** 查询单个用户的资质 */
	List<IndustryAptitude> queryUserIdAptitude(int userId);

}
