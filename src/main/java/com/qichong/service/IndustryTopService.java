package com.qichong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.IndustryTopDao;
import com.qichong.entity.IndustryTop;
import com.qichong.model.IndustryTopModel;

/**
 * 行业置顶
 * @创建人：陈文瑾
 * @创建时间：2017-12-18 16:42:29
 */

@Service
public class IndustryTopService {
	
	@Autowired
	IndustryTopDao dao; 
	
	/**
	 * 查询全部userId
	 * @return
	 */
	public List<IndustryTop> queryAllUserId(){
		return dao.queryAllUserId();
	}
	
	/**
	 * 查询企业信息
	 * */
	public List<IndustryTopModel> queryEnterprise(String s){
		return dao.queryEnterprise(s);
	}
	public List<IndustryTopModel> queryAllLoction(){
		return dao.queryAllLoction();
	}
	/**
	 * 查看是否可以抢置顶
	 * @param locationId
	 * @return
	 */
	public int industryCount(String industryCount){
		return dao.industryCount(industryCount);
	}
	
	public List<IndustryTop> industryCheck(String deskName){
		return dao.industryCheck(deskName);
	}
	
	/**
	 * 更新行业置顶
	 * @param industryTop
	 * @return
	 */
	public boolean updateIndustryTop(IndustryTop industryTop){
		return dao.updateIndustryTop(industryTop);
	}
}
