package com.qichong.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.IndustryTypeDao;
import com.qichong.entity.IndustryType;
import com.qichong.model.ChildParentModel;
import com.qichong.model.Filters;

/**
 * 
 * 行业类别 业务逻辑层
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2018年1月3日13:36:48
 */
@Service
public class IndustryTypeService {

	@Autowired
	IndustryTypeDao industryTypeDao;

	public List<ChildParentModel> queryLikeIndustry(Filters filters) {
		return industryTypeDao.selectLikeAllIndustry(filters);
	}

	/**
	 * 
	 * 查询单个职业
	 * 
	 * @param industryCode
	 *            行业Code
	 */
	public IndustryType queryOneIndustry(String industryCode) {
		return industryTypeDao.selectOne(industryCode);
	}

	/**
	 * 根据父级Id查询所有子级
	 * 
	 * @param parentId
	 *            父id
	 */
	public List<IndustryType> queryChild(String parentCode) {
		return industryTypeDao.selectChildByParentId(parentCode, null);
	}

	/**
	 * 根据父级Id查询所有子级
	 * 
	 * @param parentId
	 *            父id
	 */
	public List<IndustryType> queryChild(List<String> parentCodes) {
		return industryTypeDao.selectChildByParentId(null, parentCodes);
	}

	/**
	 * 根据 行业Code 反向查询祖宗级别
	 */
	public List<Map<String, Object>> queryParentIndustry(String industryCode) {
		boolean run = true;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		do {
			IndustryType temp = industryTypeDao.selectOne(industryCode);
			if (temp != null) {
				Map<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put("all", this.queryChild(temp.getParentCode()));
				tempMap.put("selected", industryCode);
				list.add(0, tempMap);
				// 向上查询，查询父级的父级
				industryCode = temp.getParentCode();
				// 判断是否是顶级
				if (industryCode.equals("0")) {
					run = false;
				}
			} else {
				run = false;
			}
		} while (run);
		return list;
	}

	/**
	 * 
	 * @param industryCode
	 * @return 返回单个行业
	 */
	public IndustryType selectOne(String industryCode) {
		return industryTypeDao.selectOne(industryCode);
	}

}
