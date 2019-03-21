package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.Gender;

/**
 * 性别实体类接口
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017年11月9日 下午5:57:30
 */

public interface GenderDao {

	/**
	 * 分页查询出所有性别
	 * 
	 * @param limit
	 *            偏移量
	 * @param offset
	 *            查询量
	 */
	List<Gender> selectAll(@Param("limit") int limit, @Param("offset") int offset);

}
