package com.qichong.dao;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * Share 持久层
 * 
 * @创建人 徐龙洋
 * @修改人 孙建雷
 * @修改时间 2018-11-01
 */
public interface ShareDao {

	int addShareNum(@Param("user_id") Integer user_id, @Param("yaoqingma") String yaoqingma);

	int addYaoqingMoney(@Param("user_id") Integer user_id);

	int queryAddBalanceUserid(@Param("yaoqingma") String yaoqingma, @Param("user_id") Integer user_id);

	int queryAddBalanceUseridCount(@Param("yaoqingma") String yaoqingma, @Param("user_id") Integer user_id);

}
