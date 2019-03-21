package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.qichong.entity.Theresponseteam;

/**
 * 团队响应记录Dao层
 * 
 * @创建人 徐佳文
 *
 */
public interface TheresponseteamDao {
	// 添加相应记录
	boolean addTheResponseteam(Theresponseteam theresponseteam);

	// 根据id删除响应信息
	boolean deleteTheResponseteam(int id);

	// 修改记录状态
	boolean updateTeam(@Param("id") int id, @Param("stateId") int stateId);

	// 查询此条记录是否被确认过
	int byResponseIsNo(@Param("groupId") int groupId, @Param("stateId") int stateId);

	// 查询是否响应过
	Integer byCount(@Param("teamId") Integer teamId);

	/**
	 * 个人响应个人发布的团队 响应人是登录者 我响应的
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	List<Theresponseteam> selectUserAAndUserTheResponseteam(@Param("id") int id, @Param("stateId") int stateId);

	/**
	 * 个人响应个人发布的团队 被响应人是登录者，也就是响应我的
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	List<Theresponseteam> selectUserAndUserBTheResponseteam(@Param("id") int id, @Param("stateId") int stateId);

	/**
	 * 企业响应个人发布的团队 响应人是登录者，也就是我响应的
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	List<Theresponseteam> selectEnterpriseAAndUserBTheResponseteam(@Param("id") int id, @Param("stateId") int stateId);

	/**
	 * 个人响应企业发布的团队 被响应人是登录者，也就是响应我的
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	List<Theresponseteam> selectUserAAndEnterpriseBTheResponseteam(@Param("id") int id, @Param("stateId") int stateId);

	/**
	 * 企业响应企业发布的团队 响应人是登录者，也就是我响应的
	 * 
	 * @param ids
	 * @param stateId
	 * @return
	 */
	List<Theresponseteam> selectEnterpriseAAndEnterpriseBTheResponseteam(@Param("id") int id,
			@Param("stateId") int stateId);

	/**
	 * 企业响应企业发布的团队 被响应人是登录者
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	List<Theresponseteam> selectEnterpriseAAndEnterpriseTheResponseteam(@Param("id") int id,
			@Param("stateId") int stateId);

	/**
	 * 个人响应企业发布的团队 响应者是登录者
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	List<Theresponseteam> selectByUserAAndEnterpriseBTheResponseteam(@Param("id") int id,
			@Param("stateId") int stateId);

	/**
	 * 个人响应企业发布的团队 被响应者是登录者
	 * 
	 * @param id
	 * @param stateId
	 * @return
	 */
	List<Theresponseteam> selectByUserBAndEnterpriseATheResponseteam(@Param("id") int id,
			@Param("stateId") int stateId);

}
