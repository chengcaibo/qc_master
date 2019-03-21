package com.qichong.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.qichong.enums.PathEnum;
import com.qichong.model.Image;
import com.qichong.util.web.ServletUtil;

/**
 * 
 * @author 张和勇 工具共享实体类
 */
public class ToolsInfo {

	private Integer id;// 共享工具ID
	private Users user;// 发布人实体(外表)
	private String toolName; // 名称
	private String toolDescription; // 租赁描述
	private Double toolRent;// 租金
	private SharedTypes sharedType;// 共享工具类型（外键）
	private Image picture1 = new Image(PathEnum.TOOL_PICTURE);// 共享工具照片(1)必选
	private Image picture2 = new Image(PathEnum.TOOL_PICTURE); // 照片(2、3)不是必填
	private Image picture3 = new Image(PathEnum.TOOL_PICTURE);
	private String telephone; // 联系电话
	private Double latitude; // 纬度
	private Double longitude; // 经度
	private String address; // 经纬度标识的地址（一般为获取经纬度的时候自带）
	private String addressName; // 经纬度标识的地址的名称（一般为获取经纬度的时候自带）
	private Date createTime; // 创建时间
	private Date updateTime; // 修改时间
	private Users userB;// 租出人的用户实体对象
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date expireDate;// 租赁过期时间
	private State state;// 共享状态ID

	/** 距离数字原形，仅在筛选距离后使用，表示的是该需求距筛选用户的距离（米） */
	private Integer distanceNumber;
	/** 将距离转为可识别字符串类型 */
	private String distance;

	public void setDistanceNumber(Integer distanceNumber) {
		this.distanceNumber = distanceNumber;
		this.distance = ServletUtil.calcDistance(distanceNumber); // 计算距离
	}

	public ToolsInfo() {
	}

	public ToolsInfo(Users user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users users) {
		this.user = users;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getToolDescription() {
		return toolDescription;
	}

	public void setToolDescription(String toolDescription) {
		this.toolDescription = toolDescription;
	}

	public Double getToolRent() {
		return toolRent;
	}

	public void setToolRent(Double toolRent) {
		this.toolRent = toolRent;
	}

	public SharedTypes getSharedType() {
		return sharedType;
	}

	public void setSharedType(SharedTypes sharedType) {
		this.sharedType = sharedType;
	}

	public Image getPicture1() {
		return picture1;
	}

	public void setPicture1(String name) {
		this.picture1.setName(name);
	}

	public Image getPicture2() {
		return picture2;
	}

	public void setPicture2(String name) {
		this.picture2.setName(name);
	}

	public Image getPicture3() {
		return picture3;
	}

	public void setPicture3(String name) {
		this.picture3.setName(name);
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getDistanceNumber() {
		return distanceNumber;
	}

	public String getDistance() {
		return distance;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Users getUserB() {
		return userB;
	}

	public void setUserB(Users userB) {
		this.userB = userB;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}
