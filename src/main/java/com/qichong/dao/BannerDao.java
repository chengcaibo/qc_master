package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.Banners;

/**
 * Banners Dao
 * 
 * @author 孙建雷
 *
 */
public interface BannerDao {

	/** 根据TypeId查询Banner */
	List<Banners> selectByTypeId(@Param("statusId") int statusId, @Param("typeId") int typeId);

}
