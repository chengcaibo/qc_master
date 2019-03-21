package com.qichong.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qichong.enums.OrderPrefix;
import com.qichong.enums.PathEnum;
import com.qichong.exception.QCErrorException;
import com.qichong.exception.QCErrorRuntimeException;

/**
 * 工具类
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017-11-27 15:45:12
 */
public class Utils {

	/**
	 * GSON
	 */
	public static Gson gson = new Gson();

	/** 溢出隐藏中间的字符串 */
	public static String overflowHiddenMiddleString(String text, int maxLength) {
		int length = text.length();
		if (length > --maxLength) {
			int diff = length - maxLength;
			int i = (int) maxLength / 2;
			int j = i + diff;
			String before = text.substring(0, i);
			String after = text.substring(j, length);
			return before + "…" + after;
		}
		return text;
	}

	/** 溢出隐藏末尾的字符串 */
	public static String overflowHiddenString(String text, int maxLength) {
		int length = text.length();
		if (length > --maxLength) {
			return text.substring(0, maxLength) + "…";
		}
		return text;

	}

	/** 传入一个时间，返回 该时间 是 当前时间 的多少时间 前 */
	public static String getDateAgo(Date date) {
		return getDateToDateAgo(date, new Date(), false, false, -1);
	}

	/**
	 * 传入两个时间，返回 date1 是 date2 的多少时间 前。最大返回 x年前 <br>
	 * <br>
	 * <b>例如</b><br>
	 * 传入的时间是：2018-11-12 17:00:00<br>
	 * 当前时间是：2018-11-12 18:00:00<br>
	 * 那么就会返回：1小时前
	 * 
	 * @param decimal
	 *            是否携带小数点
	 * 
	 * @param lessThanOneMinuteRetrunJust
	 *            小于一分钟前的时间返回刚刚
	 * @param maxHour
	 *            最大小时，-1为不限制
	 */
	public static String getDateToDateAgo(Date date1, Date date2, boolean decimal, boolean lessThanOneMinuteRetrunJust,
			int maxHour) {
		// 计算上次更新时间
		long begin = date1.getTime();
		long end = date2.getTime();
		Double ss = (end - begin) / 1000.0; // 秒
		Double mm = ss / 60; // 分
		Double hh = mm / 60; // 时
		Double dd = hh / 24; // 天
		Double MM = dd / 30; // 月
		Double yyyy = MM / 12; // 年

		if (maxHour != -1 && hh >= maxHour) {
			return maxHour + "小时前";
		}
		DecimalFormat df = new DecimalFormat("#.00");
		if (!decimal)
			df = new DecimalFormat("#");
		if (mm <= 1.0) {
			if (lessThanOneMinuteRetrunJust)
				return "刚刚";
			else
				return "1分钟内";
		} else if (mm >= 1 && mm <= 60) {
			return df.format(mm) + "分钟前";
		} else if (hh >= 1 && hh <= 24) {
			return df.format(hh) + "小时前";
		} else if (dd >= 1 && dd <= 30) {
			return df.format(dd) + "天前";
		} else if (MM >= 1 && MM <= 12) {
			return df.format(MM) + "月前";
		} else {
			return df.format(yyyy) + "年前";
		}
	}

	/** 创建新的订单号 */
	public static String createNewOrderId() {
		return createNewOrderId(OrderPrefix.EMPTY);
	}

	/** 创建新的订单号 */
	public static String createNewOrderId(OrderPrefix prefix) {
		// 订单号格式：日期精确到秒+随机六位数
		String currentDate = Utils.formatDate(new Date(), "yyyyMMddHHmmss");
		String randomNumber = Utils.randomNumber(6);
		return prefix.getPrefix() + currentDate + randomNumber;
	}

	/**
	 * 判断所给的异常里是否包含了期望的异常
	 * 
	 * @param th
	 *            所给的异常
	 * @param clazz
	 *            期望的异常clss
	 * @return 包含返回期望的异常，否则返回null
	 */
	public static Throwable isException(Throwable th, Class<?> clazz) {
		if (clazz != null)
			do {
				if (clazz.equals(th.getClass()) || clazz.equals(th.getClass()))
					return th;
				else
					th = th.getCause();
			} while (th != null);
		return null;
	}

	/** 判断是不是QCError异常，是返回QCError，否则返回null */
	public static Throwable isQCError(Throwable th) {
		Throwable rth = isException(th, QCErrorRuntimeException.class);
		if (rth != null)
			return rth;
		rth = isException(th, QCErrorException.class);
		if (rth != null)
			return rth;
		return null;
	}

	/** 获取文件上传路径 */
	public static String getUploadPath(HttpSession session) {
		return session.getServletContext().getRealPath("/upload");
	}

	/** 获取Log文件上传路径 */
	public static String getUploadLogPath(HttpSession session) {
		return session.getServletContext().getRealPath("E:/data/upload/userLog");
	}

	/**
	 * 重新调整头像大小并上传至阿里云
	 * 
	 * @param uploadPath
	 *            本地服务器的上传文件路径
	 * @param avatarFile
	 *            头像文件
	 * @param put
	 *            头像裁剪参数
	 * @param userId
	 *            头像对应用户的UserId
	 * @return 成功返回文件名，失败返回null
	 */
	public static String resizeAvatar(String uploadPath, MultipartFile avatarFile, String put, int userId) {
		try {
			// 输出到文件，这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
			String uploadFileName = "upload_" + avatarFile.getOriginalFilename();
			File uploadFile = new File(uploadPath, uploadFileName);
			FileUtils.copyInputStreamToFile(avatarFile.getInputStream(), uploadFile);
			// 获取裁剪参数
			int w, h, x, y;
			if (put != null) {
				// 从put中获取裁剪参数
				JsonObject putCrop = new Gson().fromJson(put, JsonObject.class);
				x = putCrop.get("x").getAsInt();
				y = putCrop.get("y").getAsInt();
				w = putCrop.get("width").getAsInt();
				h = putCrop.get("height").getAsInt();
			} else {
				// 从文件中算出裁剪参数
				int[] size = ImageUtils.getSizeInfo(uploadFile);
				int width = size[0], height = size[1];
				// 判断是长图还是宽图
				if (width > height) {
					// 宽度 > 高度 是 宽图
					w = height;
					h = height;
					x = (width / 2) - (height / 2);
					y = 0;
				} else {
					// 宽度 < 高度 是 长图
					w = width;
					h = width;
					x = 0;
					y = (height / 2) - (width / 2);
				}
			}
			// 进行文件裁剪操作
			File cropFile = new File(uploadPath, "crop_" + avatarFile.getOriginalFilename());
			ImageUtils.crop(uploadFile, cropFile, x, y, w, h);
			// 获取文件扩展名
			String suffix = Utils.getSuffix(avatarFile.getOriginalFilename());
			suffix = suffix.isEmpty() || suffix.equals(".jpeg") ? ".jpg" : suffix;
			// 生成文件名
			String avatarName = "400_" + userId + suffix;
			// 重新调整大小
			File resizeFile = new File(uploadPath, avatarName);
			ImageUtils.resize(cropFile, resizeFile, 400, 400);
			// 上传到阿里OSS
			InputStream stream = new FileInputStream(resizeFile);
			// 上传到阿里OSS
			String result = new OSSUtil(PathEnum.USER_AVATAR).uploadFile2OSS(stream, avatarName);
			// 删除上传的文件
			uploadFile.delete();
			cropFile.delete();
			resizeFile.delete();
			// 判断上传到阿里云结果
			if (!result.equals(""))
				return avatarName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * url地址去除参数
	 */
	public static String urlRemoveParameter(String url) {
		int index = url.indexOf("?");
		if (index != -1) {
			return url.substring(0, index);
		}
		return url;
	}

	/**
	 * 将一个JSON字符串反序列化成一个 <b>Map&lt;String, Object&gt;</b>
	 */
	public static Map<String, Object> jsonToMap(String json) {
		Type type = new TypeToken<HashMap<String, Object>>() {
		}.getType();
		Map<String, Object> map = gson.fromJson(json, type);
		return map;
	}

	/**
	 * 将一个JSON字符串反序列化成一个 <b>List&lt;Map&lt;String, Object&gt;&gt;</b>
	 */
	public static List<Map<String, Object>> jsonToListMap(String json) {
		Type type = new TypeToken<ArrayList<HashMap<String, Object>>>() {
		}.getType();
		List<Map<String, Object>> list = gson.fromJson(json, type);
		return list;
	}

	/**
	 * 根据生日计算年龄
	 * 
	 * @param birthday
	 *            生日
	 * @return 年龄
	 */
	public static Integer calcAgeByBirthday(Date birthday) {
		// 先截取到字符串中的年、月、日
		String strs[] = Utils.formatDate(birthday, "yyyy-MM-dd").trim().split("-");
		int birthdayYear = Integer.parseInt(strs[0]); // 获取出生年
		int birthdayMonth = Integer.parseInt(strs[1]); // 获取出生日
		int birthdayDay = Integer.parseInt(strs[2]);// 获取出生日

		// 得到当前时间的年、月、日
		Calendar cal = Calendar.getInstance();
		int todayYear = cal.get(Calendar.YEAR);
		int todayMonth = cal.get(Calendar.MONTH) + 1;
		int todayDay = cal.get(Calendar.DATE);

		int age = -1;
		if (todayMonth * 1 - birthdayMonth * 1 < 0) {
			age = (todayYear * 1 - birthdayYear * 1) - 1;
		} else {
			if (todayDay - birthdayDay >= 0) {
				age = (todayYear * 1 - birthdayYear * 1);
			} else {
				age = (todayYear * 1 - birthdayYear * 1) - 1;
			}
		}
		return age * 1;
	}

	public static String getDateYearDiffer(Date date) {
		return getDateToDateYearDiffer(new Date(), date);
	}

	/**
	 * 获取两个时间相差多少天
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static String getDateToDateYearDiffer(Date date1, Date date2) {
		long diff = date1.getTime() - date2.getTime(); // 获得两个时间的毫秒时间差异
		long day = diff / (1000 * 24 * 60 * 60);// 计算差多少天
		return new DecimalFormat("#0.0").format(day / 365.0);
	}

	/**
	 * 格式化时间
	 */
	public static String formatDate(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化时间
	 */
	public static String formatDate(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	/**
	 * 将String格式化成Date
	 * 
	 * @throws ParseException
	 *             如果传入的String不能格式化成Date的时候抛出的异常
	 */
	public static Date parseDate(String source) throws ParseException {
		return parseDate(source, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将String格式化成Date
	 * 
	 * @throws ParseException
	 *             如果传入的String不能格式化成Date的时候抛出的异常
	 */
	public static Date parseDate(String source, String fmt) throws ParseException {
		return new SimpleDateFormat(fmt).parse(source);
	}

	/**
	 * 计算时间
	 * 
	 * @param date
	 *            被计算的时间
	 * @param calcType
	 *            加 还是 减，非 "+" 即 减
	 * @param diff
	 *            要计算的时间
	 * @param dataType
	 *            时间类型，年、月、日（天）、时、分、秒
	 * @return 计算后的时间
	 */
	public static Date calcDate(Date date, String calcType, int diff, String dataType) {
		// 创建一个计算对象
		Calendar calc = Calendar.getInstance();
		calc.setTime(date);
		// 判断是 加 还是 减
		if (!calcType.equals("+")) {
			diff = 0 - diff;
		}
		switch (dataType) {
		case "年":
		case "year":
			calc.add(Calendar.YEAR, diff);
			break;
		case "月":
		case "month":
			calc.add(Calendar.MONTH, diff);
			break;
		case "周":
		case "星期":
		case "礼拜":
		case "week":
			calc.add(Calendar.DATE, diff * 7);
			break;
		case "日":
		case "天":
		case "day":
			calc.add(Calendar.DATE, diff);
			break;
		case "时":
		case "hour":
			calc.add(Calendar.HOUR, diff);
			break;
		case "分":
		case "minute":
			calc.add(Calendar.MINUTE, diff);
			break;
		case "秒":
		case "second":
			calc.add(Calendar.SECOND, diff);
			break;
		default:
			return null;
		}
		return calc.getTime();
	}

	/**
	 * 数字长度补齐
	 * 
	 * @param number
	 *            要补全的数字
	 * @param length
	 *            要补全几位
	 * @return
	 */
	public static String numberFull(int number, int length) {
		String fullStr = "0";
		String allStr = "";
		for (int i = 0; i < length; i++) {
			allStr += fullStr;
		}
		DecimalFormat df = new DecimalFormat(allStr);
		return df.format(number);
	}

	/**
	 * 生成随机整数
	 * 
	 * @param min
	 *            最小数
	 * @param max
	 *            最大数
	 * @param length
	 *            长度，不全位用0补齐
	 */
	public static String randomNumber(int min, int max, int length) {
		double random = Math.random();
		int num = (int) (random * max + min);
		return numberFull(num, length);
	}

	/**
	 * 生成随机整数
	 * 
	 * @param min
	 *            最小数
	 * @param max
	 *            最大数
	 */
	public static int randomNumber(int min, int max) {
		double random = Math.random();
		int num = (int) (random * max + min);
		return num;
	}

	/**
	 * 生成随机整数，返回字符串类型
	 * 
	 * @param length
	 *            随机数长度
	 */
	public static String randomNumber(int length) {
		String str = "";
		for (int i = 0; i < length; i++) {
			int random = new Random().nextInt(10);
			random = random == 0 ? 1 : random;
			str += random;
		}
		return str;
	}

	private static final String STRINGS = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789";

	/**
	 * 生成随机字符串，默认生成 [a-z|A-Z|0-9]范围内的字符
	 * 
	 * @param length
	 *            随机字符串长度
	 */
	public static String randomString(int length) {
		String str = "";
		for (int i = 0; i < length; i++) {
			int rdm = new Random().nextInt(STRINGS.length());
			str += STRINGS.substring(rdm, rdm + 1);
		}
		return str;
	}

	/**
	 * 获取文件名
	 * 
	 * @param path
	 *            要获取的路径
	 */
	public static String getFileName(String path) {
		path = path.trim();
		try {
			path = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String name = path.substring(path.lastIndexOf("/") + 1);
		if (name.isEmpty()) {
			name = path.substring(path.lastIndexOf("\\") + 1);
			if (name.isEmpty()) {
				return path;
			}
		}
		// 截取问号
		int indexOf = name.indexOf("?");
		name = name.substring(0, indexOf == -1 ? name.length() : indexOf);
		return name;
	}

	/**
	 * 获取扩展名,带点
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getSuffix(String fileName) {
		if (fileName != null && fileName.length() > 0 && fileName.lastIndexOf(".") > -1) {
			return fileName.substring(fileName.lastIndexOf("."));
		}
		return "";
	}

	/**
	 * 创建完整路径
	 *
	 * @param path
	 *            要创建的路径
	 */
	public static final void mkdirs(final String... path) {
		for (String foo : path) {
			final String realPath = FilenameUtils.normalizeNoEndSeparator(foo, true);
			final File folder = new File(realPath);
			if (!folder.exists() || folder.isFile()) {
				folder.mkdirs();
			}
		}
	}
}
