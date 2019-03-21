package com.qichong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.TheresponseteamDao;
import com.qichong.entity.Theresponseteam;

@Service
public class TheResponseteamService {
	@Autowired
	TheresponseteamDao theresponseteamDao;

	/**
	 * 添加响应信息
	 */
	public boolean addTheResponseteam(Theresponseteam theresponseteam) {
		return theresponseteamDao.addTheResponseteam(theresponseteam);
	}

	/**
	 * 删除响应信息
	 */
	public boolean deleteTheResponseteam(int id) {
		return theresponseteamDao.deleteTheResponseteam(id);

	}
	/**
	 * 修改响应团队的记录状态
	 * @param id
	 * @param stateId
	 * @return
	 */
	public boolean uodateTeam(int id, int stateId){
		return theresponseteamDao.updateTeam(id, stateId);
	}

	/**
	 * 查询响应人数，已响应了几人
	 * 
	 * @param userId
	 * @param userBId
	 * @param teamId
	 * @return
	 */
	public Integer byCount(Integer teamId) {
		return theresponseteamDao.byCount(teamId);
	}

	/**
	/**
	 * 判断记录是否有确认过的
	 * @param groupId
	 * @return
	 */
	public int byResponseIsNo(Integer groupId){
		return theresponseteamDao.byResponseIsNo(groupId,13);
	}
	/**
	 * 个人响应个人发布的团队 响应人是登录者 我响应的
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	public List<Theresponseteam> selectUserAAndUserTheResponseteam(int id, int stateId) {
		return theresponseteamDao.selectUserAAndUserTheResponseteam(id, stateId);

	}

	/**
	 * 个人响应个人发布的团队 被响应人是登录者，也就是响应我的
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	public List<Theresponseteam> selectUserAndUserBTheResponseteam(int id, int stateId) {
		return theresponseteamDao.selectUserAndUserBTheResponseteam(id, stateId);
	}

	/**
	 * 企业响应个人发布的团队 响应人是登录者，也就是我响应的
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	public List<Theresponseteam> selectEnterpriseAAndUserBTheResponseteam(int id, int stateId) {
		return theresponseteamDao.selectEnterpriseAAndUserBTheResponseteam(id, stateId);

	}

	/**
	 * 个人响应企业发布的团队 被响应人是登录者，也就是响应我的
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	public List<Theresponseteam> selectUserAAndEnterpriseBTheResponseteam(int id, int stateId) {
		return theresponseteamDao.selectUserAAndEnterpriseBTheResponseteam(id, stateId);

	}

	/**
	 * 企业响应企业发布的团队 响应人是登录者，也就是我响应的
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	public List<Theresponseteam> selectEnterpriseAAndEnterpriseBTheResponseteam(int id, int stateId) {
		return theresponseteamDao.selectEnterpriseAAndEnterpriseBTheResponseteam(id, stateId);

	}

	/**
	 * 企业响应企业发布的团队 被响应人是登录者
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	public List<Theresponseteam> selectEnterpriseAAndEnterpriseTheResponseteam(int id, int stateId) {
		return theresponseteamDao.selectEnterpriseAAndEnterpriseTheResponseteam(id, stateId);
	}
	/**
	 * 个人响应企业发布的团队 响应者是登录者
	 * @param id
	 * @param stateId
	 * @return
	 */
	public List<Theresponseteam> selectByUserAAndEnterpriseBTheResponseteam(int id, int stateId) {
		return theresponseteamDao.selectByUserAAndEnterpriseBTheResponseteam(id, stateId);
	}
	/**
	 * 个人响应企业发布的团队 被响应者是登录者
	 * @param id
	 * @param stateId
	 * @return
	 */
	public List<Theresponseteam> selectByUserBAndEnterpriseATheResponseteam(int id, int stateId) {
		return theresponseteamDao.selectByUserBAndEnterpriseATheResponseteam(id, stateId);
	}
}
