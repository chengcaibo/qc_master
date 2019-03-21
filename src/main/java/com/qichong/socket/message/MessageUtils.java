package com.qichong.socket.message;

import org.springframework.web.socket.TextMessage;

import com.google.gson.JsonSyntaxException;
import com.qichong.util.Utils;

public class MessageUtils {

	/**
	 * 解析消息
	 * 
	 * @param text
	 * @return 被解析后的消息类，如果解析失败则返回null，造成失败有如下几种情况：
	 *         <ol>
	 *         <li>传入的json格式不正确</li>
	 *         <li>传入的json不能解析成消息类</li>
	 *         <li>传入的json不包含消息头</li>
	 *         <li>传入的json消息头内不包含消息唯一标识（messageId）</li>
	 *         <li>传入的json消息头内不包含消息类型（type）</li>
	 *         <li>传入的json消息头内包含了不能识别的消息类型（TYPE_ERROR）</li>
	 *         </ol>
	 */
	public static Message resolveMessage(TextMessage text) {
		return resolveMessage(text.getPayload());
	}

	/**
	 * 解析消息
	 * 
	 * @param json
	 * @return 被解析后的消息类，如果解析失败则返回null，造成失败有如下几种情况：
	 *         <ol>
	 *         <li>传入的json格式不正确</li>
	 *         <li>传入的json不能解析成消息类</li>
	 *         <li>传入的json不包含消息头</li>
	 *         <li>传入的json消息头内不包含消息唯一标识（messageId）</li>
	 *         <li>传入的json消息头内不包含消息类型（type）</li>
	 *         <li>传入的json消息头内包含了不能识别的消息类型（TYPE_ERROR）</li>
	 *         </ol>
	 */
	public static Message resolveMessage(String json) {
		try {
			// 通过Gson反实例化成Message对象
			Message msg = Utils.gson.fromJson(json, Message.class);
			// 判断是存在必要参数
			if (msg != null && msg.getHeader() != null && msg.getHeader().getMessageId() != null)
				return msg;
		} catch (JsonSyntaxException e) {
			// json格式不正确
		}
		return null;
	}
}
