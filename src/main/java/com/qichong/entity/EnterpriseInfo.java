package com.qichong.entity;

import com.qichong.enums.PathEnum;
import com.qichong.model.Image;
import com.qichong.util.web.ServletUtil;

/**
 * 实体类 enterprise 企业表 对应表 enterprise_info
 * 
 * @author 吴志伟
 *
 */
public class EnterpriseInfo {

	private int id; // id
	private Users user;
	private String enterpriseName; // 企业名称
	private String personName; // 法人名称
	private String fixedTelephone; // 固定电话
	private String telephone; // 法人电话
	private String email; // 法人邮箱
	private String introduce; // 企业介绍
	private Image logo = new Image();// 公司logo
	private Image businessLicense = new Image(); // 公司营业执照
	private String website; // 公司网址
	private Double balance; // 公司余额
	private Regions region; // 公司位置代码，regions外键
	private String industryDetails;// 行业详细信息
	private IndustryType industry;
	private String addressAll;
	private String businessScope;// 企业业务范围
	private CertificationEnterprise certificationEnterprise;

	private String businessTitle;// 业务标题
	private String businessContent;// 业务内容
	private String businessTags;// 业务标签
	private Image banner1 = new Image(PathEnum.ENT_BANNER); // banner 图片
	private Image banner2 = new Image(PathEnum.ENT_BANNER);
	private Image banner3 = new Image(PathEnum.ENT_BANNER);

	private Image qrCodePicture = new Image(PathEnum.ENT_QR_CODE);// 二维码图片
	private String qrCodeName;// 二维码名称

	private String honorContent;//荣誉内容
	private String advantageContent;//企业优势内容
	private String idea;//企业理念内容

	public String getHonorContent() {
		return honorContent;
	}

	public void setHonorContent(String honorContent) {
		this.honorContent = honorContent;
	}

	public String getAdvantageContent() {
		return advantageContent;
	}

	public void setAdvantageContent(String advantageContent) {
		this.advantageContent = advantageContent;
	}

	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}
	// private Image picture0 = new Image(PathEnum.ENT_PICTURE);
	/**
	 * 企业理念
	 */
	private Image philosophy = new Image(PathEnum.ENT_PHILOSOPHY);
	/**
	 * 企业简介
	 */
	private Image profile = new Image(PathEnum.ENT_PROFILE);
	/**
	 * 企业优势
	 */
	private Image advantage = new Image(PathEnum.ENT_ADVANTAGE);
	/**
	 * 企业荣誉
	 */
	private Image honor = new Image(PathEnum.ENT_HONOR);

	public Image getPhilosophy() {
		return philosophy;
	}

	public void setPhilosophy(String  philosophy) {
		this.philosophy.setName(philosophy);
	}

	public Image getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile.setName(profile);
	}

	public Image getAdvantage() {
		return advantage;
	}

	public void setAdvantage(String advantage) {
		this.advantage.setName(advantage);
	}

	public Image getHonor() {
		return honor;
	}

	public void setHonor(String honor) {
		this.honor.setName(honor);
	}


	// 企业自上传图片
	private Image picture0 = new Image(PathEnum.ENT_PICTURE);
	private Image picture1 = new Image(PathEnum.ENT_PICTURE);
	private Image picture2 = new Image(PathEnum.ENT_PICTURE);
	private Image picture3 = new Image(PathEnum.ENT_PICTURE);
	private Image picture4 = new Image(PathEnum.ENT_PICTURE);
	private Image picture5 = new Image(PathEnum.ENT_PICTURE);
	private Image picture6 = new Image(PathEnum.ENT_PICTURE);
	private Image picture7 = new Image(PathEnum.ENT_PICTURE);
	private Image picture8 = new Image(PathEnum.ENT_PICTURE);
	private Image picture9 = new Image(PathEnum.ENT_PICTURE);

	// 经纬度记录
	private Double latitude;
	private Double longitude;
	private String address;
	private String addressName;
	/** 距离数字原形，仅在筛选距离后使用，表示的是该需求距筛选用户的距离（米） */
	private Integer distanceNumber;
	// 将距离转为可识别字符串类型
	private String distance;

	public void setDistanceNumber(Integer distanceNumber) {
		this.distanceNumber = distanceNumber;
		this.distance = ServletUtil.calcDistance(distanceNumber); // 计算距离
	}

	// 构造函数
	public EnterpriseInfo() {
	}

	public EnterpriseInfo(Integer id) {
		this.id = id;
	}

	public EnterpriseInfo(Users user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = ServletUtil.codeFormat(enterpriseName);
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = ServletUtil.codeFormat(personName);
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = ServletUtil.codeFormat(introduce);
	}

	public Image getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo.setPath(PathEnum.ENT_LOGO);
		this.logo.setName(logo);
	}

	public Image getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense.setPath(PathEnum.ENT_LICENSE);
		this.businessLicense.setName(businessLicense);
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = ServletUtil.codeFormat(website);
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Regions getRegion() {
		return region;
	}

	public void setRegion(Regions region) {
		this.region = region;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = ServletUtil.codeFormat(address);
	}

	public String getIndustryDetails() {
		return industryDetails;
	}

	public void setIndustryDetails(String industryDetails) {
		this.industryDetails = industryDetails;
	}

	public IndustryType getIndustry() {
		return industry;
	}

	public void setIndustry(IndustryType industry) {
		this.industry = industry;
	}

	public String getAddressAll() {
		return addressAll;
	}

	public void setAddressAll(String addressAll) {
		this.addressAll = addressAll;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public CertificationEnterprise getCertificationEnterprise() {
		return certificationEnterprise;
	}

	public void setCertificationEnterprise(CertificationEnterprise certificationEnterprise) {
		this.certificationEnterprise = certificationEnterprise;
	}

	public String getFixedTelephone() {
		return fixedTelephone;
	}

	public void setFixedTelephone(String fixedTelephone) {
		this.fixedTelephone = fixedTelephone;
	}

	// 用户自上传

	public Image getPicture0() {
		return picture0;
	}

	public void setPicture0(String picture0) {
		this.picture0.setName(picture0);
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

	public Image getPicture4() {
		return picture4;
	}

	public void setPicture4(String picture4) {
		this.picture4.setName(picture4);
	}

	public Image getPicture5() {
		return picture5;
	}

	public void setPicture5(String picture5) {
		this.picture5.setName(picture5);
	}

	public Image getPicture6() {
		return picture6;
	}

	public void setPicture6(String picture6) {
		this.picture6.setName(picture6);
	}

	public Image getPicture7() {
		return picture7;
	}

	public void setPicture7(String picture7) {
		this.picture7.setName(picture7);
	}

	public Image getPicture8() {
		return picture8;
	}

	public void setPicture8(String picture8) {
		this.picture8.setName(picture8);
	}

	public Image getPicture9() {
		return picture9;
	}

	public void setPicture9(String picture9) {
		this.picture9.setName(picture9);
	}

	/* 企业详情页 */

	public String getBusinessTitle() {
		return businessTitle;
	}

	public void setBusinessTitle(String businessTitle) {
		this.businessTitle = businessTitle;
	}

	public String getBusinessContent() {
		return businessContent;
	}

	public void setBusinessContent(String businessContent) {
		this.businessContent = businessContent;
	}

	public String getBusinessTags() {
		return businessTags;
	}

	public void setBusinessTags(String businessTags) {
		this.businessTags = businessTags;
	}

	public Image getBanner1() {
		return banner1;
	}

	public void setBanner1(String banner1) {
		this.banner1.setName(banner1);
	}

	public Image getBanner2() {
		return banner2;
	}

	public void setBanner2(String banner2) {
		this.banner2.setName(banner2);
	}

	public Image getBanner3() {
		return banner3;
	}

	public void setBanner3(String banner3) {
		this.banner3.setName(banner3);
	}

	public Image getQrCodePicture() {
		return qrCodePicture;
	}

	public void setQrCodePicture(String qrCodePicture) {
		this.qrCodePicture.setName(qrCodePicture);
	}

	public String getQrCodeName() {
		return qrCodeName;
	}

	public void setQrCodeName(String qrCodeName) {
		this.qrCodeName = qrCodeName;
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

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public Integer getDistanceNumber() {
		return distanceNumber;
	}

	public String getDistance() {
		return distance;
	}

}
