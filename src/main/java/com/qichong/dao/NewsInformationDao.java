package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.NewsInformation;

/**
 * 动态新闻数据访问层
 * @创建人：陈文瑾
 * @创建时间：2017年12月14日15:26:37
 *
 */
public interface NewsInformationDao {
	
	/**
	 * 模糊查询动态新闻
	 * @param name
	 * @return
	 */
	List<NewsInformation> queryNewsInformation();
	
	/**
	 * 根据id查询内容
	 * @param id
	 * @return
	 */
	NewsInformation queryNewsInformationById(@Param("id")int id);

}
