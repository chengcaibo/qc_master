package com.qichong.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.qichong.util.web.ServletUtil;

@Controller
@RequestMapping(path = "path")
public class ShareJSPController {

	/** 进入分享页面 */
	@RequestMapping(path = "/share")
	public String pathTest(Integer userid, String yaoqingcode, Model model, HttpServletResponse response) {
		// 判断参数是否完整
		if (userid == null || ServletUtil.isEmpty(yaoqingcode)) {
			response.setStatus(400);
			return ServletUtil.returnErrorPage("请求参数错误", "页面出现错误了，请检查您的分享参数是否正确！", false, model);
		}
		// 参数完整继续执行
		model.addAttribute("message", "奇虫科技");
		model.addAttribute("id", userid);
		model.addAttribute("yaoqing", yaoqingcode);
		return "share/share";
	}

	/** 进入下载页面 */
	@RequestMapping(path = "/download")
	public String doShareDownload() {
		return "share/share-download";
	}
	@RequestMapping(path = "/downloadapp")
	public String download() {
		return "share/download";
	}
	// 异常捕获
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String catchBadRequest(Throwable th, Model model) {
		return ServletUtil.returnErrorPage("请求参数错误", "请求参数错误，请检查您的分享参数是否正确！", false, model);
	}

	// 异常捕获
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String catchException(Throwable th, Model model) {
		th.printStackTrace();
		return ServletUtil.returnErrorPage("异常的请求", "异常的请求", false, model);
	}

}
