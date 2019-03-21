package com.qichong.entity;

import com.qichong.util.web.ServletUtil;

/**
 * 动态新闻表实体类
 * 
 * @创建人：陈文瑾 @创建时间：2017年12月14日15:22:56
 *
 */

public class NewsInformation {

	// 字段
	private int id;// 新闻行业id
	private String industryName;// 行业
	private String headings;// 新闻标题
	private String content;// 新闻内容
	private String source;// 新闻来源

	/**
	 * 构造函数
	 */
	public NewsInformation() {
		super();
	}

	public NewsInformation(int id) {
		super();
		this.id = id;
	}

	public NewsInformation(int id, String industryName, String headings, String content, String source) {
		super();
		this.id = id;
		this.industryName = industryName;
		this.headings = headings;
		this.content = content;
		this.source = source;
	}

	/**
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

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getHeadings() {
		return headings;
	}

	public void setHeadings(String headings) {
		this.headings = ServletUtil.codeFormat(headings);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = ServletUtil.codeFormat(content);
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = ServletUtil.codeFormat(source);
	}

	/**
	 * toString
	 */
	@Override
	public String toString() {
		return "NewsInformation [id=" + id + ", industryName=" + industryName + ", headings=" + headings + ", content="
				+ content + ", source=" + source + "]";
	}

}
