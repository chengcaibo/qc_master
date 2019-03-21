package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.JournalismInfo;

/**
 * 新闻数据访问层
 * 
 * @创建者 陈文瑾
 * @创建时间 2017年12月13日11:43:23
 */
public interface JournalismDao {
	/***
	 * 模糊查询最后的四条数据
	 * 
	 * @param name
	 * @return
	 */
	List<JournalismInfo> queryJournalism();

	/***
	 * 根据id查询内容
	 */
	JournalismInfo queryJournalimsById(@Param("id") int id);

}
