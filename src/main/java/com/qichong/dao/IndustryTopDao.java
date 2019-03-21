package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.IndustryTop;
import com.qichong.model.IndustryTopModel;

public interface IndustryTopDao {
	
	/**
	 * 查询全部userId
	 * @return
	 */
	List<IndustryTop> queryAllUserId();
	
	List<IndustryTopModel> queryEnterprise(@Param("name") String name);
	
	List<IndustryTopModel> queryAllLoction();
	/**
	 * 查询是否可抢位置
	 * @param locationId
	 * @return
	 */
	int industryCount(@Param("industryCode")String industryCode);
	/**
	 * 查看结束时间
	 * @param locationId
	 * @return
	 */
	List<IndustryTop> industryCheck(@Param("industryCode")String industryCode);
	/***
	 * 更新，抢置顶用
	 * @param industryTop
	 * @return
	 */
	boolean  updateIndustryTop(IndustryTop industryTop);
}
