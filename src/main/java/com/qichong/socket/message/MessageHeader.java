package com.qichong.socket.message;

/**
 * 消息头
 * 
 * @author 孙建雷
 */
public class MessageHeader {

	private String messageId; // 消息的唯一标识
	private MessageType type;// 消息的类型
	private String sendTime; // 消息发送的时间戳
	private String token; // 发送人的令牌，一般是JWT
	private String keepCode; // 发送人的保持字符串
	private String sender; // 发件人，一般是userId
	private String receiver; // 收信人，一般是userId
	private String receiverType; // 收件人类型，只能说user或group

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public void setType(String type) {
		switch (type) {
		case "ONLINE_MESSAGE":
			this.type = MessageType.ONLINE_MESSAGE;
			break;
		case "OFFLINE_MESSAGE":
			this.type = MessageType.OFFLINE_MESSAGE;
			break;
		case "ORDINARY_MESSAGE":
			this.type = MessageType.ORDINARY_MESSAGE;
			break;
		case "RECEIPT_MESSAGE":
			this.type = MessageType.RECEIPT_MESSAGE;
			break;
		case "HEARTBEAT_MESSAGE":
			this.type = MessageType.HEARTBEAT_MESSAGE;
			break;
		default:
			this.type = MessageType.TYPE_ERROR;
			break;
		}
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.setSendTime(String.valueOf(sendTime));
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getKeepCode() {
		return keepCode;
	}

	public void setKeepCode(String keepCode) {
		this.keepCode = keepCode;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public void setReceiver(Integer receiver) {
		this.receiver = receiver.toString();
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public void setSender(Integer sender) {
		this.sender = sender.toString();
	}

	public String getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}

	@Override
	public String toString() {
		return "MessageHeader [messageId=" + messageId + ", type=" + type + ", sendTime=" + sendTime + ", token="
				+ token + ", keepCode=" + keepCode + ", sender=" + sender + ", receiver=" + receiver + ", receiverType="
				+ receiverType + "]";
	}

}
