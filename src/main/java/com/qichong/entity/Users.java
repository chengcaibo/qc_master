package com.qichong.entity;

import java.text.DecimalFormat;
import java.util.Date;

import com.qichong.enums.StateEnum;

/**
 * 
 * 用户表实体类（Users）
 * 
 * @创建人 吴志伟
 * @修改人 孙建雷
 * @修改时间 2017-11-20 14:19:53
 */
public class Users {

	private Integer id; // id
	private String username; // 用户名
	private String password;// 密码
	private Date createTime; // 这个账户的创建时间，即用户的注册时间
	private String wxOpenId; // 微信的OpenId
	private String wxUnionId; // 微信的UnionId

	private Double longitude; // 用户所在的经度
	private Double latitude; // 用户所在的纬度
	private Date lastUpdateTime; // 上一次更新时间，用来判断用户几小时前出现在此位置

	private Integer typeId;

	private State state;

	private UserInfo userInfo;// 虚拟字段，用户信息
	private EnterpriseInfo enterpriseInfo;// 虚拟字段，企业信息

	/** 虚拟字段：距离数字原形，仅在筛选距离后使用，表示的是该用户距筛选用户的距离（米） */
	private Integer distanceNumber;
	/** 虚拟字段：将距离转为可识别字符串类型 */
	private String distance;

	public void setDistanceNumber(Integer distanceNumber) {
		this.distanceNumber = distanceNumber;

		// 计算距离
		if (distanceNumber != null) {
			DecimalFormat df = new DecimalFormat("#.00");
			if (distanceNumber >= 1000) {
				String d = df.format(distanceNumber / 1000.0);
				this.distance = d + "km";
			} else if (distanceNumber < 10) {
				this.distance = "10m 以内";
			} else {
				this.distance = distanceNumber + "m";
			}
		}
	}

	public Users() {
	}

	public Users(Integer id) {
		this.id = id;
	}

	/** 这个构造函数是 Cookie 用来登录的 */
	public Users(int id, String password) {
		this.id = id;
		this.password = password;
	}

	/** 这个构造函数是用来更新用户的位置的 */
	public Users(int id, Double longitude, Double latitude) {
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.lastUpdateTime = new Date();
	}

	public Users(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Users(String username, String password, Date createTime, int typeId) {
		this.username = username;
		this.password = password;
		this.createTime = createTime;
		this.typeId = typeId;
	}

	public Integer getId() {
		return id;
	}

	/** 用户所在的经度 */
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/** 用户所在的纬度 */
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/** 上一次更新时间，用来判断用户几小时前出现在此位置 */
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;

		// 计算上次更新时间
		if (lastUpdateTime != null) {
			DecimalFormat df = new DecimalFormat("#.00");
			long begin = lastUpdateTime.getTime();
			long end = System.currentTimeMillis();
			Double ss = (end - begin) / 1000.0; // 秒
			Double mm = ss / 60; // 分
			Double hh = mm / 60; // 时
			Double dd = hh / 24; // 天
			Double MM = dd / 30; // 月
			Double yyyy = MM / 12; // 年
			if (mm <= 1.0) {
				this.lastUpdateTimeStr = "1分钟内";
			} else if (mm >= 1 && mm <= 60) {
				this.lastUpdateTimeStr = df.format(mm) + "分钟前";
			} else if (hh >= 1 && hh <= 24) {
				this.lastUpdateTimeStr = df.format(hh) + "小时前";
			} else if (dd >= 1 && dd <= 30) {
				this.lastUpdateTimeStr = df.format(dd) + "天前";
			} else if (MM >= 1 && MM <= 12) {
				this.lastUpdateTimeStr = df.format(MM) + "月前";
			} else {
				this.lastUpdateTimeStr = df.format(yyyy) + "年前";
			}
		}
	}

	public Integer getDistanceNumber() {
		return distanceNumber;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	private String lastUpdateTimeStr;

	/** 获取上一次更新的时间 */
	public String getLastUpdateTimeStr() {
		return this.lastUpdateTimeStr;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getWxOpenId() {
		return wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public EnterpriseInfo getEnterpriseInfo() {
		return enterpriseInfo;
	}

	public void setEnterpriseInfo(EnterpriseInfo enterpriseInfo) {
		this.enterpriseInfo = enterpriseInfo;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setState(StateEnum state) {
		this.state = new State(state);
	}

	public String getWxUnionId() {
		return wxUnionId;
	}

	public void setWxUnionId(String wxUnionId) {
		this.wxUnionId = wxUnionId;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", password=" + password + ", createTime=" + createTime
				+ ", wxOpenId=" + wxOpenId + ", longitude=" + longitude + ", latitude=" + latitude + ", lastUpdateTime="
				+ lastUpdateTime + ", typeId=" + typeId + ", state=" + state + ", userInfo=" + userInfo
				+ ", enterpriseInfo=" + enterpriseInfo + ", distanceNumber=" + distanceNumber + ", distance=" + distance
				+ ", lastUpdateTimeStr=" + lastUpdateTimeStr + "]";
	}

}
