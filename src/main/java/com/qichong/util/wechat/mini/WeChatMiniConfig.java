package com.qichong.util.wechat.mini;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.github.wxpay.sdk.IWXPayDomain;
import com.github.wxpay.sdk.WXPayConfig;

public class WeChatMiniConfig extends WXPayConfig {

	private byte[] certData;

	public WeChatMiniConfig() throws Exception {
		String certPath = WeChatMini.Pay.CERT_PATH;
		File file = new File(certPath);
		InputStream certStream = new FileInputStream(file);
		this.certData = new byte[(int) file.length()];
		certStream.read(this.certData);
		certStream.close();
	}

	@Override
	public String getAppID() {
		return WeChatMini.APP_ID;
	}

	@Override
	public String getMchID() {
		return WeChatMini.Pay.MCH_ID;
	}

	@Override
	public String getKey() {
		return WeChatMini.Pay.API_PASSWORD;
	}

	@Override
	public InputStream getCertStream() {
		ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
		return certBis;
	}

	@Override
	public int getHttpConnectTimeoutMs() {
		return 8000;
	}

	@Override
	public int getHttpReadTimeoutMs() {
		return 10000;
	}

	@Override
	public IWXPayDomain getWXPayDomain() {
		return null;
	}

}