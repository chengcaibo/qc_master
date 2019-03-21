package com.qichong.dao;

import com.qichong.entity.OrderComment;

/**
 * 私有广告表
 * 
 * @创建人 孙建雷
 */
public interface OrderCommentDao {

	/** 插入一条订单 */
	int insertOne(OrderComment orderComment);

	/** 修改一条订单 */
	int updateOne(OrderComment orderComment);

}
