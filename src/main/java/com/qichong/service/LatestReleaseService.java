package com.qichong.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.LatestReleaseDao;
import com.qichong.entity.DemandInfo;
import com.qichong.entity.EnterpriseInfo;
import com.qichong.entity.GroupInfo;
import com.qichong.entity.UserInfo;
import com.qichong.model.JSONResult;
import com.qichong.model.LatestModel;
import com.qichong.util.Utils;

/**
 * 查询最新发布的
 * 
 * @author 孙建雷
 *
 */
@Service
public class LatestReleaseService {

	@Autowired
	LatestReleaseDao latestReleaseDao;

	/** 查询最新的信息 */
	public JSONResult queryLatest() {
		try {
			List<LatestModel> uinfos = this.queryLatestUserInfo();
			List<LatestModel> einfos = this.queryLatestEnterpriseInfo();
			List<LatestModel> ginfos = this.queryLatestGroupInfo();
			List<LatestModel> dinfos = this.queryLatestDemandInfo();

			List<LatestModel> allInfos = new ArrayList<LatestModel>();
			allInfos.addAll(uinfos);
			allInfos.addAll(einfos);
			allInfos.addAll(ginfos);
			allInfos.addAll(dinfos);

			// 排序
			for (int i = 0; i < allInfos.size(); i++) {
				for (int j = 0; j < allInfos.size(); j++) {
					LatestModel temp1 = allInfos.get(i);
					LatestModel temp2 = allInfos.get(j);
					if (temp1.getTime().getTime() > temp2.getTime().getTime()) {
						LatestModel temp3 = temp1;
						allInfos.set(i, temp2);
						allInfos.set(j, temp3);
					}
				}
			}

			// System.out.println(allInfos.size());
			allInfos = allInfos.subList(0, 10);
			// System.out.println(allInfos.size());
			// System.out.println(JSONResult.gson.toJson(allInfos));
			return JSONResult.success(allInfos);
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.exception();
		}
	}

	private final int MAX_LENGTH = 22;
	private final int MAX_HOUR = 9;

	/** 查询最新注册的需求信息 */
	private List<LatestModel> queryLatestDemandInfo() {
		List<LatestModel> latests = new ArrayList<LatestModel>();
		Date currentTime = new Date();

		List<DemandInfo> dinfos = latestReleaseDao.selectLatestDemandInfo();
		for (DemandInfo dinfo : dinfos) {
			Date time = dinfo.getReleaseTime();
			int id = dinfo.getId();

			int typeId = dinfo.getUser().getTypeId();
			String name = "";
			if (typeId == 1) {
				name = dinfo.getUserInfo().getNickName();
			} else {
				name = dinfo.getEnterpriseInfo().getEnterpriseName();
			}
			String ctx = dinfo.getContent();
			String ago = Utils.getDateToDateAgo(time, currentTime, false, true, MAX_HOUR);
			String formatStr = "[" + ago + "] %s发布了新需求%s";
			int length = formatStr.length() - 4;// -4 是为了减去%s的位置
			if (length + ctx.length() + name.length() > MAX_LENGTH) {
				int maxLength = MAX_LENGTH - length;
				int length1 = ctx.length();
				int length2 = name.length();
				int diff = maxLength / 2;
				int diff1 = diff, diff2 = diff;
				if (length1 < diff) {
					diff2 += (diff - length1);

				} else if (length2 < diff) {
					diff1 += (diff - length2);
				}
				ctx = Utils.overflowHiddenMiddleString(ctx, diff1);
				name = Utils.overflowHiddenMiddleString(name, diff2);
			}
			String content = String.format(formatStr, name, ctx);
			latests.add(new LatestModel(id, content, time, "demand"));
		}
		return latests;
	}

	/** 查询最新注册的团体信息 */
	private List<LatestModel> queryLatestGroupInfo() {
		List<LatestModel> latests = new ArrayList<LatestModel>();
		Date currentTime = new Date();

		List<GroupInfo> ginfos = latestReleaseDao.selectLatestGroupInfo();
		for (GroupInfo ginfo : ginfos) {
			Date time = ginfo.getCreateTime();
			int id = ginfo.getId();

			int typeId = ginfo.getUser().getTypeId();
			String name = "";
			if (typeId == 1) {
				name = ginfo.getUserInfo().getNickName();
			} else {
				name = ginfo.getEnterpriseInfo().getEnterpriseName();
			}
			String gname = ginfo.getGroupName();
			String ago = Utils.getDateToDateAgo(time, currentTime, false, true, MAX_HOUR);
			String formatStr = "[" + ago + "] %s发布了新团体%s";
			int length = formatStr.length() - 4;// -4 是为了减去%s的位置
			if (length + gname.length() + name.length() > MAX_LENGTH) {
				int maxLength = MAX_LENGTH - length;
				int length1 = gname.length();
				int length2 = name.length();
				int diff = maxLength / 2;
				int diff1 = diff, diff2 = diff;
				if (length1 < diff) {
					diff2 += (diff - length1);

				} else if (length2 < diff) {
					diff1 += (diff - length2);
				}
				gname = Utils.overflowHiddenMiddleString(gname, diff1);
				name = Utils.overflowHiddenMiddleString(name, diff2);
			}
			String content = String.format(formatStr, name, gname);
			latests.add(new LatestModel(id, content, time, "group"));
		}
		return latests;
	}

	/** 查询最新注册的企业信息 */
	private List<LatestModel> queryLatestEnterpriseInfo() {
		List<LatestModel> latests = new ArrayList<LatestModel>();
		Date currentTime = new Date();

		List<EnterpriseInfo> einfos = latestReleaseDao.selectLatestEnterpriseInfo();
		for (EnterpriseInfo einfo : einfos) {
			Date time = einfo.getUser().getCreateTime();
			int id = einfo.getUser().getId();
			String name = einfo.getEnterpriseName();
			String ago = Utils.getDateToDateAgo(time, currentTime, false, true, MAX_HOUR);
			String formatStr = "[" + ago + "] 欢迎新企业%s注册了";
			int length = formatStr.length() - 2;// -2 是为了减去%s的位置
			if (formatStr.length() + name.length() > MAX_LENGTH) {
				int maxLength = MAX_LENGTH - length;
				name = Utils.overflowHiddenMiddleString(name, maxLength);
			}
			String content = String.format(formatStr, name);
			latests.add(new LatestModel(id, content, time, "enterprise"));
		}
		return latests;
	}

	/** 查询最新注册的个人信息 */
	private List<LatestModel> queryLatestUserInfo() {
		List<LatestModel> latests = new ArrayList<LatestModel>();
		List<UserInfo> uinfos = latestReleaseDao.selectLatestUserInfo();
		Date currentTime = new Date();
		for (UserInfo uinfo : uinfos) {
			Date time = uinfo.getUser().getCreateTime();
			String name = uinfo.getNickName();
			String ago = Utils.getDateToDateAgo(time, currentTime, false, true, MAX_HOUR);

			String formatStr = "[" + ago + "] 欢迎新用户%s注册了";
			int length = formatStr.length() - 2;// -2 是为了减去%s的位置
			if (formatStr.length() + name.length() > MAX_LENGTH) {
				int maxLength = MAX_LENGTH - length;
				name = Utils.overflowHiddenMiddleString(name, maxLength);
			}
			String content = String.format(formatStr, name);
			latests.add(new LatestModel(uinfo.getUser().getId(), content, time, "personal"));
		}
		return latests;
	}

}
