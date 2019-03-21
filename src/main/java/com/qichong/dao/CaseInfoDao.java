package com.qichong.dao;

import java.util.List;

import com.qichong.entity.CaseInfo;

/**
 * 成功案例Dao
 * 
 * @author 吴志伟
 */
public interface CaseInfoDao {

	/** 查询个人的成功案例 */
	List<CaseInfo> selectOneByUserId(int userId);

	/** 查询单个案例的详细信息 */
	CaseInfo selectOneByCaseId(int caseId);

	/** 修改成功案例信息 */
	int updateOne(CaseInfo ci);

	/** 新增案例 */
	int insertOne(CaseInfo caseInfo);

	/** 删除一个成功案例 */
	int deleteOne(int id);

	/** 查询caseInfo里的userId的总数 */
	int caseUserIdCount(int userId);
}
