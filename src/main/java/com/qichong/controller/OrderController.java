package com.qichong.controller;

import static com.qichong.util.web.ServletUtil.isEmpty;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qichong.entity.AppintmentTime;
import com.qichong.entity.OrderInfo;
import com.qichong.entity.State;
import com.qichong.entity.UserInfo;
import com.qichong.entity.Users;
import com.qichong.entity.Wallets;
import com.qichong.enums.PaymentStatus;
import com.qichong.enums.RetEnum;
import com.qichong.enums.StateEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.model.OrderStatusCount;
import com.qichong.service.AppintmentTimeService;
import com.qichong.service.OrderService;
import com.qichong.service.UsersService;
import com.qichong.service.WalletService;
import com.qichong.token.LoginToken;
import com.qichong.util.Utils;
import com.qichong.util.sms.SMSUtil;
import com.qichong.util.web.ServletUtil;
import com.qichong.util.wechat.mini.WeChatMiniUtil;
import com.qichong.util.wechat.mini.WeChatMiniPayUtil;
import com.qichong.util.wechat.mini.WeChatTemplateMessageUtil;

@RestController
@RequestMapping(path = "/order", produces = ServletUtil.JSON_PRODUCES) // NOT_API
public class OrderController {

	@Autowired
	OrderService orderService;
	@Autowired
	UsersService usersService;
	@Autowired
	AppintmentTimeService timeService;
	@Autowired
	AppintmentTimeService appintmentTimeService;

	@Autowired
	WalletService walletService;

	/** 返回是否有未确定的新订单 */
	@RequestMapping(path = "/there-new", method = RequestMethod.GET)
	public String doThereNew(HttpSession session, LoginToken token) {
		// 判断是否已登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
		}
		Filters filters = Filters.builder().setGoodsId(loginUser.getId().toString()).setStateId(102);
		List<OrderInfo> list = orderService.query(filters);
		if (list.size() > 0) {
			OrderInfo last = list.get(list.size() - 1);
			return new JSONResult(RetEnum.SUCCESS).setResult(last).toJSON();
		} else {
			return new JSONResult(RetEnum.VALUE_EMPTY).toJSON();
		}
	}

	/**
	 * 根据UserId判断是否可以预约（下单），返回可预约时间
	 * 
	 * @throws Exception
	 */
	@RequestMapping(path = "/confirm-user/{userId}", method = RequestMethod.GET)
	public String doConfirmUser(@PathVariable("userId") Integer userId, HttpSession session, LoginToken token)
			throws Exception {
		if (userId == null) {
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数").toJSON();
		}
		// 判断是否已登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
		}
		// 判断是否是自己
		if (userId.equals(loginUser.getId())) {
			return JSONResult.builder(RetEnum.ERROR, "自己无法给自己下单！").toJSON();
		}
		// 判断对方的状态是接活还是咨询
		UserInfo uinfo = usersService.queryUserInfo(userId);
		if (uinfo.getJobType() != null && (uinfo.getJobType().getId() == 3 || uinfo.getJobType().getId() == 4)) {
			// // 判断是否有时间
			// Date endTime = new Date(1000 * 60 * 60 * 24 * 14);
			String now = Utils.formatDate(new Date(), "yyyy-MM-dd") + " 00:00:00";
			Date beginTime = Utils.parseDate(now);
			Filters timeFilter = Filters.builder().setUserId(userId).setBeginTime(beginTime);
			List<AppintmentTime> times = timeService.search(timeFilter);
			if (times.size() == 0) {
				return JSONResult.builder(RetEnum.ERROR, "对方没有可预约时间，暂时不能下单！").toJSON();
			} else {
				return JSONResult.builder(RetEnum.SUCCESS, "可以下单！").setList(times).toJSON();
			}
		} else {
			return JSONResult.builder(RetEnum.ERROR, "对方停止了接活，暂时不能下单！").toJSON();
		}

	}

	/** 根据UserId查询订单，查询的是我下的订单 */
	@RequestMapping(path = "/user/self")
	public String doQueryByUserIdSelf(Filters filters, HttpSession session, LoginToken token) {
		filters = Filters.valueOf(filters);// 非空处理
		// 需要登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
		}
		return this.doQueryByUserId(loginUser.getId(), filters);
	}

	/** 根据GoodsId查询订单，查询的是别人预约我的订单 */
	@RequestMapping(path = "/goods/self")
	public String doQueryByGoodsIdSelf(Filters filters, HttpSession session, LoginToken token) {
		filters = Filters.valueOf(filters);// 非空处理
		// 需要登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
		}
		return this.doQueryByGoodsId(loginUser.getId().toString(), filters);
	}

	/** 根据UserId查询订单 */
	@RequestMapping(path = "/user/{userId}")
	public String doQueryByUserId(@PathVariable("userId") Integer userId, Filters filters) {
		filters = Filters.valueOf(filters);// 非空处理
		if (userId == null)
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数").toJSON();
		filters.setUserId(userId);
		List<OrderInfo> list = orderService.queryListByUserId(filters);
		return JSONResult.builder(RetEnum.SUCCESS).setList(list).toJSON();
	}

	/** 根据GoodsId查询订单 */
	@RequestMapping(path = "/goods/{goodsId}")
	public String doQueryByGoodsId(@PathVariable("goodsId") String goodsId, Filters filters) {
		filters = Filters.valueOf(filters);// 非空处理
		if (goodsId == null)
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数").toJSON();
		filters.setGoodsId(goodsId);
		List<OrderInfo> list = orderService.queryListByUserId(filters);
		return JSONResult.builder(RetEnum.SUCCESS).setList(list).toJSON();
	}

	/** 根据OrderId查询订单 */
	@RequestMapping(path = "/{orderId}", method = RequestMethod.GET)
	public String doQueryByOrderId(@PathVariable("orderId") String orderId, Filters filters) {
		filters = Filters.valueOf(filters);
		if (orderId == null)
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数").toJSON();
		filters.setOrderId(orderId);
		OrderInfo result = orderService.queryOneByOrderId(filters);
		return new JSONResult(RetEnum.SUCCESS).setResult(result).toJSON();
	}

	/** 统计我的订单数量 */
	@RequestMapping(path = "/status-count/user/{userId}", method = RequestMethod.GET)
	public String doStatusCountUser(@PathVariable("userId") Integer userId) {
		if (userId == null)
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数").toJSON();
		try {
			OrderStatusCount count = orderService.queryOrderStatusCountByUserId(userId);
			return JSONResult.builder(RetEnum.SUCCESS).setResult(count).toJSON();
		} catch (Exception e) {
			Throwable th = Utils.isQCError(e);
			if (th != null)
				return JSONResult.builder(RetEnum.ERROR, th.getMessage()).toJSON();
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, "系统异常").toJSON();
		}
	}

	/** 根据UserId统计订单数量 */
	@RequestMapping(path = "/status-count/user/self", method = RequestMethod.GET)
	public String doStatusCountUserSelf(HttpSession session, LoginToken token) {
		// 需要登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null)
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
		return this.doStatusCountUser(loginUser.getId());
	}

	/** 根据GoodsId统计订单数量 */
	@RequestMapping(path = "/status-count/goods/{goodsId}", method = RequestMethod.GET)
	public String doStatusCountGoods(@PathVariable("goodsId") Integer goodsId) {
		if (goodsId == null)
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数").toJSON();
		try {
			OrderStatusCount count = orderService.queryOrderStatusCountByGoodsId(goodsId);
			return new JSONResult(RetEnum.SUCCESS).setResult(count).toJSON();
		} catch (Exception e) {
			Throwable th = Utils.isQCError(e);
			if (th != null)
				return JSONResult.builder(RetEnum.ERROR, th.getMessage()).toJSON();
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, "系统异常").toJSON();
		}
	}

	/** 根据GoodsId统计订单数量 */
	@RequestMapping(path = "/status-count/complete/{userId}", method = RequestMethod.GET)
	public String doCountCompeteOrder(@PathVariable("userId") Integer userId) {
		if (userId == null)
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数").toJSON();
		try {
			int count = orderService.queryCompleteOrder(userId);
			return new JSONResult(RetEnum.SUCCESS).setResult(count).toJSON();
		} catch (Exception e) {
			Throwable th = Utils.isQCError(e);
			if (th != null)
				return JSONResult.builder(RetEnum.ERROR, th.getMessage()).toJSON();
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, "系统异常").toJSON();
		}
	}

	/** 统计别人预约我的订单数量 */
	@RequestMapping(path = "/status-count/goods/self", method = RequestMethod.GET)
	public String doStatusCountGoodsSelf(HttpSession session, LoginToken token) {
		// 需要登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null)
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
		return this.doStatusCountGoods(loginUser.getId());
	}

	/**
	 * 下单接口
	 * 
	 * @param goodsId
	 *            商品ID，一般是被预约人的userId，必填
	 * @param payment
	 *            付款金额，单位是元，必填
	 * @param orderType
	 *            订单类型，0用户订单、1工具订单、2需求订单，一般填写0，必填
	 * @param remarks
	 *            备注，可空
	 * @param formId
	 *            formId，小程序字段，其他APP不填，可空
	 * @param beginTime
	 *            用户选择可预约时间的开始时间，必填
	 * @param endTime
	 *            用户选择可预约时间的结束时间，必填
	 * @return 下单成功后返回订单号（orderId）和是否需要付款（needPayment）
	 * @throws ParseException
	 */
	@RequestMapping(path = "/place", method = RequestMethod.POST)
	public String doOrderPlace(OrderInfo orderInfo, String beginTime, String endTime, String ids, String all,
			HttpSession session, LoginToken token) throws ParseException {
		// 需要登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
		}
		if (isEmpty(beginTime, endTime) > 0) {
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数").toJSON();
		} else {
			try {
				Date begin = Utils.parseDate(beginTime);
				Date end = Utils.parseDate(endTime);
				orderInfo.setMarkBeginTime(begin);
				orderInfo.setMarkEndTime(end);
			} catch (ParseException e) {
				return JSONResult.builder(RetEnum.PARAM_ERROR, "beginTime或endTime的格式不正确，应为 yyyy-MM-dd HH:mm:ss 格式")
						.toJSON();
			}
		}
		// 预约时间
		addReservationTwo(orderInfo.getGoodsId(), ids, all);
		// 添加预约记录
		orderInfo.setUserId(loginUser.getId());
		return orderService.newOrder(orderInfo).toJSON();
	}

	public void addReservationTwo(Integer userB, String ids, String all) {

		// 已选中的预约时间
		List<Map<String, Object>> idss = Utils.jsonToListMap(ids);

		List<AppintmentTime> listTime = new ArrayList<AppintmentTime>();
		for (Map<String, Object> map : idss) {
			String idStr = map.get("id").toString();
			if (!idStr.equalsIgnoreCase("on")) {
				int id = Integer.parseInt(idStr);
				boolean flag = false;
				for (int i = 0; i < listTime.size(); i++) {
					AppintmentTime appintmentTime = listTime.get(i);
					if (appintmentTime.getId() == id) {
						appintmentTime = jsonTojson(appintmentTime, map.get("value").toString());
						flag = true;
						break;
					}
				}
				if (!flag) {
					AppintmentTime tempTime = appintmentTimeService.queryOneById(id);
					tempTime = jsonTojson(tempTime, map.get("value").toString());
					listTime.add(tempTime);
				}
			}
		}

		// 添加到数据库里面去
		for (AppintmentTime appintmentTime : listTime) {

			// 判断所有待选中的值
			Type type = new TypeToken<HashMap<String, String[]>>() {
			}.getType();
			Type typeArray = new TypeToken<String[]>() {
			}.getType();
			Map<String, String[]> alls = new Gson().fromJson(all, type);
			String[] allJson = alls.get(appintmentTime.getId().toString());
			if (allJson != null) {
				String[] selectedTimeArray = new Gson().fromJson(appintmentTime.getSelectedTime(), typeArray);
				// 判断待选中和已选中是否相等
				if (selectedTimeArray.length == allJson.length) {
					appintmentTime.setState(new State(3));
				}
			}
			appintmentTimeService.setSelectedTime(appintmentTime);
		}
	}

	private AppintmentTime jsonTojson(AppintmentTime appintmentTime, String value) {
		List<String> selectedTimes = this.jsonToListString(appintmentTime.getSelectedTime());
		selectedTimes.add(value);
		appintmentTime.setSelectedTime(new Gson().toJson(selectedTimes));
		return appintmentTime;
	}

	private List<String> jsonToListString(String json) {
		List<String> selectedTimes = new Gson().fromJson(json, new TypeToken<List<String>>() {
		}.getType());
		return selectedTimes;
	}

	/** 获取调起支付所需的参数 */
	@RequestMapping(path = "/get-payment-params", method = RequestMethod.GET)
	public String doOrderGetPaymentParams(String orderId, String jsCode, HttpSession session, LoginToken token) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("orderId", orderId);
		// 需要登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").setResult(resultMap).toJSON();
		}
		if (isEmpty(orderId)) {
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数").setResult(resultMap).toJSON();
		}
		// int userId = loginUser.getId();

		try {
			// 获取订单信息
			OrderInfo oinfo = orderService.queryOneByOrderId(Filters.builder().setOrderId(orderId));
			// 判断订单状态
			if (oinfo.getOrderStatus().getId() != StateEnum.ORDER_PENDING_PAYMENT.getId()) {
				return JSONResult.builder(RetEnum.ERROR, "订单无需支付").setResult(resultMap).toJSON();
			}
			String prepayId = oinfo.getPrepayId();
			// 判断是否已下单，没有下单就调用微信“统一下单”接口
			if (isEmpty(prepayId)) {
				// 获取openID
				String openId = WeChatMiniUtil.codeToOpenId(jsCode).get("openId");
				String body = "预约付款(" + oinfo.getOrderId() + ")";
				// 预支付交易会话标识
				prepayId = WeChatMiniPayUtil.placeOrder(openId, body, orderId, oinfo.getPayment());
			}
			if (prepayId != null) {
				resultMap.put("prepayId", prepayId);
				resultMap.put("paymentParams", WeChatMiniPayUtil.getClientSign(prepayId));
				// 更新数据库
				OrderInfo orderInfo = new OrderInfo(orderId);
				orderInfo.setPrepayId(prepayId);
				orderService.updateOrder(orderInfo);
				return JSONResult.builder(RetEnum.SUCCESS).setResult(resultMap).toJSON();
			}
			return JSONResult.builder(RetEnum.ERROR, "获取支付参数失败").setResult(resultMap).toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, e.getMessage()).setResult(resultMap).toJSON();
		}
	}

	/** 通过余额付款 */
	@RequestMapping(path = "/payment-balance", method = RequestMethod.POST)
	public String doOrderPaymentBalance(String orderId, String payment, HttpSession session, LoginToken token) {
		// 需要登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
		}
		if (isEmpty(orderId, payment) > 0) {
			return JSONResult.paramLack().toJSON();
		}
		// 获取该用户的余额
		Wallets wallet = walletService.queryOneMustHaveValue(loginUser.getId());
		try {
			double payment1 = Double.parseDouble(payment);
			double payment2 = wallet.getBalance();
			if (payment1 > payment2) {
				return JSONResult.builder(RetEnum.ERROR, "余额不足").toJSON();
			}
			if (walletService.lessBalance(loginUser.getId(), orderId, payment1, null, "预约付款")) {
				return JSONResult.builder(RetEnum.SUCCESS).toJSON();
			} else {
				return JSONResult.builder(RetEnum.ERROR, "付款失败，错误未知").toJSON();
			}
		} catch (java.lang.NumberFormatException e) {
			return JSONResult.builder(RetEnum.PARAM_ERROR, "payment参数必须是double类型").toJSON();
		} catch (Exception e) {
			return JSONResult.builder(RetEnum.EXCEPTION, e.getMessage()).toJSON();
		}

	}

	/**
	 * 付款成功后调用
	 * 
	 * @throws Exception
	 */
	@RequestMapping(path = "/payment-success", method = RequestMethod.POST)
	public String doPaymentSuccess(String orderId, String phone, String name, String time, HttpSession session,
			LoginToken token) throws Exception {
		// 需要登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
		} else if (isEmpty(orderId)) {
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数").toJSON();
		}
		OrderInfo order = new OrderInfo(orderId);
		order.setUserId(loginUser.getId());
		// 付款成功更改状态，并发送短信
		order.setPaymentStatus(PaymentStatus.ALREADY_PAID.getValue()); // 已付款
		order.setOrderStatus(new State(102));
		if (isEmpty(phone, name, time) == 0) {
			// 发送一条短信
			SMSUtil.sendNewOrder(phone, name, time);
		}
		return orderService.updateOrder(order).toJSON();
	}

	/**
	 * 拒绝订单
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(path = "/refuse", method = RequestMethod.POST)
	public String doRefuse(String orderId, String reason, String goodsName, HttpSession session, LoginToken token)
			throws Exception {
		// 需要登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
		} else if (isEmpty(orderId, goodsName) > 0) {
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数").toJSON();
		}
		OrderInfo order = new OrderInfo(orderId);
		order.setOrderStatus(new State(106)); // 已关闭
		order.setCloseTime(new Date()); // 关闭时间
		order.setReason(reason);
		JSONResult res = orderService.updateOrder(order);
		if (res.getRet() == 0) {
			/* 发送模板消息 */
			// 获取订单的详情
			OrderInfo oinfo = orderService.queryOneByOrderId(Filters.builder().setOrderId(orderId));
			// 获取用户信息
			String openId = usersService.queryByUserId(oinfo.getUserId()).getWxOpenId();
			String page = "/pages/share/share?path=" + URLEncoder.encode("/pages/my/orders?type=0", "UTF-8");
			String tempRes = WeChatTemplateMessageUtil.sendOrderRefuse(openId, oinfo, page, goodsName);
			System.out.println("发送模板消息结果：" + tempRes);
			// 金额返还到预约方
			String detailReason = oinfo.getReason() == null ? "对方未填写原因" : oinfo.getReason();
			detailReason = "订单拒绝退款：" + detailReason;
			walletService.plusBalance(oinfo.getUserId(), orderId, oinfo.getPayment(), detailReason);
		}
		return res.toJSON();
	}

	/**
	 * 响应订单
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(path = "/response", method = RequestMethod.POST)
	public String doResponse(String orderId, String goodsName, HttpSession session, LoginToken token) throws Exception {
		// 需要登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
		} else if (isEmpty(orderId, goodsName) > 0) {
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数").toJSON();
		}
		OrderInfo order = new OrderInfo(orderId);
		order.setOrderStatus(new State(103)); // 进行中
		order.setConfirmTime(new Date()); // 确认时间
		JSONResult res = orderService.updateOrder(order);
		if (res.getRet() == 0) {
			/* 发送模板消息 */
			// 获取订单的详情
			OrderInfo oinfo = orderService.queryOneByOrderId(Filters.builder().setOrderId(orderId));
			// 获取用户信息
			String openId = usersService.queryByUserId(oinfo.getUserId()).getWxOpenId();
			String page = "/pages/share/share?path=" + URLEncoder.encode("/pages/my/orders?type=0", "UTF-8");
			String tempRes = WeChatTemplateMessageUtil.sendOrderResponse(openId, oinfo, page, goodsName);
			System.out.println("发送模板消息结果：" + tempRes);
		}
		return res.toJSON();
	}

	/** 完成订单 */
	@RequestMapping(path = "/complete", method = RequestMethod.POST)
	public String doComplete(String orderId, HttpSession session, LoginToken token) {
		// 需要登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
		} else if (isEmpty(orderId)) {
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数").toJSON();
		}
		OrderInfo order = new OrderInfo(orderId);
		order.setOrderStatus(new State(104)); // 待评价
		order.setCompleteTime(new Date()); // 完成时间

		JSONResult res = orderService.updateOrder(order);
		if (res.getRet() == 0) {
			OrderInfo oinfo = orderService.queryOneByOrderId(Filters.builder().setOrderId(orderId));
			// 金额打款到被预约方
			walletService.plusBalance(oinfo.getGoodsId(), orderId, oinfo.getPayment(), "订单完成收款");
			walletService.lessBalance(oinfo.getGoodsId(), 1, "订单完成后系统抽取服务费");
		}
		return res.toJSON();
	}

	/** 评分订单 */
	@RequestMapping(path = "/score-add", method = RequestMethod.POST)
	public String doSocreAdd(String orderId, String goodsId, Integer score, String comment, HttpSession session,
			LoginToken token) {
		// 需要登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
		} else if (isEmpty(orderId, goodsId) > 0 || score == null) {
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数").toJSON();
		}
		try {
			return orderService.commentOrder(orderId, loginUser.getId(), score, comment).toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, e.getMessage()).toJSON();
		}
	}

}
