package com.qichong.util.wechat;

import com.github.wxpay.sdk.WXPay;
import com.qichong.util.wechat.mini.WeChatMini;
import com.qichong.util.wechat.mini.WeChatMiniConfig;

public class WeChatGetWXPay {

	public static WXPay getMiniWxPay = null;

	static {
		try {
			WeChatMiniConfig config = new WeChatMiniConfig();
			getMiniWxPay = new WXPay(config, WeChatMini.Pay.NOTIFY_URL, true, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
