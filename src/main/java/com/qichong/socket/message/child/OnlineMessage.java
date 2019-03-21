package com.qichong.socket.message.child;

import com.qichong.socket.message.Message;

/**
 * 消息的子类 -> 上线消息 <br>
 * 
 * 这里面的字段就是消息体
 * 
 * @author 孙建雷
 */
public class OnlineMessage extends Message {

	@Override
	public String toString() {
		return "OnlineMessage [super.toString()=" + super.toString() + "]";
	}

	@Override
	public String toJSON() {
		return super.toJSON();
	}

}
