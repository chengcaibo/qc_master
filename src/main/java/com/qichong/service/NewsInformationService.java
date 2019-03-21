package com.qichong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.NewsInformationDao;
import com.qichong.entity.NewsInformation;

/***
 * 动态新闻
 * @创建者：陈文瑾
 * @创建时间：2017年12月14日15:29:01
 *
 */
@Service
public class NewsInformationService {

	@Autowired
	NewsInformationDao dao;
	
	/**
	 * 模糊查询动态新闻
	 * @param s
	 * @return
	 */
	public List<NewsInformation> queryNewsInformation(){
		return dao.queryNewsInformation();
	}
	
	/**
	 * 根据id查询内容
	 * @param id
	 * @return
	 */
	public NewsInformation queryNewsInformationById(int id){
		return dao.queryNewsInformationById(id);
	}
}
