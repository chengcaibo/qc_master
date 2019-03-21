package com.qichong.util.wechat.mini;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.google.gson.JsonObject;
import com.qichong.enums.OrderPrefix;
import com.qichong.util.JSONUtil;
import com.qichong.util.Utils;
import com.qichong.util.XMLUtil;
import com.qichong.util.web.NetworkUtil;
import com.qichong.util.wechat.WeChatSSLNetworkUtil;

/** 微信支付辅助类 */
public class WeChatMiniPayUtil extends WeChatMini {

	/**
	 * 请求微信退款接口
	 * 
	 * @param openId
	 *            商户订单号
	 * @param totalFee
	 *            订单总金额
	 * @param refundFee
	 *            要退款的金额
	 * @param refundDesc
	 *            退款原因
	 * @return 返回商户退款单号（refund_fee）
	 */
	public static String requestRefund(String openId, int totalFee, int refundFee, String refundDesc) {
		try {
			JsonObject json = new JsonObject();
			json.addProperty("appid", APP_ID);
			json.addProperty("mch_id", Pay.MCH_ID);
			json.addProperty("nonce_str", Utils.randomString(32));
			json.addProperty("out_trade_no", openId);
			json.addProperty("total_fee", totalFee); // 订单总金额
			json.addProperty("refund_fee", refundFee); // 要退款的金额
			String outRefundNo = Utils.createNewOrderId(OrderPrefix.REFUND);
			json.addProperty("out_refund_no", outRefundNo);// 退款单号
			json.addProperty("refund_desc", refundDesc);// 退款原因
			// json.addProperty("notify_url", Pay.NOTIFY_URL);
			String sign = getMd5Sign(json);
			json.addProperty("sign", sign);
			// 将JSON转成XML格式
			String xmlParams = XMLUtil.JsonToXml(json);

			// System.out.println("= = = = = =\nxmlParams: \n" + xmlParams +
			// "\n= = = = = =");

			// 发起请求
			WeChatSSLNetworkUtil network = new WeChatSSLNetworkUtil(Pay.CERT_PATH, Pay.MCH_ID);
			InputStream in = network.sendPostBinaryReturnInputStream(Url.POST_PAY_REFUND, xmlParams);
			if (in == null)
				throw new Exception("无返回数据，可能是证书错误");

			// 解析请求返回的XML
			SAXReader reader = new SAXReader();
			Document doc = reader.read(in);
			network.close();
			// 获取xml文档的根标签（一般创建doc对象后回先调用此方法得到根标签）
			Element root = doc.getRootElement();
			// 获取返回参数
			String returnCode = XMLUtil.getStringValue(root, "return_code"); // 返回状态码
			if (returnCode.equals("SUCCESS")) {
				String resultCode = XMLUtil.getStringValue(root, "result_code");// 业务结果
				if (resultCode.equals("SUCCESS")) {// 判断业务是否成功
					return outRefundNo; // 返回商户退款单号
				} else {
					String errCode = XMLUtil.getStringValue(root, "err_code");// 错误代码
					String errCodeDes = XMLUtil.getStringValue(root, "err_code_des");// 错误代码描述
					throw new Exception("微信统一下单接口业务失败，错误代码：" + errCode + "；错误描述：" + errCodeDes);
				}
			} else {
				String returnMsg = XMLUtil.getStringValue(root, "return_msg");// 返回信息
				throw new Exception("调用微信统一下单接口失败，错误描述：" + returnMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 调用微信统一下单接口
	 * 
	 * @param openId
	 *            用户标识
	 * @param body
	 *            商品简单描述，已加前缀：奇虫共享- 。只需填写后缀即可
	 * @param outTradeNo
	 *            商户订单号，商户系统内部订单号
	 * @param totalFee
	 *            订单金额，单位为元
	 * @return 成功返回 prepay_id，失败返回 null
	 */
	public static String placeOrder(String openId, String body, String outTradeNo, Double totalFee) {
		try {
			String ip = "39.106.49.36";
			// String ip = "123.121.100.208";
			// 填充json参数
			JsonObject json = new JsonObject();
			json.addProperty("appid", APP_ID);// 小程序AppID，必填1
			json.addProperty("mch_id", Pay.MCH_ID); // 商户ID，必填
			json.addProperty("nonce_str", Utils.randomString(32)); // 随机32位字符串，必填
			// String builderBody = new String("奇虫共享-".getBytes(), "utf-8") +
			// new String(body.getBytes(), "utf-8");
			String builderBody = "奇虫-" + body;
			json.addProperty("body", builderBody); // 商品简单描述，例如：奇虫共享-预约付款，必填
			json.addProperty("out_trade_no", outTradeNo); // 商户订单号，商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一，必填
			Double payment = totalFee * 10.0 * 10.0;// 订单总金额
			json.addProperty("total_fee", payment.intValue()); // 订单总金额，单位为分，必填
			json.addProperty("spbill_create_ip", ip); // APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP，必填
			json.addProperty("notify_url", Pay.NOTIFY_URL); // 通知地址，必填
			json.addProperty("trade_type", "JSAPI"); // 交易类型，小程序取值如下：JSAPI，必填
			json.addProperty("openid", openId); // 用户标识，trade_type=JSAPI，此参数必传，必填
			String sign = getMd5Sign(json);
			json.addProperty("sign", sign); // 通过签名算法计算得出的签名值，必填，32位，类型默认MD5，必填
			// 将JSON转成XML格式
			String xmlParams = XMLUtil.JsonToXml(json);

			// System.out.println("= = = = = =\nxmlParams: \n" + xmlParams +
			// "\n= = = = = =");

			// 发起下单请求
			InputStream in = NetworkUtil.sendPostReturnInputStream(Url.POST_UNIFIED_ORDER, xmlParams);
			// 解析请求返回的XML
			SAXReader reader = new SAXReader();
			Document doc = reader.read(in);
			// 获取xml文档的根标签（一般创建doc对象后回先调用此方法得到根标签）
			Element root = doc.getRootElement();
			// 获取返回参数
			String returnCode = XMLUtil.getStringValue(root, "return_code"); // 返回状态码
			if (returnCode.equals("SUCCESS")) {
				String resultCode = XMLUtil.getStringValue(root, "result_code");// 业务结果
				if (resultCode.equals("SUCCESS")) {// 判断业务是否成功
					String prepay_id = XMLUtil.getStringValue(root, "prepay_id");// 预支付交易会话标识
					return prepay_id; // 返回prepay_id
				} else {
					String errCode = XMLUtil.getStringValue(root, "err_code");// 错误代码
					String errCodeDes = XMLUtil.getStringValue(root, "err_code_des");// 错误代码描述
					throw new Exception("微信统一下单接口业务失败，错误代码：" + errCode + "；错误描述：" + errCodeDes);
				}
			} else {
				String returnMsg = XMLUtil.getStringValue(root, "return_msg");// 返回信息
				throw new Exception("调用微信统一下单接口失败，错误描述：" + returnMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 得到签名 */
	private static String getMd5Sign(JsonObject json) {
		// 得到已排序后的keys
		String[] keys = JSONUtil.getJsonObjectKeys(json, true);
		// 将keys格式化成 key1=value1&key2=value2 的形式
		String stringA = "";
		for (int i = 0; i < keys.length; i++) {
			String key = keys[i];
			stringA += key + "=" + json.get(key).getAsString() + "&";
		}
		// 拼接API秘钥
		stringA += "key=" + Pay.API_PASSWORD;
		// System.out.println("签名：" + stringA);

		// 使用md5加密
		String sign = String.valueOf(DigestUtils.md5Hex(stringA)).toUpperCase();
		return sign;
	}

	/**
	 * 生成小程序所需的sign签名
	 * 
	 * @param prepayId
	 *            预订单id
	 * @return 返回携带签名的map
	 */
	public static Map<String, String> getClientSign(String prepayId) {
		// 生成必要的参数
		String _package = "prepay_id=" + prepayId;
		String nonceStr = Utils.randomString(32);
		long timeStamp = System.currentTimeMillis();
		// 生成要签名
		JsonObject json = new JsonObject();
		json.addProperty("appId", APP_ID);
		json.addProperty("nonceStr", nonceStr);
		json.addProperty("package", _package);
		json.addProperty("signType", "MD5");
		json.addProperty("timeStamp", timeStamp);
		String[] keys = JSONUtil.getJsonObjectKeys(json, true);
		String stringA = "";
		for (String key : keys) {
			stringA += key + "=" + json.get(key).getAsString() + "&";
		}
		stringA += "key=" + Pay.API_PASSWORD;
		// 使用md5加密签名
		String sign = String.valueOf(DigestUtils.md5Hex(stringA)).toUpperCase();
		// 生成小程序所需的参数map
		Map<String, String> map = new HashMap<String, String>();
		map.put("package", _package);
		map.put("nonceStr", nonceStr);
		map.put("timeStamp", String.valueOf(timeStamp));
		map.put("signType", "MD5");
		map.put("paySign", sign);
		return map;
	}

}
