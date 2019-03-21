package com.qichong.service;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qichong.entity.OrderInfo;
import com.qichong.entity.State;
import com.qichong.entity.UserInfo;
import com.qichong.enums.StateEnum;
import com.qichong.util.wechat.mini.WeChatTemplateMessageUtil;

@Service
public class TaskService {

	@Autowired
	OrderService orderService;
	@Autowired
	UsersService usersService;
	@Autowired
	UserInfoService userInfoService;

	@Autowired
	WalletService walletService;

	/**
	 * 定时关闭没有在24小内确认的订单，并将支付的金额返还到原账户中 
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update24HourNotConfirmOrderAndBack() {
		try {
			List<OrderInfo> oinfos = orderService.selectAllNotConfirmOrderId();
			for (OrderInfo orderInfo : oinfos) {
				// 更新订单状态
				OrderInfo oinfo = new OrderInfo(orderInfo.getOrderId());
				oinfo.setOrderStatus(new State(StateEnum.ORDER_CLOSED));
				oinfo.setReason("对方没有在24小内响应，已自动关闭。");
				oinfo.setCloseTime(new Date());
				orderInfo.setCloseTime(new Date());
				orderService.updateOrder(oinfo);
				// 更新钱包余额
				String reason = "订单超时退款";
				walletService.plusBalance(orderInfo.getUserId(), orderInfo.getOrderId(), orderInfo.getPayment(),
						reason);

				// 获取用户信息并发送模板消息
				UserInfo userInfo = userInfoService.queryUserInfo(orderInfo.getUserId());
				String openId = userInfo.getUser().getWxOpenId();
				String param = "orderId=" + oinfo.getOrderId() + "&type=a";

				String url = "/pages/my/orders?" + param;
				String encode = url;
				try {
					encode = URLEncoder.encode(url, "UTF-8");
				} catch (Exception e) {
				}
				String page = "/pages/share/share?path=" + encode;

				String tempRes = WeChatTemplateMessageUtil.sendOrderClosed(openId, orderInfo, page,
						userInfo.getNickName());

				System.out.println("[DEBUG:TIMED_TASK_1]：发送模板消息结果：" + tempRes);

				System.out.print("[DEBUG:TIMED_TASK_1]：");
				System.out.println("退还到userId为" + orderInfo.getUserId() + "的余额里" + orderInfo.getPayment() + "元");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
