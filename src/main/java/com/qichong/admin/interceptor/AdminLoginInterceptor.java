package com.qichong.admin.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.qichong.admin.util.AdminUtil;

/** 管理员登录拦截器 */
public class AdminLoginInterceptor implements HandlerInterceptor {

	private List<String> ignoreURIs;

	public List<String> getIgnoreURIs() {
		return ignoreURIs;
	}

	public void setIgnoreURIs(List<String> ignoreURIs) {
		this.ignoreURIs = ignoreURIs;
	}

	/** 进入Handler方法之前执行 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 遍历忽略拦截的URI
		String requestURI = request.getRequestURI();
		// System.out.print("requestURI: " + requestURI);
		for (String uri : ignoreURIs) {
			if (uri.equals(requestURI)) {
				// System.out.println(" - 忽略");
				return true;
			}
		}
		// 判断是否登录
		boolean isLogin = AdminUtil.isLogin(request.getSession());
		if (isLogin) {
			// System.out.println(" - 放行");
			return true;
		} else {
			// System.out.println(" - 拦截");
			response.setStatus(401); // 返回状态码 401 Unauthorized 所请求的页面需要用户名和密码。
			request.getRequestDispatcher("/admin/login").forward(request, response);// 转发到登录页面
			return isLogin; // true 放行
		}
	}

	/** 进入Handler方法之后执行，返回modelAndView之前执行 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	/** Handler执行完成后执行 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
