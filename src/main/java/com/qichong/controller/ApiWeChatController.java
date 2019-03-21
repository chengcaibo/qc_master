package com.qichong.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.gson.JsonObject;
import com.qichong.dao.OrderBalanceDao;
import com.qichong.entity.OrderBalance;
import com.qichong.enums.OrderPrefix;
import com.qichong.model.JSONResult;
import com.qichong.service.OrderMarginService;
import com.qichong.service.OrderService;
import com.qichong.service.WalletService;
import com.qichong.util.Utils;
import com.qichong.util.XMLUtil;
import com.qichong.util.web.ServletUtil;
import com.qichong.util.wechat.WeChatGetWXPay;

/** 微信API地址 */
@RestController()
@RequestMapping(path = "/api/wechat", produces = ServletUtil.JSON_PRODUCES)// NOT_API
public class ApiWeChatController {

	@Autowired
	OrderBalanceDao orderBalanceDao;
	@Autowired
	WalletService walletService;
	@Autowired
	OrderService orderService;

	@Autowired
	OrderMarginService marginService;

	/** 通知地址返回值 */
	private final String NOTIFY_SUCCESS = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
	private final String NOTIFY_FAIL = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[FAIL]]></return_msg></xml>";

	/** 微信支付通知地址 */
	@RequestMapping(path = "/pay/notify", method = RequestMethod.POST, produces = "text/xml")// NOT_API
	@ResponseBody
	public String doPayNotifyFixXXE(HttpServletRequest request) {
		try {

			System.out.println("\n = = = = = = = = = = = = = = = = =");
			System.out.println("请求类型: 微信支付回调，解决了XXE问题");
			System.out.println("请求时间: " + Utils.formatDate(new Date()));

			// 获取POST消息体
			BufferedReader in = request.getReader();
			String line, result = "";
			while ((line = in.readLine()) != null) {
				result += line + "\n";
			}
			in.close();

			 System.out.println("POST消息体：" + result);

			// 将XML转为Map
			Map<String, String> map = WXPayUtil.xmlToMap(result);

			WXPay wxpay = WeChatGetWXPay.getMiniWxPay;

			// 判断sign是否有效
			if (!wxpay.isPayResultNotifySignatureValid(map)) {
				System.out.println("签名不存在或无效，已拒绝访问。");
				System.out.println("= = = = = = = = = = = = = = = = =");
				return NOTIFY_FAIL;
			}

			String resultCode = map.get("result_code");
			String returnCode = map.get("return_code");
			System.out.println("resultCode：" + resultCode);
			System.out.println("returnCode：" + returnCode);

			// 判断是否是成功
			if ("SUCCESS".equals(resultCode) && "SUCCESS".equals(returnCode)) {
				// 获取基础参数
				String orderId = map.get("out_trade_no");
				String prefix = OrderPrefix.getOrderPrefix(orderId);
				String transactionId = map.get("transaction_id");
				int totalFee = Integer.parseInt(map.get("total_fee"));

				System.out.println("orderId：" + orderId);
				System.out.println("orderIdPrefix：" + prefix);
				System.out.println("transactionId：" + transactionId);
				System.out.println("totalFee：" + totalFee);

				/* 执行逻辑 */

				JSONResult jr = new JSONResult().setMsg("初始状态");

				// 充值余额订单
				if (prefix.equals(OrderPrefix.BALANCE.getPrefix())) {
					OrderBalance oinfo = orderBalanceDao.selectByOrderId(orderId);
					if (oinfo != null) {
						boolean flag = walletService.plusBalance(oinfo.getUserId(), orderId, oinfo.getMoney(),
								transactionId, "充值余额");
						jr = JSONResult.returnFlag(flag);
						System.out.println("成功给userId:" + oinfo.getUserId() + "充值了" + oinfo.getMoney() + "元！");
					}

					// 团队保证金订单
				} else if (prefix.equals(OrderPrefix.GROUP_MARGIN.getPrefix())) {
					jr = marginService.paymentSuccessWithGroup(orderId, transactionId);
					System.out.println("团体订单号：" + orderId + "，充值" + (totalFee / 100.0) + "元保证金成功！");

					// 用户保证金订单
				} else if (prefix.equals(OrderPrefix.USER_MARGIN.getPrefix())) {
					jr = marginService.paymentSuccessWithUser(orderId, transactionId);
					System.out.println("用户订单号：" + orderId + "，充值" + (totalFee / 100.0) + "元保证金成功！");
					// 预约订单支付
				} else {
					jr = orderService.paymentSuccess(orderId);
					System.out.println("预约订单号：" + orderId + "，预约付款成功（" + (totalFee / 100.0) + "元）");
				}

				System.out.println("JSONResult：" + jr.toJSON());
			}
			System.out.println("= = = = = = = = = = = = = = = = =");

			return NOTIFY_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return NOTIFY_FAIL;
		}
	}

	/** 微信支付通知地址 */
	// @RequestMapping(path = "/pay/notify", method = RequestMethod.POST,
	// produces = "text/xml")
	// @ResponseBody
	public String doPayNotify(HttpServletRequest request) throws IOException {
		System.out.println("\n = = = = = = = = = = = = = = = = =");
		System.out.println("请求类型: 微信支付回调");
		System.out.println("请求时间: " + Utils.formatDate(new Date()));

		// 获取POST消息体
		BufferedReader in = request.getReader();
		String line, result = "";
		while ((line = in.readLine()) != null) {
			result += line + "\n";
		}
		in.close();
		// System.out.println("POST消息体：" + result);

		// 将XML转为JsonObject
		JsonObject jo = XMLUtil.xmlToJson(result);

		String resultCode = getAsString(jo, "result_code");
		String returnCode = getAsString(jo, "return_code");
		System.out.println("resultCode：" + resultCode);
		System.out.println("returnCode：" + returnCode);

		// 判断是否是成功
		if ("SUCCESS".equals(resultCode) && "SUCCESS".equals(returnCode)) {
			// 获取基础参数
			String orderId = getAsString(jo, "out_trade_no");
			String prefix = OrderPrefix.getOrderPrefix(orderId);
			String transactionId = getAsString(jo, "transaction_id");
			int totalFee = getAsInt(jo, "total_fee");
			System.out.println("orderId：" + orderId);
			System.out.println("orderIdPrefix：" + prefix);
			System.out.println("transactionId：" + transactionId);
			System.out.println("totalFee：" + totalFee);

			/* 执行逻辑 */

			JSONResult jr = new JSONResult().setMsg("初始化状态");

			// 充值余额订单
			if (prefix.equals(OrderPrefix.BALANCE.getPrefix())) {
				OrderBalance oinfo = orderBalanceDao.selectByOrderId(orderId);
				if (oinfo != null) {
					boolean flag = walletService.plusBalance(oinfo.getUserId(), orderId, oinfo.getMoney(),
							transactionId, "充值余额");
					jr = JSONResult.returnFlag(flag);
					System.out.println("成功给userId:" + oinfo.getUserId() + "充值了" + oinfo.getMoney() + "元！");
				}

				// 团队保证金订单
			} else if (prefix.equals(OrderPrefix.GROUP_MARGIN.getPrefix())) {
				jr = marginService.paymentSuccessWithGroup(orderId, transactionId);
				System.out.println("团体订单号：" + orderId + "，充值" + (totalFee / 100.0) + "元保证金成功！");

				// 用户保证金订单
			} else if (prefix.equals(OrderPrefix.USER_MARGIN.getPrefix())) {
				jr = marginService.paymentSuccessWithUser(orderId, transactionId);
				System.out.println("用户订单号：" + orderId + "，充值" + (totalFee / 100.0) + "元保证金成功！");
				// 预约订单支付
			} else {
				jr = orderService.paymentSuccess(orderId);
				System.out.println("预约订单号：" + orderId + "，预约付款成功（" + (totalFee / 100.0) + "元）");
			}

			System.out.println("JSONResult：" + jr.toJSON());
		}
		System.out.println("= = = = = = = = = = = = = = = = =");

		// XMLUtil.

		return NOTIFY_SUCCESS;
	}

	private String getAsString(JsonObject jo, String name) {
		try {
			return jo.get(name).getAsString();
		} catch (Exception e) {
			return null;
		}
	}

	private int getAsInt(JsonObject jo, String name) {
		return jo.get(name).getAsInt();
	}

	// 付款成功后返回：
	// = = = = = = = = = = = = = = = = =
	// 请求时间: 2018-10-10 23:59:50
	// ContentType: text/xml
	// ContextPath:
	// Method: POST
	// QueryString: null
	// Parameters:
	//
	// POST消息体：
	// <xml><appid><![CDATA[wx89ea7d0a3986529b]]></appid>
	// <bank_type><![CDATA[CFT]]></bank_type>
	// <cash_fee><![CDATA[100]]></cash_fee>
	// <fee_type><![CDATA[CNY]]></fee_type>
	// <is_subscribe><![CDATA[N]]></is_subscribe>
	// <mch_id><![CDATA[1497944322]]></mch_id>
	// <nonce_str><![CDATA[KjUaE4v1zn3DGwBmDzNGFNuTWVCqvpcf]]></nonce_str>
	// <openid><![CDATA[oezAI0YPdJrGUXjYmG55yi9BRVP8]]></openid>
	// <out_trade_no><![CDATA[20181010235930441949]]></out_trade_no>
	// <result_code><![CDATA[SUCCESS]]></result_code>
	// <return_code><![CDATA[SUCCESS]]></return_code>
	// <sign><![CDATA[86D4999607B1A4784084A29632985299]]></sign>
	// <time_end><![CDATA[20181010235949]]></time_end>
	// <total_fee>100</total_fee>
	// <trade_type><![CDATA[JSAPI]]></trade_type>
	// <transaction_id><![CDATA[4200000202201810109214192430]]></transaction_id>
	// </xml>

}
