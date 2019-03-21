package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.TheResponse;

/**
 * 响应记录Dao层
 * 
 * @创建人吴志伟
 *
 */
public interface TheResponseDao {
	// 添加相应记录
	boolean addTheResponse(TheResponse theResponse);

	// 根据id删除响应信息
	boolean deleteTheResponse(int id);

	int byTheResposIsNo(@Param("demandId") int demandId, @Param("stateId") int stateId);

	/**
	 * 修改记录状态
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	boolean updateState(@Param("id") int id, @Param("stateId") int stateId);

	/**
	 * B方个人被响应方记录
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	List<TheResponse> byUbserBTheResponse(@Param("id") int id, @Param("stateId") int stateId);

	/**
	 * A方个人响应方记录
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	List<TheResponse> byUserTheResponse(@Param("id") int id, @Param("stateId") int stateId);

	/**
	 * -A方企业被响应记
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	List<TheResponse> byEnterpriseTheResponse(@Param("id") int id, @Param("stateId") int stateId);

	/**
	 * B方企业被响应记录
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	List<TheResponse> byEnterpriseBTheResponse(@Param("id") int id, @Param("stateId") int stateId);

	/**
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	List<TheResponse> byUserAndEnterpriseTheResponse(@Param("id") int id, @Param("stateId") int stateId);

	/**
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	List<TheResponse> byEnterpriseAndUserTheResponse(@Param("id") int id, @Param("stateId") int stateId);

	/**
	 * 个人响应企业记录 登录的企业是被响应方
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	List<TheResponse> byUserBAndEnterpriseTheResponse(@Param("id") int id, @Param("stateId") int stateId);

	/**
	 * 企业响应个人的 个人是被响应方就是登录方
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	List<TheResponse> byUserAndEnterpriseBTheResponse(@Param("id") int id, @Param("stateId") int stateId);

	/**
	 * 查询是否响应过
	 * 
	 * @param userId
	 * @param userIdB
	 * @param demandId
	 * @return
	 */
	Integer byCount(@Param("userId") Integer userId, @Param("userIdB") Integer userIdB, @Param("demandId") int demandId);

}
