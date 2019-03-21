package com.qichong.dao;

import java.util.List;

import com.qichong.entity.OrderUserMargin;

/** 用户保证金 */
public interface OrderUserMarginDao {

	/** 查询数据 */
	List<OrderUserMargin> selectByUserId(int userId);

	/** 根据userId查询未退款的保证金 */
	OrderUserMargin selectNoRefundByUserId(int userId);

	/** 更新一条数据 */
	int updateOne(OrderUserMargin oum);

	/** 插入一条数据 */
	int insertOne(OrderUserMargin oum);

}