package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.Users;
import com.qichong.model.Filters;

/**
 * 用户实体类接口
 *
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017年11月7日 10:31:36
 */
public interface UsersDao {

	/** 查询用户根据OpenId */
	// Users selectUserByWxOpenId(String openId);

	/**
	 * select users 表，根据userId查询
	 * 
	 * @param id
	 * @return
	 */
	Users selectUserById(int id);

	/**
	 * select users 表，根据userId查询，可以有 多个userId
	 */
	List<Users> selectUsersById(@Param("ids") int... ids);

	/** 修改用户 */
	int updateUser(Users user);

	/**
	 * 执行Select count(1)，根据表名
	 * 
	 * @param tableName
	 *            表名
	 * @return 返回该表的总数
	 */
	int selectCountByTableName(@Param("tn") String tn);

	/**
	 * 执行Select count(1)，根据表名，状态
	 * 
	 * @param Filters里面有name(表名)stateId(状态)
	 *            表名
	 * @return 返回该表的总数
	 */
	int selectCountByTableNameAndState(Filters filters);

	Users loginWithTelephone(String telephone);

	/**
	 * 微信登录
	 */
	Users loginWeChat(@Param("openId") String openId, @Param("unionId") String unionId);

	/**
	 * 个人登陆
	 */
	Users loginPersonal(Users user);

	/**
	 * 企业登陆
	 */
	Users loginEnterprise(Users user);

	/**
	 * Cookie登录
	 */
	Users cookieLogin(Users user);

	/**
	 * 新增用户
	 */
	int insertOneAndGetId(Users user);

	/**
	 * 查询用户类型type
	 */
	Users queryTypeId(int userId);

	/**
	 * 修改密码
	 * 
	 * @param user
	 * @return
	 */
	int modifyPassword(@Param("id") int id, @Param("password") String password);

	/**
	 * 查询已注册的用户数量
	 * 
	 * @return
	 */
	int queryCountUser();
}
