package com.qichong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.AdPrivateDao;
import com.qichong.entity.AdPrivate;
import com.qichong.model.Filters;
import com.qichong.model.adprivate.DemandSearchListAd;
import com.qichong.model.adprivate.EnterpriseSearchListAd;
import com.qichong.model.adprivate.GroupSearchListAd;
import com.qichong.model.adprivate.PersonalSearchListAd;
import com.qichong.model.adprivate.ToolsSearchListAd;

/**
 * 私有广告Service层
 *
 * @创建人 孙建雷
 */
@Service
public class AdPrivateService {

	@Autowired
	AdPrivateDao adPrivateDao;

	/** 查询所有【工具搜索位】广告 */
	public List<ToolsSearchListAd> queryToolsSearchListAd() {
		return adPrivateDao.selectToolsSearchListAd();
	}

	/** 查询所有【需求搜索位】广告 */
	public List<DemandSearchListAd> queryDemandSearchListAd() {
		return adPrivateDao.selectDemandSearchListAd();
	}

	/** 查询所有【团体搜索位】广告 */
	public List<GroupSearchListAd> queryGroupSearchListAd() {
		return adPrivateDao.selectGroupSearchListAd();
	}

	/** 查询所有【企业热门】广告 */
	public List<AdPrivate> queryEnterpriseHotsAd() {
		List<AdPrivate> list = adPrivateDao.selectEnterpriseHotsAd();
		for (AdPrivate adp : list) {
			adp.cleanPictrue();
		}
		return list;
	}

	/** 查询所有【企业搜索位】广告 */
	public List<EnterpriseSearchListAd> queryEnterpriseSearchListAd() {
		return adPrivateDao.selectEnterpriseSearchListAd();
	}

	/** 查询所有【个人搜索位】广告 */
	public List<PersonalSearchListAd> queryPersonalSearchListAd() {
		return adPrivateDao.selectPersonalSearchListAd();
	}

	/** 搜索多条记录 */
	private List<AdPrivate> searchList(Filters filters) {
		return adPrivateDao.selectAllByFilter(filters);
	}

	/** 搜索一条记录 */
	private AdPrivate searchOne(Filters filters) {
		List<AdPrivate> list = this.searchList(filters);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	/** 搜索 */
	public List<AdPrivate> search(Filters filters) {
		return this.searchList(filters);
	}

	/** 根据私有广告ID查询单个广告 */
	public AdPrivate queryOneByAdId(int adId) {
		// 构造Filter
		Filters filters = new Filters();
		filters.setAdId(adId);
		// 执行查询
		return this.searchOne(filters);
	}

	/** 根据ID查询私有广告 */
	public AdPrivate queryOneById(int id) {
		// 构造Filter
		Filters filters = new Filters();
		filters.setId(id);
		// 执行查询
		return this.searchOne(filters);
	}

}
