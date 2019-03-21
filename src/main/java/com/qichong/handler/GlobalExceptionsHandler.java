package com.qichong.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;

/**
 * 全局异常处理
 * 
 * @author 孙建雷
 */
@ControllerAdvice
public class GlobalExceptionsHandler {

	/** 处理参数类型转换错误，可捕获大部分400 - Bad Request错误 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseBody
	public String catchBadRequest(Throwable th) {
		if (th != null) {
			Throwable cause = th.getCause();
			String message = cause.getMessage();
			String msg = "";
			if (cause instanceof java.lang.NumberFormatException) {
				String str = this.getString(message);
				msg = "参数类型不匹配：不能将 \"" + str + "\" 转换成数字类型";
			} else {
				return JSONResult.builder(RetEnum.PARAM_ERROR, "参数类型不匹配：" + message).toJSON();
			}
			return JSONResult.builder(RetEnum.PARAM_ERROR, msg).toJSON();
		}
		return JSONResult.builder(RetEnum.PARAM_ERROR, "参数类型不匹配").toJSON();
	}

	/** 获取两个引号中间的内容 */
	private String getString(String str) {
		int beginIndex = str.indexOf("\"");
		int endIndex = str.lastIndexOf("\"");
		if (beginIndex != -1 && endIndex != -1) {
			return str.substring(beginIndex + 1, endIndex);
		}
		return "";
	}

}
