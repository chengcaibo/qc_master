package com.qichong.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qichong.dao.CertificationInfoDao;
import com.qichong.entity.CertificationInfo;
import com.qichong.entity.State;
import com.qichong.entity.UserInfo;
import com.qichong.enums.RetEnum;
import com.qichong.enums.StateEnum;
import com.qichong.exception.QCErrorException;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.util.web.ServletUtil;
import com.qichong.util.wechat.mini.WeChatTemplateMessageUtil;

/**
 * 认证表 Service 层实现类
 */
@Service
public class CertificationInfoService {

	@Autowired
	CertificationInfoDao dao;

	@Autowired
	UserInfoService userInfoService;

	/** 驳回实名审核 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult rejectedRealnameAudit(int id, String reason) throws QCErrorException {
		if (ServletUtil.isEmpty(reason))
			throw new QCErrorException("缺少reason参数！");
		CertificationInfo ci = new CertificationInfo(id);
		ci.setReason(reason);
		ci.setState(new State(StateEnum.AUDIT_REJECTED));
		this.updateOne(ci);
		CertificationInfo queryTemp = this.queryOneById(id);
		String openId = queryTemp.getUser().getWxOpenId();
		if (openId != null && queryTemp.getFormId() != null) {
			String node = "很抱歉您的资料有误，未能通过审核，请点击进入小程序重新上传资料！";
			String page = "pages/me/data/certification";
			WeChatTemplateMessageUtil.sendAuditNotPassed(openId, queryTemp, "实名认证", node, page);
		}
		return JSONResult.builder(RetEnum.SUCCESS);
	}

	/** 通过实名审核 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult passedRealnameAudit(int id) {
		// 更新审核表中的数据
		CertificationInfo ci = new CertificationInfo(id);
		ci.setState(new State(StateEnum.AUDIT_PASS));
		this.updateOne(ci);
		// 更新用户表中的数据
		CertificationInfo cinfo = this.queryOneById(id);
		UserInfo uinfo = new UserInfo(cinfo.getUser());
		uinfo.setIdentity(cinfo.getIdentity());
		uinfo.setRealName(cinfo.getRealName());
		userInfoService.changeUserInfo(uinfo);
		// 发送小程序模板消息，满足两个条件：openId != null and formId != null
		String openId = cinfo.getUser().getWxOpenId();
		if (openId != null && cinfo.getFormId() != null) {
			String node = "恭喜您的资料已通过审核了，您可以发布信息了，点击进入小程序查看详情！";
			String page = "pages/me/data/certification";
			WeChatTemplateMessageUtil.sendAuditPassed(openId, cinfo, "实名认证", node, page);
		}
		return JSONResult.builder(RetEnum.SUCCESS);
	}

	/** 查询出所有【待审核】的资料 */
	public List<CertificationInfo> queryAllWait(Filters filter, JSONResult jr) {
		filter.setStateId(4);
		return this.queryAll(filter, jr);
	}

	/** 查询出所有【审核通过】的资料 */
	public List<CertificationInfo> queryAllPassed(Filters filter, JSONResult jr) {
		filter.setStateId(5);
		return this.queryAll(filter, jr);

	}

	/** 查询出所有【审核未通过】的资料 */
	public List<CertificationInfo> queryAllNotPassed(Filters filter, JSONResult jr) {
		filter.setStateId(6);
		return this.queryAll(filter, jr);

	}

	/** 查询，会将total存入JSONResult */
	public List<CertificationInfo> queryAll(Filters filter, JSONResult jr) {
		List<CertificationInfo> list = dao.selectAll(filter);
		int total = dao.selectAllCount(filter);
		jr.setValue(RetEnum.SUCCESS, "", list, total);
		return list;
	}

	/** 根据记录Id查询资料(后台) */
	public CertificationInfo queryOneById(int id) {
		Filters filter = new Filters();
		filter.setId(id);
		return this.queryOne(filter);
	}

	/** 根据UserId查询资料 */
	public CertificationInfo queryOneByUserId(int userId) {
		Filters filter = new Filters();
		filter.setUserId(userId);
		return this.queryOne(filter);
	}

	/** 查询下一条待审核的资料 */
	public CertificationInfo queryNextWait(int notId) {
		Filters filter = new Filters();
		filter.setStateId(4);
		filter.setNotId(notId);
		return this.queryOne(filter);
	}

	/** 查询一条 */
	private CertificationInfo queryOne(Filters filter) {
		List<CertificationInfo> result = dao.selectAll(filter);
		if (result.size() == 0)
			return null;
		return result.get(0);
	}

	/** 插入一条信息 */
	public boolean insertOne(CertificationInfo ci) {
		ci.setCreateTime(new Date());
		return dao.insertOne(ci) > 0;
	}

	/** 更新一条信息 */
	public boolean updateOne(CertificationInfo ci) {
		ci.setEditTime(new Date());
		return dao.updateOne(ci) > 0;
	}

	/** 删除一条信息 */
	public boolean deleteOne(int id) {
		return dao.deleteOne(id) > 0;
	}

	/** 获取没有发布过广告的认证用户(admin) */
	public List<CertificationInfo> getCertificationInfos(Filters filter) {
		return dao.selectUserByadPublic(filter);
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
