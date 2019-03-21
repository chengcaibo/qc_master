package com.qichong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qichong.dao.SkillInfoDao;
import com.qichong.entity.SkillInfo;
import com.qichong.entity.Users;

/**
 * 
 * @author 吴志伟
 * 
 *
 */
@Service
public class SkillInfoService {

	@Autowired
	SkillInfoDao skillinfoDao;

	/** 查询某userId下所有的技能 */
	public SkillInfo queryAllById(int id) {
		return skillinfoDao.selectOneById(id);
	}

	/** 查询某userId下所有的技能 */
	public List<SkillInfo> queryAllByUserId(int userId) {
		return skillinfoDao.queryUserSkillInfo(userId);
	}

	/** 新增技能 */
	@Transactional(rollbackFor = Exception.class) // 标识是一个事务
	public void addTimes(String[] skillName, Users user) {
		for (int i = 0; i < skillName.length; i++) {
			// 事务
			skillinfoDao.addSkillInfo(new SkillInfo(user, skillName[i]));
		}
	}

	/** 根据userId查询技能总数 */
	public int skillCount(int userId) {
		return skillinfoDao.skillCount(userId);
	}

	/** 根据Id修改技能 */
	public boolean editOne(int id, String skill) {
		return skillinfoDao.updateOne(id, skill) > 0;
	}

	/** 根据Id删除技能 */
	public boolean removeOneById(int id) {
		return skillinfoDao.deleteOne(id) > 0;
	}
}
