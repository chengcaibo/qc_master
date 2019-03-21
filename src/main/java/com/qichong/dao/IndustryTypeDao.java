package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.IndustryType;
import com.qichong.model.ChildParentModel;
import com.qichong.model.Filters;

/**
 * 
 * 行业类别 持久层 接口
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2018年1月3日13:38:58
 */
public interface IndustryTypeDao {

	/** 模糊查询行业 */
	List<ChildParentModel> selectLikeAllIndustry(Filters filters);

	/**
	 * 查询单个职业
	 * 
	 * @param industryCode
	 *            行业Code
	 */
	IndustryType selectOne(String industryCode);

	/**
	 * 根据父级Id查询所有子级
	 * 
	 * @param parentCode
	 *            父级行业Code
	 */
	List<IndustryType> selectChildByParentId( //
			@Param("parentCode") String parentCode, // 查询单个
			@Param("parentCodes") List<String> parentCodes // 查询多个，如果不查询多个，就可以传入null
	);
}
