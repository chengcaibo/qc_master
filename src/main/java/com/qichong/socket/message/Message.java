package com.qichong.socket.message;

import com.qichong.util.Utils;

/**
 * WebSocket Message <br>
 * 
 * 一个完整的消息应该包含如下内容
 * <ol>
 * <li>消息头</li>
 * <li>消息体</li>
 * </ol>
 * <b>消息头</b>中应该包含：
 * <ol>
 * <li>消息的唯一标识（messageId）</li>
 * <li>消息的类型（type）</li>
 * <li>消息发送的时间戳（sendTime）</li>
 * <li>发送人的令牌，一般是JWT（token）</li>
 * <li>发送人的保持字符串（keepCode）</li>
 * <li>发件人，一般是userId（sender）</li>
 * <li>收信人，一般是userId（receiver）</li>
 * <li>收信人的类型，只能说user或group（receiverType）</li>
 * </ol>
 * <b>消息的类型</b>包含：
 * <ol>
 * <li>上线消息（ONLINE_MESSAGE，用户上线的时候给服务器发送的消息，让服务器知道该用户已上线（已连接WebSocket））</li>
 * <li>离线消息（OFFLINE_MESSAGE，用户离线的时候给服务器发送的消息）</li>
 * <li>普通消息（ORDINARY_MESSAGE，用户A给用户B发送的消息）</li>
 * <li>回执消息（RECEIPT_MESSAGE，用户B收到用户A给它发送的消息后，回复给用户A一条回执消息，表示收到了该条消息）</li>
 * <li>心跳消息（HEARTBEAT_MESSAGE，用于心跳检测，无需处理直接返回给服务器相同的消息）</li>
 * </ol>
 * <b>普通消息的消息体</b>中应该包含：
 * <ol>
 * <li>消息的内容（data）</li>
 * </ol>
 * <b>回执消息的消息体</b>中应该包含：
 * <ol>
 * <li>表示收到的消息唯一标识（messageID）</li>
 * </ol>
 * 
 * 参考格式：<br>
 * {<br>
 * &nbsp;"header": {<br>
 * &nbsp;&nbsp;&nbsp;"messageId": "0",<br>
 * &nbsp;&nbsp;&nbsp;"type": "MESSAGE",<br>
 * &nbsp;&nbsp;&nbsp;"sendTime": "消息发送的时间戳",<br>
 * &nbsp;&nbsp;&nbsp;"token": "发送人的令牌",<br>
 * &nbsp;&nbsp;&nbsp;"keepCode": "发送人的保持字符串",<br>
 * &nbsp;&nbsp;&nbsp;"receiver": "收信人，一般是userId"<br>
 * &nbsp;&nbsp;&nbsp;"receiverType": "收信人的类型，只能是user或group"<br>
 * &nbsp;},<br>
 * &nbsp;"body": {<br>
 * &nbsp;&nbsp;&nbsp;"data": "你好啊"<br>
 * &nbsp;}<br>
 * 
 * }<br>
 * 
 * @author 孙建雷
 */
public class Message {

	private MessageHeader header; // 消息头
	private MessageBody body; // 消息体

	public MessageBody getBody() {
		return body;
	}

	public void setBody(MessageBody body) {
		this.body = body;
	}

	public void setHeader(MessageHeader header) {
		this.header = header;
	}

	public MessageHeader getHeader() {
		return header;
	}

	@Override
	public String toString() {
		return "Message [header=" + header + ", body=" + body + "]";
	}

	public String toJSON() {
		return Utils.gson.toJson(this);
	}

}
