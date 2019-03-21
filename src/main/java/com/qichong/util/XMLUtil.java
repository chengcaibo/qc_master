package com.qichong.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.dom4j.CDATA;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class XMLUtil {

	public static JsonObject xmlToJson(String xml) {
		try {
			// 解析xml字符串
			Document document = DocumentHelper.parseText(xml); // 将字符串转为XML
			return xmlToJson(document);
		} catch (DocumentException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static JsonObject xmlToJson(Document document) {
		JsonObject jsonObject = new JsonObject();
		Element root = document.getRootElement(); // 获取根节点
		// 遍历根节点
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		for (Element element : list) {
			jsonObject.addProperty(element.getName(), getStringValue(element));
		}
		return jsonObject;
	}

	/**
	 * 获取节点的string值
	 * 
	 * @param element
	 *            xml节点
	 * @return 值
	 */
	public static String getStringValue(Element element) {
		return element.getStringValue().trim();
	}

	/**
	 * 获取节点的string值
	 * 
	 * @param element
	 *            xml节点
	 * @param childName
	 *            子节点名称
	 * @return 子节点的值
	 */
	public static String getStringValue(Element element, String childName) {
		return getStringValue(element.element(childName));
	}

	/**
	 * 将JSON转为XML
	 * 
	 * @param jsonString
	 *            要转换的JSON字符串
	 * @return xml文本
	 * @throws IOException
	 */
	public static String JsonToXml(String jsonString) throws IOException {

		JsonObject json = new Gson().fromJson(jsonString, JsonObject.class);
		return JsonToXml(json);
	}

	/**
	 * 将JSON字符串转为XML
	 * 
	 * @param json
	 *            要转换的JSON对象
	 * @return xml文本
	 * @throws IOException
	 */
	public static String JsonToXml(JsonObject json) throws IOException {
		String[] keys = JSONUtil.getJsonObjectKeys(json, true);
		// 创建document节点
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		// 创建根节点
		Element root = document.addElement("xml");
		// 遍历添加属性
		for (String key : keys) {
			root.add(createElement(key, json.get(key).getAsString()));
		}
		return documentToString(document);
	}

	/** 创建XML节点 */
	public static Element createElement(String name, String text) {
		Element element = DocumentHelper.createElement(name);
		CDATA cdata = DocumentHelper.createCDATA(text);
		element.add(cdata);
		return element;
	}

	/** XMLDocument转为String */
	public static String documentToString(Document document) throws IOException {
		OutputFormat format = new OutputFormat(" ", true);
		format.setEncoding("UTF-8");
		StringWriter out = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(out, format);
		// 写出xml
		xmlWriter.write(document);
		xmlWriter.flush();
		String xml = out.toString();
		// 关闭输出器的流
		xmlWriter.close();
		out.close();
		return xml;
	}

}
