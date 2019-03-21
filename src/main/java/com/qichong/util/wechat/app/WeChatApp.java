package com.qichong.util.wechat.app;

/** 微信安卓APP父类，用于记录一些常量值 */
public class WeChatApp {

	/** 安卓的AppID */
	protected static final String APP_ID = "wx56d0bfb1cefe691b";
	//protected static final String APP_ID = "wx7781bdb5e128dc84";
	/** 安卓APP的AppSecret */
 	protected static final String APP_SECRET = "88a886af9be70087316ebe87ca3d5a2b";
	//protected static final String APP_SECRET = "f8044b8baf9652b92c46ecc38397ae36";

	/** 请求地址相关值 */
	protected class Url {
		/** 获取AccessToken */
		static final String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
	}

	/** 请求地址相关值 */
	protected class UrlParam {
		/** 获取AccessToken所需的参数：(1)AppID; (2)AppSecret；(3)code */
		static final String GET_ACCESS_TOKEN = "grant_type=authorization_code&appid=%s&secret=%s&code=%s";
	}

}
