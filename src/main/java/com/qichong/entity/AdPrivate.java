package com.qichong.entity;

import java.util.Date;

import com.qichong.enums.PathEnum;
import com.qichong.model.Image;
import com.qichong.util.web.ServletUtil;

/**
 * 私有广告表
 * 
 * @创建人 孙建雷
 *
 */
public class AdPrivate {

	private Integer id; // 广告id
	private String title; // 广告标题
	private String content; // 广告内容
	private String offerText; // 优惠文本
	private String address; // 地址
	private String addressName; // 地址名称
	private Double latitude; // 维度
	private Double longitude; // 经度
	private String openUrl; // 打开的url地址

	// openType，小程序字段，记录打开的方式，可取值：
	// OPEN_ENTERPRISE_INFO（打开企业详情页面）
	// OEPN_PERSONAL_INFO（ 打开个人详情页面）
	// OPEN_GROUP_INFO（打开团体详情页面）
	// OPEN_DEMAND_INFO（打开需求详情页面）
	// OPEN_AD_INFO（打开广告详情页面）
	// OPEN_TOOLS_INFO（打开工具详情页面）
	// OPEN_WECAHT_MINI_APP（打开微信小程序）
	private String openType;
	private String openId; // 小程序字段，打开的id
	private String openPage; // 小程序字段，打开的页面，优先于open_type
	private String openWeAppId; // 要打开的微信小程序appid
	private String openWeOriginalId; // 要打开的微信小程序原始id（多用于app）
	private String openWePath; // 要打开的微信小程序的页面
	private String openWeData; // 要打开的微信小程序携带的参数，json对象

	private Regions filterRegion; // 所属地区
	private IndustryType filterIndustry; // 所属行业
	private JobInfo filterJob; // 所属职业
	private SharedTypes filterSharedType; // 所属共享分类

	private Date beginTime; // 开始时间
	private Date endTime; // 结束时间
	private Image coverPicture = new Image(PathEnum.AD_PRIVATE); // 封面图片
	private Image qrCodePicture1 = new Image(PathEnum.AD_PRIVATE); // 二维码图片1
	private String qrCodeName1; // 二维码图片的名称1
	private Image qrCodePicture2 = new Image(PathEnum.AD_PRIVATE); // 二维码图片2
	private String qrCodeName2; // 二维码图片的名称2
	private Image qrCodePicture3 = new Image(PathEnum.AD_PRIVATE); // 二维码图片3
	private String qrCodeName3; // 二维码图片的名称3
	private String qrCodeShowNum; // 二维码图片显示第几张（默认第一张，显示在首页的）
	private Image picture1 = new Image(PathEnum.AD_PRIVATE); // 其他图片1
	private Image picture2 = new Image(PathEnum.AD_PRIVATE); // 其他图片2
	private Image picture3 = new Image(PathEnum.AD_PRIVATE); // 其他图片3

	private Integer displayOrder;

	private AdType adType; // 状态id
	private State state; // 状态id

	/** 距离数字原形，仅在筛选距离后使用，表示的是该需求距筛选用户的距离（米） */
	private Integer distanceNumber;
	// 将距离转为可识别字符串类型
	private String distance;

	public void setDistanceNumber(Integer distanceNumber) {
		this.distanceNumber = distanceNumber;
		this.distance = ServletUtil.calcDistance(distanceNumber); // 计算距离
	}

	public AdPrivate() {
	}

	public void cleanPictrue() {
		if (this.coverPicture.getName() == null)
			this.coverPicture = null;

		if (this.qrCodePicture1.getName() == null)
			this.qrCodePicture1 = null;

		if (this.qrCodePicture2.getName() == null)
			this.qrCodePicture2 = null;

		if (this.qrCodePicture3.getName() == null)
			this.qrCodePicture3 = null;

		if (this.picture1.getName() == null)
			this.picture1 = null;

		if (this.picture2.getName() == null)
			this.picture2 = null;

		if (this.picture3.getName() == null)
			this.picture3 = null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOfferText() {
		return offerText;
	}

	public void setOfferText(String offerText) {
		this.offerText = offerText;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getOpenUrl() {
		return openUrl;
	}

	public void setOpenUrl(String openUrl) {
		this.openUrl = openUrl;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getOpenPage() {
		return openPage;
	}

	public void setOpenPage(String openPage) {
		this.openPage = openPage;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Image getCoverPicture() {
		return coverPicture;
	}

	public void setCoverPicture(String coverPicture) {
		this.coverPicture.setName(coverPicture);
	}

	public Image getQrCodePicture1() {
		return qrCodePicture1;
	}

	public void setQrCodePicture1(String qrCodePicture1) {
		this.qrCodePicture1.setName(qrCodePicture1);
	}

	public String getQrCodeName1() {
		return qrCodeName1;
	}

	public void setQrCodeName1(String qrCodeName1) {
		this.qrCodeName1 = qrCodeName1;
	}

	public Image getQrCodePicture2() {
		return qrCodePicture2;
	}

	public void setQrCodePicture2(String qrCodePicture2) {
		this.qrCodePicture2.setName(qrCodePicture2);
	}

	public String getQrCodeName2() {
		return qrCodeName2;
	}

	public void setQrCodeName2(String qrCodeName2) {
		this.qrCodeName2 = qrCodeName2;
	}

	public Image getQrCodePicture3() {
		return qrCodePicture3;
	}

	public void setQrCodePicture3(String qrCodePicture3) {
		this.qrCodePicture3.setName(qrCodePicture3);
	}

	public String getQrCodeName3() {
		return qrCodeName3;
	}

	public void setQrCodeName3(String qrCodeName3) {
		this.qrCodeName3 = qrCodeName3;
	}

	public String getQrCodeShowNum() {
		return qrCodeShowNum;
	}

	public void setQrCodeShowNum(String qrCodeShowNum) {
		this.qrCodeShowNum = qrCodeShowNum;
	}

	public Image getPicture1() {
		return picture1;
	}

	public void setPicture1(String picture1) {
		this.picture1.setName(picture1);
	}

	public Image getPicture2() {
		return picture2;
	}

	public void setPicture2(String picture2) {
		this.picture2.setName(picture2);
	}

	public Image getPicture3() {
		return picture3;
	}

	public void setPicture3(String picture3) {
		this.picture3.setName(picture3);
	}

	public AdType getAdType() {
		return adType;
	}

	public void setAdType(AdType adType) {
		this.adType = adType;
	}

	public void setAdTypeId(Integer adTypeId) {
		this.adType = new AdType(adTypeId);
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setStateId(Integer stateId) {
		this.state = new State(stateId);
	}

	public String getDistance() {
		return distance;
	}

	public Integer getDistanceNumber() {
		return distanceNumber;
	}

	public Regions getFilterRegion() {
		return filterRegion;
	}

	public void setFilterRegion(Regions filterRegion) {
		this.filterRegion = filterRegion;
	}

	public IndustryType getFilterIndustry() {
		return filterIndustry;
	}

	public void setFilterIndustry(IndustryType filterIndustry) {
		this.filterIndustry = filterIndustry;
	}

	public JobInfo getFilterJob() {
		return filterJob;
	}

	public void setFilterJob(JobInfo filterJob) {
		this.filterJob = filterJob;
	}

	public SharedTypes getFilterSharedType() {
		return filterSharedType;
	}

	public void setFilterSharedType(SharedTypes filterSharedType) {
		this.filterSharedType = filterSharedType;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getOpenWeAppId() {
		return openWeAppId;
	}

	public void setOpenWeAppId(String openWeAppId) {
		this.openWeAppId = openWeAppId;
	}

	public String getOpenWeOriginalId() {
		return openWeOriginalId;
	}

	public void setOpenWeOriginalId(String openWeOriginalId) {
		this.openWeOriginalId = openWeOriginalId;
	}

	public String getOpenWePath() {
		return openWePath;
	}

	public void setOpenWePath(String openWePath) {
		this.openWePath = openWePath;
	}

	public String getOpenWeData() {
		return openWeData;
	}

	public void setOpenWeData(String openWeData) {
		this.openWeData = openWeData;
	}

}
