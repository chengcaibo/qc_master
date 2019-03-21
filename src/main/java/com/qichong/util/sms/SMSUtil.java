package com.qichong.util.sms;

import java.util.HashMap;
import java.util.Map;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.gson.JsonObject;
import com.qichong.enums.RetEnum;
import com.qichong.model.sms.VCodeSMS;
import com.qichong.util.Utils;

/**
 * 
 * 短信工具类
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017-11-16 17:44:25
 */
public class SMSUtil {

	/** 常量，有效时间，单位为秒，这里设置为五分钟 */
	private final static int EFFECTIVE_TIME = 60 * 5;
	/** 短信验证码的长度，这里设置为6位 */
	private final static int VCODE_LENGTH = 6;
	/** 签名 */
	private final static String SIGN_NAME = "奇虫";
	/** 短信模板 */
	private final static String SMS_TEMPLATE_NORMAL = "SMS_152208570";// 普通验证码
	private final static String SMS_TEMPLATE_PHONE_LOGIN = "SMS_148080295"; // 手机号登录验证码短信
	private final static String SMS_TEMPLATE_VCODE = "SMS_110505056"; // 验证码短信
	private final static String SMS_TEMPLATE_CHANGE_PASSWORD = "SMS_123667984"; // 修改密码短信
	@SuppressWarnings("unused")
	private final static String SMS_TEMPLATE_NEW_ORDER_1 = "SMS_142950502"; // 新订单提醒短信
	private final static String SMS_TEMPLATE_NEW_ORDER_2 = "SMS_142950894"; // 新订单提醒短信
	/** 验证码 Map，Key为手机号，Value为对应手机号的实体类 vCode = verification code */
	private static Map<String, VCodeSMS> vCodeMap = new HashMap<String, VCodeSMS>();
	private final static String SMS_TEMPLATE_WINNING_NOTIFY = "SMS_156275741"; // 中奖短信通知
	private final static String SMS_TEMPLATE_ACCOUNT_INFO = "SMS_157449197";
	/**
	 * 【发送短信】普通样式验证码
	 * 
	 * @param phone
	 *            要发送的手机号
	 */
	public static SendSmsResponse sendNormalSMS(String phone) throws ServerException, ClientException {
		// 然后短信
		return aliyun_sendVCodeSMS(phone, SMS_TEMPLATE_NORMAL);
	}

	/**
	 * 【发送短信】新订单提醒
	 * 
	 * @param phone
	 *            要发送的手机号
	 * @param name
	 *            被预约人的昵称
	 * @param time
	 *            被预约的时间
	 */
	public static SendSmsResponse sendNewOrder(String phone, String name, String time)
			throws ServerException, ClientException {
		// 生成参数
		JsonObject param = new JsonObject();
		param.addProperty("name", name);
		param.addProperty("time", time);
		// 然后短信
		SendSmsResponse response = aliyun_SendSMS(phone, SIGN_NAME, SMS_TEMPLATE_NEW_ORDER_2, param.toString());
		System.out.println("发送新订单提醒：");
		System.out.println("收件人：" + phone + "（" + name + "）");
		System.out.println("BizId：" + response.getBizId());
		System.out.println("RequestId：" + response.getRequestId());
		System.out.println("Code：" + response.getCode());
		System.out.println("Message：" + response.getMessage());
		System.out.println();
		return response;
	}

	/**
	 * 【发送短信】手机号登录验证码短信
	 * 
	 * @param phone
	 *            要发送的手机号，验证码自动生成
	 */
	public static SendSmsResponse sendPhoneLoginVCodeSMS(String phone) throws ServerException, ClientException {
		return aliyun_sendVCodeSMS(phone, SMS_TEMPLATE_PHONE_LOGIN);
	}

	/**
	 * 【发送短信】注册验证码短信
	 * 
	 * @param phone
	 *            要发送的手机号，验证码自动生成
	 */
	public static SendSmsResponse sendSignupVCodeSMS(String phone) throws ServerException, ClientException {
		// // 首先添加进Map，并获取验证码
		// String vCode = addVCode(phone);
		// // 然后发送验证码
		// return aliyun_sendVCodeSMS(phone, vCode);
		return aliyun_sendVCodeSMS(phone, SMS_TEMPLATE_VCODE);
	}

	/**
	 * 【发送短信】修改密码验证码短信
	 * 
	 * @param phone
	 *            要发送的手机号，验证码自动生成
	 */
	public static SendSmsResponse sendChangePasswordVCodeSMS(String phone) throws ServerException, ClientException {
		return aliyun_sendVCodeSMS(phone, SMS_TEMPLATE_CHANGE_PASSWORD);
	}

	/**
	 * 判断验证码是否正确并有效
	 * 
	 * @param phone
	 *            要判断的手机号
	 * @param code
	 *            要判断的验证码
	 * @return 0 - 验证通过；202 - 验证码不正确；201 - 验证码已过期
	 */
	public static RetEnum validationVCode(String phone, String vCode) {
		// 首先判断是否有效，因为一个手机号只能对应一个验证码，所以判断手机号就可以了，手机号作为 map 的 key
		VCodeSMS code = vCodeMap.get(phone);
		if (code == null) {
			return RetEnum.VCODE_ERROR; // 验证码不存在，直接视为不正确
		} else if (System.currentTimeMillis() >= code.getEndTime().getTime()) {
			return RetEnum.VCODE_NOT_EFFECTIVE; // 判断是否过期
		} else if (!code.getvCode().equalsIgnoreCase(vCode)) {
			return RetEnum.VCODE_ERROR; // 判断给定的验证码和生成的验证码是否一致
		} else {
			return RetEnum.SUCCESS; // 正确
		}
	}

	/**
	 * 清理无效的VCode，返回清理数量
	 */
	public static int cleanVCode() {
		int count = 0;
		for (String key : vCodeMap.keySet()) {
			// 判断是否失效
			if (!isEffective(key)) {
				// 失效删除
				cleanVCode(key);
				count++;
			}
		}
		return count;
	}

	/**
	 * 清理指定的vCode，无论是否失效
	 */
	public static boolean cleanVCode(String phone) {
		return vCodeMap.remove(phone) != null;
	}

	/**
	 * 创建一个手机验证码并存入集合，返回生成的验证码
	 */
	private static String createVCode(String phone) {
		String vCode = "";
		// 判断该手机号是否有效
		if (!isEffective(phone)) {
			vCode = randomVCode();
			vCodeMap.put(phone, new VCodeSMS(phone, vCode, EFFECTIVE_TIME));
		} else {
			VCodeSMS temp = vCodeMap.get(phone);
			// 如果还有效就重置失效时间
			temp.resetEffectiveTime();
			vCode = temp.getvCode();
		}
		return vCode;
	}

	/**
	 * 判断手机号的验证码是否还有效，如果有效返回 true
	 */
	private static boolean isEffective(String phone) {
		VCodeSMS temp = vCodeMap.get(phone);
		if (temp == null) {
			return false;
		} else {
			return !(System.currentTimeMillis() >= temp.getEndTime().getTime());
		}
	}

	/**
	 * 随机生成一个验证码
	 */
	private static String randomVCode() {
		String vCode = "";
		do {
			vCode = Utils.randomNumber(VCODE_LENGTH);
			// 特殊需求：验证码首位不能为0
		} while (vCode.indexOf("0") == 0);
		return vCode;
	}

	// = = = = = = = = = = = = = = = = = = //
	// = = = = = = 阿里云提供的代码 = = = = = = = //
	// = = = = = = = = = = = = = = = = = = //

	// 产品名称:云通信短信API产品,开发者无需替换
	static final String product = "Dysmsapi";
	// 产品域名,开发者无需替换
	static final String domain = "dysmsapi.aliyuncs.com";

	// 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
	static final String accessKeyId = "LTAIl0Q18maq7QUk";
	static final String accessKeySecret = "oQOXZ8B9rffHTqvhTK8zcfOfTFU0s8";

	/**
	 * 发送验证码短信
	 * 
	 * @param phone
	 *            要发送的手机号
	 * @param template
	 *            模板
	 * @throws ClientException
	 * @throws ServerException
	 */
	private static SendSmsResponse aliyun_sendVCodeSMS(String phone, String template)
			throws ServerException, ClientException {
		// 生成参数
		JsonObject param = new JsonObject();
		// 创建一个手机验证码并存入集合，返回生成的验证码
		String vCode = createVCode(phone);
		param.addProperty("code", vCode);
		// 然后短信
		return aliyun_SendSMS(phone, SIGN_NAME, template, param.toString());
	}

	/**
	 * 发短信
	 * 
	 * @param phone
	 *            要发送的手机号
	 * @param signName
	 *            发送签名
	 * @param template
	 *            短信模板
	 * @param param
	 *            短信模板附带参数
	 */
	private static SendSmsResponse aliyun_SendSMS(String phone, String signName, String template, String param)
			throws ServerException, ClientException {
		// 设置超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		// 阿里云系统配置
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		// 设置 request发送参数
		SendSmsRequest request = new SendSmsRequest();
		request.setPhoneNumbers(phone);
		request.setSignName(signName);
		request.setTemplateCode(template);
		request.setTemplateParam(param);
		// request.setOutId("yourOutId");
		// 正式发送短信并返回结果
		return acsClient.getAcsResponse(request);
	}

	/**
	 *
	 * @param phone 中奖者的手机号
	 * @param winningCode 中奖码
	 * @return
	 * @throws ClientException
	 */
	public static SendSmsResponse winningNotify(String phone,Integer winningCode) throws ClientException {
		JsonObject param = new JsonObject();
		param.addProperty("code", winningCode);
		// 然后短信
		SendSmsResponse response = aliyun_SendSMS(phone, SIGN_NAME, SMS_TEMPLATE_WINNING_NOTIFY, param.toString());
		return response;
	}
	public static SendSmsResponse accountInfo(String phone,String password) throws ClientException {
		JsonObject param = new JsonObject();
		param.addProperty("username", phone);
		param.addProperty("password", password);
		// 然后短信
		SendSmsResponse response = aliyun_SendSMS(phone, SIGN_NAME, SMS_TEMPLATE_ACCOUNT_INFO, param.toString());
		return response;
	}
}
