package com.qichong.socket.handler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/** Echo处理程序 */
public class EchoHandler extends TextWebSocketHandler {

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 原封不动的返回：echo
		session.sendMessage(message);
	}

}
