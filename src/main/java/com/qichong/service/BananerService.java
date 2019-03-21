package com.qichong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.BananerDao;
import com.qichong.entity.Bananer;

/**
 * 新闻轮播访问层
 * @创建人：陈文瑾
 * @创建时间：2017年12月14日10:30:11
 *
 */
@Service
public class BananerService {

	@Autowired
	BananerDao dao;
	
	/**
	 * 模糊查询新闻轮播图
	 * @param name
	 * @return
	 */
	public List<Bananer> queryCarousel(String s){
		return dao.queryCarousel(s);
	}
	
}
