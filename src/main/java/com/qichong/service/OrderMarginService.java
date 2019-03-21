package com.qichong.service;

import static com.qichong.util.web.ServletUtil.isEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qichong.dao.OrderGroupMarginDao;
import com.qichong.dao.OrderUserMarginDao;
import com.qichong.entity.GroupInfo;
import com.qichong.entity.OrderGroupMargin;
import com.qichong.entity.OrderMargin;
import com.qichong.entity.OrderUserMargin;
import com.qichong.enums.MarginPrice;
import com.qichong.enums.OrderPrefix;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.util.Utils;
import com.qichong.util.wechat.mini.WeChatMiniPayUtil;
import com.qichong.util.wechat.mini.WeChatMiniUtil;

@Service
public class OrderMarginService {

	@Autowired
	OrderUserMarginDao userMarginDao;
	@Autowired
	OrderGroupMarginDao groupMarginDao;
	@Autowired
	GroupInfoServcie ginfoService;

	/* = = = = = = = = UserId = = = = = = = */

	/** 根据 userId 查询 */
	public List<OrderUserMargin> queryByUserId(int userId) {
		return userMarginDao.selectByUserId(userId);
	}

	/** 根据 userId 查询，并去掉敏感参数 */
	public OrderUserMargin queryMarginByUserId(int userId) {
		OrderUserMargin oum = userMarginDao.selectNoRefundByUserId(userId);
		if (oum == null)
			return null;
		oum.setRefundId(null);
		oum.setPrepayId(null);
		oum.setTransactionId(null);
		return oum;
	}

	/** 用户保证金退款 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult refundWithUser(int userId) {
		// 查询出订单详情
		OrderUserMargin oum = userMarginDao.selectNoRefundByUserId(userId);
		if (oum == null)
			return JSONResult.builder(RetEnum.ERROR, "订单不存在，可能是用户并未充值保证金，无法退还");
		else if (oum.getRefunded()) {
			return JSONResult.builder(RetEnum.ERROR, "您的保证金已全额退款");
		}
		String openId = oum.getOrderId();
		int totalFee = (int) (oum.getMargin() * 100);
		int refundFee = (int) (oum.getMargin() * 100);
		String refundDesc = "用户主动申请退还保证金";
		// 发起请求
		String refundId = WeChatMiniPayUtil.requestRefund(openId, totalFee, refundFee, refundDesc);
		if (refundId == null)
			return JSONResult.builder(RetEnum.ERROR, "发起退款请求失败，请稍后再试");
		// 将退款单号存入数据库
		OrderUserMargin updateOum = new OrderUserMargin(userId);
		updateOum.setRefundId(refundId);
		updateOum.setRefunded(true);
		return this.updateOne(updateOum);
	}

	/** 用户下单 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult placeWithUser(int userId, String jsCode, String offerCode) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 判断是否已存在未超时的订单
			OrderUserMargin queryOum = userMarginDao.selectNoRefundByUserId(userId);
			if (queryOum != null) {
				// 判断是否已成功支付
				if (queryOum.getTransactionId() == null) {
					resultMap.put("orderId", queryOum.getOrderId());
					resultMap.put("prepayId", queryOum.getPrepayId());
					resultMap.put("paymentParams", WeChatMiniPayUtil.getClientSign(queryOum.getPrepayId()));
					return JSONResult.builder(RetEnum.SUCCESS).setResult(resultMap);
				} else {
					return JSONResult.builder(RetEnum.ERROR, "已完成支付保证金，无需重复支付！");
				}
			}
			// 发起新订单
			Map<String, String> maps = WeChatMiniUtil.codeToOpenId(jsCode);
			if (maps == null)
				return JSONResult.builder(RetEnum.ERROR, "jsCode无效或已过期");
			// 获取参数
			String openId = maps.get("openId");
			String body = "支付个人保证金";
			String orderId = Utils.createNewOrderId(OrderPrefix.USER_MARGIN); // outTradeNo

			OrderUserMargin oum = new OrderUserMargin(userId);
			oum.setOrderId(orderId);

			double totalFee = MarginPrice.PERSONAL.getPrice();
			if (String.valueOf(offerCode).equals("qc1318.com")) { // 是否开启了优惠
				totalFee = MarginPrice.TEST.getPrice(); // 测试金额
				oum.setOfferContent("管理员测试直接优惠299.99元");
				oum.setOfferMoney(299.99);
			}
			oum.setMargin(totalFee);
			
			String prepayId = WeChatMiniPayUtil.placeOrder(openId, body, orderId, totalFee);
			oum.setPrepayId(prepayId);

			// 向数据库中插入数据
			if (userMarginDao.insertOne(oum) > 0) {
				// 获取支付参数
				resultMap.put("orderId", orderId);
				resultMap.put("prepayId", prepayId);
				resultMap.put("paymentParams", WeChatMiniPayUtil.getClientSign(prepayId));
				return JSONResult.builder(RetEnum.SUCCESS).setResult(resultMap);
			}
			return JSONResult.builder(RetEnum.ERROR, "存储失败");
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, "服务器异常");
		}
	}

	/** 用户保证金充值成功 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult paymentSuccessWithUser(String orderId, String transactionId) {
		// 更新用户保证金表
		OrderUserMargin oum = new OrderUserMargin(orderId, transactionId);
		return this.updateOne(oum);
	}

	/* = = = = = = = = GroupId = = = = = = = */

	/** 根据 groupId 查询 */
	public List<OrderGroupMargin> queryByGroupId(int groupId) {
		return groupMarginDao.selectByGroupId(groupId);
	}

	/** 根据groupId查询已支付成功且未退款的保证金 */
	public List<OrderGroupMargin> queryNoRefundByGroupId(int groupId) {
		List<OrderGroupMargin> ogms = groupMarginDao.selectNoRefundByGroupId(groupId);
		return this.removeSensitiveParams(ogms);
	}

	/** 根据groupId查询已支付的总保证金数量 */
	public double queryMarginCountByGroupId(int groupId) {
		List<OrderGroupMargin> ogms = groupMarginDao.selectNoRefundByGroupId(groupId);
		double countMargin = 0;
		for (OrderGroupMargin ogm : ogms) {
			if (ogm.getTransactionId() != null)
				// 实际交付金额 + 优惠金额
				countMargin += (ogm.getMargin() + ogm.getOfferMoney());
		}
		return countMargin;
	}

	/** 去掉团队敏感参数 */
	public List<OrderGroupMargin> removeSensitiveParams(List<OrderGroupMargin> ogms) {
		if (ogms == null)
			return null;
		List<OrderGroupMargin> list = new ArrayList<OrderGroupMargin>();
		for (OrderGroupMargin ogm : ogms) {
			list.add(this.removeSensitiveParams(ogm));
		}
		return list;
	}

	/** 去掉团队敏感参数 */
	public OrderGroupMargin removeSensitiveParams(OrderGroupMargin ogm) {
		if (ogm == null)
			return null;
		ogm.setRefundId(null);
		ogm.setPrepayId(null);
		ogm.setTransactionId(null);
		return ogm;
	}

	/** 团体保证金退款 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult refundWithGroup(int groupId) {
		// 查询出订单详情
		List<OrderGroupMargin> ogms = groupMarginDao.selectNoRefundByGroupId(groupId);
		if (ogms.size() == 0)
			return JSONResult.builder(RetEnum.ERROR, "订单不存在，可能是并未充值保证金，无法退还");
		int refundedCount = 0;
		int errorCount = 0;
		int successCount = 0;
		for (OrderGroupMargin ogm : ogms) {
			if (ogm.getRefunded()) {
				refundedCount++;
				continue;
			}
			String openId = ogm.getOrderId();
			int totalFee = (int) (ogm.getMargin() * 100);
			int refundFee = (int) (ogm.getMargin() * 100);
			String refundDesc = "团队主动申请退还保证金";
			// 发起请求
			String refundId = WeChatMiniPayUtil.requestRefund(openId, totalFee, refundFee, refundDesc);
			if (refundId == null) {
				errorCount++;
				continue;
			}
			// 将退款单号存入数据库
			OrderGroupMargin updateOgm = new OrderGroupMargin(groupId);
			updateOgm.setRefundId(refundId);
			updateOgm.setRefunded(true);
			JSONResult jr = this.updateOne(updateOgm);
			if (jr.getRet() == RetEnum.SUCCESS.getValue()) {
				successCount++;
			} else {
				errorCount++;
			}
		}
		if (refundedCount == ogms.size()) {
			return JSONResult.builder(RetEnum.ERROR, "您的保证金已全额退款");
		}
		if (successCount == ogms.size()) {
			return JSONResult.builder(RetEnum.SUCCESS, "全部申请退款成功！");
		}
		String msg = String.format("部分订单申请退款失败，请稍后重试。总申请数：%d，成功数：%d，失败数：%d", ogms.size(), successCount, errorCount);
		return JSONResult.builder(RetEnum.ERROR, msg);
	}

	/** 团体补差价 */
	public JSONResult makeupTheDiffWithGroup(int groupId, int userId, String jsCode) throws Exception {
		// 查询团体信息
		GroupInfo ginfo = ginfoService.queryOneByGroupId(groupId);
		if (!ginfo.getUser().getId().equals(userId)) {
			return JSONResult.builder(RetEnum.AUTH_ERROR, "不是您创建的团体，您没有权限操作。");
		}
		double marginCount = this.queryMarginCountByGroupId(groupId);

		// 取出现在的价格
		if (marginCount < MarginPrice.STAGE1.getPrice()) {
			return this.placeMakeUpTheDiffWithGroup(groupId, userId, jsCode, MarginPrice.STAGE1);
		} else if (marginCount == MarginPrice.STAGE1.getPrice()) {
			return this.placeMakeUpTheDiffWithGroup(groupId, userId, jsCode, MarginPrice.STAGE2);
		} else if (marginCount == MarginPrice.STAGE2.getPrice()) {
			return this.placeMakeUpTheDiffWithGroup(groupId, userId, jsCode, MarginPrice.STAGE3);
		} else if (marginCount == MarginPrice.STAGE3.getPrice()) {
			return this.placeMakeUpTheDiffWithGroup(groupId, userId, jsCode, MarginPrice.STAGE4);
		} else if (marginCount == MarginPrice.STAGE4.getPrice()) {
			return this.placeMakeUpTheDiffWithGroup(groupId, userId, jsCode, MarginPrice.STAGE5);
		} else if (marginCount == MarginPrice.STAGE5.getPrice()) {// 最高价位
			return JSONResult.builder(RetEnum.ERROR, "您已交付最高价位的保证金，无需再进行补差价操作！");
		}
		return JSONResult.builder(RetEnum.ERROR, "系统出现异常，请联系客户或稍后重试");
	}

	/** 团体补差价保证金下单 */
	@Transactional(rollbackFor = Exception.class)
	private JSONResult placeMakeUpTheDiffWithGroup(int groupId, int userId, String jsCode, MarginPrice price)
			throws Exception {
		System.out.println("\n= = = = = = = = 补差价 = = = = = = = = = =");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 判断是否已存在未支付的订单
		OrderGroupMargin queryOgm = groupMarginDao.selectNoPaymentByGroupId(groupId);
		if (queryOgm != null) {
			resultMap.put("orderId", queryOgm.getOrderId());
			resultMap.put("prepayId", queryOgm.getPrepayId());
			resultMap.put("paymentParams", WeChatMiniPayUtil.getClientSign(queryOgm.getPrepayId()));

			System.out.println("当前已存在未支付的订单，直接返回，OrderId：" + queryOgm.getOrderId());

			return JSONResult.builder(RetEnum.SUCCESS).setResult(resultMap);
		}

		// 取出所有的团队保证金订单
		// List<OrderGroupMargin> ogms = this.queryByGroupId(groupId);
		// 算出差价
		double margin = this.queryMarginCountByGroupId(groupId);
		double diffMargin = price.getPrice() - margin;

		System.out.println("当前已交付：" + margin);
		System.out.println("还需交付差价：" + diffMargin);

		if (diffMargin <= 0) {
			return JSONResult.builder(RetEnum.ERROR, "您已交付最高价位的保证金，无需再进行补差价操作！");
		}

		// 发起新订单
		Map<String, String> maps = WeChatMiniUtil.codeToOpenId(jsCode);
		if (maps == null)
			return JSONResult.builder(RetEnum.ERROR, "jsCode无效或已过期");
		// 获取参数
		String openId = maps.get("openId");
		String body = "支付团队保证金";
		String orderId = Utils.createNewOrderId(OrderPrefix.GROUP_MARGIN); // outTradeNo

		double totalFee = diffMargin;

		// double totalFee = MarginPrice.TEST.getPrice(); // 团队保证金测试金额
		// double offerMoney = (diffMargin - totalFee);
		// String offerContent = "管理员测试，直接优惠" + offerMoney + "元！";
		// System.out.println("由于测试实际交付：" + totalFee);

		// double totalFee = diffMargin;

		String prepayId = WeChatMiniPayUtil.placeOrder(openId, body, orderId, totalFee);

		// 存入数据库
		OrderGroupMargin ogm = new OrderGroupMargin(groupId);
		ogm.setOrderId(orderId);
		ogm.setMargin(totalFee);
		ogm.setPrepayId(prepayId);
		// ogm.setOfferMoney(offerMoney);
		// ogm.setOfferContent(offerContent);
		if (groupMarginDao.insertOne(ogm) > 0) {
			// 获取支付参数
			resultMap.put("orderId", orderId);
			resultMap.put("prepayId", prepayId);
			resultMap.put("paymentParams", WeChatMiniPayUtil.getClientSign(prepayId));
			return JSONResult.builder(RetEnum.SUCCESS).setResult(resultMap);
		}
		return JSONResult.builder(RetEnum.ERROR, "存储失败");
	}

	/** 团体保证金充值成功 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult paymentSuccessWithGroup(String orderId, String transactionId) {
		// 更新用户保证金表
		OrderGroupMargin ogm = new OrderGroupMargin(orderId, transactionId);
		return this.updateOne(ogm);
	}

	/* = = = = = = = = Common = = = = = = = */

	/** 更新数据，必填 groupId或userId或orderId */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult updateOne(OrderMargin om) {
		int line = 0;
		// 类型判断
		if (om instanceof OrderUserMargin) {
			OrderUserMargin oum = (OrderUserMargin) om;
			if (oum.getUserId() == null && isEmpty(oum.getOrderId()))
				return JSONResult.builder(RetEnum.PARAM_LACK, "缺少必要的参数");
			line += userMarginDao.updateOne(oum);
		} else if (om instanceof OrderGroupMargin) {
			OrderGroupMargin ogm = (OrderGroupMargin) om;
			if (ogm.getGroupId() == null && isEmpty(ogm.getOrderId()))
				return JSONResult.builder(RetEnum.PARAM_LACK, "缺少必要的参数");
			line += groupMarginDao.updateOne(ogm);
		} else {
			return JSONResult.builder(RetEnum.PARAM_ERROR, "参数类型错误");
		}
		// 判断受影响的行数
		if (line > 0)
			return JSONResult.builder(RetEnum.SUCCESS);
		else
			return JSONResult.builder(RetEnum.ERROR, "没有权限或缺少必要的参数");
	}

}
