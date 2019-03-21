package com.qichong.socket.message;

public class MessageBody {

	private String data; // 消息的内容

	private Integer uid;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "MessageBody [data=" + data + "]";
	}

}
