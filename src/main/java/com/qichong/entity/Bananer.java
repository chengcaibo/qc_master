package com.qichong.entity;

/***
 * 新闻轮播实体类
 * 
 * @创建人：陈文瑾 @时间：2017年12月14日10:08:22
 *
 */
public class Bananer {

	/***
	 * 字段
	 */
	private int id;// id
	private String picture;// 图片
	private String industryName;// 行业名称

	/**
	 * 构造函数
	 * 
	 * @param id
	 */
	public Bananer(int id) {
		super();
		this.id = id;
	}


	public Bananer(Integer id, String picture, String industryName) {
		super();
		this.id = id;
		this.picture = picture;
		this.industryName = industryName;
	}




	/***
	 * getter/setter
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	/**
	 * toString
	 */
	
	@Override
	public String toString() {
		return "Bananer [id=" + id + ", picture=" + picture + ", industryName=" + industryName + "]";
	}

	

	
	

}
