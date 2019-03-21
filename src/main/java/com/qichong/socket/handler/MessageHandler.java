package com.qichong.socket.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.qichong.entity.MessageOffline;
import com.qichong.entity.Users;
import com.qichong.service.MessageOfflineService;
import com.qichong.service.UsersService;
import com.qichong.socket.message.Database;
import com.qichong.socket.message.Message;
import com.qichong.socket.message.MessageHeader;
import com.qichong.socket.message.MessageType;
import com.qichong.socket.message.MessageUtils;
import com.qichong.socket.message.OnlineUser;
import com.qichong.socket.message.child.HeartbeatMessage;
import com.qichong.socket.message.child.OfflineMessage;
import com.qichong.socket.message.child.OnlineMessage;
import com.qichong.socket.message.child.OrdinaryMessage;
import com.qichong.socket.message.child.ReceiptMessage;
import com.qichong.token.JWToken;
import com.qichong.token.LoginToken;
import com.qichong.util.Utils;
import com.qichong.util.web.ServletUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * 消息处理程序
 * 
 * @author 孙建雷
 *
 */
public class MessageHandler extends TextWebSocketHandler {

	@Autowired
	UsersService usersService;

	/**
	 * 收到消息
	 * 
	 * @param session
	 *            当前session
	 * @param message
	 *            发来的消息
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		try {
			// 处理消息，将消息转换成可识别的消息对象
			Message msg = MessageUtils.resolveMessage(message);
			if (msg == null) {
				System.out.println("解析消息失败：" + message.getPayload());
			} else {
				String payload = message.getPayload();
				switch (String.valueOf(msg.getHeader().getType())) {
				case "ONLINE_MESSAGE":// 上线消息
					this.onlineMessage(Utils.gson.fromJson(payload, OnlineMessage.class), session);
					break;
				case "OFFLINE_MESSAGE":// 离线消息
					this.offlineMessage(Utils.gson.fromJson(payload, OfflineMessage.class), session);
					break;
				case "ORDINARY_MESSAGE": // 普通消息
					this.ordinaryMessage(Utils.gson.fromJson(payload, OrdinaryMessage.class), session);
					break;
				case "RECEIPT_MESSAGE": // 回执消息
					this.receiptMessage(Utils.gson.fromJson(payload, ReceiptMessage.class), session);
					break;
				case "HEARTBEAT_MESSAGE": // 心跳消息
					this.heartbeatMessage(Utils.gson.fromJson(payload, HeartbeatMessage.class), session);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 发送消息 */
	private boolean sendMessage(WebSocketSession session, String message) {
		try {
			TextMessage text = new TextMessage(message);
			if (session.isOpen()) {
				session.sendMessage(text);
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/** 保存用户 */
	private OnlineUser saveUser(Users user, WebSocketSession session, String jwt) {
		OnlineUser onlineUser = new OnlineUser(user, session, jwt);
		Database.onlineUsers.put(user.getId(), onlineUser);
		return onlineUser;
	}

	/** 获得用户 */
	private OnlineUser getOnlineUser(Message message, WebSocketSession session) {
		try {
			// 从Token中获取用户
			Jws<Claims> claims = JWToken.parseJWT(message.getHeader().getToken());// 解析JWToken
			Object userId = claims.getBody().get("u_id");// 获取u_id
			OnlineUser onlineUser = Database.getOnlineUser(userId);// 获取在线用户
			if (onlineUser != null)
				return saveUser(onlineUser.getUser(), session, onlineUser.getToken());
			return null;
		} catch (UnsupportedJwtException e) {
			// System.out.println(" --- 这个JWT有点问题，不能被识别");
		} catch (MalformedJwtException e) {
			// System.out.println(" --- 这个JWT格式有点问题，不能被识别");
		} catch (SignatureException e) {
			// System.out.println(" --- JWT的签名错误，有效性不能被保证");
		} catch (ExpiredJwtException e) {
			// System.out.println(" --- 这个JWT已经过期了");
		} catch (IllegalArgumentException e) {
			// System.out.println(" --- 提供的JWT不能为空");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 从KeepCode中获取用户
		Users user = ServletUtil.getThisLoginUser(new LoginToken(message.getHeader()), usersService);
		if (user.getId() != null)
			return saveUser(user, session, JWToken.buildJWT(user, 3600000 * 6)); // 6小时过期
		return null;
	}

	/** 处理上线消息 */
	private void onlineMessage(OnlineMessage message, WebSocketSession session) {
		OnlineUser onlineUser = this.getOnlineUser(message, session);// 根据KeepCode获取在线用户
		if (onlineUser != null) {
			message.getHeader().setSender("server");
			message.getHeader().setReceiver(onlineUser.getUser().getId());
			message.getHeader().setSendTime(System.currentTimeMillis());
			// 返回一个JWToken
			message.getHeader().setToken(onlineUser.getToken());
			this.sendMessage(session, message.toJSON());
		} else {
			// 未登录
		}
	}

	/** 处理离线消息 */
	private void offlineMessage(OfflineMessage message, WebSocketSession session) {
	}

	@Autowired
	MessageOfflineService messageOfflineService;

	/** 处理普通消息 */
	private void ordinaryMessage(OrdinaryMessage message, WebSocketSession senderSession) {
		OnlineUser onlineUser = this.getOnlineUser(message, senderSession); // 根据Token获取在线用户
		if (onlineUser != null) {
			MessageHeader header = message.getHeader();
			header.setKeepCode(null);
			header.setToken(null);
			header.setSender(onlineUser.getUser().getId()); // 发件人
			// 获取收信人，并判断是否在线
			Integer receiver = Integer.parseInt(header.getReceiver());
			OnlineUser receiverUser = Database.getOnlineUser(receiver);
			if (receiverUser != null && receiverUser.getSession().isOpen()) { // 用户在线，直接发送消息
				this.sendMessage(receiverUser.getSession(), message.toJSON());
			} else { // 用户不在线，存入离线消息数据库
				MessageOffline msgoff = new MessageOffline(onlineUser.getUser().getId(), receiver, message.toJSON());
				messageOfflineService.insert(msgoff);
				// 然后返回给发送者一条回执消息
				header.setType(MessageType.RECEIPT_MESSAGE);
				header.setSender(header.getReceiver());
				header.setReceiver(onlineUser.getUser().getId());
				message.getBody().setData("NOT_ONLINE");
				this.sendMessage(senderSession, message.toJSON());
			}
		} else {
			System.out.println("【OrdinaryMessage】未登录，不允许给其他人发消息");
		}
	}

	/** 处理回执消息 */
	private void receiptMessage(ReceiptMessage message, WebSocketSession session) {
	}

	/** 处理心跳消息 */
	private void heartbeatMessage(HeartbeatMessage message, WebSocketSession session) {
	}
}
