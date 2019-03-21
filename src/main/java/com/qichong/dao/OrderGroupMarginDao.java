package com.qichong.dao;

import java.util.List;

import com.qichong.entity.OrderGroupMargin;

/** 用户保证金 */
public interface OrderGroupMarginDao {

	/** 查询数据 */
	List<OrderGroupMargin> selectByGroupId(int groupId);

	/** 根据groupId查询已支付且未退款的保证金 */
	List<OrderGroupMargin> selectNoRefundByGroupId(int groupId);

	/** 根据groupId查询未支付的保证金订单 */
	OrderGroupMargin selectNoPaymentByGroupId(int groupId);

	/** 更新一条数据 */
	int updateOne(OrderGroupMargin ogm);

	/** 插入一条数据 */
	int insertOne(OrderGroupMargin ogm);

}