package com.qichong.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.admin.dao.AdminCountDao;
import com.qichong.admin.model.CountThreeDaysModel;
import com.qichong.exception.QCErrorRuntimeException;
import com.qichong.model.JSONResult;

/**
 * 管理员count service
 * 
 * @author 孙建雷
 *
 */
@Service
public class AdminCountService {

	@Autowired
	AdminCountDao countDao;

	/** 统计需求三天内的发布情况 */
	public JSONResult countDemandThreeDays() {
		return JSONResult.success(countDao.countDemandThreeDays());
	}

	/** 统计企业用户注册量 */
	public JSONResult countEnterpriseSign() {
		return this.countUserSign(2);
	}

	/** 统计个人用户注册量 */
	public JSONResult countPersonalSign() {
		return this.countUserSign(1);
	}

	/** 统计用户的注册量 */
	private JSONResult countUserSign(int typeId) {
		if (typeId == 1 || typeId == 2) {
			CountThreeDaysModel cusm = countDao.countUserSign(typeId);
			return JSONResult.success(cusm);
		} else {
			throw new QCErrorRuntimeException("不支持的typeId");
		}
	}

}
