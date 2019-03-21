package com.qichong.entity;

/**
 * 区域表（regions）表实体类
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017年11月9日 下午6:12:23
 */
public class Regions {

	private String regionCode; // 地区
	private String regionName;
	private String parentRegionCode;

	public Regions() {
	}

	public Regions(String regionCode) {
		this.regionCode = regionCode;
	}

	public Regions(String regionCode, String regionName, String parentRegionCode) {
		this.regionCode = regionCode;
		this.regionName = regionName;
		this.parentRegionCode = parentRegionCode;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getParentRegionCode() {
		return parentRegionCode;
	}

	public void setParentRegionCode(String parentRegionCode) {
		this.parentRegionCode = parentRegionCode;
	}

	@Override
	public String toString() {
		return "RegionsCopy [regionCode=" + regionCode + ", regionName=" + regionName + ", parentRegionCode="
				+ parentRegionCode + "]";
	}
}