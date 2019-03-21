package com.qichong.entity;

import com.qichong.model.Image;
/**
 * 
 * @author wuzhiwei
 * 企业产品信息类
 */
public class WebSite {
	private int id;
	private Image enterprisePicture = new Image();
	private Image productPicture = new Image();
	private String product_introduce;
	private String phone;
	private String email;

	public WebSite() {
	}
	public WebSite(Image enterprisePicture, Image productPicture, String product_introduce, String phone,
			String email) {
		super();
		this.enterprisePicture = enterprisePicture;
		this.productPicture = productPicture;
		this.product_introduce = product_introduce;
		this.phone = phone;
		this.email = email;
	}


	public WebSite(int id, Image enterprisePicture, Image productPicture, String product_introduce, String phone,
			String email) {
		super();
		this.id = id;
		this.enterprisePicture = enterprisePicture;
		this.productPicture = productPicture;
		this.product_introduce = product_introduce;
		this.phone = phone;
		this.email = email;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Image getEnterprisePicture() {
		return enterprisePicture;
	}

	public void setEnterprisePicture(Image enterprisePicture) {
		this.enterprisePicture = enterprisePicture;
	}

	public Image getProductPicture() {
		return productPicture;
	}

	public void setProductPicture(Image productPicture) {
		this.productPicture = productPicture;
	}

	public String getProduct_introduce() {
		return product_introduce;
	}

	public void setProduct_introduce(String product_introduce) {
		this.product_introduce = product_introduce;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "WebSite [id=" + id + ", enterprisePicture=" + enterprisePicture + ", productPicture=" + productPicture
				+ ", product_introduce=" + product_introduce + ", phone=" + phone + ", email=" + email + "]";
	}

}
