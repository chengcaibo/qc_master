package com.qichong.util.wechat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class WeChatSSLNetworkUtil {

	private CloseableHttpClient httpClient;
	private HttpEntity responseEntity;

	public WeChatSSLNetworkUtil(String certPath, String password) throws Exception {
		try {
			char[] passwordChars = password.toCharArray();
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			FileInputStream instream = new FileInputStream(new File(certPath));
			// 加载证书
			try {
				keyStore.load(instream, passwordChars);
			} finally {
				instream.close();
			}
			// 信任自己的CA和所有自签名证书
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, passwordChars).build();
			// 仅允许TLSv1协议
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			// 创建HttpClient
			this.httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (Exception e) {
			throw e;
		}
	}

	public void close() throws Exception {
		if (httpClient != null)
			this.httpClient.close();
		if (responseEntity != null)
			EntityUtils.consume(responseEntity);// 关闭实体流
	}

	/**
	 * 发送Post请求，post二进制数据，并返回InputStream
	 * 
	 * @param url
	 *            要请求的地址
	 * @param binary
	 *            要传输的二进制参数
	 * 
	 * @return {@link InputStream}
	 * @throws Exception
	 */
	public InputStream sendPostBinaryReturnInputStream(String url, String binary) throws Exception {
		CloseableHttpResponse response = null;
		try {
			// 创建HttpPost
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(EntityBuilder.create().setBinary(binary.getBytes()).build());
			// 发送请求
			response = httpClient.execute(httpPost);
			responseEntity = response.getEntity();
			if (responseEntity != null)
				return responseEntity.getContent();
		} finally {
			if (response != null)
				response.close();
		}
		return null;
	}

	/**
	 * 发送Post请求，post二进制数据，并返回String
	 * 
	 * @param url
	 *            要请求的地址
	 * @param binary
	 *            要传输的二进制参数
	 * 
	 * @return {@link String}
	 * @throws Exception
	 */
	public String sendPostBinary(String url, String binary) throws Exception {
		InputStream is = this.sendPostBinaryReturnInputStream(url, binary);
		if (is != null) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
			StringBuffer sb = new StringBuffer();
			String text;
			while ((text = bufferedReader.readLine()) != null) {
				sb.append(text);
			}
			return sb.toString();
		}
		return null;
	}

}
