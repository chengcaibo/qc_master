package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.Bananer;

/***
 * 轮播数据访问层
 * @创建人：陈文瑾
 * @创建时间：2017年12月14日10:20:52
 */


public interface BananerDao {
	
	//查询全部
	List<Bananer> queryCarousel(@Param("s")String s);

}
