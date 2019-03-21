package com.qichong.service;

import static com.qichong.util.web.ServletUtil.isEmpty;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qichong.dao.OrderInfoDao;
import com.qichong.entity.OrderInfo;
import com.qichong.entity.State;
import com.qichong.entity.UserInfo;
import com.qichong.entity.Users;
import com.qichong.enums.PaymentStatus;
import com.qichong.enums.RetEnum;
import com.qichong.enums.StateEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.model.OrderStatusCount;
import com.qichong.util.Utils;
import com.qichong.util.sms.SMSUtil;

/**
 * 订单Service层
 *
 * @创建人 孙建雷
 * @创建时间 2018年8月9日13:44:28
 */
@Service
public class OrderService {

	@Autowired
	OrderInfoDao orderInfoDao;

	@Autowired
	OrderCommentService orderCommentService;

	@Autowired
	UsersService usersService;
	@Autowired
	UserInfoService userInfoService;

	/** 查询所有没有在24小时内确认的订单 */
	public List<OrderInfo> selectAllNotConfirmOrderId() {
		return orderInfoDao.selectAllNotConfirmOrderId();
	}

	/** 搜索多条记录 */
	private List<OrderInfo> queryList(Filters filters) {
		return orderInfoDao.selectAllByFilter(filters);
	}

	/** 搜索一条记录 */
	private OrderInfo queryOne(Filters filters) {
		List<OrderInfo> list = this.queryList(filters);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	/** 搜索 */
	public List<OrderInfo> query(Filters filters) {
		return this.queryList(filters);
	}

	/** 根据UserId查询所有的订单信息 */
	public List<OrderInfo> queryListByUserId(Filters filters) {
		return this.queryList(filters);
	}

	/** 根据OrderId查询单个订单信息 */
	public OrderInfo queryOneByOrderId(Filters filters) {
		return this.queryOne(filters);
	}

	/** 根据OrderId查询单个订单信息 */
	public OrderInfo queryOneByOrderId(String orderId) {
		return this.queryOneByOrderId(Filters.builder().setOrderId(orderId));
	}

	/** 查询小程序模板消息的TemplateId */
	public OrderInfo queryTemplateId(String orderId) {
		return orderInfoDao.selectTemplateId(orderId);
	}

	/** 根据用户ID统计订单状态数量 */
	public OrderStatusCount queryOrderStatusCountByUserId(int id) {
		return this.queryOrderStatusCount("user_id", id);
	}

	/** 根据商品ID统计订单状态数量 */
	public OrderStatusCount queryOrderStatusCountByGoodsId(int id) {
		return this.queryOrderStatusCount("goods_id", id);
	}

	/** 统计订单状态数量 */
	private OrderStatusCount queryOrderStatusCount(String field, int id) {
		Filters filters = Filters.builder().setField(field).setId(id).setS101(true).setS102(true).setS103(true)
				.setS104(true).setS105(true).setS106(true);
		return orderInfoDao.selectStatusCount(filters);
	}

	/** 统计已完成的订单数量 */
	public int queryCompleteOrder(int userId) {
		Filters filters = Filters.builder().setField("goods_id").setId(userId).setS104(true).setS105(true);
		OrderStatusCount sc = orderInfoDao.selectStatusCount(filters);
		int count = sc.getS104() + sc.getS105();
		return count;

	}

	/** 新增一条订单 */
	public JSONResult newOrder(OrderInfo orderInfo) {
		// 非空判断
		if (orderInfo == null || orderInfo.getUserId() == null || orderInfo.getGoodsId() == null
				|| orderInfo.getPayment() == null || orderInfo.getOrderType() == null) {
			return new JSONResult(RetEnum.PARAM_LACK).setMsg("缺少参数");
		}
		// 默认值
		orderInfo.setOrderId(Utils.createNewOrderId());
		boolean NoNeedPayment = orderInfo.getPayment() <= 0; // 待付金额为0则无需付款
		orderInfo.setPaymentStatus(NoNeedPayment ? -1 : 0);
		orderInfo.setCreateTime(new Date());
		// 若无需付款状态直接改为等待中
		orderInfo.setOrderStatus(new State(NoNeedPayment ? 102 : 101));
		// 插入数据
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 向数据库中插入数据
			if (orderInfoDao.insertOne(orderInfo) > 0) {
				resultMap.put("orderId", orderInfo.getOrderId());
				resultMap.put("needPayment", !NoNeedPayment);
				return new JSONResult(RetEnum.SUCCESS).setResult(resultMap);
			} else {
				return new JSONResult(RetEnum.ERROR).setMsg("未知错误，写入数据失败。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONResult(RetEnum.EXCEPTION).setMsg(e.getMessage());
		}
	}

	@Transactional
	public JSONResult paymentSuccess(String orderId) {
		// 发送一条短信
		try {
			if (isEmpty(orderId)) {
				return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数");
			}
			// 查询出订单详情
			OrderInfo queryOrderInfo = this.queryOneByOrderId(orderId);
			String time = Utils.formatDate(queryOrderInfo.getMarkBeginTime(), "yyyy年M月d日 hh:mm:ss");
			// 查询出被预约人详情
			UserInfo uinfo = userInfoService.queryUserInfo(queryOrderInfo.getGoodsId());

			// 付款成功更改状态，并发送短信
			OrderInfo order = new OrderInfo(orderId);
			order.setPaymentStatus(PaymentStatus.ALREADY_PAID.getValue()); // 已付款
			order.setOrderStatus(new State(StateEnum.ORDER_WAITING)); // 等待中
			// order.setUserId(loginUser.getId());
			JSONResult jr = this.updateOrder(order);
			if (jr.getRet() == RetEnum.SUCCESS.getValue())
				SMSUtil.sendNewOrder(uinfo.getTelephone(), uinfo.getNickName(), time);
			return jr;
		} catch (Exception e) {
			return JSONResult.exception();
		}

	}

	/** 更新一条订单 */
	@Transactional
	public JSONResult updateOrder(OrderInfo orderInfo) {
		// 非空判断
		if (orderInfo == null || orderInfo.getOrderId() == null) {
			return new JSONResult(RetEnum.PARAM_LACK).setMsg("缺少参数");
		}
		// 更新数据
		if (orderInfoDao.updateOne(orderInfo) > 0) {
			return new JSONResult(RetEnum.SUCCESS);
		} else {
			return new JSONResult(RetEnum.AUTH_ERROR).setMsg("没有权限");
		}
	}

	/** 评价订单 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult commentOrder(String orderId, int userId, int score, String comment) {
		JSONResult jr = null;
		// 查询并判断订单状态
		OrderInfo oinfo = this.queryOneByOrderId(Filters.builder().setOrderId(orderId));
		int status = oinfo.getOrderStatus().getId();
		if (status != StateEnum.ORDER_TO_BE_EVALUATED.getId()) {
			return JSONResult.builder(RetEnum.ERROR, "订单不在待评论状态");
		}
		// 更新订单状态为已完成
		OrderInfo updateOinfo = new OrderInfo(orderId);
		updateOinfo.setOrderStatus(new State(StateEnum.ORDER_COMPLETED)); // 已完成
		jr = this.updateOrder(updateOinfo);
		if (jr.getRet() != RetEnum.SUCCESS.getValue())
			throw new RuntimeException(jr.getMsg()); // 主动回滚
		// 添加评论
		jr = orderCommentService.addComment(orderId, userId, score, comment);
		if (jr.getRet() != RetEnum.SUCCESS.getValue())
			throw new RuntimeException(jr.getMsg());// 主动回滚
		// 更新用户评分
		Integer goodsId = oinfo.getGoodsId();
		Double uScore = orderInfoDao.selectAvgScore(goodsId);
		UserInfo uinfo = new UserInfo(new Users(goodsId));
		uinfo.setScore(uScore);
		jr = userInfoService.changeUserInfo(uinfo);
		if (jr.getRet() != RetEnum.SUCCESS.getValue())
			throw new RuntimeException(jr.getMsg());// 主动回滚
		return JSONResult.builder(RetEnum.SUCCESS);
	}

}
