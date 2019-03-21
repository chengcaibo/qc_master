package com.qichong.dao;

import java.util.List;

import com.qichong.entity.OrderInfo;
import com.qichong.model.Filters;
import com.qichong.model.OrderStatusCount;

/**
 * 私有广告表
 * 
 * @创建人 孙建雷
 */
public interface OrderInfoDao {

	/** 查询所有没有在24小时内确认的订单 */
	List<OrderInfo> selectAllNotConfirmOrderId();

	/** 查询所有的订单并筛选 */
	List<OrderInfo> selectAllByFilter(Filters filters);

	/** 查询出用户的评分 */
	Double selectAvgScore(int userId);

	/** 查询小程序模板消息的TemplateId */
	OrderInfo selectTemplateId(String orderId);

	/** 查询订单状态数量 */
	OrderStatusCount selectStatusCount(Filters filters);

	/** 插入一条订单 */
	int insertOne(OrderInfo orderInfo);

	/** 修改一条订单 */
	int updateOne(OrderInfo orderInfo);

}
