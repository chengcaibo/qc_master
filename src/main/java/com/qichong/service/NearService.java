package com.qichong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.UserInfoDao;
import com.qichong.entity.UserInfo;
import com.qichong.model.Filters;

/**
 * 附近相关功能Service层
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017-11-20 19:37:32
 */
@Service
public class NearService {
	@Autowired
	UserInfoDao userInfoDao;

	/** 查询附近的个人 */
	public List<UserInfo> queryNeaybyPersonal(Filters filters) {
		return userInfoDao.selectAllByFilter(filters);
	}

	/** 查询附近的企业 */
	public List<UserInfo> queryNeaybyEnterprise() {
		return null;
	}

	/** 查询附近的团体 */
	public List<UserInfo> queryNeaybyGroup() {
		return null;
	}

	/** 查询附近的需求 */
	public List<UserInfo> queryNeaybyDemand() {
		return null;
	}

	/** 查询附近的广告 */
	public List<UserInfo> queryNeaybyAd() {
		return null;
	}
}
