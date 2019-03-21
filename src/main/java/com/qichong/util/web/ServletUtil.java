package com.qichong.util.web;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qichong.entity.EnterpriseInfo;
import com.qichong.entity.IndustryType;
import com.qichong.entity.JobInfo;
import com.qichong.entity.Regions;
import com.qichong.entity.SharedTypes;
import com.qichong.entity.UserInfo;
import com.qichong.entity.Users;
import com.qichong.enums.PathEnum;
import com.qichong.enums.RetEnum;
import com.qichong.model.Image;
import com.qichong.service.EnterpriseInfoService;
import com.qichong.service.IndustryTypeService;
import com.qichong.service.JobInfoService;
import com.qichong.service.RegionsService;
import com.qichong.service.SharedTypeService;
import com.qichong.service.UsersService;
import com.qichong.task.TimedTask;
import com.qichong.token.LoginToken;
import com.qichong.util.EncryptUtil;
import com.qichong.util.Utils;

/**
 * Servlet 工具类
 * 
 * @author 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017年11月28日 下午1:41:21
 */
public class ServletUtil {
	// 合法域名列表
	private static List<String> legalOriginList = new ArrayList<String>();

	static {
		// 配置合法Origini
		legalOriginList.add("m.luocat.com");
		legalOriginList.add("m.qc1318.com");

		String osName = System.getProperty("os.name");
		System.out.println("[DEBUG：SystemOS]当前操作系统为：" + osName);
		if (osName.equalsIgnoreCase("Linux") || "Windows Server 2012 R2".equalsIgnoreCase(osName)) {
			System.out.println("[DEBUG：LegalOriginList]已不允许null和localhost为合法Origin");

			// 计时任务
			TimedTask task = new TimedTask();
			task.init();
			task.runAll();
		} else {
			legalOriginList.add("null");
			legalOriginList.add("localhost");
			legalOriginList.add("localhost:9080");
			System.out.println("[DEBUG：LegalOriginList]已允许null和localhost为合法Origin");
		}

	}

	/**
	 * 返回 JSON 时使用的 produces<br>
	 * ServletUtil.JSON_PRODUCES
	 */
	public static final String JSON_PRODUCES = "application/json;charset=utf-8";

	/**
	 * 返回错误页面
	 * 
	 * @param title
	 *            标题
	 * @param body
	 *            提示内容
	 * @param model
	 *            modal
	 */
	public static String returnErrorPage(String title, String body, Model model) {
		return returnErrorPage(title, body, false, model);
	}

	/**
	 * 返回错误页面
	 * 
	 * @param title
	 *            标题
	 * @param body
	 *            提示内容
	 * @param returnHome
	 *            是否显示返回主页按钮
	 * @param model
	 *            modal
	 */
	public static String returnErrorPage(String title, String body, boolean returnHome, Model model) {
		model.addAttribute("noReturnHome", !returnHome);
		model.addAttribute("errorTitle", title);
		model.addAttribute("errorBody", body);
		return "error";
	}

	/** 计算距离 */
	public static String calcDistance(Integer distanceNumber) {
		if (distanceNumber != null) {
			DecimalFormat df = new DecimalFormat("#.00");
			if (distanceNumber >= 1000) {
				String d = df.format(distanceNumber / 1000.0);
				return d + "km";
			} else if (distanceNumber < 10) {
				return "10m 以内";
			} else {
				return distanceNumber + "m";
			}
		}
		return "";
	}

	/** 检查Origin是否合法 */
	public static boolean checkOriginISLegal(String origin) {
		origin = String.valueOf(origin);
		for (String string : legalOriginList) {
			if (origin.equals(string)) {
				return true;
			} else if (origin.equals("http://" + string)) {
				return true;
			} else if (origin.equals("https://" + string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据code查询所有的子级以及子级的所有子级
	 * 
	 * @param code
	 *            待查询的code
	 * @param service
	 *            Service层对象，必须是子父关系的
	 */
	public static String queryAllChildCodes(String code, Object service) {
		if (code != null) {
			List<String> list;
			if (service instanceof RegionsService) {
				list = queryAllChild(code, (RegionsService) service);
			} else if (service instanceof IndustryTypeService) {
				list = queryAllChild(code, (IndustryTypeService) service);
			} else if (service instanceof JobInfoService) {
				list = queryAllChild(code, (JobInfoService) service);
			} else if (service instanceof SharedTypeService) {
				list = queryAllChild(code, (SharedTypeService) service);
			} else {
				return null;
			}
			if (list != null)
				return ServletUtil.listStringToString(list, ", ");
		}
		return null;
	}

	private static Map<String, List<String>> regionAllChild = new HashMap<String, List<String>>();

	/** 根据地区code查询所有的子级以及子级的所有子级 */
	public static List<String> queryAllChild(String code, RegionsService service) {
		if (code == null || code == "0" || code == "")
			return null; // 不允许查询最大级别 0
		// 从缓存中查询是否有数据
		List<String> list = regionAllChild.get(code);
		if (list != null)
			return list;
		// 定义总列表，所有的子级将加入到此列表中
		list = new ArrayList<String>();
		// 定义查询的code列表
		List<String> codes = new ArrayList<String>();
		codes.add(code);
		do {
			// 将查询列表添加到总列表中
			list.addAll(codes);
			// 查询列表中的所有子级别
			List<Regions> temp = service.queryChildByCodes(codes);
			// 遍历子级别所有的code并添加到下次循环的code列表
			List<String> nextCodes = new ArrayList<String>();
			for (Regions region : temp) {
				nextCodes.add(region.getRegionCode());
			}
			// 赋值给查询code列表并进入下一次循环
			codes = new ArrayList<String>(nextCodes);
		} while (codes.size() > 0); // 如果没有子级了则退出循环
		// 加入缓存
		regionAllChild.put(code, list);
		return list;
	}

	private static Map<String, List<String>> industryAllChild = new HashMap<String, List<String>>();

	/** 根据地区code查询所有的子级以及子级的所有子级 */
	public static List<String> queryAllChild(String code, IndustryTypeService service) {
		if (code == null || code == "0" || code == "")
			return null;
		List<String> list = industryAllChild.get(code);
		if (list != null)
			return list;
		list = new ArrayList<String>();
		List<String> codes = new ArrayList<String>();
		codes.add(code);
		do {
			list.addAll(codes);
			List<IndustryType> temp = service.queryChild(codes);
			List<String> nextCodes = new ArrayList<String>();
			for (IndustryType industryType : temp) {
				nextCodes.add(industryType.getIndustryCode());
			}
			codes = new ArrayList<String>(nextCodes);
		} while (codes.size() > 0);
		industryAllChild.put(code, list);
		return list;
	}

	private static Map<String, List<String>> jobAllChild = new HashMap<String, List<String>>();

	/** 根据地区code查询所有的子级以及子级的所有子级 */
	public static List<String> queryAllChild(String code, JobInfoService service) {
		if (code == null || code == "0" || code == "")
			return null;
		List<String> list = jobAllChild.get(code);
		if (list != null)
			return list;
		list = new ArrayList<String>();
		List<String> codes = new ArrayList<String>();
		codes.add(code);
		do {
			list.addAll(codes);
			List<JobInfo> temp = service.queryChild(codes);
			List<String> nextCodes = new ArrayList<String>();
			for (JobInfo jobInfo : temp) {
				nextCodes.add(jobInfo.getJobCode());
			}
			codes = new ArrayList<String>(nextCodes);
		} while (codes.size() > 0);
		jobAllChild.put(code, list);
		return list;
	}

	private static Map<String, List<String>> sharedTypesAllChild = new HashMap<String, List<String>>();

	/** 根据地区code查询所有的子级以及子级的所有子级 */
	public static List<String> queryAllChild(String code, SharedTypeService service) {
		if (code == null || code == "0" || code == "")
			return null;
		List<String> list = sharedTypesAllChild.get(code);
		if (list != null)
			return list;
		list = new ArrayList<String>();
		List<String> codes = new ArrayList<String>();
		codes.add(code);
		do {
			list.addAll(codes);
			List<SharedTypes> temp = service.queryChild(codes);
			List<String> nextCodes = new ArrayList<String>();
			for (SharedTypes jobInfo : temp) {
				nextCodes.add(jobInfo.getCode());
			}
			codes = new ArrayList<String>(nextCodes);
		} while (codes.size() > 0);
		sharedTypesAllChild.put(code, list);
		return list;
	}

	/** 将list转换为string */
	public static String listStringToString(List<String> list, String split) {
		return listStringToString(list, split, true);
	}

	/**
	 * 将list转换为string
	 * 
	 * @param list
	 *            待转换的list
	 * @param split
	 *            中间分隔的字符
	 * @param lastNoSplit
	 *            末尾是否去除分割字符
	 * @return 转换后的string
	 */
	public static String listStringToString(List<String> list, String split, boolean lastNoSplit) {
		String string = "";
		for (String str : list) {
			string += str + split;
		}
		if (lastNoSplit) { // 是否去除最后一个添加的split
			int i = string.lastIndexOf(split);
			string = string.substring(0, i);
		}
		return string;
	}

	@Test
	public void test() {
		String a = codeFormat("233&amp;1&1&&2&");
		System.out.println();
		System.out.println(a);
	}

	/**
	 * 代码格式化，注意循序
	 */
	public static String codeFormat(String str) {
		if (str == null)
			return null;

		// String[] strs = str.split("&");
		// String a = "";
		// for (int i = 0; i < strs.length; i++) {
		// System.out.println("a: \"" + strs[i] + "\"");
		//
		// if (i == 0 && strs[i].equals(""))
		// continue;
		//
		// String b = strs[i].split(";")[0];
		// System.out.println("b: \"" + b + "\"");
		// if (!b.equals("amp") && !b.equals("nbsp") && !b.equals("quot") &&
		// !b.equals("lt") && !b.equals("gt")) {
		// a += "&amp;" + strs[i];
		// } else {
		// a += "&" + strs[i];
		// }
		// }
		// str = a;

		// int index = 0;
		// while (index != -1) {
		// index = str.indexOf(str, index);
		// int end = index + 4;
		// if (!str.substring(index, end).equals("amp;")) {
		// }
		// }

		// str = str.replace("&", "&amp;");
		// str = str.replace(" ", "&nbsp;");
		// str = str.replace("\"", "&quot;");
		// str = str.replace("<", "&lt;");// 关键
		// str = str.replace(">", "&gt;");// 关键
		// str = str.replace("\r\n", "<br>");
		// str = str.replace("\n", "<br>");

		str = str.replace("<", "&lt;");// 关键
		str = str.replace(">", "&gt;");// 关键
		str = str.replace("\r\n", " ");
		str = str.replace("\n", " ");

		return str;
	}

	/** 根据评分计算星级，返回 icon 的名称数组 */
	public static String[] calcScoreView(double score) {
		String[] view = new String[5];
		String before = "icon-like-xing-";
		if (score != -1) {
			// 将评分四舍五入
			// int scoreInt = (int) Math.round(score);
			// 四舍五入后 / 2，用来计算实星.半星
			double num1 = score / 2.0;
			// num2 是实星数量
			int num2 = (int) num1;
			// num3 是半星，如果num3 = 0则没有半星，>0 && <1 则有半星
			double num3 = num1 - num2;
			// 填充实星
			for (int i = 0; i < num2; i++) {
				view[i] = before + "2"; // 2 = 实星
			}
			// 填充半星和空星
			for (int i = num2; i < 5; i++) {
				if (num3 > 0 && i == num2) {
					view[i] = before + "3"; // 3 = 半星
				} else {
					view[i] = before + "1"; // 1 = 空星
				}
			}
		}
		return view;
	}

	/**
	 * 清理Session，清理所有
	 * 
	 * @param session
	 *            {@link HttpSession}
	 */
	public static void cleanSession(HttpSession session) {
		cleanSession(session, "currentUser");
	}

	/**
	 * 清理Session，注销登录定制
	 */
	public static void cleanSessionInLogoff(HttpSession session) {
		cleanSession(session, "currentUser", "currentUserInfo", "currentEntepriseInfo");
	}

	/**
	 * 
	 * 清理Session，传入要清理的参数
	 * 
	 * @param session
	 *            {@link HttpSession}
	 * @param keys
	 *            要清理的参数
	 */
	public static void cleanSession(HttpSession session, String... keys) {
		for (String key : keys) {
			session.setAttribute(key, null);
		}
	}

	public static void applicationLogin(String userId, HttpSession session) {
		// 首先判断是否已登录
		ServletContext application = session.getServletContext();

		HttpSession tempSession = (HttpSession) application.getAttribute(userId);

		if (tempSession != null && !tempSession.getId().equals(session.getId())) {
			try {
				// 清理Session
				tempSession.invalidate();
			} catch (Exception e) {
			}
		}
		// 再将新的Session存入Application
		application.setAttribute(userId, session);
	}

	/** 小程序PC通用：获取当前登录的用户，Users.id若为空则代表没有登录 */
	public static Users getThisLoginUser(HttpSession session, LoginToken token, UsersService usersService) {
		Users loginUser = new Users();
		if (token == null || isEmpty(token.getKeepCode())) { // 请求可能不是来自小程序
			Users tempUser = (Users) session.getAttribute("currentUser"); // 所以从Session中获取Users对象
			if (tempUser != null)
				// loginUser.setId(tempUser.getId());
				loginUser = tempUser;
		} else if (token.isEffective(usersService)) { // 来自小程序的请求
			loginUser.setId(token.getUserId()); // 从Token中获取UserId
		}
		return loginUser;
	}

	/** 小程序PC通用：获取当前登录的用户，Users.id若为空则代表没有登录 */
	public static Users getThisLoginUser(LoginToken token, UsersService usersService) {
		Users loginUser = new Users();
		if (token != null && !isEmpty(token.getKeepCode()) && token.isEffective(usersService)) {
			loginUser.setId(token.getUserId()); // 从Token中获取UserId
			loginUser = token.getUser();
		}
		return loginUser;
	}

	/**
	 * 判断是否登录
	 * 
	 * @param request
	 * @param usersService
	 * @return
	 */
	public static Users isLogin(HttpServletRequest request, UsersService usersService,
			EnterpriseInfoService enterpriseService, boolean strong) {
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("currentUser");
		UserInfo userInfo = (UserInfo) session.getAttribute("currentUserInfo");
		EnterpriseInfo enterpriseInfo = (EnterpriseInfo) session.getAttribute("currentEntepriseInfo");
		if (user == null) {
			// 在 cookie 中判断是否登录
			user = isLogin(getCookieValue(request, "keepCode"), usersService);
		}
		if (user != null) {
			// 存入 Session
			try {
				session.setAttribute("currentUser", user);
			} catch (Exception e) {
				return null;
			}

			// 将Session存到Application里，来做限制只能在一台电脑上登录
			if (strong)
				ServletUtil.applicationLogin(user.getId().toString(), session);

			try {
				if (user.getTypeId() == 1) {
					userInfo = usersService.queryUserInfo(user.getId());
					session.setAttribute("currentUserInfo", userInfo);
				} else if (user.getTypeId() == 2) {
					enterpriseInfo = enterpriseService.queryOneByUserId(user.getId());
					session.setAttribute("currentEntepriseInfo", enterpriseInfo);
				}
			} catch (java.lang.IllegalStateException ise) {
				request.setAttribute("isLogin", false);
				return null;
			}

			request.setAttribute("isLogin", true);
		} else {
			request.setAttribute("isLogin", false);
		}

		return user;
	}

	/**
	 * 提供一个KeepCode，程序将会对其进行解码并在 cookie 中判断是否登录
	 * 
	 * @param keepCode
	 * @param usersService
	 * @return
	 */
	public static Users isLogin(String keepCode, UsersService usersService) {
		String[] strs = keepCodeDecode(keepCode);
		Users user = null;
		if (strs != null) {
			int uid = Integer.parseInt(strs[1]);
			// 请求登录
			user = usersService.cookieLogin(uid, strs[2]);
		}
		return user;
	}

	/**
	 * 获取Cookie里的值
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param key
	 *            不区分大小写
	 */
	public static String getCookieValue(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase(key)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 删除Cookie
	 * 
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	public static void delCookie(HttpServletResponse response, String key) {
		Cookie cookie = new Cookie(key, null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	/**
	 * 获取 x 天数后的时间戳
	 * 
	 * @param day
	 *            几天
	 * @return time stamp
	 */
	public static long getTimeStampDay(int day) {
		return System.currentTimeMillis() + (day * 24 * 60 * 60 * 1000);
	}

	private static final String KEEP_CODE_PWD = "we54ArfH4HAHdf324WASWErWHY1W6JADHH5sdf4q5we1f56ewqr43qr1re6y14a36se71y56$W;&E%y154e1645y";

	/**
	 * KeepCode【解码】
	 * 
	 * @param keepCode
	 *            要解密的字符串
	 * @return 解密后的数据，0 = 过期时间 1 = key 2 = value，失败或过期返回 null
	 */
	public static String[] keepCodeDecode(String keepCode) {
		if (!isEmpty(keepCode)) {
			try {
				String str = new String(Base64.decodeBase64(keepCode));
				EncryptUtil des = new EncryptUtil(KEEP_CODE_PWD, "UTF-8");
				String[] result = des.decode(str).split("-");
				// 判断是否过期
				long thisTime = System.currentTimeMillis();
				long flagTime = (Long.parseLong(result[0]));
				if (thisTime < flagTime) {
					// 未过期
					return result;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * KeepCode【编码】
	 * 
	 * @param key
	 *            要加密的 key，可以是userId
	 * @param value
	 *            要加密的value，可以是password
	 * @param failureTimeStamp
	 *            失效时间戳
	 * @return 加密后的字符串
	 */
	public static String keepCodeEncode(long failureTimeStamp, Object key, Object value) {
		String keepCode = null;
		try {
			// 进行des加密
			EncryptUtil des = new EncryptUtil(KEEP_CODE_PWD, "UTF-8");
			keepCode = des.encode((failureTimeStamp + "-" + key + "-" + value + "-" + Utils.randomString(16)));
			// 因为使用des加密后的字符串带有换行符，无法被放入cookie或小程序的storage中，所以需要再进行一次base64编码，使其去掉换行符
			keepCode = new String(Base64.encodeBase64(keepCode.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keepCode;
	}

	@Test
	public void name() {
		String keepCode = "eElFak5USXRBRHdUSGs5aWZPUC9yV1E5ZTB6S3Vra2ZtWGRQS0RXeGZHV2pqaWJaQUdUcFdlQ0VLYTRqUHlzb3NpUWxzS2VtYnUxaA0KblRVQThTM29vQ1lOTFl3ZlJNY08=";
		String[] result = ServletUtil.keepCodeDecode(keepCode);
		System.out.print(new Gson().toJson(result));
	}

	/**
	 * 保持登录字符串 解码
	 */
	@SuppressWarnings("unused")
	private static String[] keepDecode(String code) {
		if (code == null)
			return null;
		// 进行 Base64 解码
		return new String(Base64.decodeBase64(code)).split("-");
	}

	/**
	 * 保持登录字符串 编码
	 */
	public static String keepEncode(int uid, String password) {
		// 进行 Base64 编码
		return new String(Base64.encodeBase64((uid + "-" + password).getBytes()));
	}

	/**
	 * 上传图片，随机生成文件名，文件名长度为 23 + 23
	 * 
	 * @param multipartFile
	 *            Spring文件流对象
	 * @param pathEnum
	 *            pathEnum对象
	 * @param session
	 *            HttpSession对象
	 * @return 返回自动生成的文件名
	 * @throws IOException
	 */
	public static String springUploadImage(MultipartFile multipartFile, PathEnum pathEnum, HttpSession session)
			throws IOException {
		return springUploadFile(multipartFile, new Image(pathEnum).getPath(), ".jpg", session);
	}

	/**
	 * 上传文件，随机生成文件名，文件名长度为 23 + 23
	 * 
	 * @param multipartFile
	 *            Spring文件流对象
	 * @param stitchingPath
	 *            拼接路径，跟服务器的真实路径拼接在一起
	 * @param defaultSuffix
	 *            默认后缀名
	 * @param session
	 *            HttpSession对象
	 * @return 返回自动生成的文件名
	 * @throws IOException
	 */
	public static String springUploadFile(MultipartFile multipartFile, String stitchingPath, String defaultSuffix,
			HttpSession session) throws IOException {
		// 获取后缀名
		String suffix = Utils.getSuffix(multipartFile.getOriginalFilename());
		suffix = suffix.isEmpty() ? defaultSuffix : suffix;
		// 生成文件名
		String fileName = Utils.randomString(23 + 23) + suffix;
		// 调用下一个方法
		springUploadFileName(multipartFile, stitchingPath, fileName, session);
		// 返回自动生成的文件名
		return fileName;
	}

	/**
	 * 上传文件，需要提供要上传的文件名
	 * 
	 * @param multipartFile
	 *            pring文件流对象
	 * @param parent
	 *            父级路径，网站中的根路径，例如 <code>/img/avatar/</code>
	 * @param child
	 *            子级路径，即文件名称，例如 <code>default.jpg</code>
	 * @param session
	 *            HttpSession对象，用来获取服务器的真实路径
	 * @throws IOException
	 *             如果目标路径是一个目录
	 * @throws IOException
	 *             如果目标路径无法被写入
	 * @throws IOException
	 *             如果目标路径需要创建目录
	 * @throws IOException
	 *             如果在复制过程中发生IO错误
	 */
	public static void springUploadFileName(MultipartFile multipartFile, String parent, String child,
			HttpSession session) throws IOException {
		// 获取服务器真实路径
		String realPath = session.getServletContext().getRealPath(parent);
		// 创建这个目录
		Utils.mkdirs(realPath);
		// 使用 apache common的FileUtils来写出文件
		FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), new File(realPath, child));
	}

	/**
	 * 获取网站的根路径
	 * 
	 * @param request
	 *            提供一个HttpServletRequest
	 * @return 网站的根路径
	 */
	public static String getBasePath(HttpServletRequest request) {
		String scheme = request.getScheme(); // 协议
		String name = request.getServerName(); // 域名/IP
		int port = request.getServerPort(); // 端口号
		String path = request.getContextPath(); // 访问路径

		String basePath = scheme + "://" + name + ":" + port + path + "/";
		return basePath;
	}

	/**
	 * 判断多个 字符串 中，空字符串的数量
	 * 
	 * @param strs
	 *            要检查的字符串
	 */
	public static int isEmpty(String... strs) {
		int count = 0;
		if (strs != null && strs.length > 0) {
			for (String string : strs) {
				count = ServletUtil.isEmpty(string) ? count + 1 : count;
			}
		}
		return count;
	}

	/**
	 * 文本是否为空，为空返回true
	 * 
	 * @param text
	 *            要检查的文本
	 */
	public static boolean isEmpty(String text) {
		return text == null || text.isEmpty();
	}

	/**
	 * 螺丝帽人机验证的 api_key
	 */
	private final static String LUO_API_KEY = "f5d5570b6e7089c389cb0ffb191b2fc4";

	public static RetEnum luoValidation(String response) {
		try {
			String result = NetworkUtil.sendGet("https://captcha.luosimao.com/api/site_verify",
					"api_key=" + LUO_API_KEY + "&response=" + response);
			Map<String, Object> map = Utils.gson.fromJson(result, new TypeToken<Map<String, Object>>() {
			}.getType());
			// String error = String.valueOf(map.get("error"));
			String res = String.valueOf(map.get("res"));
			// String msg = String.valueOf(map.get("msg"));
			if (res.equalsIgnoreCase("success")) {
				return RetEnum.SUCCESS;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return RetEnum.ERROR;
	}
}
