package com.qichong.enums;

/**
 * 图片路径枚举，只能添加，不能修改，一定要加文档注释
 * 
 * @author 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017-11-27 15:44:36
 */
public enum PathEnum {

	/** 用户的头像 ("avatar") */
	USER_AVATAR("avatar"),

	/** 用户的资历证书 ("cert") */
	USER_CERT("cert"),

	/** 企业的Logo ("ent/logo") */
	ENT_LOGO("ent/logo"),
	/** 企业的许可证，一般是营业执照 ("ent/license") */
	ENT_LICENSE("ent/license"),
	/** 企业BANNER图片 ("ent/banner") */
	ENT_BANNER("ent/banner"),
	/** 企业qrCode图片 ("ent/qrcode") */
	ENT_QR_CODE("ent/qrcode"),
	/** 企业自上传图片 ("ent/picture") */
	ENT_PICTURE("ent/picture"),
	/** 企业理念*/
	ENT_PHILOSOPHY("ent/philosophy"),
	/**	企业简介*/
	ENT_PROFILE("ent/profile"),
	/** 企业优势*/
	ENT_ADVANTAGE("ent/advantage"),
	/** 企业荣誉*/
	ENT_HONOR("ent/honor"),


	/** 团体封面 ("group") */
	GROUP_COVER("group"),

	// /** 个人企业广告 ("adpublic") */
	// ADPUBLIC_AD("Ad_Public"),

	/** 公开广告位图片路径 ("ad") */
	AD_PUBLIC("ad"),

	/** 私有广告位图片路径 ("ad") */
	AD_PRIVATE("ad-private"),

	/** 成功案例图片路径 */
	SUCCESS_CASE("success-case"),

	/** 需求图片1 */
	DEMANDINFO_PICTURE("demand"),

	/** 认证需要用到的身份证照片 */
	USER_IDENTITY("user/identity"),

	/** 新闻轮播("news") */
	JOURNALIMS_NEWS("news"),

	/** 工具图片("tools") */
	TOOL_PICTURE("tools"),
	/** 用户申诉的图片("allege")*/
	USER_ALLEGE("allege"),

	/** banner图片("banner") */
	BANNER_PICTURE("banners");

	private String path;

	private PathEnum(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

}
