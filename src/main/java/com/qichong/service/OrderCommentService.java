package com.qichong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.OrderCommentDao;
import com.qichong.entity.OrderComment;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.util.web.ServletUtil;

/**
 * 订单评价Service层
 *
 * @创建人 孙建雷
 * @创建时间 2018年9月2日23:40:02
 */
@Service
public class OrderCommentService {

	@Autowired
	OrderCommentDao orderCommentDao;

	public JSONResult addComment(String orderId, Integer userId, Integer score, String comment) {
		if (userId == null || score == null || ServletUtil.isEmpty(orderId, comment) > 0) {
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数");
		}
		OrderComment temp = new OrderComment(orderId);
		temp.setUserId(userId);
		temp.setScore(score);
		temp.setCommentContent(comment);
		temp.setStateId(1);

		if (orderCommentDao.insertOne(temp) > 0) {
			return JSONResult.builder(RetEnum.SUCCESS);
		}
		return JSONResult.builder(RetEnum.ERROR, "未知错误");
	}

}
