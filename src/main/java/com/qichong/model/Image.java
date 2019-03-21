package com.qichong.model;

import com.qichong.enums.PathEnum;
import com.qichong.util.web.ServletUtil;

/**
 * 图片 Model，所有跟图片有关的字段都使用这个类型<br>
 * <br>
 * <b>使用方法：</b><br>
 * <ol>
 * <li>若要实例化这个对象，则必须要提供一个路径，这个路径已经被提前定义好了，你可以在 {@link com.qichong.enums.PathEnum
 * PathEnum} 中找到，若没有找到请自行添加一个（只能添加，不能修改）</li>
 * <li>name 字段必须要实例化，你可以使用构造函数或对象名打点调 <code>setName()</code>
 * 方法来赋值，若未实例化就使用，则会抛出一个异常</li>
 * <li>若要获取路径，你可以对象名打点调 <code>toString()</code> 或者 <code>getUrl()</code>
 * 方法返回整体路径</li>
 * <li>你可以对象名打点调 <code>getName()</code> 方法可以返回不带路径的文件名</li>
 * </ol>
 * 
 * @author 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017-11-27 15:44:46
 */
public class Image {

	/** 域名路径 */
	private transient final String DOMAIN = "https://img.qc1318.com";
	/** 图片文件夹路径 */
	private transient final String PARENT = "/img/";

	private String path;
	private String name;
	private String url;

	private transient PathEnum pathEnum;

	public Image() {
	}

	/**
	 * 拼接路径 <br>
	 * path = "/img/" + path + "/";
	 */
	public Image(PathEnum path) {
		this.setPath(path);
	}

	/**
	 * 拼接路径 <br>
	 * path = "/img/" + path + "/";
	 */
	public Image(PathEnum path, String name) {
		this.name = name;
		this.setPath(path);
	}

	public String getPath() {
		return path;
	}

	public PathEnum getPathEnum() {
		return pathEnum;
	}

	/**
	 * 拼接路径 <br>
	 * path = "/img/" + path + "/";
	 */
	public void setPath(PathEnum path) {
		this.pathEnum = path;
		this.path = DOMAIN + PARENT + path.getPath() + "/";
		this.url = this.path + this.name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.url = this.path + this.name;
	}

	/**
	 * 获取整个 url 路径，若name字段尚未实例化，则会抛出一个异常
	 */
	public String getUrl() {
		if (ServletUtil.isEmpty(this.name)) {
			throw new RuntimeException("Image类运行时异常：\"name\"字段尚未实例化！");
		} else if (ServletUtil.isEmpty(this.path)) {
			throw new RuntimeException("Image类运行时异常：\"path\"字段尚未实例化！");
		} else {
			return url;
		}
	}

	@Override
	public String toString() {
		if (ServletUtil.isEmpty(this.name)) {
			return "";
		}
		return this.getUrl();
	}

	public String toString(boolean a) {
		return "";
	}

}
