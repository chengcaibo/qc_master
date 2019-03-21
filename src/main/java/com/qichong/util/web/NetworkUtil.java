package com.qichong.util.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * 网络连接工具类
 *
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017年11月7日 10:31:36
 */
public class NetworkUtil {

	/** 发起Post请求 */
	public static String sendPost(String url, String data) throws Exception {
		InputStream is = sendPostReturnInputStream(url, data);

		BufferedReader in = new BufferedReader(new InputStreamReader(is));

		String line, result = "";
		while ((line = in.readLine()) != null) {
			result += line;
		}
		// 关闭释放资源
		in.close();
		is.close();
		return result;
	}

	/** 发起POST请求，并返回 InputStream */
	public static InputStream sendPostReturnInputStream(String url, String data) throws Exception {
		// 创建连接
		URLConnection conn = new URL(url).openConnection();
		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// 获取URLConnection对象对应的输出流
		PrintWriter out = new PrintWriter(conn.getOutputStream());
		// 发送请求参数
		out.print(data);
		// flush输出流的缓冲
		out.flush();
		out.close();
		// 读取输入流
		return conn.getInputStream();
	}

	/**
	 * 发起Get请求
	 */
	public static String sendGet(String url, String data) throws IOException {
		// 创建连接
		URLConnection conn = new URL(url + "?" + data).openConnection();
		// 建立连接
		conn.connect();
		// 读取输入流
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line, result = "";
		while ((line = in.readLine()) != null) {
			result += line;
		}
		// 关闭释放资源
		in.close();
		return result;
	}

}
