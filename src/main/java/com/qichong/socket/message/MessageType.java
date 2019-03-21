package com.qichong.socket.message;

public enum MessageType {

	/** 上线消息 */
	ONLINE_MESSAGE,
	/** 下线消息 */
	OFFLINE_MESSAGE,
	/** 普通消息 */
	ORDINARY_MESSAGE,
	/** 回执消息 */
	RECEIPT_MESSAGE,
	/** 心跳消息 */
	HEARTBEAT_MESSAGE,
	/** 传入的类型错误，无法实例化 */
	TYPE_ERROR;

}
