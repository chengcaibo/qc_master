package com.qichong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.TheResponseDao;
import com.qichong.entity.TheResponse;

@Service
public class TheResponseService {
	@Autowired
	TheResponseDao theResponseDao;
	/**
	 * 查询详情根据Id
	 */
	
	/**
	 * 添加响应信息
	 */
	public boolean addTheResponse(TheResponse theResponse){
		return theResponseDao.addTheResponse(theResponse);
	}
	/**
	 * 删除响应信息
	 */
	public boolean deleteOne(int id){
		return theResponseDao.deleteTheResponse(id);
		
	}
	
	public int byTheResposIsNo(Integer demandId){
		return theResponseDao.byTheResposIsNo(demandId, 13);
	}
	/**
	 * B方个人被响应方记录
	 * @param id
	 * @param stateId
	 * @return
	 */
	public boolean update(int id, int stateId){
		return theResponseDao.updateState(id, stateId);
	}

	public List<TheResponse> byUbserBTheResponse(int id, int stateId){
		return theResponseDao.byUbserBTheResponse(id,stateId);
	}
	/**
	 *  A方个人响应方记录
	 * @param id
	 * @param stateId
	 * @return
	 */
	public List<TheResponse> byUserTheResponse(int id, int stateId){
		return theResponseDao.byUserTheResponse(id,stateId);
	}
	/**
	 * 个人响应企业记录 登录的企业是被响应方
	 * @param id
	 * @param stateId
	 * @return
	 */
	public List<TheResponse> byUserBAndEnterpriseTheResponse(int id, int stateId){
		return theResponseDao.byUserBAndEnterpriseTheResponse(id,stateId);
	}
	/**
	 *  企业响应个人的 个人是被响应方就是登录方
	 * @param id
	 * @param stateId
	 * @return
	 */
	public List<TheResponse> byUserAndEnterpriseBTheResponse(int id, int stateId){
		return theResponseDao.byUserAndEnterpriseBTheResponse(id,stateId);
	}
	/**
	 * A方企业响应记录
	 * @param id
	 * @param stateId
	 * @return
	 */
	public List<TheResponse> byEnterpriseTheResponse(int id, int stateId){
		return theResponseDao.byEnterpriseTheResponse(id,stateId);
	}
	/**
	 * B方企业被响应记录
	 * @param id
	 * @param stateId
	 * @return
	 */
	public List<TheResponse> byEnterpriseBTheResponse(int id, int stateId){
		return theResponseDao.byEnterpriseBTheResponse(id,stateId);
	}
	/**
	 * A方个人响应企业记录 
	 * @param id
	 * @param stateId
	 * @return
	 */
	public List<TheResponse> byUserAndEnterpriseTheResponse(int id, int stateId){
		return theResponseDao.byUserAndEnterpriseTheResponse(id,stateId);
	}
	/**
	 * b方个人响应企业记录 
	 * @param id
	 * @param stateId
	 * @return
	 */
	public List<TheResponse> byEnterpriseAndUserTheResponse(int id, int stateId){
		return theResponseDao.byEnterpriseAndUserTheResponse(id,stateId);
	}
	/**
	 * 查询是否响应过
	 * @param userId
	 * @param userIdB
	 * @param demandId
	 * @return
	 */
	public Integer byCount(int userId,int userIdB,int demandId){
		return theResponseDao.byCount(userId, userIdB, demandId);
	}
	
}
