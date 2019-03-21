package com.qichong.dao;

import java.util.List;

import com.qichong.entity.AdPrivate;
import com.qichong.model.Filters;
import com.qichong.model.adprivate.DemandSearchListAd;
import com.qichong.model.adprivate.EnterpriseSearchListAd;
import com.qichong.model.adprivate.GroupSearchListAd;
import com.qichong.model.adprivate.PersonalSearchListAd;
import com.qichong.model.adprivate.ToolsSearchListAd;

/**
 * 私有广告表
 * 
 * @创建人 孙建雷
 */
public interface AdPrivateDao {

	/** 查询所有【工具搜索位】广告 */
	List<ToolsSearchListAd> selectToolsSearchListAd();
	
	/** 查询所有【需求搜索位】广告 */
	List<DemandSearchListAd> selectDemandSearchListAd();

	/** 查询所有【团体搜索位】广告 */
	List<GroupSearchListAd> selectGroupSearchListAd();

	/** 查询所有【企业热门】广告 */
	List<AdPrivate> selectEnterpriseHotsAd();

	/** 查询所有【企业搜索位】广告 */
	List<EnterpriseSearchListAd> selectEnterpriseSearchListAd();

	/** 查询所有【个人搜索位】广告 */
	List<PersonalSearchListAd> selectPersonalSearchListAd();

	/** 查询所有【私有广告】信息并且筛选 */
	List<AdPrivate> selectAllByFilter(Filters filters);
}
