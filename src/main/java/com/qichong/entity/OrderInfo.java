package com.qichong.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * 订单信息表（order_info）
 * 
 * @创建人 孙建雷
 * @创建时间 2018年8月7日16:20:37
 */
public class OrderInfo {

	private String orderId; // 订单编号，格式为日期精确到秒+随机六位数，唯一主键
	private Users user; // 下单用户（users表关联）
	private Integer userId; // 下单用户（users表关联）
	private Integer goodsId; // 商品id，可能是用户id、工具id或需求id
	private Double payment; // 实付金额，若无需付款则留0
	private Integer paymentStatus; // 支付状态（-1无需付款，0未付款，1已支付）
	private String remarks; // 备注

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date markBeginTime; // 预约开始时间
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date markEndTime; // 预约结束时间

	private Date createTime; // 订单创建时间
	private Date completeTime; // 订单完成时间
	private Date confirmTime; // 交易完成时间
	private Date closeTime; // 交易关闭时间（付款超时，用户方取消等因素）
	private String reason; // 订单关闭后的原因
	private Integer orderType; // 订单类型（0用户订单、1工具订单、2需求订单）
	private State orderStatus; // 订单状态（state表关联）
	private String formId; // formId
	private String prepayId; // prepayId

	private Double latitude;
	private Double longitude;
	private String address;

	// 表关联
	private OrderComment orderComment; // 评论
	private UserInfo userInfo;
	private DemandInfo demandInfo;
	private ToolsInfo toolsInfo;

	public OrderInfo() {

	}

	public OrderInfo(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getMarkBeginTime() {
		return markBeginTime;
	}

	public void setMarkBeginTime(Date markBeginTime) {
		this.markBeginTime = markBeginTime;
	}

	public Date getMarkEndTime() {
		return markEndTime;
	}

	public void setMarkEndTime(Date markEndTime) {
		this.markEndTime = markEndTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public State getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(State orderStatus) {
		this.orderStatus = orderStatus;
	}

	public OrderComment getOrderComment() {
		return orderComment;
	}

	public void setOrderComment(OrderComment orderComment) {
		this.orderComment = orderComment;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public DemandInfo getDemandInfo() {
		return demandInfo;
	}

	public void setDemandInfo(DemandInfo demandInfo) {
		this.demandInfo = demandInfo;
	}

	public ToolsInfo getToolsInfo() {
		return toolsInfo;
	}

	public void setToolsInfo(ToolsInfo toolsInfo) {
		this.toolsInfo = toolsInfo;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
