package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.SkillInfo;

/**
 * 
 * @author 吴志伟 综合技能类
 *
 */
public interface SkillInfoDao {

	/** 根据Id查询单个技能 */
	SkillInfo selectOneById(int id);

	/** 查询用户所拥有的的技能 */
	List<SkillInfo> queryUserSkillInfo(int userId);

	/** 新增技能 */
	boolean addSkillInfo(SkillInfo si);

	/** 查询userId在skillInfo里的总数 */
	int skillCount(int userId);

	/** 根据id更新单个技能 */
	int updateOne(@Param("id") int id, @Param("skill") String skill);

	/** 根据id删除单个技能 */
	int deleteOne(int id);

	/** 删除多个技能，暂未实现 */
	int deleteMany(int[] ids);
}
