package com.qichong.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.RegionsDao;
import com.qichong.entity.Regions;
import com.qichong.model.ChildParentModel;
import com.qichong.model.Filters;

/**
 * 
 * RegionsServer
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017年11月9日 下午6:41:24
 */
@Service
public class RegionsService {

	@Autowired
	RegionsDao dao;

	/** 模糊查询地区 */
	public List<ChildParentModel> queryLikeRegion(Filters filters) {
		return dao.selectLikeRegion(filters);
	}

	/**
	 * 
	 * 根据传入的【父地区Code】，查询所有的【子行业】信息
	 * 
	 * @param parentCode
	 *            父地区Code
	 * @return 有数据则返回，无数据返回 null
	 */
	public List<Regions> queryChild(String parentCode) {
		return dao.selectChildByParentId(parentCode, null);
	}

	public List<Regions> queryChildByCodes(List<String> parentCodes) {
		return dao.selectChildByParentId(null, parentCodes);
	}

	/**
	 * 根据传入的【子地区Code】， 反向迭代查询【地区家谱】，直至【顶级】
	 * 
	 * @param regionCode
	 *            子地区Code
	 * @return 有数据则返回，无数据返回 空 List
	 */
	public List<Map<String, Object>> queryParent(String regionCode) {
		boolean run = true;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (regionCode.equals("0")) {
			regionCode = "110000000000";
		}
		do {
			Regions temp = dao.selectOne(regionCode);
			if (temp != null) {
				Map<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put("all", this.queryChild(temp.getParentRegionCode()));
				tempMap.put("selected", regionCode);
				// 插入到顶部
				list.add(0, tempMap);

				// 向上查询
				regionCode = temp.getParentRegionCode();
				// 判断是否是顶级
				if (regionCode.equals("0")) {
					run = false;
				}
			} else {
				run = false;
			}
		} while (run);
		return list;
	}

	/**
	 * 根据单个地区号查询单个地区的名称
	 * 
	 * @param regionCode
	 * @return
	 */
	public Regions selectOne(String regionCode) {
		return dao.selectOne(regionCode);
	}
}
