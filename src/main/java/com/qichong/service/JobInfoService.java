package com.qichong.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.JobInfoDao;
import com.qichong.entity.JobInfo;
import com.qichong.model.ChildParentModel;
import com.qichong.model.Filters;

/**
 * 
 * 职业信息表 业务逻辑层
 * 
 * @创建人 孙建雷
 * @修改时间 2018-4-13 16:16:36
 */
@Service
public class JobInfoService {

	@Autowired
	JobInfoDao jobInfoDao;

	/** 根据关键字模糊查询所有的职业 */
	public List<ChildParentModel> queryLikeAllJob(Filters filters) {
		return jobInfoDao.selectLikeJob(filters);
	}

	/** 查询单个职业 */
	public JobInfo queryOne(String code) {
		return jobInfoDao.selectOne(code);
	}

	/** 根据父级Id查询所有子级 */
	public List<JobInfo> queryChild(String parentCode) {
		return jobInfoDao.selectChildByParentId(parentCode, null);
	}

	/** 重构：根据多个父级Id查询所有子级 */
	public List<JobInfo> queryChild(List<String> parentCodes) {
		return jobInfoDao.selectChildByParentId(null, parentCodes);
	}

	/** 根据 行业Code 反向查询祖宗级别 */
	public List<Map<String, Object>> queryParentIndustry(String code) {
		boolean run = true;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		do {
			JobInfo temp = jobInfoDao.selectOne(code);
			if (temp != null) {
				Map<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put("all", this.queryChild(temp.getParentCode()));
				tempMap.put("selected", code);
				list.add(0, tempMap);
				// 向上查询，查询父级的父级
				code = temp.getParentCode();
				// 判断是否是顶级
				if (code.equals("0")) {
					run = false;
				}
			} else {
				run = false;
			}
		} while (run);
		return list;
	}

}
