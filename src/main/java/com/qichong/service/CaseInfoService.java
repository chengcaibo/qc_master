package com.qichong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.CaseInfoDao;
import com.qichong.entity.CaseInfo;

@Service
public class CaseInfoService {

	@Autowired
	CaseInfoDao caseInfoDao;

	/** 根据UserId查询成功案例 */
	public List<CaseInfo> queryOneByUserId(int userId) {
		return caseInfoDao.selectOneByUserId(userId);
	}

	/** 根据id查询数据 */
	public CaseInfo queryOneByCaseId(int id) {
		return caseInfoDao.selectOneByCaseId(id);
	}

	/** 修改成功案例信息 */
	public boolean changeCase(CaseInfo ci) {
		return caseInfoDao.updateOne(ci) > 0;
	}

	/** 新增成功案例 */
	public boolean insertOne(CaseInfo caseInfo) {
		return caseInfoDao.insertOne(caseInfo) > 0;
	}

	/** 删除成功案例 */
	public boolean deleteOne(int id) {
		return caseInfoDao.deleteOne(id) > 0;
	}

	/** 查询caseInfo里的userId的总数 */
	public int caseUserIdCount(int userId) {
		return caseInfoDao.caseUserIdCount(userId);
	}
}
