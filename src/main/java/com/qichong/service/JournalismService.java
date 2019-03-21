package com.qichong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.JournalismDao;
import com.qichong.entity.JournalismInfo;

/***
 * 
 * 新闻业务层
 * @创建者：陈文瑾
 * @时间：2017年12月13日11:44:45
 * 
 */
@Service
public class JournalismService {
	
	@Autowired
	JournalismDao journalismDao;
	/**
	 * 模糊查询最后四条数据
	 * @param s
	 * @return
	 */
	public List<JournalismInfo> queryJournalism(){
		return journalismDao.queryJournalism();
	}
	
	/**
	 * 根据id查询内容
	 * @param id
	 * @return
	 */
	public JournalismInfo queryJournalimsById(int id){
		return journalismDao.queryJournalimsById(id);
	}
	
}
