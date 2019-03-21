package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.Regions;
import com.qichong.model.ChildParentModel;
import com.qichong.model.Filters;

/**
 * 
 * Regions 持久层
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2018年1月4日11:58:28
 */
public interface RegionsDao {

	/** 模糊查询地区 */
	List<ChildParentModel> selectLikeRegion(Filters filters);

	/** 查询单个地区 */
	Regions selectOne(String regionCode);

	/** 根据父行政区代码查询出全部的子地区 */
	List<Regions> selectChildByParentId( //
			@Param("parentCode") String parentCode, // 查询单个
			@Param("parentCodes") List<String> parentCodes // 查询多个，如果不查询多个，就可以传入null
	);

}
