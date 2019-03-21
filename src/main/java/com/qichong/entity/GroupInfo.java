package com.qichong.entity;

import java.util.Date;
import com.qichong.enums.PathEnum;
import com.qichong.model.Image;
import com.qichong.util.web.ServletUtil;

/**
 * 实体类 团体 对应表 group
 */
public class GroupInfo {

	private Integer id; // id
	private Users user;// 所属用户id，根据id得知所属企业还是个人
	private UserInfo userInfo;
	private EnterpriseInfo enterpriseInfo;
	private String groupName;// 团体名称
	private Integer quantity;// 人员数量

	private Integer maxQuantity;// 最大人员数量，用于补差价

	private Image picture = new Image();// 团体封面
	private String introduce;// 团体介绍
	private String personnelCost;// 团体人员费用
	private String toolCost;// 团体工具费用
	private IndustryType industry;
	private Regions region;// 地区编号
	private Date createTime;// 团体创建时间
	private String address;// 详细地址
	private String industryDetails;
	private String personnelExplain;// 人员说明
	private String toolExplain;// 工具说明

	private State state;// 状态外键

	public GroupInfo() {
	}

	public GroupInfo(Integer id) {
		this.id = id;
	}

	/** id，图片路径 */
	public GroupInfo(Integer id, String picture) {
		this.id = id;
		this.setPicture(picture);
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

	public void setUser(Users user) {
		this.user = user;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Image getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture.setPath(PathEnum.GROUP_COVER);
		this.picture.setName(picture);
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = ServletUtil.codeFormat(introduce);
	}

	public String getPersonnelCost() {
		return personnelCost;
	}

	public void setPersonnelCost(String personnelCost) {
		this.personnelCost = personnelCost;
	}

	public String getToolCost() {
		return toolCost;
	}

	public void setToolCost(String toolCost) {
		this.toolCost = toolCost;
	}

	public Regions getRegion() {
		return region;
	}

	public void setRegion(Regions region) {
		this.region = region;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public IndustryType getIndustry() {
		return industry;
	}

	public void setIndustry(IndustryType industry) {
		this.industry = industry;
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

	public String getPersonnelExplain() {
		return personnelExplain;
	}

	public void setPersonnelExplain(String personnelExplain) {
		this.personnelExplain = personnelExplain;
	}

	public String getToolExplain() {
		return toolExplain;
	}

	public void setToolExplain(String toolExplain) {
		this.toolExplain = toolExplain;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Integer getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(Integer maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	@Override
	public String toString() {
		return "GroupInfo [id=" + id + ", user=" + user + ", groupName=" + groupName + ", quantity=" + quantity
				+ ", picture=" + picture + ", introduce=" + introduce + ", personnelCost=" + personnelCost
				+ ", toolCost=" + toolCost + ", industry=" + industry + ", region=" + region + ", createTime="
				+ createTime + ", address=" + address + ", industryDetails=" + industryDetails + ", personnelExplain="
				+ personnelExplain + ", toolExplain=" + toolExplain + "]";
	}

}
