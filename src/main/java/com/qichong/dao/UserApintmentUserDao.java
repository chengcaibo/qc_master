package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.qichong.entity.UserApintmentUser;

/**
 * 用户约用户关系实体类接口
 */

public interface UserApintmentUserDao {
	
	/**
	 * 根据id查询预约成功后的信息
	 */
	List<UserApintmentUser> selUserApintment(Integer userIdB);
	
	
	/**
	 * 新增预约记录
	 */
	/*boolean insertReservation(@Param("userApintmentUser") UserApintmentUser userApintmentUser);*/
		boolean insertReservation(@Param("userApintmentUser")UserApintmentUser userApintmentUser);
		
	/**
	 * 根据用户id 查询用户预约记录
	 */
	List<UserApintmentUser> byUserIdQueryUserAppintmentUser(int userId);

	/**
	 * 返回list集合 查询被预约的记录
	 */
	List<UserApintmentUser> byUserIdQueryUserBeAppintment(int userId);
	/**
	 * 查询用户被预约次数
	 * @param userId
	 * @return
	 */
	int queryUserCount(int userId);
	/**
	 * 根据A用户Id 状态查询   吴志伟
	 * @param userAID
	 * @param stateId
	 * @return
	 */
	List<UserApintmentUser> byStateAndAId(@Param("userAId")int userAId,@Param("stateId")int stateId);
	/**
	 * 根据A用户Id 状态查询   吴志伟
	 * @param userAID
	 * @param stateId
	 * @return
	 */
	List<UserApintmentUser> byStateAndBId(@Param("userBId")int userBId,@Param("stateId")int stateId);
	/**
	 * 根据企业Id查询企业预约的人
	 * @param eId
	 * @param stateId
	 * @return 
	 */
	List<UserApintmentUser> byStateAndEId(@Param("eId")int eId,@Param("stateId")int stateId);
	/**
	 * 根据用户Id查询被企业约的记录
	 * @param userBId
	 * @param stateId
	 * @return
	 */
	List<UserApintmentUser> byStateAndUIdE(@Param("userBId")int userBId,@Param("stateId")int stateId);
	/**
	 * 更改记录的状态 吴志伟
	 * @param uAUId
	 * @param stateId
	 * @return
	 */
	boolean updateState(@Param("uAUId")int uAUId,@Param("stateId")int stateId);
	/**
	 * 删除未确认的预约记录  吴志伟
	 */
	boolean delete(int uAUId);
	/**
	 *  查询待操作数量
	 * @param userAId
	 * @return 
	 */
	int userAMessgSum(int userAId);
	int userBMessgSum(int userBId);
	int responseIdA(int userAId);
	int responseIdB(int userBId);
	int responseTeamIdA(int userAId) ;
	int responseTeamIdB(int userBId);
}
