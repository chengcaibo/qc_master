package com.qichong.service;

import static com.qichong.util.web.ServletUtil.isEmpty;
import static com.qichong.util.web.ServletUtil.queryAllChildCodes;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.qichong.dao.DemandInfoDao;
import com.qichong.entity.DemandInfo;
import com.qichong.entity.State;
import com.qichong.enums.PathEnum;
import com.qichong.enums.RetEnum;
import com.qichong.enums.StateEnum;
import com.qichong.model.DemandInfoModel;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.util.OSSUtil;
import com.qichong.util.Utils;
import com.qichong.util.web.ServletUtil;

/**
 * 需求（demand）业务逻辑层
 * 
 * @创建人 吴志伟
 * @修改人 孙建雷
 * @修改时间 2018年4月21日
 */
@Service
public class DemandInfoService {

	@Autowired
	DemandInfoDao dao;

	@Autowired
	SharedTypeService sharedTypeService;

	private static OSSUtil ossUtil = new OSSUtil(PathEnum.DEMANDINFO_PICTURE);

	/** 需求重新开启 */
	@Transactional
	public JSONResult demandRestart(int demandId, int userId, Date endTime) {
		// 首先查询出需求详情
		DemandInfo dinfo = this.queryOneByDemandId(demandId);
		if (!dinfo.getUser().getId().equals(userId)) {
			return JSONResult.error("只能重新开启你自己发布的需求");

		} else if (!(dinfo.getState().getId().equals(StateEnum.DEMAND_EXPIRED.getId())
				|| dinfo.getState().getId().equals(StateEnum.DEMAND_CLOSED.getId()))) {
			return JSONResult.error("当前状态的需求不能重新开启");

		} else {
			DemandInfo updateInfo = new DemandInfo(demandId);
			updateInfo.setEndTime(endTime);
			updateInfo.setState(new State(StateEnum.DEMAND_NORMAL));
			dao.updateOne(updateInfo);
			return JSONResult.success();
		}
	}

	/** 需求续期 */
	@Transactional
	public JSONResult demandRenewal(int demandId, int userId) {
		// 首先查询出需求详情
		DemandInfo dinfo = this.queryOneByDemandId(demandId);

		if (!dinfo.getUser().getId().equals(userId)) {
			return JSONResult.error("只能续期你自己发布的需求");
		} else if (!dinfo.getState().getId().equals(StateEnum.DEMAND_NORMAL.getId())) {
			return JSONResult.error("当前状态的需求不能进行续期操作");
		} else {
			Date newEndTime = Utils.calcDate(dinfo.getEndTime(), "+", 7, "天");
			// 判断是否超过一年
			long maxTime = Utils.calcDate(new Date(), "+", 1, "年").getTime();
			if (newEndTime.getTime() > maxTime) {
				return JSONResult.error("已达到可续期的最大值");
			}
			DemandInfo updateInfo = new DemandInfo(demandId);
			updateInfo.setEndTime(newEndTime);
			dao.updateOne(updateInfo);
			return JSONResult.success();
		}
	}

	/** 搜索多条记录 */
	private List<DemandInfo> searchList(Filters filters) {
		// 将搜索某项的所有子级
		filters.setSharedCodes(queryAllChildCodes(filters.getSharedCode(), sharedTypeService));
		return dao.selectAllByFilter(filters);
	}

	/** 搜索一条记录 */
	private DemandInfo searchOne(Filters filters) {
		List<DemandInfo> list = this.searchList(filters);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	/** 搜索 */
	public List<DemandInfo> search(Filters filters) {
		// 构造Filter
		return this.searchList(filters);
	}

	/** 搜索总结果数 */
	public int searchTotal(Filters filters) {
		return dao.selectAllByFilterTotal(filters);
	}

	/** 根据需求ID查询单个需求 */
	public DemandInfo queryOneByDemandId(int demandId) {
		return this.queryOneByDemandId(demandId, null);
	}

	/** 根据需求ID查询单个需求 */
	public DemandInfo queryOneByDemandId(int demandId, Integer fieldId) {
		// 构造Filter
		Filters filters = Filters.builder().setDemandId(demandId).setFieldId(fieldId);
		// 执行查询
		return this.searchOne(filters);
	}

	/** 根据用户ID查询需求 */
	public List<DemandInfo> queryListByUserId(Filters filters) {
		// 执行查询
		return this.searchList(filters);
	}

	/** 删除需求 */
	public JSONResult deleteDemand(Integer id, String pictureName, int loginUserId) {
		JSONResult jr = new JSONResult();
		if (id == null || ServletUtil.isEmpty(pictureName)) {
			jr.setValue(RetEnum.PARAM_LACK, "缺少参数");
		} else {
			// 删除阿里云OSS上的图片
			String fileName = Utils.getFileName(pictureName);
			if (fileName.indexOf("default-demand") == -1)
				ossUtil.deleteOneFile(fileName);
			int result = dao.deleteOne(id, loginUserId);
			if (result > 0) {
				jr.setValue(RetEnum.SUCCESS);
			} else {
				jr.setValue(RetEnum.AUTH_ERROR, "删除失败：没有权限");
			}
		}
		return jr;
	}

	/** 查询最新注册的三个人发布的需求 */
	public List<DemandInfoModel> demandPreferred() {
		return dao.demandPreferred();
	}

	/**
	 * 发布需求
	 * 
	 * @param demandInfoFile
	 *            封面图片
	 * @param demandInfo
	 *            待发布的需求信息
	 * @throws Exception
	 */
	public JSONResult submitDemand(MultipartFile coverFile, DemandInfo demandInfo) {
		return submitDemand(coverFile, demandInfo, "WX_MINI");
	}

	public JSONResult submitDemand(MultipartFile coverFile, DemandInfo demandInfo, String fromClient) {
		JSONResult jr = new JSONResult();
		// 判断参数是否合法
		int errorCount = isEmpty(demandInfo.getAddress(), demandInfo.getContent(), demandInfo.getPhone(),
				demandInfo.getContact());

		// 判断请求是否来自教练吧
		boolean uploadToOss = !String.valueOf(fromClient).equals("JIAO_LIAN_BA");

		if (uploadToOss) {
			errorCount += coverFile == null || coverFile.isEmpty() ? 1 : 0;
		}
		// 判断参数是否合法
		if (errorCount > 0 || demandInfo.getReward() == null) {
			jr.setValue(RetEnum.PARAM_LACK);
		} else {
			try {
				String name = demandInfo.getUser().getId() + Utils.randomString(32).toLowerCase() + ".jpg";
				if (uploadToOss)// 上传图片到阿里云OSS
					ossUtil.uploadFile2OSS(coverFile.getInputStream(), name);
				else
					name = "normal/default-02.jpg?1";// 教练吧默认图片
				demandInfo.setPicture(name + "?" + Utils.randomNumber(6));
				// set默认值
				if (isEmpty(demandInfo.getNote()))
					demandInfo.setNote("无备注");
				demandInfo.setReleaseTime(new Date());
				// 判断是否有过期时间，如果没有则自动填写一年后
				if (demandInfo.getEndTime() == null) {
					Date endTimeCalc = Utils.calcDate(new Date(), "+", 1, "年");
					try {
						String endTimeStr = Utils.formatDate(endTimeCalc, "yyyy-MM-dd HH:mm") + ":00";
						demandInfo.setEndTime(Utils.parseDate(endTimeStr));
					} catch (ParseException e) {
						demandInfo.setEndTime(endTimeCalc);
					}
				}
				// 执行SQL语句
				dao.insertOne(demandInfo);
				jr.setValue(RetEnum.SUCCESS);
			} catch (IOException e) {
				e.printStackTrace();
				jr.setValue(RetEnum.EXCEPTION, e.getMessage());
			}
		}
		return jr;
	}

	/** 修改需求 */
	public JSONResult updateDemandInfo(MultipartFile coverFile, DemandInfo demandInfo) {
		return updateDemandInfo(coverFile, demandInfo, "WX_MINI");
	}

	/** 修改需求，有来自客户端 */
	public JSONResult updateDemandInfo(MultipartFile coverFile, DemandInfo demandInfo, String fromClient) {
		JSONResult jr = new JSONResult();
		// 判断参数是否合法
		int errorCount = isEmpty(demandInfo.getAddress(), demandInfo.getContent(), demandInfo.getPhone(),
				demandInfo.getContact(), demandInfo.getPicture().getName());
		if (errorCount > 0 || demandInfo.getReward() == null) {
			jr.setValue(RetEnum.PARAM_LACK);
		} else {
			try {
				if (coverFile != null && !coverFile.isEmpty()) {
					// 修改图片到阿里云OSS
					String name = demandInfo.getPicture().getName();
					int indexOf = name.indexOf("?");
					name = name.substring(0, indexOf == -1 ? name.length() : indexOf);
					ossUtil.uploadFile2OSS(coverFile.getInputStream(), name);
					demandInfo.setPicture(name + "?" + Utils.randomNumber(6));
				}
				// 存入数据库
				if (isEmpty(demandInfo.getNote()))
					demandInfo.setNote("无备注");
				demandInfo.setReleaseTime(new Date());
				// 执行SQL语句
				dao.updateOne(demandInfo);
				jr.setValue(RetEnum.SUCCESS);
			} catch (IOException e) {
				e.printStackTrace();
				jr.setValue(RetEnum.EXCEPTION, e.getMessage());
			}
		}
		return jr;
	}

	/**
	 * 后台修改需求的状态
	 * 
	 * @param coverFile
	 * @param demandInfo
	 * @return
	 */
	public JSONResult updateDemandState(DemandInfo demandInfo) {
		JSONResult jr = new JSONResult();
		// 判断参数是否合法
		if (demandInfo.getId() == 0 || demandInfo.getState().getId() == 1) {
			jr.setValue(RetEnum.PARAM_LACK);
		} else {
			try {
				// 存入数据库
				// 执行SQL语句
				dao.updateOne(demandInfo);
				jr.setValue(RetEnum.SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				jr.setValue(RetEnum.EXCEPTION, e.getMessage());
			}
		}
		return jr;
	}

	/** 查询Id最大的五条 */
	public List<DemandInfoModel> queryNew(int limit, int offset, String city) {
		return dao.maxFive(limit, offset, city);
	}

}
