package com.qichong.socket.message.child;

import com.qichong.socket.message.Message;

/**
 * 消息的子类 -> 离线消息 <br>
 * 
 * 这里面的字段就是消息体
 * 
 * @author 孙建雷
 */
public class OfflineMessage extends Message {

	@Override
	public String toString() {
		return "OfflineMessage [super.toString()=" + super.toString() + "]";
	}

	@Override
	public String toJSON() {
		return super.toJSON();
	}
}
