package com.qichong.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.qichong.util.web.ServletUtil;

/** 跨域拦截器 */
public class CrossDomainInterceptor implements HandlerInterceptor {

	/** 进入Handler方法之前执行 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		// 检查Origin是否合法 ，若合法则允许跨域请求
		if (ServletUtil.checkOriginISLegal(request.getHeader("Origin"))) {
			// response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
			response.setHeader("Access-Control-Allow-Credentials", "true");
		}
		// false 拦截
		return true; // true 放行
	}

	/** 进入Handler方法之后执行，返回modelAndView之前执行 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) {
		// 拦截 api.qc1318.com 域名访问页面
		String url = request.getRequestURL().toString();
		// 判断url中是否包含 api 域名
		if (url.indexOf("api.qc1318.com") != -1) {
			// 判断返回的是否是 modeAndView
			if (modelAndView != null) {
				response.setStatus(404);
				modelAndView.setViewName("api_404");
			}
		}
	}

	/**
	 * Handler执行完成后执行
	 * 
	 * @throws IOException
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws IOException {
	}

}
