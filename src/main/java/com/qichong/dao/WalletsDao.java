package com.qichong.dao;

import com.qichong.entity.Wallets;

/**
 * 钱包实体类接口
 *
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2018年9月26日15:11:18
 */
public interface WalletsDao {

	/** 根据userId查询 */
	Wallets selectOneByUserId(int userId);

	/** 插入一条新数据 */
	int insertOne(Wallets wallets);

	/** 更新一条数据 */
	int updateOne(Wallets wallets);

}
