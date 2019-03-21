package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.WalletDetail;

/**
 * 钱包实体类接口
 *
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2018年9月26日15:11:18
 */
public interface WalletDetailDao {

	/** 根据userId查询 */
	List<WalletDetail> selectListByUserId(int userId);

	/** 插入一条新数据 */
	int insertOne(WalletDetail walletDetail);

	/** 更新一条数据 */
	// int updateOne(WalletDetail wallets);

	/** 删除一条数据 */
	int deleteOne(@Param("id") int id, @Param("userId") int userId);

}
