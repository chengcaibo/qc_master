package com.qichong.admin.dao;

import com.qichong.admin.model.CountThreeDaysModel;

public interface AdminCountDao {

	/** 统计用户的注册量，通过typeId区分是个人还是企业 */
	CountThreeDaysModel countUserSign(int typeId);

	/** 统计需求三天内的信息 */
	CountThreeDaysModel countDemandThreeDays();

}
