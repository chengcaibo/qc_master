package com.qichong.util.wechat.mini;

/** 微信小程序父类，用于记录一些常量值 */
public class WeChatMini {

	/** 小程序AppID */
	protected static final String APP_ID = "wx89ea7d0a3986529b";
	/** 小程序的AppSecret */
	protected static final String APP_SECRET = "d5b43ebbbd35bed766ff3ff61d359215";

	/** 请求地址相关值 */
	protected class Url {
		/** 获取AccessToken */
		static final String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
		/** 微信统一下单接口 */
		static final String POST_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		/** 微信退款地址 */
		static final String POST_PAY_REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		/** POST 获取小程序码，适用于需要的码数量较少的业务场景。通过该接口生成的小程序码，永久有效，有数量限制 */
		static final String GET_WXA_CODE = "https://api.weixin.qq.com/wxa/getwxacode";// ?
	}

	/** 请求地址相关值 */
	protected class UrlParam {
		/**
		 * 获取AccessToken所需的参数
		 * <ol>
		 * <li>AppID</li>
		 * <li>AppSecret</li>
		 * </ol>
		 */
		static final String GET_ACCESS_TOKEN = "grant_type=client_credential&appid=%s&secret=%s";
		/**
		 * 获取小程序码所需的参数
		 * <ol>
		 * <li>access_token</li>
		 * <li>path | 扫码进入的小程序页面路径，最大长度 128 字节，不能为空</li>
		 * <li>width | 宽度，最小 280px，最大 1280px</li>
		 * <li>is_hyaline | 是否需要透明底色</li>
		 * </ol>
		 */
		static final String GET_WXA_CODE = "access_token=%s&path=%s&width=%s&is_hyaline=%s";
	}

	/** 微信支付相关值 */
	public class Pay {
		/** 商户ID */
		public static final String MCH_ID = "1497944322";
		/** 商户API密码 */
		static final String API_PASSWORD = "WeChatMiniApiPwdQiChong1318JTuan";
		/** 通知地址 */
		public static final String NOTIFY_URL = "https://qc.luocat.com/api/wechat/pay/notify";
		/** 证书路径 */
		static final String CERT_PATH = "E:\\data\\cert\\apiclient_cert.p12";
	}

	/** 模板消息ID类 */
	protected class TemplateId {
		/** 审核未通过通知 */
		static final String AUDIT_NOT_PASSED = "ZI8tX1rvYYc2WPpEH2l6NxgqIiV6XDCrHwlCWw9Gpls";
		/** 审核通过通知 */
		static final String AUDIT_PASSED = "OA_YMD3UiHa0q2SvDhMAGZZBNd3bVYWlcN4tHTaylP4";
		/** 预约成功通知 */
		static final String APPOINTMENT_SUCCESS = "ZI8tX1rvYYc2WPpEH2l6N25NhKn3XYe7dGTGsmZDwrk";

		/** 订单响应通知 */
		static final String ORDER_RESPONSE = "EeTqkqKjLhIWUUNYFv1WBYGdIlBLyFIFjJ1iXoA2gEQ";
		/** 订单拒绝通知 */
		static final String ORDER_REFUSE = "kaCNMQpcttOCiTFr6p3qMimEiZoNMfTlgpv64cJTMVU";

		static final String ORDER_CLOSED = "r3HoKeuJpT7keKKqWjsCQzA7fkz_HTUMYNOhWeeu2j8";
	}

}
