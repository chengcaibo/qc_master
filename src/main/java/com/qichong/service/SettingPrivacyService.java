package com.qichong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qichong.dao.SettingPrivacyDao;
import com.qichong.entity.SettingPrivacy;
import com.qichong.model.JSONResult;

/**
 * 隐私设置Service层
 * 
 * @author 孙建雷
 *
 */
@Service
public class SettingPrivacyService {

	@Autowired
	SettingPrivacyDao dao;

	/**
	 * 更新一条数据
	 * 
	 * @param privacy
	 *            要更新的数据
	 * @param mustHaveValue
	 *            是否执行MustHaveValue方法，如果确定要更新的数据一定有值的话，可以填false，否则填true
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult updateOne(SettingPrivacy privacy, boolean mustHaveValue) {
		if (privacy == null || privacy.getUserId() == null) {
			return JSONResult.paramLack("userId不能为空");
		}
		if (mustHaveValue)
			this.queryOneByUserIdMustHaveValue(privacy.getUserId());
		int line = dao.updateOne(privacy);
		return JSONResult.returnFlag(line > 0, "未知错误");
	}

	/** 根据userId查询一个隐私设置，并一定有值 */
	@Transactional(rollbackFor = Exception.class)
	public SettingPrivacy queryOneByUserIdMustHaveValue(int userId) {
		SettingPrivacy privacy = dao.selectByUserId(userId);
		if (privacy == null) {
			dao.initialByUserId(userId);
			privacy = dao.selectByUserId(userId);
			if (privacy == null)
				throw new RuntimeException("无法获取" + userId + "的SettingPrivacy信息");
		}
		return privacy;
	}

}
