package com.qichong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.qichong.exception.QCErrorException;
import com.qichong.model.JSONResult;
import com.qichong.service.BannerService;
import com.qichong.util.Utils;
import com.qichong.util.web.ServletUtil;

@RestController
@RequestMapping(path = "/banner", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
public class BannerController {

	@Autowired
	BannerService service;

	/** 查询小程序首页Banner */
	@RequestMapping("/weapp-home")
	public String doWeAppHome() {
		return JSONResult.success(service.queryWeAppHomeBanner()).toJSON();
	}

	/** 根据TypeID查询Banner */
	@RequestMapping("/query")
	public String doQueryByType(Integer typeId) {
		if (typeId == null)
			return JSONResult.paramLack("typeId 不能为空").toJSON();
		return JSONResult.success(service.queryBanner(typeId)).toJSON();
	}

	/** 全局异常处理 */
	@ExceptionHandler(Exception.class)
	public String catchException(Exception ex) {
		Throwable th = null;

		th = Utils.isException(ex, QCErrorException.class);
		if (th != null) {
			return JSONResult.error(th.getMessage()).toJSON();
		}
		th = Utils.isException(ex, MethodArgumentTypeMismatchException.class);
		if (th != null) {
			return JSONResult.error("bad request - 所给参数类型与期望类型不符合").toJSON();
		}

		System.out.println("= = = = = = BannerController = = = = = =");
		ex.printStackTrace();
		return JSONResult.exception().toJSON();
	}

}
