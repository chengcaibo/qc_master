package com.qichong.entity;

/**
 * 
 * Share 分享实体类
 * 
 * @创建人 徐龙洋
 * @修改人 徐龙洋
 * @修改时间 2018-4-17
 */
public class Share {
	private int id;// id
	private int userid;// 用户ID
	private int share_num;// 分享次数
	private String yaoqingma;// 邀请码
	
	public Share(){
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	public int getShareNum() {
		return share_num;
	}

	public void setShareNum(int share_num) {
		this.share_num = share_num;
	}
	
	public String getYaoqingma() {
		return yaoqingma;
	}

	public void setYaoqingma(String yaoqingma) {
		this.yaoqingma = yaoqingma;
	}
}
