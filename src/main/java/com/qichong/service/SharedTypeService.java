package com.qichong.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.SharedTypesDao;
import com.qichong.entity.SharedTypes;
import com.qichong.model.ChildParentModel;
import com.qichong.model.Filters;

@Service
public class SharedTypeService {

	@Autowired
	SharedTypesDao dao;

	/** 根据关键字模糊查询所有的项，可进行分页 */
	public List<ChildParentModel> queryLikeAll(Filters filters) {
		return dao.selectLikeAll(filters);
	}

	/** 根据传入的code查询单个项 */
	public SharedTypes queryOne(String code) {
		return dao.selectOne(code);
	}

	/** 根据父级Id查询所有子级 */
	public List<SharedTypes> queryChild(String parentCode) {
		return dao.selectChildByParentCode(parentCode, null);
	}

	/** 重构：根据多个父级Id查询所有子级 */
	public List<SharedTypes> queryChild(List<String> parentCodes) {
		return dao.selectChildByParentCode(null, parentCodes);
	}

	/** 根据 行业Code 反向查询祖宗级别 */
	public List<Map<String, Object>> queryParentToTopLevel(String code) {
		boolean run = true;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		do {
			SharedTypes temp = dao.selectOne(code);
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
