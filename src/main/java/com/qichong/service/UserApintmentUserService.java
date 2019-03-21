package com.qichong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.UserApintmentUserDao;
import com.qichong.entity.UserApintmentUser;

/**
 * 
 * UserApintmentUserService
 * 
 * @创建人 吴志伟
 * @修改人 孙建雷
 * @修改时间 2017年11月9日 下午5:44:13
 */

@Service
public class UserApintmentUserService {

	@Autowired
	UserApintmentUserDao userApintmentUserDao;

	/**
	 * 根据id查询预约成功后的信息
	 */
	public List<UserApintmentUser> selUserApintment(Integer userIdB) {
		return userApintmentUserDao.selUserApintment(userIdB);
	}

	/** 新增预约记录 */
	public boolean insertReservation(UserApintmentUser userList) {
		return userApintmentUserDao.insertReservation(userList);
	}

	/**
	 * 返回list集合 查询被预约的记录
	 */
	public List<UserApintmentUser> byUserIdQueryUserBeAppintment(int userId) {
		return userApintmentUserDao.byUserIdQueryUserBeAppintment(userId);
	}

	/**
	 * 根据用户id 查询用户已预约记录
	 */
	public List<UserApintmentUser> byUserIdQueryUserAppintmentUser(int userId) {
		return userApintmentUserDao.byUserIdQueryUserAppintmentUser(userId);
	}

	public int queryUserCount(int userId) {
		return userApintmentUserDao.queryUserCount(userId);
	}

	/**
	 * 根据状态，用户AId 查询记录
	 */
	public List<UserApintmentUser> byStateAndAId(int userAId, int stateId) {
		return userApintmentUserDao.byStateAndAId(userAId, stateId);
	}

	/**
	 * 根据状态，用户BId 查询记录
	 */
	public List<UserApintmentUser> byStateAndBId(int userBId, int stateId) {
		return userApintmentUserDao.byStateAndBId(userBId, stateId);
	}

	/**
	 * 根据企业Id查询企业预约的人
	 * 
	 * @param eId
	 * @param stateId
	 * @return
	 */
	public List<UserApintmentUser> byStateAndEId(int eId, int stateId) {
		return userApintmentUserDao.byStateAndEId(eId, stateId);
	}

	/**
	 * 根据用户Id查询被企业约的记录
	 * 
	 * @param userBId
	 * @param stateId
	 * @return
	 */
	public List<UserApintmentUser> byStateAndUIdE(int userBId, int stateId) {
		return userApintmentUserDao.byStateAndUIdE(userBId, stateId);
	}

	/**
	 * 更改用户的状态
	 */
	public boolean updateState(Integer uAUId, int stateId) {
		return userApintmentUserDao.updateState(uAUId, stateId);
	}

	/**
	 * 删除已拒绝后的记录
	 * 
	 * @param uAUId
	 * @return
	 */
	public boolean delete(Integer uAUId) {
		return userApintmentUserDao.delete(uAUId);
	}

	public int messgSum(int userId) {
		return userApintmentUserDao.userAMessgSum(userId) + userApintmentUserDao.userBMessgSum(userId)
				+ userApintmentUserDao.responseIdA(userId) + userApintmentUserDao.responseIdB(userId)
				+ userApintmentUserDao.responseTeamIdA(userId) + userApintmentUserDao.responseTeamIdB(userId);
	}

}
