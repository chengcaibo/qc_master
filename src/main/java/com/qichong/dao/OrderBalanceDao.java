package com.qichong.dao;

import com.qichong.entity.OrderBalance;

/**
 * 余额订单表
 * 
 * @author 孙建雷
 */
public interface OrderBalanceDao {

	/** 根据orderId查询订单 */
	OrderBalance selectByOrderId(String orderId);

	/** 插入一条订单 */
	int insertOne(OrderBalance orderBalance);

}
