package com.qichong.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.qichong.admin.service.AdminCountService;
import com.qichong.model.JSONResult;
import com.qichong.util.Utils;

/**
 * 管理员统计Controller
 * 
 * @author 孙建雷
 *
 */
@RestController
@RequestMapping(path = "/admin/count", method = RequestMethod.GET)// NOT_API
public class AdminCountController {

	@Autowired
	AdminCountService countSer;

	/** 统计需求三天内的发布信息 */
	@RequestMapping(path = "/three-days-demand")// NOT_API
	public String doThreeDaysDemand() {
		return countSer.countDemandThreeDays().toJSON();
	}

	/** 统计企业的注册信息 */
	@RequestMapping(path = "/sign-enterprise")// NOT_API
	public String doSignEnterprise() {
		return countSer.countEnterpriseSign().toJSON();
	}

	/** 统计个人的注册信息 */
	@RequestMapping(path = "/sign-personal")// NOT_API
	public String doSignPersonal() {
		return countSer.countPersonalSign().toJSON();
	}

	/** 全局异常处理 */
	@ExceptionHandler(Exception.class)
	public String catchException(Exception ex) {
		Throwable th = null;

		// 判断是否是QCError
		th = Utils.isQCError(ex);
		if (th != null) {
			return JSONResult.error(th.getMessage()).toJSON();
		}

		// 判断是否是方法参数错误异常
		th = Utils.isException(ex, MethodArgumentTypeMismatchException.class);
		if (th != null) {
			return JSONResult.error("bad request - 所给参数类型与期望类型不符合").toJSON();
		}

		// 都不是则抛出
		System.out.println("= = = = = = BannerController = = = = = =");
		ex.printStackTrace();
		return JSONResult.exception().toJSON();
	}

}
