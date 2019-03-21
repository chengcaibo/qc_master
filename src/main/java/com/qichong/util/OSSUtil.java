package com.qichong.util;

import java.io.*;
import java.net.URL;
import java.util.Date;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.qichong.enums.PathEnum;

import org.springframework.util.StringUtils;

/**
 * 阿里云 OSS文件类
 *
 * @author YuanDuDu
 */
public class OSSUtil {

	private static String endpoint;
	private static String accessKeyId;
	private static String accessKeySecret;
	private static String bucketName;

	static {
		accessKeyId = "LTAIl0Q18maq7QUk";
		accessKeySecret = "oQOXZ8B9rffHTqvhTK8zcfOfTFU0s8";
		bucketName = "qc-master";

		String osName = System.getProperty("os.name");
		System.out.println("[DEBUG：OSS]当前操作系统为：" + osName);
		if (osName.equalsIgnoreCase("Linux")) {
			endpoint = "http://oss-cn-beijing-internal.aliyuncs.com";
			System.out.println("[DEBUG：OSS]已将endpoint调为内网");
		} else {
			endpoint = "http://oss-cn-beijing.aliyuncs.com";
			System.out.println("[DEBUG：OSS]已将endpoint调为外网");
		}
	}

	// 文件存储目录
	private String dir = "img/";

	private OSSClient ossClient;

	// public OSSUtil() {
	// ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	// }

	public OSSUtil(String dir) {
		ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		this.dir = dir;
	}

	public OSSUtil(PathEnum path) {
		ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		this.dir = getDir(path);
	}

	private String getDir(PathEnum path) {
		return "img/" + path.getPath() + "/";
	}

	/**
	 * 初始化
	 */
	public void init() {
		ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	}

	/**
	 * 销毁
	 */
	public void destory() {
		ossClient.shutdown();
	}

	public void deleteOneFile(String fileName) {
		ossClient.deleteObject(bucketName, dir + fileName);
	}

	/**
	 * 获得图片路径
	 *
	 * @param fileUrl
	 * @return
	 */
	public String getImgUrl(String fileUrl) {
		if (!StringUtils.isEmpty(fileUrl)) {
			String[] split = fileUrl.split("/");
			return this.getUrl(dir + split[split.length - 1]);
		}
		return null;
	}

	/**
	 * 上传到OSS服务器 如果同名文件会覆盖服务器上的
	 *
	 * @param path
	 *            上传路径
	 * @param instream
	 *            文件流
	 * @param fileName
	 *            文件名称 包括后缀名
	 * @return 出错返回"" ,唯一MD5数字签名
	 */
	public String uploadFile2OSS(PathEnum path, InputStream instream, String fileName) {
		this.dir = getDir(path);
		return this.uploadFile2OSS(instream, fileName);
	}

	/**
	 * 上传到OSS服务器 如果同名文件会覆盖服务器上的
	 *
	 * @param instream
	 *            文件流
	 * @param fileName
	 *            文件名称 包括后缀名
	 * @return 出错返回"" ,唯一MD5数字签名
	 */
	public String uploadFile2OSS(InputStream instream, String fileName) {
		String ret = "";
		try {
			// 创建上传Object的Metadata
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(instream.available());
			objectMetadata.setCacheControl("no-cache");
			objectMetadata.setHeader("Pragma", "no-cache");
			objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
			objectMetadata.setContentDisposition("inline;filename=" + fileName);
			// 上传文件
			PutObjectResult putResult = ossClient.putObject(bucketName, dir + fileName, instream, objectMetadata);
			ret = putResult.getETag();
		} catch (IOException e) {
			System.out.println("上传文件到OSS时发生异常");
			e.printStackTrace();
		} finally {
			try {
				if (instream != null) {
					instream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	/**
	 * 获得url链接
	 *
	 * @param key
	 * @return
	 */
	public String getUrl(String key) {
		// 设置URL过期时间为10年 3600l* 1000*24*365*10
		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
		// 生成URL
		URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
		if (url != null) {
			return url.toString();
		}
		return null;
	}

	/**
	 * Description: 判断OSS服务文件上传时文件的contentType
	 *
	 * @param FilenameExtension
	 *            文件后缀
	 * @return String
	 */
	public static String getcontentType(String FilenameExtension) {
		if (FilenameExtension.equalsIgnoreCase(".bmp")) {
			return "image/bmp";
		}
		if (FilenameExtension.equalsIgnoreCase(".gif")) {
			return "image/gif";
		}
		if (FilenameExtension.equalsIgnoreCase(".jpeg") || FilenameExtension.equalsIgnoreCase(".jpg")
				|| FilenameExtension.equalsIgnoreCase(".png")) {
			return "image/jpeg";
		}
		if (FilenameExtension.equalsIgnoreCase(".html")) {
			return "text/html";
		}
		if (FilenameExtension.equalsIgnoreCase(".txt")) {
			return "text/plain";
		}
		if (FilenameExtension.equalsIgnoreCase(".vsd")) {
			return "application/vnd.visio";
		}
		if (FilenameExtension.equalsIgnoreCase(".pptx") || FilenameExtension.equalsIgnoreCase(".ppt")) {
			return "application/vnd.ms-powerpoint";
		}
		if (FilenameExtension.equalsIgnoreCase(".docx") || FilenameExtension.equalsIgnoreCase(".doc")) {
			return "application/msword";
		}
		if (FilenameExtension.equalsIgnoreCase(".xml")) {
			return "text/xml";
		}
		return "image/jpeg";
	}

}