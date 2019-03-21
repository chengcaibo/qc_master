package com.qichong.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.qichong.dao.CertificationEnterpriseDao;
import com.qichong.entity.CertificationEnterprise;
import com.qichong.entity.State;
import com.qichong.enums.PathEnum;
import com.qichong.enums.RetEnum;
import com.qichong.enums.StateEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.util.OSSUtil;
import com.qichong.util.Utils;

@Service
public class CertificationEnterpriseService {

	@Autowired
	CertificationEnterpriseDao dao;

	/** 营业执照 */
	private static OSSUtil ossUtil2 = new OSSUtil(PathEnum.ENT_LICENSE);

	@Transactional(rollbackFor = Exception.class)
	public JSONResult auditEnterpriseUpload(CertificationEnterprise ce, int userId, MultipartFile licenseFile)
			throws Exception {
		String pictureName = userId + "_license.jpg";
		ce.setBusinessLicense(pictureName + "?" + Utils.randomNumber(6));
		ossUtil2.uploadFile2OSS(licenseFile.getInputStream(), pictureName);

		// 判断是否已经提交过资料了
		CertificationEnterprise queryTemp = this.queryOneByUserId(userId);
		ce.setState(new State(StateEnum.AUDIT_PENDING));
		if (queryTemp == null) { // 没有提交过，直接插入
			this.insertOne(ce);
		} else { // 提交过，直接修改
			ce.setId(queryTemp.getId());
			ce.setCreateTime(new Date());
			this.updateOne(ce);
		}
		return JSONResult.builder(RetEnum.SUCCESS).setResult(ce.getId());
	}

	/** 查询出所有【待审核】的资料 */
	public List<CertificationEnterprise> queryAllWait(Filters filter, JSONResult jr) {
		filter.setStateId(4);
		return this.queryAll(filter, jr);
	}

	/** 查询出所有【审核通过】的资料 */
	public List<CertificationEnterprise> queryAllPassed(Filters filter, JSONResult jr) {
		filter.setStateId(5);
		return this.queryAll(filter, jr);

	}

	/** 查询出所有【审核未通过】的资料 */
	public List<CertificationEnterprise> queryAllNotPassed(Filters filter, JSONResult jr) {
		filter.setStateId(6);
		return this.queryAll(filter, jr);

	}

	/** 查询，会将total存入JSONResult */
	public List<CertificationEnterprise> queryAll(Filters filter, JSONResult jr) {
		List<CertificationEnterprise> list = dao.selectAll(filter);
		int total = dao.selectAllCount(filter);
		jr.setValue(RetEnum.SUCCESS, "", list, total);
		return list;
	}

	/** 根据记录Id查询资料(后台) */
	public CertificationEnterprise queryOneById(int id) {
		Filters filter = new Filters();
		filter.setId(id);
		return this.queryOne(filter);
	}

	/** 根据UserId查询资料 */
	public CertificationEnterprise queryOneByUserId(int userId) {
		Filters filter = new Filters();
		filter.setUserId(userId);
		return this.queryOne(filter);
	}

	/** 查询下一条待审核的资料 */
	public CertificationEnterprise queryNextWait(int notId) {
		Filters filter = new Filters();
		filter.setStateId(4);
		filter.setNotId(notId);
		return this.queryOne(filter);
	}

	/** 查询一条 */
	private CertificationEnterprise queryOne(Filters filter) {
		List<CertificationEnterprise> result = dao.selectAll(filter);
		if (result.size() == 0)
			return null;
		return result.get(0);
	}

	/** 插入一条信息 */
	public boolean insertOne(CertificationEnterprise ci) {
		ci.setCreateTime(new Date());
		return dao.insertOne(ci) > 0;
	}

	/** 更新一条信息 */
	public boolean updateOne(CertificationEnterprise ci) {
		ci.setEditTime(new Date());
		return dao.updateOne(ci) > 0;
	}

	/** 更新一条信息 */
	public boolean byUserIdUpdateOne(CertificationEnterprise ci) {
		ci.setEditTime(new Date());
		return dao.byUserIdUpdateOne(ci) > 0;
	}

	/** 删除一条信息 */
	public boolean deleteOne(int id) {
		return dao.deleteOne(id) > 0;
	}

	public Map<String, String> updateIsAgree(Integer userId) {
		HashMap<String, String> map = new HashMap<>();
		Integer ret = dao.updateIsAgree(userId);
		if(ret>0){
			map.put("msg","ok");
		}else{
			map.put("msg","fail");
		}
		return map;
	}
}
