package com.qichong.socket;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/** 公共的握手拦截器 */
public class CommonHandshake extends HttpSessionHandshakeInterceptor {

	/** 握手操作之前 */
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		// System.out.println(" MessageOnline 即将开始进行握手ヽ(*^ｰ^)人(^ｰ^*)ノ");

		return super.beforeHandshake(request, response, wsHandler, attributes);
	}

	/** 握手操作之后 */
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		// System.out.println(" MessageOnline 握手操作结束ヽ(*^ｰ^)人(^ｰ^*)ノ");

		super.afterHandshake(request, response, wsHandler, ex);
	}

}
