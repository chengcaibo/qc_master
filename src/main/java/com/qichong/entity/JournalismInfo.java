package com.qichong.entity;

import com.qichong.util.web.ServletUtil;

/***
 * @标题：新闻实体类
 * @创建者：陈文瑾
 * @时间 2017年12月13日11:29:09
 */
public class JournalismInfo {
	// 字段
	private int id;// 新闻行业id
	private String industryName;// 行业
	private String headings;// 新闻标题
	private String content;// 新闻内容
	private String source;// 新闻来源

	// 构造
	public JournalismInfo() {}

	public JournalismInfo(int id) {
		super();
		this.id = id;
	}

	public JournalismInfo(int id, String industryName, String headings, String content, String source) {
		super();
		this.id = id;
		this.industryName = industryName;
		this.headings = headings;
		this.content = content;
		this.source = source;
	}

	// getter/setter
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

	@Override
	public String toString() {
		return "JournalismInfo [id=" + id + ", industryName=" + industryName + ", headings=" + headings + ", content="
				+ content + ", source=" + source + "]";
	}

}
