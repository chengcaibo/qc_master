package com.qichong.dao;

import com.qichong.entity.SettingPrivacy;

/** 隐私设置持久层 */
public interface SettingPrivacyDao {

	/** 根据userId查询隐私设置 */
	SettingPrivacy selectByUserId(int userId);

	/** 根据userId初始化一个隐私设置 */
	boolean initialByUserId(int userId);

	/** 修改一条数据 */
	int updateOne(SettingPrivacy privacy);

	// /** 灵活插入一条数据 */
	// int insertOne(SettingPrivacy privacy);

}
