package com.qichong.model;

import static com.qichong.util.web.ServletUtil.isEmpty;

import java.util.Calendar;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.qichong.exception.QCErrorRuntimeException;
import com.qichong.util.Utils;

/**
 * 用于筛选的Model，新版
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2018年1月10日15:17:37
 */
public class Filters {

	/** 构造一个新的Filters */
	public static Filters builder() {
		return new Filters();
	}

	public static Filters valueOf(Filters filters) {
		return filters == null ? builder() : filters;
	}

	/* ---------------------------------------------------------------------- */
	/* - - - - - - - - - - - - - 通用类 ----> Begin - - - - - - - - - - - - - - */
	/* ---------------------------------------------------------------------- */

	private String field; // 字段
	private String keyword; // 筛选关键词
	private String name; // 名称
	private String regionCity;
	private Integer userStatus;

	/* ---------------------------------------------------------------------- */
	/* - - - - - - - - - - - - - 通用类 ----> End - - - - - - - - - - - - - - - */
	/* ---------------------------------------------------------------------- */
	/* - - - - - - - - - - - - ID、Code 类 ----> Begin - - - - - - - - - - - - */
	/* ---------------------------------------------------------------------- */

	private Integer id;// Id
	private String ids; // ids
	private Integer notId; // 不包含的id
	private Integer userId;// 用户Id
	private Integer userIdA;// 用户IdA
	private Integer userIdB;// 用户IdB
	private Integer whereUserId;// 用于Where的用户Id
	private Integer fieldId; // field id
	private Integer stateId; // 状态Id
	private Integer auditStateId; // 审核状态Id
	private String auditStateIds; // 审核状态Ids，多个id使用, 分割，例如 1,2,3
	private Integer genderId; // 性别Id
	private Integer groupId; // 团体Id
	private Integer demandId; // 需求Id
	private Integer adId; // 广告Id
	private Integer adTypeId; // 广告类型Id
	private String adTypeIds; // 多个广告类型Id
	private String phone; // 根据手机号码查询
	private String userType; // 用户标识符

	/* ---------------------------------------------------------------------- */
	/* - - - - - - - - - - - - ID、Code 类 ----> End - - - - - - - - - - - - - */
	/* ---------------------------------------------------------------------- */
	/* ---------------------------------------------------------------------- */
	/* - - - - - - - - - - - - - 分页类 ----> Begin - - - - - - - - - - - - - - */
	/* ---------------------------------------------------------------------- */

	private Integer pageNum;// 页码
	private Integer pageSize; // 页大小

	/** 获取界限（查询出几条数据） */
	public Integer getLimit() {
		// 根据 page（页码 ）和 pageSize（页大小）算出 limit，页码从 1 开始
		int num = this.pageNum == null ? 1 : this.pageNum;
		return (num - 1) * this.getOffset();
	}

	/** 获取偏移量（从第几条开始查询） */
	public Integer getOffset() {
		// 默认偏移量为10
		return this.pageSize == null ? 10 : this.pageSize;
	}

	/* ---------------------------------------------------------------------- */
	/* - - - - - - - - - - - - - - 分页类 ----> End - - - - - - - - - - - - - - */
	/* ---------------------------------------------------------------------- */
	/* - - - - - - - - - - - - - 附近类 ----> Begin - - - - - - - - - - - - - - */
	/* ---------------------------------------------------------------------- */

	private Double latitude; // 维度
	private Double longitude; // 经度
	private Integer distance;// 距离（米）
	private Integer lastUpdateTime; // 最后一次更新时间（xxx分钟前）

	public Date getLastUpdateDatetime() {
		if (lastUpdateTime == null)
			return null;
		return Utils.calcDate(new Date(), "-", lastUpdateTime, "时");
	}

	/* ---------------------------------------------------------------------- */
	/* - - - - - - - - - - - - - - 附近类 ----> End - - - - - - - - - - - - - - */
	/* ---------------------------------------------------------------------- */
	/* - - - - - - - - - - - - - - 排序类 ----> Begin - - - - - - - - - - - - */
	/* ---------------------------------------------------------------------- */

	/* ASC 升序，DESC 降序 */

	private String orderScore;// 按照评分排序
	private String orderPay;// 按照工资，时薪或酬劳排序
	private String orderReward;// 按照酬劳排序
	private String orderReleaseTime;// 按照发布时间排序
	private String orderCreateTime;// 按照创建时间排序
	private String orderDistance;// 按照距离排序
	private String orderLastUpdateTime;// 按照最后更新时间排序
	private String orderQuantity;// 按照人员数量排序
	private String orderCost;// 按照费用排序
	private String orderPersonnelCost;// 按照个人费用排序
	private String orderToolCost;// 按照工具费用排序
	private String orderRent;// 按照租金排序
	private boolean orderIntegrate = false; // 综合排序

	/* ---------------------------------------------------------------------- */
	/* - - - - - - - - - - - - - - 排序类 ----> End - - - - - - - - - - - - - - */
	/* ---------------------------------------------------------------------- */
	/* - - - - - - - - - - - - - 范围、区间类 ----> Begin - - - - - - - - - - - */
	/* ---------------------------------------------------------------------- */

	/* 时薪 */
	private Double beginHourlyWage;
	private Double endHourlyWage;

	/* 年龄 */
	private Integer beginAge;
	private Integer endAge;

	public String getBeginAge() {
		return beginAge == null ? null : (Calendar.getInstance().get(Calendar.YEAR) - beginAge) + "-01-01";
	}

	public String getEndAge() {
		return endAge == null ? null : (Calendar.getInstance().get(Calendar.YEAR) - endAge) + "-01-01";
	}

	/* 数量、人数 */
	private Integer beginQuantity;
	private Integer endQuantity;

	/* 费用 */
	private Integer beginCost;
	private Integer endCost;

	/* 人员费用 */
	private Integer beginPersonnelCost;
	private Integer endPersonnelCost;

	/* 工具费用 */
	private Integer beginToolCost;
	private Integer endToolCost;

	/* 酬劳 */
	private Double beginReward;
	private Double endReward;

	/* 创建时间 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date beginReleaseTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endReleaseTime;

	/* 开始时间 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date beginTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;

	/* 租金区间 */
	private Integer beginRent;
	private Integer endRent;

	/* 工作年限区间 */
	private Integer beginJobYears;
	private Integer endJobYears;

	public String getBeginJobYears() {
		return beginJobYears == null ? null : (Calendar.getInstance().get(Calendar.YEAR) - beginJobYears) + "-01-01";
	}

	public String getEndJobYears() {
		return endJobYears == null ? null : (Calendar.getInstance().get(Calendar.YEAR) - endJobYears) + "-01-01";
	}

	/* ---------------------------------------------------------------------- */
	/* - - - - - - - - - - - - - 范围、区间类 ----> End - - - - - - - - - - - - */
	/* ---------------------------------------------------------------------- */
	/* - - - - - - - - - - - - - 布尔类 ----> Begin - - - - - - - - - - - */
	/* ---------------------------------------------------------------------- */

	private boolean ignoreLoginUser = false; // 忽略当前登录的用户，默认不忽略
	private Boolean onlyNegotiable;
	private Boolean onlyNotNegotiable;
	// 是否查询 企业自定义图片，默认false不查询
	private boolean enterpriseDiyPicture = false;

	/* ---------------------------------------------------------------------- */
	/* - - - - - - - - - - - - - 布尔类 ----> End - - - - - - - - - - - - */
	/* ---------------------------------------------------------------------- */
	/* - - - - - - - - - - - - - 订单类 ----> Begin - - - - - - - - - - - */
	/* ---------------------------------------------------------------------- */

	private String orderId; // 订单ID
	private String goodsId; // 商品ID，可能是用户id、工具id或需求id
	private Integer orderType;
	private Integer paymentStatus;

	// 订单状态
	private boolean s101 = false;
	private boolean s102 = false;
	private boolean s103 = false;
	private boolean s104 = false;
	private boolean s105 = false;
	private boolean s106 = false;

	// 是否返回total
	private boolean total = false;

	/* ---------------------------------------------------------------------- */
	/* - - - - - - - - - - - - - 订单类 ----> End - - - - - - - - - - - */
	/* ---------------------------------------------------------------------- */

	private String openType;

	private Double toolRent;

	// 行业Code
	private String industryCode;
	// 地区Code
	private String regionCode;

	// 行业拼接后的字符串
	private String industryDetails;

	// 职业，职位Code
	private String jobCode;

	// 工作类型
	private Integer jobTypeId;

	// 共享类型Code
	private String sharedCode;

	// 多个行业Code
	private String industryCodes;
	// 多个地区Code
	private String regionCodes;
	// 多个职业，职位Code
	private String jobCodes;
	// 多个共享类型Code
	private String sharedCodes;

	public Filters setIndustryCodes(String industryCodes) {
		if (industryCodes != null) {
			this.industryCode = null;
			this.industryCodes = industryCodes;
		}
		return this;
	}

	public Filters setRegionCodes(String regionCodes) {
		if (regionCodes != null) {
			this.regionCode = null;
			this.regionCodes = regionCodes;
		}
		return this;
	}

	public Filters setJobCodes(String jobCodes) {
		if (jobCodes != null) {
			this.jobCode = null;
			this.jobCodes = jobCodes;
		}
		return this;
	}

	public Filters setSharedCodes(String sharedCodes) {
		if (sharedCodes != null) {
			this.sharedCode = null;
			this.sharedCodes = sharedCodes;
		}
		return this;
	}

	// 详细地址
	private String address;
	// 技能
	private String skillName;
	// 拼接后的职位字符串
	private String searchKeyword;

	// getter && setter //

	// --- 有默认值的 getter --- //

	public Integer getUserId() {
		return userId;
	}

	/** 获取状态Id */
	public Integer getStateId() {
		return stateId;
	}

	public String getIndustryCodes() {
		return industryCodes;
	}

	public String getRegionCodes() {
		return regionCodes;
	}

	public Integer getJobTypeId() {
		return jobTypeId;
	}

	public Filters setJobTypeId(Integer jobTypeId) {
		this.jobTypeId = jobTypeId;
		return this;
	}

	public String getJobCodes() {
		return jobCodes;
	}

	// 无 默认值 的 getter //

	public Integer getOrderType() {
		return orderType;
	}

	public Filters setOrderType(Integer orderType) {
		this.orderType = orderType;
		return this;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public Filters setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
		return this;
	}

	public String getSkillName() {
		return skillName;
	}

	public String getSharedCodes() {
		return sharedCodes;
	}

	public Double getToolRent() {
		return toolRent;
	}

	public Filters setToolRent(Double toolRent) {
		this.toolRent = toolRent;
		return this;
	}

	public Integer getNotId() {
		return notId;
	}

	public String getJobCode() {
		return jobCode;
	}

	public Filters setJobCode(String jobCode) {
		this.jobCode = jobCode;
		return this;
	}

	public Integer getId() {
		return id;
	}

	public boolean getIgnoreLoginUser() {
		return ignoreLoginUser;
	}

	public Filters setIgnoreLoginUser(boolean ignoreLoginUser) {
		this.ignoreLoginUser = ignoreLoginUser;
		return this;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getAddress() {
		return address;
	}

	public Double getEndHourlyWage() {
		return endHourlyWage;
	}

	public boolean getEnterpriseDiyPicture() {
		return enterpriseDiyPicture;
	}

	public Filters setEnterpriseDiyPicture(boolean enterpriseDiyPicture) {
		this.enterpriseDiyPicture = enterpriseDiyPicture;
		return this;
	}

	public String getAuditStateIds() {
		return auditStateIds;
	}

	public Filters setAuditStateIds(String auditStateIds) {
		this.auditStateIds = auditStateIds;
		return this;
	}

	public Integer getEndQuantity() {
		return endQuantity;
	}

	public Date getBeginReleaseTime() {
		return beginReleaseTime;
	}

	public Filters setBeginReleaseTime(Date beginReleaseTime) {
		this.beginReleaseTime = beginReleaseTime;
		return this;
	}

	public Double getEndReward() {
		return endReward;
	}

	public Date getEndReleaseTime() {
		return endReleaseTime;
	}

	public Integer getBeginRent() {
		return beginRent;
	}

	public Filters setBeginRent(Integer beginRent) {
		this.beginRent = beginRent;
		return this;
	}

	public Integer getEndRent() {
		return endRent;
	}

	public Filters setEndRent(Integer endRent) {
		this.endRent = endRent;
		return this;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getName() {
		return name;
	}

	public Integer getBeginQuantity() {
		return beginQuantity;
	}

	public Filters setBeginQuantity(Integer beginQuantity) {
		this.beginQuantity = beginQuantity;
		return this;
	}

	public String getIndustryCode() {
		return industryCode;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public String getIndustryDetails() {
		return industryDetails;
	}

	public Filters setIndustryDetails(String industryDetails) {
		this.industryDetails = industryDetails;
		return this;
	}

	public String getOrderId() {
		return orderId;
	}

	public Filters setOrderId(String orderId) {
		this.orderId = orderId;
		return this;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public Filters setGoodsId(String goodsId) {
		this.goodsId = goodsId;
		return this;
	}

	// setter //
	public Filters setOrderPay(String orderPay) {
		this.orderPay = orderPay;
		return this;
	}

	public Filters setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
		return this;
	}

	public Filters setNotId(Integer notId) {
		this.notId = notId;
		return this;
	}

	public Filters setId(Integer id) {
		this.id = id;
		return this;
	}

	public Filters setKeyword(String keyword) {
		this.keyword = keyword;
		return this;
	}

	public Filters setUserId(Integer userId) {
		this.userId = userId;
		return this;
	}

	public Filters setAddress(String address) {
		this.address = isEmpty(address) ? null : address;
		return this;
	}

	public Filters setName(String name) {
		this.name = isEmpty(name) ? null : name;
		return this;
	}

	public Filters setIndustryCode(String industryCode) {
		this.industryCode = isEmpty(industryCode) ? null : industryCode;
		return this;
	}

	public Filters setRegionCode(String regionCode) {
		this.regionCode = isEmpty(regionCode) ? null : regionCode;
		return this;
	}

	public Double getBeginReward() {
		return beginReward;
	}

	public Filters setBeginReward(Double beginReward) {
		this.beginReward = beginReward;
		return this;
	}

	public Filters setSkillName(String skillName) {
		this.skillName = isEmpty(skillName) ? null : skillName;
		return this;
	}

	public Filters setEndHourlyWage(Double endHourlyWage) {
		this.endHourlyWage = endHourlyWage;
		return this;
	}

	public Filters setBeginAge(Integer beginAge) {
		this.beginAge = beginAge;
		return this;
	}

	public Filters setEndAge(Integer endAge) {
		this.endAge = endAge;
		return this;
	}

	public Filters setEndQuantity(Integer endQuantity) {
		this.endQuantity = endQuantity;
		return this;
	}

	public Filters setEndReward(Double endReward) {
		this.endReward = endReward;
		return this;
	}

	public Filters setEndReleaseTime(Date endReleaseTime) {
		this.endReleaseTime = endReleaseTime;
		return this;
	}

	public Filters setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
		return this;
	}

	public String getSharedCode() {
		return sharedCode;
	}

	public Filters setSharedCode(String sharedCode) {
		this.sharedCode = sharedCode;
		return this;
	}

	public Integer getWhereUserId() {
		return whereUserId;
	}

	public Filters setWhereUserId(Integer whereUserId) {
		this.whereUserId = whereUserId;
		return this;
	}

	public Filters setEndTime(Date endTime) {
		this.endTime = endTime;
		return this;
	}

	public Filters setStateId(Integer stateId) {
		this.stateId = stateId;
		return this;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public Filters setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
		return this;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public Filters setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public Double getLatitude() {
		return latitude;
		// return latitude == null ? 0 : latitude;
	}

	public Integer getBeginCost() {
		return beginCost;
	}

	public Filters setBeginCost(Integer beginCost) {
		this.beginCost = beginCost;
		return this;
	}

	public Integer getEndCost() {
		return endCost;
	}

	public Filters setEndCost(Integer endCost) {
		this.endCost = endCost;
		return this;
	}

	public Integer getBeginPersonnelCost() {
		return beginPersonnelCost;
	}

	public Filters setBeginPersonnelCost(Integer beginPersonnelCost) {
		this.beginPersonnelCost = beginPersonnelCost;
		return this;
	}

	public Integer getEndPersonnelCost() {
		return endPersonnelCost;
	}

	public Filters setEndPersonnelCost(Integer endPersonnelCost) {
		this.endPersonnelCost = endPersonnelCost;
		return this;
	}

	public Integer getBeginToolCost() {
		return beginToolCost;
	}

	public Filters setBeginToolCost(Integer beginToolCost) {
		this.beginToolCost = beginToolCost;
		return this;
	}

	public Integer getEndToolCost() {
		return endToolCost;
	}

	public Filters setEndToolCost(Integer endToolCost) {
		this.endToolCost = endToolCost;
		return this;
	}

	public Double getLongitude() {
		return longitude;
		// return longitude == null ? 0 : longitude;
	}

	public Filters setLatitude(Double latitude) {
		this.latitude = latitude;
		return this;
	}

	public Filters setLongitude(Double longitude) {
		this.longitude = longitude;
		return this;
	}

	public Integer getLastUpdateTime() {
		return lastUpdateTime;
	}

	public Filters setLastUpdateTime(Integer lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
		return this;
	}

	public Integer getDistance() {
		return distance;
	}

	public Filters setDistance(Integer distance) {
		this.distance = distance;
		return this;
	}

	public String getRegionCity() {
		return regionCity;
	}

	public Filters setRegionCity(String regionCity) {
		this.regionCity = regionCity;
		return this;
	}

	public Integer getAuditStateId() {
		return auditStateId;
	}

	public Filters setAuditStateId(Integer auditStateId) {
		this.auditStateId = auditStateId;
		return this;
	}

	public Filters setOrderScore(String orderScore) {
		this.orderScore = orderScore;
		return this;
	}

	/** 获取排序方式 */
	private String getOrderWay(String order) {
		// try {
		if (order != null) {
			String way = order.toUpperCase();
			if (way.equals("ASC") || way.equals("DESC"))
				return way;
			else
				throw new QCErrorRuntimeException("未知的排序类型：\"" + order + "\"，仅支持 ASC(升序)和DESC(降序)");
		}
		return order;
		// } catch (RuntimeException e) {
		// e.printStackTrace();
		// return null;
		// }
	}

	public String getOrderPay() {
		return getOrderWay(orderPay);
	}

	public String getOrderScore() {
		return getOrderWay(orderScore);
	}

	public String getOrderReward() {
		return getOrderWay(orderReward);
	}

	public String getOrderReleaseTime() {
		return getOrderWay(orderReleaseTime);
	}

	public String getOrderCreateTime() {
		return getOrderWay(orderCreateTime);
	}

	public String getOrderDistance() {
		return getOrderWay(orderDistance);
	}

	public String getOrderLastUpdateTime() {
		return getOrderWay(orderLastUpdateTime);
	}

	public String getOrderCost() {
		return getOrderWay(orderCost);
	}

	public String getOrderPersonnelCost() {
		return getOrderWay(orderPersonnelCost);
	}

	public String getOrderToolCost() {
		return getOrderWay(orderToolCost);
	}

	public String getOrderQuantity() {
		return getOrderWay(orderQuantity);
	}

	public String getOrderRent() {
		return getOrderWay(orderRent);
	}

	/* ----- */

	public Integer getFieldId() {
		return fieldId;
	}

	public Filters setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
		return this;
	}

	public Filters setOrderRent(String orderRent) {
		this.orderRent = orderRent;
		return this;
	}

	public Filters setOrderQuantity(String orderQuantity) {
		this.orderQuantity = orderQuantity;
		return this;
	}

	public Filters setOrderDistance(String orderDistance) {
		this.orderDistance = orderDistance;
		return this;
	}

	public Filters setOrderLastUpdateTime(String orderLastUpdateTime) {
		this.orderLastUpdateTime = orderLastUpdateTime;
		return this;
	}

	public Filters setOrderReward(String orderReward) {
		this.orderReward = orderReward;
		return this;
	}

	public Filters setOrderReleaseTime(String orderReleaseTime) {
		this.orderReleaseTime = orderReleaseTime;
		return this;
	}

	public Filters setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
		return this;
	}

	public Filters setOrderCost(String orderCost) {
		this.orderCost = orderCost;
		return this;
	}

	public Filters setOrderPersonnelCost(String orderPersonnelCost) {
		this.orderPersonnelCost = orderPersonnelCost;
		return this;
	}

	public Filters setOrderToolCost(String orderToolCost) {
		this.orderToolCost = orderToolCost;
		return this;
	}

	public boolean isOrderIntegrate() {
		return orderIntegrate;
	}

	public void setOrderIntegrate(boolean orderIntegrate) {
		this.orderIntegrate = orderIntegrate;
	}

	public Integer getGenderId() {
		return genderId;
	}

	public Filters setGenderId(Integer genderId) {
		this.genderId = genderId;
		return this;
	}

	public Double getBeginHourlyWage() {
		return beginHourlyWage;
	}

	public Filters setBeginHourlyWage(Double beginHourlyWage) {
		this.beginHourlyWage = beginHourlyWage;
		return this;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public Filters setGroupId(Integer groupId) {
		this.groupId = groupId;
		return this;
	}

	public Boolean getOnlyNegotiable() {
		return onlyNegotiable;
	}

	public Filters setOnlyNegotiable(Boolean onlyNegotiable) {
		this.onlyNegotiable = onlyNegotiable;
		return this;
	}

	public Boolean getOnlyNotNegotiable() {
		return onlyNotNegotiable;
	}

	public Filters setOnlyNotNegotiable(Boolean onlyNotNegotiable) {
		this.onlyNotNegotiable = onlyNotNegotiable;
		return this;
	}

	public Integer getDemandId() {
		return demandId;
	}

	public Filters setDemandId(Integer demandId) {
		this.demandId = demandId;
		return this;
	}

	public Integer getAdId() {
		return adId;
	}

	public Filters setAdId(Integer adId) {
		this.adId = adId;
		return this;
	}

	public Integer getAdTypeId() {
		return adTypeId;
	}

	public Filters setAdTypeId(Integer adTypeId) {
		this.adTypeId = adTypeId;
		return this;
	}

	public String getAdTypeIds() {
		if (adTypeIds != null) {
			try {
				Integer[] ids = Utils.gson.fromJson(adTypeIds, Integer[].class);
				String idsString = "";
				for (Integer id : ids) {
					idsString += id + ", ";
				}
				return idsString.substring(0, idsString.length() - 2);
			} catch (Exception e) {
				System.err.println("无法将：" + adTypeIds + " 转换为Integer[]");
			}
		}
		return null;
	}

	public Filters setAdTypeIds(String adTypeIds) {
		this.adTypeIds = adTypeIds;
		return this;
	}

	public Integer getUserIdA() {
		return userIdA;
	}

	public Filters setUserIdA(Integer userIdA) {
		this.userIdA = userIdA;
		return this;
	}

	public Integer getUserIdB() {
		return userIdB;
	}

	public Filters setUserIdB(Integer userIdB) {
		this.userIdB = userIdB;
		return this;
	}

	public String getOpenType() {
		return openType;
	}

	public Filters setOpenType(String openType) {
		this.openType = openType;
		return this;
	}

	public Filters setBeginJobYears(Integer beginJobYears) {
		this.beginJobYears = beginJobYears;
		return this;
	}

	public Filters setEndJobYears(Integer endJobYears) {
		this.endJobYears = endJobYears;
		return this;
	}

	public String getIds() {
		return ids;
	}

	public Filters setIds(String ids) {
		this.ids = ids;
		return this;
	}

	public String getField() {
		return field;
	}

	public Filters setField(String field) {
		this.field = field;
		return this;
	}

	public boolean isS101() {
		return s101;
	}

	public Filters setS101(boolean s101) {
		this.s101 = s101;
		return this;
	}

	public boolean isS102() {
		return s102;
	}

	public Filters setS102(boolean s102) {
		this.s102 = s102;
		return this;
	}

	public boolean isS103() {
		return s103;
	}

	public Filters setS103(boolean s103) {
		this.s103 = s103;
		return this;
	}

	public boolean isS104() {
		return s104;
	}

	public Filters setS104(boolean s104) {
		this.s104 = s104;
		return this;
	}

	public boolean isS105() {
		return s105;
	}

	public Filters setS105(boolean s105) {
		this.s105 = s105;
		return this;
	}

	public boolean isS106() {
		return s106;
	}

	public Filters setS106(boolean s106) {
		this.s106 = s106;
		return this;
	}

	public boolean isTotal() {
		return total;
	}

	public Filters setTotal(boolean total) {
		this.total = total;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public Filters setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
