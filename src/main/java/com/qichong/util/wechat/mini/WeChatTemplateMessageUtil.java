package com.qichong.util.wechat.mini;

import java.util.Date;

import com.google.gson.JsonObject;
import com.qichong.entity.CertificationInfo;
import com.qichong.entity.OrderInfo;
import com.qichong.util.Utils;
import com.qichong.util.web.NetworkUtil;

/** 微信小程序模板消息辅助类 */
public class WeChatTemplateMessageUtil extends WeChatMini {

	private static final String fmt = "yyyy年MM月dd日 HH:mm:ss";

	/** 发送订单关闭通知 */
	public static String sendOrderClosed(String openId, OrderInfo oinfo, String page, String goodsName) {
		JsonObject json = new JsonObject();
		// 填充json
		json.addProperty("touser", openId);
		json.addProperty("template_id", TemplateId.ORDER_CLOSED);
		json.addProperty("page", page);
		String formId = oinfo.getPrepayId() == null ? oinfo.getFormId() : oinfo.getPrepayId();
		json.addProperty("form_id", formId);
		System.out.println("发送模板消息openId: " + openId);
		System.out.println("发送模板消息formId: " + formId);
		// 填充Data
		JsonObject data = new JsonObject();
		// 订单号
		tectonicsTemplateKeyword(1, oinfo.getOrderId(), "#000000", data);
		// 商品名称
		tectonicsTemplateKeyword(2, goodsName, "#000000", data);
		// 下单时间
		tectonicsTemplateKeyword(3, Utils.formatDate(oinfo.getCreateTime(), fmt), "#000000", data);
		// 关闭时间
		tectonicsTemplateKeyword(4, Utils.formatDate(oinfo.getCloseTime(), fmt), "#000000", data);
		// 订单金额
		tectonicsTemplateKeyword(5, oinfo.getPayment().toString(), "#000000", data);
		// 退款金额
		tectonicsTemplateKeyword(6, oinfo.getPayment().toString(), "#000000", data);
		// 备注
		tectonicsTemplateKeyword(7, "很抱歉，对方没有在24小时内响应您的预约请求，所以已为您自动关闭，账单金额已返还到您的余额中，可直接使用。", "#000000", data);
		json.add("data", data);
		// 立即发送
		return sendTemplateMessage(json);
	}

	/** 发送订单拒绝通知 */
	public static String sendOrderRefuse(String openId, OrderInfo oinfo, String page, String goodsName) {
		JsonObject json = new JsonObject();
		// 填充json
		json.addProperty("touser", openId);
		json.addProperty("template_id", TemplateId.ORDER_REFUSE);
		json.addProperty("page", page);
		String formId = oinfo.getPrepayId() == null ? oinfo.getFormId() : oinfo.getPrepayId();
		json.addProperty("form_id", formId);
		// 填充Data
		JsonObject data = new JsonObject();
		// 订单号
		tectonicsTemplateKeyword(1, oinfo.getOrderId(), "#000000", data);
		// 商品名称
		tectonicsTemplateKeyword(2, goodsName, "#000000", data);
		// 下单时间
		tectonicsTemplateKeyword(3, Utils.formatDate(oinfo.getCreateTime(), fmt), "#000000", data);
		// 拒绝时间
		tectonicsTemplateKeyword(4, Utils.formatDate(oinfo.getCloseTime(), fmt), "#000000", data);
		// 拒绝原因
		String reason = oinfo.getReason() == null ? "对方未填写原因" : oinfo.getReason();
		tectonicsTemplateKeyword(5, reason, "#000000", data);
		// 订单金额
		tectonicsTemplateKeyword(6, oinfo.getPayment().toString(), "#000000", data);
		// 备注
		tectonicsTemplateKeyword(7, "很抱歉，对方拒绝了您的预约请求。", "#000000", data);
		json.add("data", data);
		// 立即发送
		return sendTemplateMessage(json);
	}

	/** 发送订单响应通知 */
	public static String sendOrderResponse(String openId, OrderInfo oinfo, String page, String goodsName) {
		JsonObject json = new JsonObject();
		// 填充json
		json.addProperty("touser", openId);
		json.addProperty("template_id", TemplateId.ORDER_RESPONSE);
		json.addProperty("page", page);
		String formId = oinfo.getPrepayId() == null ? oinfo.getFormId() : oinfo.getPrepayId();
		json.addProperty("form_id", formId);
		// 填充Data
		JsonObject data = new JsonObject();
		// 订单号
		tectonicsTemplateKeyword(1, oinfo.getOrderId(), "#000000", data);
		// 商品名称
		tectonicsTemplateKeyword(2, goodsName, "#000000", data);
		// 下单时间
		tectonicsTemplateKeyword(3, Utils.formatDate(oinfo.getCreateTime(), fmt), "#000000", data);
		// 确认时间
		tectonicsTemplateKeyword(4, Utils.formatDate(new Date(), fmt), "#000000", data);
		// 订单金额
		tectonicsTemplateKeyword(5, oinfo.getPayment().toString(), "#000000", data);
		// 备注
		tectonicsTemplateKeyword(6, "对方已确认您的订单，请等待约定时间并进行下一步的操作。", "#000000", data);
		json.add("data", data);
		// 立即发送
		return sendTemplateMessage(json);
	}

	/** 发送审核未通过模板消息 */
	public static String sendAuditNotPassed(String openId, CertificationInfo ci, String content, String note,
			String page) {
		JsonObject json = new JsonObject();
		// 填充json
		json.addProperty("touser", openId);
		json.addProperty("template_id", TemplateId.AUDIT_NOT_PASSED);
		json.addProperty("page", page);
		json.addProperty("form_id", ci.getFormId());

		// 填充Data
		JsonObject data = new JsonObject();
		// 申请人
		tectonicsTemplateKeyword(1, ci.getRealName(), "#000000", data);
		// 申请时间
		tectonicsTemplateKeyword(2, Utils.formatDate(ci.getCreateTime(), fmt), "#000000", data);
		// 申请内容
		tectonicsTemplateKeyword(3, content, "#000000", data);
		// 审核时间
		tectonicsTemplateKeyword(4, Utils.formatDate(ci.getEditTime(), fmt), "#000000", data);
		// 未通过原因
		tectonicsTemplateKeyword(5, ci.getReason(), "#FF0000", data);
		// 备注
		tectonicsTemplateKeyword(6, note, "#000000", data);
		json.add("data", data);

		// 立即发送
		return sendTemplateMessage(json);
	}

	/** 发送审核通过模板消息 */
	public static String sendAuditPassed(String openId, CertificationInfo ci, String content, String note,
			String page) {
		JsonObject json = new JsonObject();
		// 填充json
		json.addProperty("touser", openId);
		json.addProperty("template_id", TemplateId.AUDIT_PASSED);
		json.addProperty("page", page);
		json.addProperty("form_id", ci.getFormId());

		// 填充Data
		JsonObject data = new JsonObject();
		// 申请人
		tectonicsTemplateKeyword(1, ci.getRealName(), "#000000", data);
		// 申请时间
		tectonicsTemplateKeyword(2, Utils.formatDate(ci.getCreateTime(), fmt), "#000000", data);
		// 申请内容
		tectonicsTemplateKeyword(3, content, "#000000", data);
		// 审核时间
		tectonicsTemplateKeyword(4, Utils.formatDate(ci.getEditTime(), fmt), "#000000", data);
		// 备注
		tectonicsTemplateKeyword(5, note, "#000000", data);
		json.add("data", data);

		// 立即发送
		return sendTemplateMessage(json);
	}

	/**
	 * 构造模板消息单个Keyword
	 * 
	 * @param num
	 *            第几个keyword
	 * @param value
	 *            显示的内容
	 * @param color
	 *            显示的颜色
	 * @param parent
	 *            父json，用于拼接
	 */
	private static void tectonicsTemplateKeyword(int num, String value, String color, JsonObject parent) {
		JsonObject keyword = new JsonObject();
		keyword.addProperty("value", value);
		keyword.addProperty("color", color);
		parent.add("keyword" + num, keyword);
	}

	/**
	 * 发送小程序的模板消息
	 * 
	 * @param openId
	 *            被发送人的openId
	 */
	private static String sendTemplateMessage(JsonObject json) {
		try {
			String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?";
			String param = "access_token=" + WeChatMiniUtil.getAccessToken(); // 获取AccessToken
			return NetworkUtil.sendPost(url + param, json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
